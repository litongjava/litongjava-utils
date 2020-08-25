package com.litong.utils.kod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by litong on 2019/2/18 0018.
 * 分享文件 缓存
 * @see 03.小哆客服后端编码记录.docx
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SidCache {
  private String sha1, localFilePath, sid, remoteAbsPath;
}
