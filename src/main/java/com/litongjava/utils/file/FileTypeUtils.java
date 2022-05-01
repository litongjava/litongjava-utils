package com.litongjava.utils.file;
import java.util.ArrayList;
import java.util.List;

public class FileTypeUtils {
  private static List<String> voideList = null;
  private static List<String> radioList = null;
  private static List<String> imageList=null;

  public static String getType(String extension) {
    if (voideList().contains(extension)) {
      return "video";
    }else if(radioList().contains(extension)) {
      return "radio";
    }else if(imageList().contains(extension)) {
      return "image";
    }
    return "other";
  }

  public static List<String> voideList() {
    if (voideList == null) {
      voideList = new ArrayList<>();
      voideList.add("mp4");
      voideList.add("avi");
      voideList.add("wmv");
    }
    return voideList;
  }

  public static List<String> radioList() {
    if (radioList == null) {
      radioList = new ArrayList<>();
      radioList.add("mp3");
      radioList.add("wav");
    }
    return radioList;
  }
  public static List<String> imageList(){
    if(imageList==null) {
      imageList=new ArrayList<>();
      imageList.add("jpg");
      imageList.add("png");
    }
    return imageList;
  }
}
