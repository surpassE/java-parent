package com.sirding.stragety;

/**
 * 处理器定义
 * @author dingzhichao3
 * @date 2021-04-02 10:14
 */
public interface StrategyHandler<E, R> {
    /**
     * 处理器
     * @param param 请求参数
     * @return 返回结果
     */
    R handler(E param);
}
