package com.ximalaya.yan.lock.redisson;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author yan.gao
 * @date 2019/10/31 4:55 下午
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonClientConfig {

    private String host;
    private String port;
    private String password;
    private Integer database = 0;
    private Integer timeout = 500;
    private Jedis jedis;
    private Jedis.Pool pool;

    @Bean
    public RedissonClient redissonClient() {
//        int poolSize = 64;
//        if (jedis != null && jedis.pool != null && jedis.pool.minIdle != null) {
//            poolSize = jedis.pool.minIdle;
//        }
//        if (pool != null && pool.minIdle != null) {
//            poolSize = pool.minIdle;
//        }
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password)
                .setDatabase(database)
                .setConnectTimeout(timeout);
//                .setConnectionPoolSize(poolSize);
        //添加主从配置
        //config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"", ""});
        return Redisson.create(config);
    }

    @Data
    public static class Jedis {

        public Pool pool;

        @Data
        public static class Pool {
            private Integer maxActive = 300;
            private Integer minIdle = 100;
            private Integer maxIdle = 300;
            private Integer maxWait = 1000;
        }
    }
}
