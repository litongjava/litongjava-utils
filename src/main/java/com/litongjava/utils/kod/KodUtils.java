package com.litongjava.utils.kod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.litongjava.utils.digest.SHA1Utils;
import com.litongjava.utils.http.FileUploadUtil;
import com.litongjava.utils.http.litonghttpclient.HttpClientUtils;
import com.litongjava.utils.io.IOUtils;
import com.litongjava.utils.url.URLUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author litong
 * @version 1.0
 * @date 2019年2月16日_下午6:17:03
 * @see 02.可道云API.docx kod工具类
 */
@Slf4j
public class KodUtils {

  /* 保存以获取的accessToken */
  private static Map<String, AccessTokenCache> accessTokenMap = new HashMap<>();
  /* 保存分享成功的sid信息 */
  private static Map<String, SidCache> sidCacheMap = new HashMap<>();

  public static Map<String, SidCache> getSidCacheMap() {
    return sidCacheMap;
  }

  /**
   * 获取 access_token
   *
   * @return
   */
  public static String getAccessToken(String serverURL, String username, String apiLoginTonken) {
    String retval = null;
    AccessTokenCache cache = accessTokenMap.get(serverURL);
    if (cache == null || cache.isExpire()) { // 如果accessToken已经过期,重新请求
      // 1.请求 获取accessToekn
      String loginToken = getLoginToken(username, apiLoginTonken);
      StringBuffer link = URLUtil.append(serverURL, "?user/accessToken");
      Map<String, Object> params = new HashMap<>();
      params.put("link", link.toString());
      params.put("login_token", loginToken);
      serverURL = URLUtil.append(serverURL, "?user/loginSubmit").toString();
      String jsonString = HttpClientUtils.get(serverURL, params);
      // 2.解析返回的JSON
      JSONObject jsonObject = JSON.parseObject(jsonString);
      Boolean code = jsonObject.getBoolean("code");
      if (code) {
        retval = jsonObject.getString("data");
      } else {
        retval = jsonString;
      }
      // 3.将获取到的accessToken存入缓存
      cache = new AccessTokenCache();
      cache.setAccessToken(retval);
      cache.setExpireTime(System.currentTimeMillis() + 86400000); // 默认24小时后过期
    } else { // 如果accessToke没有过期
      retval = cache.getAccessToken();
    }
    return retval;
  }

  /**
   * 获取loginToken
   *
   * @param username
   * @param apiLoginTonken
   * @return
   */
  public static String getLoginToken(String username, String apiLoginTonken) {
    byte[] userBytes = username.getBytes();
    String encodeBase64String = Base64.encodeBase64String(userBytes);
    String md5Hex = DigestUtils.md5Hex(username + apiLoginTonken);
    String retval = encodeBase64String + '|' + md5Hex;
    return retval;
  }

  /**
   * 获取文件列表
   */
  public static String pathList(String serverURL, String username, String apiLoginTonken, String path) {
    String retval = null;
    String accessToken = getAccessToken(serverURL, username, apiLoginTonken);
    StringBuffer append = URLUtil.append(serverURL, "?explorer/pathList");
    Map<String, Object> params = new HashMap<>();
    params.put("accessToken", accessToken);
    params.put("path", path);
    retval = HttpClientUtils.get(append.toString(), params);
    return retval;
  }

  public static String upload(String serverURL, String username, String apiLoginToken, String uploadTo, FileInputStream fis, String filePath) {
    String accessToken = getAccessToken(serverURL, username, apiLoginToken);
    StringBuffer append = URLUtil.append(serverURL, "?explorer/fileUpload");
    Map<String, Object> params = new HashMap<>();
    params.put("accessToken", accessToken);
    params.put("upload_to", uploadTo);
    // 请求地址中的请求参数
    StringBuffer queryParams = HttpClientUtils.buildHttpQueryParams(params);
    // 完整的请求地址
    String url = append.toString() + queryParams.toString();

    StringBuilder stringBuilder = FileUploadUtil.uploadFile(url, fis, "file", filePath);
    return stringBuilder.toString();
  }

