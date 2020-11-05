package com.kxmall.market.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Administrator
 */
@EnableAsync
@MapperScan("com.kxmall.market.data")
@SpringBootApplication(scanBasePackages = {"com.kxmall.market"}, exclude = {RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
@EnableTransactionManagement
public class MarketAdminApplication {

    private static final Logger logger = LoggerFactory.getLogger(MarketAdminApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MarketAdminApplication.class, args);
        logger.info("[系统初始化完毕]");
    }

}
