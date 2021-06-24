package com.litongjava.utils.spring;
//package com.litong.utils.spring;
//
//import java.util.Properties;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.core.env.MutablePropertySources;
//import org.springframework.core.env.PropertySource;
//import org.springframework.core.env.PropertySources;
//import org.springframework.core.env.StandardEnvironment;
//
///*
// * SpringConfig工具类
// */
//public class SpringConfigTool implements ApplicationContextAware {
//  private static ApplicationContext context = null;
//  private static SpringConfigTool stools = null;
//
//  /**
//   * 单例
//   */
//  public synchronized static SpringConfigTool init() {
//    if (stools == null) {
//      stools = new SpringConfigTool();
//    }
//    return stools;
//  }
//
//  /**
//   * 设置applicationContext
//   */
//  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//    context = applicationContext;
//  }
//
//  public static ApplicationContext getApplicationContext() {
//    return context;
//  }
//
//  /**
//   * 获取bean
//   * 
//   * @param beanName
//   * @return
//   */
//  public synchronized static Object getBean(String beanName) {
//    return context.getBean(beanName);
//  }
//
//  /**
//   * 获取bean
//   * 
//   * @param beanName
//   * @param clazz
//   * @return
//   */
//  @SuppressWarnings("all")
//  public synchronized static <T> T getBean(Class<T> clazz) {
//    T t = context.getBean(clazz);
//    return t;
//  }
//
//  /**
//   * 获取所有beans
//   * 
//   * @return
//   * @throws Exception
//   */
//  public static String[] getBeanDefinitionNames() throws Exception {
//    if (context == null) {
//      throw new Exception("context is null");
//    } else {
//      String[] beanDefinitionNames = context.getBeanDefinitionNames();
//      return beanDefinitionNames;
//    }
//  }
//
//  /**
//   * @param key
//   * @return application.properties中的value
//   */
//  public static String getPropertiesValue(String key) {
//    if (context == null) {
//      // spring容器没有启动,直接返回null
//      return null;
//    }
//    Properties properties = getProperties();
//    // 获取配置的地址
//    String value = (String) properties.get(key);
//    return value;
//  }
//
//  /**
//   * @return application.properties
//   */
//  public static Properties getProperties() {
//    if (context == null) {
//      return null;
//    }
//    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholerConfigure = context
//        .getBean(PropertySourcesPlaceholderConfigurer.class);
//    // 获取应用 propertySources
//    PropertySources applied = propertySourcesPlaceholerConfigure.getAppliedPropertySources();
//    // 获取 environmentProperties
//    PropertySource<?> environmentProperties = applied.get("environmentProperties");
//    // 获取 environmentProperties的source
//    StandardEnvironment standrdEnvironment = (StandardEnvironment) environmentProperties.getSource();
//    // 获取 mutablePropertySources
//    MutablePropertySources mutablePropertySources = standrdEnvironment.getPropertySources();
//    // 获取 application.properties的属性
//    PropertySource<?> applicationProperties = mutablePropertySources
//        .get("applicationConfig: [classpath:/application.properties]");
//    // 获取 applicationProperties的sources
//    Properties properties = (Properties) applicationProperties.getSource();
//    return properties;
//  }
//}
