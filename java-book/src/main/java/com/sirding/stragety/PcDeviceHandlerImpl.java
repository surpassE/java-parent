package com.sirding.stragety;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@StrategyHandlerSelector(key = "PC", type = "LOGIN")
public class PcDeviceHandlerImpl implements StrategyHandler<LoginInfo, LoginResult> {

    @Override
    public LoginResult handler(LoginInfo param ) {
        // 获取登录态，解析结果
        return null;
    }
}
