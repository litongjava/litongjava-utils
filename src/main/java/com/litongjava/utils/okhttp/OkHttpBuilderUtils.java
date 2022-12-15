package com.litongjava.utils.okhttp;

import java.util.concurrent.TimeUnit;

/**
 * @author litongjava <litongjava@qq.com>
 *
 */
public class OkHttpBuilderUtils {

  public static okhttp3.OkHttpClient.Builder newBuilder(long timeout) {
    // clientBuilder
    okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();
    // 连接超时
    clientBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
    // 读取超时
    clientBuilder.readTimeout(timeout, TimeUnit.SECONDS);

    return clientBuilder;
  }

  public static okhttp3.OkHttpClient.Builder newBuilder(long connectTimeout,long readtimeout) {
    // clientBuilder
    okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();
    // 连接超时
    clientBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
    // 读取超时
    clientBuilder.readTimeout(readtimeout, TimeUnit.SECONDS);

    return clientBuilder;
  }
}
