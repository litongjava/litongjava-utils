# litongjava-utils
java开发常用工具类和实体类封装
开源地址  

开源地址  
[https://github.com/litongjava/litongjava-utils](https://github.com/litongjava/litongjava-utils)  
[https://gitee.com/ppnt/litongjava-utils](https://gitee.com/ppnt/litongjava-utils)

## 使用步骤

### 添加依赖
```
<dependency>
  <groupId>com.litongjava</groupId>
  <artifactId>litongjava-utils</artifactId>
  <version>1.1.0</version>
</dependency>
```
默认使用的日志是sfl4j+logback,如果日志框架冲突可以使用下面的配置排除logback
```
<dependency>
  <groupId>com.litongjava</groupId>
  <artifactId>litongjava-utils</artifactId>
  <version>1.0.9</version>
  <exclusions>
    <exclusion>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

### 使用工具类  
工具类都在com.litongjava.utils下,使用比较简单,基本上看一下就会使用  
那个工具类不会用可提issue,我会解答  
下面列出一些工具类的使用方法
#### ClassPathUtils
```
URL resource = new ClassPathResource(filepath).getResource();
```
#### ClassPathResource
```
URL url = ClassPathUtils.getResource("timg.jpg");
```
#### ExcelUtils
使用ExcelUtils工具类导出Excel表格  
完整代码示例如下
```
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.litongjava.easyexcel.pojo.Student;
import com.litongjava.utils.excel.ExcelUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("excel")
public class ExcelController {

  @RequestMapping("export")
  public void export(HttpServletResponse response) {
    // 模拟用户查询数据
    List<Student> data = new ArrayList<>();
    data.add(new Student(1, "张1", 11.1, new Date()));
    data.add(new Student(2, "张2", 11.2, new Date()));
    data.add(new Student(3, "张3", 11.3, new Date()));
    data.add(new Student(4, "张4", 11.4, new Date()));
    data.add(new Student(5, "张5", 11.5, new Date()));

    String filename = "用户管理";
    String sheetName = "用户管理";
    ExcelUtils.export(response, filename, sheetName, data, Student.class);
  }
}

```
### PoiExcelUtils
使用示例读取文件为 List<Map<String,Object>>
```
List<Map<String, Object>> listMap = PoiExcelUtils.readExcel(filepath, 0);
```
完整代码示例如下  
excel文件  
|id																|appid		|apisecret												|apikey														|
|12d28291e4314d48bcdd8a7566ba5337	|qwdfrugi	|d8117b01d8e64fe5afffffffffffffff	|3718fffffffffffffffffffffffffff	|

读取代码
```
package top.ppnt.arcsoft.face;

import java.util.List;
import java.util.Map;

import com.litongjava.utils.excel.PoiExcelUtils;

public class AppidAndSdkKey {
  public static void main(String[] args) {
    String appId = null;
    String sdkKey = null;
    String docPath = "D:\\document\\secret\\appkey-and-appscret.xls";
    List<Map<String, Object>> listMap = PoiExcelUtils.readExcel(docPath, 0);
    for (Map<String, Object> map : listMap) {
      if ("12d28291e4314d48bcdd8a7566ba5337".equals(map.get("id"))) {
        appId = (String) map.get("appid");
        sdkKey = (String) map.get("apisecret");
      }
    }
    System.out.println(appId + " " + sdkKey);
  }
}

```

HttpUploadUtils
```
import org.junit.Test;

import com.litongjava.utils.httpclient.HttpUploadUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUploadUtilsTest {

  @Test
  public void testUpload() {
    //上传地址
    String uploadURL = "https://www.yozodcs.com/fcscloud/file/upload";
    //上传文件
    String localFilePath = "ppnt-yz-office-agent\\data\\upload\\2022\\8\\28\\22\\键鼠合一.doc";
    //上传文件
    StringBuilder string = HttpUploadUtils.uploadFile(uploadURL, localFilePath, "file");
    log.info("{}",string);
  }
}
```