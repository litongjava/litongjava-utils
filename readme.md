# litongjava-utils

java开发常用工具类和实体类  

## 使用步骤

添加依赖
```
<dependency>
  <groupId>com.litongjava</groupId>
  <artifactId>litongjava-utils</artifactId>
  <version>1.0.0</version>
</dependency>
```
默认使用的sfl4j+logback,可以排除logback
```
<dependency>
  <groupId>com.litongjava</groupId>
  <artifactId>litongjava-utils</artifactId>
  <version>1.0.0</version>
  <exclusions>
    <exclusion>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```
jave-all-deps 非常大,如果不需要也可以排除
```
<dependency>
  <groupId>com.litongjava</groupId>
  <artifactId>litongjava-utils</artifactId>
  <version>1.0.0</version>
  <exclusions>
    <exclusion>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
    </exclusion>
    <exclusion>
      <groupId>ws.schild</groupId>
      <artifactId>jave-all-deps</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

step4:使用工具类  
工具类都在com.litongjava.utils下,使用也和简单,那个工具列不会用可提issue,我会解答