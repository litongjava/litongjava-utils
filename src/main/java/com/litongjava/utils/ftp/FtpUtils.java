package com.litongjava.utils.ftp;

import static org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litongjava.utils.file.ConfigUtils;
import com.litongjava.utils.file.FileUtils;


/**
 * Created by litong on 2018/4/12 0012.
 * 上传文件到ftp_server
 * 该工具类只支持连接一台ftp_server,连接在上传文件关闭后自动关闭，其它情况下不会关闭连接
 */

public class FtpUtils {
  private static String ftp_server = ConfigUtils.getValue("ftp_server");
  private static Integer ftp_port = Integer.valueOf(ConfigUtils.getValue("ftp_port"));
  private static String ftp_user = ConfigUtils.getValue("ftp_user");
  private static String ftp_password = ConfigUtils.getValue("ftp_password");
  private static FTPClient ftpClient = new FTPClient();
  private static Logger log = LoggerFactory.getLogger(FtpUtils.class);

  static {
    try {
      ftpClient.connect(ftp_server, ftp_port);
      ftpClient.login(ftp_user, ftp_password);
      ftpClient.setFileType(BINARY_FILE_TYPE);
      ftpClient.enterLocalPassiveMode();
      ftpClient.setControlEncoding("UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    int replyCode = ftpClient.getReplyCode();
    log.info("replyCode : " + replyCode);
    // 如果返回的状态吗 >=200 amd <300
    if (FTPReply.isPositiveCompletion(replyCode)) {
      log.info("connect to ftp success");
    }
  }

  /**
   * 连接到服务器
   */
  private static boolean connecte() {
    /*
     * 判断是否连接，只有当没有连接到ftp服务器时才连接到ftp服务器
     */
    boolean connected = ftpClient.isConnected();
    if (!connected) {
      try {
        ftpClient.connect(ftp_server, ftp_port);
        connected = ftpClient.login(ftp_user, ftp_password);
        ftpClient.setFileType(BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setControlEncoding("UTF-8");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return connected;
  }

  /**
   * 断开到服务器的连接
   */
  private static void disConnect() {
    boolean connected = ftpClient.isConnected();
    if (connected) {
      try {
        ftpClient.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 设置ftp的工作目录,不确定是否支持递归
   *
   * @param workDir
   */
  public static boolean setWorkDir(String workDir) {
    connecte();
    boolean retval = false;
    try {
      retval = ftpClient.changeWorkingDirectory(workDir);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return retval;
  }

  /**
   * 创建目录,不支持递归
   * @param folder eg."litong/scene1"
   *               如果litong文件夹存在则scene1创建成功，如果liotng文件夹不存在，则scene1创建失败
   *
   * @return
   */
  public static int mkdir(String folder) {
    connecte();
    int retval = 0;
    try {
      retval = ftpClient.mkd(folder);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return retval;
  }

  /**
   * 只上传文件，不确定连接到服务器是否成功,如果发生异常，关闭连接
   *
   * @param localFile
   * @return
   */
  private static boolean onlyUpload(String localFile) throws IOException {
    BufferedInputStream bufIns = null;
    boolean isUploadSuccess = false;

    try {
      FileInputStream ins = new FileInputStream(localFile);
      // localFile=new String(localFile.getBytes("UTF-8"),ftp_server_encode);
      bufIns = new BufferedInputStream(ins);
      isUploadSuccess = ftpClient.storeFile(FileUtils.getFileName(localFile), bufIns);
      if (isUploadSuccess) {
        log.info("file upload success : " + localFile);
      } else {
        log.info("file upload file :" + localFile);
      }
    } catch (FileNotFoundException e) {
      disConnect();
      e.printStackTrace();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return isUploadSuccess;
  }

  /**
   * 上传一个录音文件，返回上传是否成功
   *
   * @param localFile
   * @return
   */
  public static boolean upload(String localFile) {
    /*
     * connecte();如果已连接ftp服务器，会不进行连接，如果没有连接ftp服务器，会进行连接
     */
    connecte();
    boolean isUploadSuccess = false;
    try {
      isUploadSuccess = onlyUpload(localFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // 上传完后关闭连接
    disConnect();
    return isUploadSuccess;
  }

  /**
   * 上传多个文件到ftp_server
   *
   * @param localFiles
   * @return Map 泛型开始filename:String,isUploadSuucess:Boolean泛型结束
   */
  public static Map<String, Boolean> upload(List<String> localFiles) {
    Map<String, Boolean> retval = new HashMap<>();
    connecte();
    // 上传文件到服务器
    for (String localFile : localFiles) {
      boolean b = false;
      try {
        b = onlyUpload(localFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
      retval.put(localFile, b);
    }
    disConnect();
    return retval;
  }

  /**
   * 上传目录到ftp服务器,会创建文件夹
   * @param foler 目录名，支持相对路径和绝对路径
   * @return
   *
   */
  public static Map<String, Boolean> uploadFoler(String foler) {
    Map<String, Boolean> retval = new HashMap<>();
    String fileName = FileUtils.getFileName(foler);
    mkdir(fileName);
    setWorkDir(fileName);
    File[] files = new File(foler).listFiles();
    connecte();
    for (File file : files) {
      boolean b = false;
      try {
        b = onlyUpload(file.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      }
      retval.put(file.getAbsolutePath(), b);
    }
    disConnect();
    return retval;
  }
}
