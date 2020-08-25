package com.litong.utils.xml;
/**
 * @author Administrator
 * @date 2019年1月23日_上午10:11:58 
 * @version 1.0 
 */

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articles {
  private List<ImgTxtMsg> items;
}