  /**
   * 上传文件,会自动关流
   *
   * @param serverURL
   *          eg:http://zhishi.uairobot.com
   * @param username
   *          eg:admin
   * @param apiLoginToken
   *          eg:admin
   * @param uploadTo
   *          /var/www/html/data/User/admin/home/广西电网/
   * @param file
   *          D:\ibot-robot-ui-lxqzw\download_dir\河北师范大学专业设置一览表.docx
   * @return
   */
  public static String upload(String serverURL, String username, String apiLoginToken, String uploadTo, File file) {
    String path = file.getAbsolutePath();
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      return upload(serverURL, username, apiLoginToken, uploadTo, fis, path);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(fis);
    }
    return null;
  }

  /**
   * 上传文件
   */
  public static String upload(String serverURL, String username, String apiLoginToken, String uploadTo, String localFilepath) {
    File localFile = new File(localFilepath);
    String upload = upload(serverURL, username, apiLoginToken, uploadTo, localFile);
    return upload;
  }

  /**
   * 设置分享文件
   */
  public static String setUserShare(String serverURL, String username, String apiLoginToken, String fileType, String remoteFilePath) {
    String accessToken = getAccessToken(serverURL, username, apiLoginToken);
    String name = FilenameUtils.getName(remoteFilePath);
    Map<String, Object> params = new HashMap<>();
    StringBuffer append = URLUtil.append(serverURL, "?userShare/set");
    params.put("accessToken", accessToken);
    params.put("type", fileType);
    params.put("path", remoteFilePath);
    params.put("name", name);
    String retval = HttpClientUtils.post(append.toString(), null, params);
    return retval;
  }

  /**
   * 获取分享文件列表 正常的文件分享链接如下
   * 
   */
  public static String getUserShare(String serverURL, String username, String apiLoginTonken) {
    /*
     * serverURL eg
     * http://zhishi.uairobot.com/index.php?share/file&user=1&sid=TpGnCQ9X
     */
    String retval = null;
    StringBuffer append = URLUtil.append(serverURL, "?userShare/get");
    String accessToken = getAccessToken(serverURL, username, apiLoginTonken);
    Map<String, Object> params = new HashMap<>();
    params.put("accessToken", accessToken);
    retval = HttpClientUtils.get(append.toString(), params);
    return retval;
  }

  /**
   * 上传并分享文件,返回文件分享完整链接
   *
   * @param serverURL
   *          eg http://zhishi.uairobot.com
   * @param username
   *          eg admin
   * @param apiLoginTonken
   *          eg litong
   * @param uploadTo
   *          eg /var/www/html/data/User/admin/home/äºä¿¡å±ä¼/
   * @param localFile
   *          eg
   *          D:\dev_workspace\java\hg_project\.metadata\.plugins\org.eclipse.
   *          wst.server.core\tmp3\wtpwebapps\ibot-robot-ui-lxqzw\download_dir\
   *          河北师范大学2018年招生章程.docx
   * @return
   */
  public static String uploadAndShare(String serverURL, String username, String apiLoginTonken, String uploadTo, File localFile) {
    // 查询本地缓存,如果查询到则返回
    String sha1 = SHA1Utils.getSha1(localFile);
    SidCache sidCache = sidCacheMap.get(sha1);
    if (sidCache != null) {
      // 以获取到可道云上的分享链接,判断可道云的文件是否存在,如果不存在则重新上传
      String pathInfoResult = pathInfo(serverURL, username, apiLoginTonken, "file", sidCache.getRemoteAbsPath());
      log.info("获取到的远程文件属性:" + pathInfoResult);
      JSONObject jsonObject = JSON.parseObject(pathInfoResult);
      boolean code = jsonObject.getBooleanValue("code");
      if (code) { // 如果文件存在
        JSONObject dataObject = jsonObject.getJSONObject("data");
        Boolean isReadable = dataObject.getBoolean("isReadable");
        if (isReadable) {
          log.info("远程文件存在,返回缓存的上传地址");
          String downloadURL = getDownloadURL(serverURL, sidCache.getSid());
          return downloadURL;
        }
      }
      log.info("远程远程已经不存在,重新上传");
      // 如果文件不存在做执行下面的代码
    }
    String uploadReturnJSONString = upload(serverURL, username, apiLoginTonken, uploadTo, localFile); // 上传文件
    Boolean code = false;
    JSONObject jsonObject = null;
    try {
      jsonObject = JSON.parseObject(uploadReturnJSONString);
      code = jsonObject.getBoolean("code");
    } catch (Exception e) {
      // log.error("解析错误:"+jsonString);
      e.printStackTrace();
    }

    if (!code) {
      System.out.println("KodUtil.uploadAndShare()");
      System.out.println("文件上传失败:" + uploadReturnJSONString);
      System.out.println("开始创建文件夹");
      // 创建远程文件,在上传一次
      @SuppressWarnings("unused")
      String mkdirResult = mkdir(serverURL, username, apiLoginTonken, uploadTo);
      uploadReturnJSONString = upload(serverURL, username, apiLoginTonken, uploadTo, localFile); // 上传文件
      // 将第二次的上传结果解析为json对象
      try {
        jsonObject = JSON.parseObject(uploadReturnJSONString);
        code = jsonObject.getBoolean("code");
      } catch (Exception e) {
        // log.error("解析错误:"+jsonString);
        e.printStackTrace();
      }
    }

    String fullPath = null;
    // 获取文件全路径
    if (code) {
      fullPath = jsonObject.getString("info");
    } else {
      System.out.println("KodUtil.uploadAndShare()");
      System.out.println("文件上传失败:" + uploadReturnJSONString);
    }
    // 设置文件分享
    String fileType = getFileType(localFile);
    String userSharRetrunJSONstring = KodUtils.setUserShare(serverURL, username, apiLoginTonken, fileType, fullPath);
    try {
      jsonObject = JSON.parseObject(userSharRetrunJSONstring);
      code = jsonObject.getBoolean("code");
    } catch (Exception e) {
      System.out.println("解析错误:" + userSharRetrunJSONstring);
      e.printStackTrace();
    }
    String sid = null;
    if (code) { // 分享成功,取出sio
      sid = jsonObject.getJSONObject("data").getString("sid");
      String remoteFileFullPath = jsonObject.getJSONObject("data").getString("path"); // 获取远程文件全路径
      // 7.添加到缓存中
      sidCache = new SidCache(sha1, localFile.getAbsolutePath().toString(), sid, remoteFileFullPath);
      sidCacheMap.put(sha1, sidCache);
    } else {
      System.out.println("文件分享失败:" + userSharRetrunJSONstring);
    }
    String downloadURL = getDownloadURL(serverURL, sid);
    return downloadURL;

  }

  /**
   * 创建文件
   */
  public static String mkdir(String serverURL, String username, String apiLoginTonken, String uploadTo) {
    String accessToken = getAccessToken(serverURL, username, apiLoginTonken);
    StringBuffer url = URLUtil.append(serverURL, "?explorer/mkdir");
    Map<String, Object> params = new HashMap<>();
    params.put("accessToken", accessToken);
    params.put("path", uploadTo);
    String post = HttpClientUtils.post(url.toString(), null, params);
    JSONObject jsonObject = JSON.parseObject(post);
    Boolean code = jsonObject.getBoolean("code");
    if (!code) {
      System.out.println("KodUtil.mkdir()");
      System.out.println("文件夹创建失败:" + post);
    }
    return post;

  }

  /**
   * 返回文件分享的完整链接
   *
   * @param serverURL
   * @param sid
   * @return
   */
  public static String getDownloadURL(String serverURL, String sid) {
    StringBuffer append = URLUtil.append(serverURL, "index.php?share/file&user=1&sid=" + sid);
    return append.toString();
  }

  /**
   * 返回文件类型
   *
   * @param localFile
   * @return
   */
  private static String getFileType(File localFile) {
    if (localFile.isFile()) {
      return "file";
    } else if (localFile.isDirectory()) {
      return "folder";
    }
    return null;
  }

  /**
   * 获取文件数据属性
   *
   * @param serverURL
   * @param username
   * @param apiLoginTonken
   * @param fileType
   * @param absPath
   * @return
   */
  public static String pathInfo(String serverURL, String username, String apiLoginTonken, String fileType, String absPath) {
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("type", fileType);
    jsonObject.put("path", absPath);
    jsonArray.add(jsonObject);
    return pathInfo(serverURL, username, apiLoginTonken, jsonArray);
  }

  /**
   * 获取多个文件属性
   *
   * @param serverURL
   * @param username
   * @param apiLoginTonken
   * @param jsonArray
   * @return
   */
  private static String pathInfo(String serverURL, String username, String apiLoginTonken, JSONArray jsonArray) {
    String accessToken = getAccessToken(serverURL, username, apiLoginTonken);
    StringBuffer url = URLUtil.append(serverURL, "?explorer/pathInfo");
    Map<String, Object> params = new HashMap<>();
    params.put("accessToken", accessToken);
    params.put("dataArr", jsonArray.toJSONString());
    String post = HttpClientUtils.post(url.toString(), null, params);
    JSONObject jsonObject = JSON.parseObject(post);
    Boolean code = jsonObject.getBoolean("code");
    if (!code) {
      System.out.println("KodUtil.pathInfo()" + "获取文件属性失败:" + post);
    }
    return post;
  }
}