package com.sirding.javase.templatemethod;

/**
 * @Description   : 定义模板方法
 * @Project       : java-book
 * @Program Name  : com.sirding.javase.templatemethod.TemplateMothedI.java
 * @Author        : zhichaoding@hongkun.com zc.ding
 */
public interface TemplateMethodI<T> {

	T call(String param);
}
