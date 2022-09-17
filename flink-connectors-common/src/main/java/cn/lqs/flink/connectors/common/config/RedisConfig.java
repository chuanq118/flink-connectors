package cn.lqs.flink.connectors.common.config;

import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;

/**
 * 2022/9/17 13:17
 * created by @lqs
 */
public class RedisConfig {

    public static String host = "localhost";
    public static int port = 6379;

    public static String password = "lqs.cn";

    public static FlinkJedisPoolConfig redisCfg = new FlinkJedisPoolConfig.Builder()
            .setHost(RedisConfig.host)
            .setPort(RedisConfig.port)
            .setPassword(RedisConfig.password)
            .build();
}
