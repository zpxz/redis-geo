package com.fufu.config;

import com.fufu.entity.vo.TerminalPositionReqVo;
import com.fufu.serializer.FastJsonRedisSerializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置
 * @author: fufu
 * @version: 0.1.0
 * @date: 2018-07-30
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWait;

    @Value("${spring.redis.pool.max-active}")
    private int maxTotal;

    @Value("${spring.redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.redis.pool.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);

        return jedisPoolConfig;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        /** SprintBoot1.5.9版本这么配置ok
         *  SpringBoot2.0.1版本这么配置会报如下异常
         *      java.lang.ClassNotFoundException: org.apache.commons.pool2.impl.GenericObjectPoolConfig
         *      java.lang.ClassNotFoundException: redis.clients.jedis.JedisPoolConfig
         *  解决方法：引入commons-pool2包和jedis包
         */
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();

        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setDatabase(database);

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, TerminalPositionReqVo> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, TerminalPositionReqVo> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用fastjson序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));

        return redisTemplate;
    }

}
