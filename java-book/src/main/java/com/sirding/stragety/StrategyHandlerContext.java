package com.sirding.stragety;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 策略处理器上下文对象
 * @author dingzhichao3
 * @date 2021-04-02 10:16
 */
@Component
@Slf4j
public class StrategyHandlerContext {
    /**
     * 策略处理器集合
     */
    private Map<String, StrategyHandler> map = new HashMap<>(200);

    public StrategyHandlerContext(List<StrategyHandler> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(handler -> {
                StrategyHandlerSelector selector = AnnotationUtils.getAnnotation(handler.getClass(), StrategyHandlerSelector.class);
                Objects.requireNonNull(selector);
                // type:key作为map的key值
                Arrays.stream(selector.key().split(",")).map(String::trim).forEach(item ->{
                    String key = this.getKey(selector.type(), item);
                    if (map.containsKey(key)) {
                        log.error("[策略上下文]同一个[{}]对应多个处理, 请处理", key);
                        throw new RuntimeException(String.format("[策略上下文]同一个[%s]对应多个处理, 请处理", key));
                    }
                    map.put(key, handler);
                });
            });
        }
    }

    @SuppressWarnings(value = {"unchecked"})
    public <E, R> R handler(String type, String key, E param) {
        String selectKey = this.getKey(type, key);
        StrategyHandler<E, R> handler = (StrategyHandler<E, R>)map.get(selectKey);
        if (handler == null) {
            log.info("[策略上下文]未找到[{}]对应的策略处理器", selectKey);
            throw new RuntimeException(String.format("[策略上下文]未找到[%s]对应的策略处理器", selectKey));
        }
        return handler.handler(param);
    }

    /**
     * 解析key
     */
    private String getKey(String type, String key) {
        return type + ":" + key;
    }
}
