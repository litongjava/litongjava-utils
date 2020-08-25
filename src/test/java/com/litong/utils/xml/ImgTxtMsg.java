package com.litong.utils.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @date 2019年1月23日_上午10:12:14 
 * @version 1.0 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgTxtMsg {
  private String title;
  private String description;
  private String picUrl;
  private String url;
}
