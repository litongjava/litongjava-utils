package com.litongjava.utils.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * @author litong
 * @date 2019年4月24日_下午1:42:12
 * @version 1.0
 * @desc 将特定日志消息写入文件
 */
public class LogWriterUtil {
	// 日志文件的位置
	private static File logFile = null;
	// 缓存流
	private BufferedWriter bufWriter = null;
	// 本实例
	private static LogWriterUtil log;

	private LogWriterUtil() {
		// 如果不指定日志文件的位置,将日志文件放到放到当前工程下log文件夹下,文件名mylog01.log
		new LogWriterUtil("mylog.log");
	}

	/**
	 * 指定日志文件名,将日志文件classpath下的log目录
	 * 
	 * @param logFilename
	 */
	private LogWriterUtil(String logFilename) {
		// 添加后缀名
		if (!logFilename.endsWith(".log")) {
			logFilename += ".log";
		}

		// 在classpaht下创建logs文件夹
		URL resource = LogWriterUtil.class.getClassLoader().getResource("/");
		File logFolderFile = new File(resource.toString() + "logs");
		if (!logFolderFile.exists()) {
			logFolderFile.mkdirs();
		}
		// 补全 logFile
		logFile = new File(resource.getFile() + "logs" + File.separator + logFilename);
		System.out.println("自定义日志文件路径:" + logFile.getAbsolutePath());
	}

	/**
	 * @param logFilename
	 * @return
	 */
	public static LogWriterUtil getInstance(String logFilename) {
		if (log == null) {
			log = new LogWriterUtil(logFilename);
		}
		return log;
	}

	/*
	 * 将内容写入到文件
	 */
	public void info(String logContnet) {
		FileWriter writer = null;
		try {
			// 文件不存在会自动创建架
			writer = new FileWriter(logFile, true);
			bufWriter = new BufferedWriter(writer);
			bufWriter.write(getlinePre());
			bufWriter.write(logContnet + "");
			bufWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 时间+类名全称+方法名+行号
	 */
	@SuppressWarnings("deprecation")
  public String getlinePre() {
		StringBuffer strBuf = new StringBuffer();
		String localeString = new Date().toLocaleString();
		strBuf.append(localeString + ":");
		// 得到最终调用者的信息
		StackTraceElement[] ss = Thread.currentThread().getStackTrace();
		// 第四个元素是业务逻辑
		strBuf.append(ss[3].getClassName() + ":");
		strBuf.append(ss[3].getMethodName() + ":");
		strBuf.append(ss[3].getLineNumber() + ":\r\n");
		// strBuf.append(ss[ss.length-1].getClassName()+":");
		// strBuf.append(ss[ss.length-1].getMethodName()+":");
		// strBuf.append(ss[ss.length-1].getLineNumber()+":");
		return strBuf.toString();
	}

	/*
	 * 结束时,调用此方法,关闭流
	 */
	public void destory() {
		if (bufWriter != null) {
			try {
				bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}