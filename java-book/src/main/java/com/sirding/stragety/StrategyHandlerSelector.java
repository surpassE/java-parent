package com.sirding.stragety;

import java.lang.annotation.*;

/**
 * 策略选择器
 * @author dingzhichao3
 * @date 2021-04-02 10:12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrategyHandlerSelector {
    /** 键值 **/
    String key();
    /** 业务分类 **/
    String type();
}