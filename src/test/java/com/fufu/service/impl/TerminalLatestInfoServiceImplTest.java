package com.fufu.service.impl;

import com.fufu.entity.vo.TerminalPositionReqVo;
import com.fufu.service.TerminalLatestInfoService;
import com.fufu.util.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author fufu
 * @version 0.1.0
 * @date 2018-07-30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TerminalLatestInfoServiceImplTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TerminalLatestInfoService terminalLatestInfoService;

    @Before
    public void addPosition() {
        TerminalPositionReqVo terminalPositionReqVo1 = new TerminalPositionReqVo();
        terminalPositionReqVo1.setImei("111");
        terminalPositionReqVo1.setLongitude(new BigDecimal("116.111"));
        terminalPositionReqVo1.setLatitude(new BigDecimal("39.111"));
        terminalPositionReqVo1.setCarType(0);
        terminalLatestInfoService.setPosition(terminalPositionReqVo1);

        TerminalPositionReqVo terminalPositionReqVo2 = new TerminalPositionReqVo();
        terminalPositionReqVo2.setImei("222");
        terminalPositionReqVo2.setLongitude(new BigDecimal("116.222"));
        terminalPositionReqVo2.setLatitude(new BigDecimal("39.222"));
        terminalPositionReqVo2.setCarType(1);
        terminalLatestInfoService.setPosition(terminalPositionReqVo2);

        TerminalPositionReqVo terminalPositionReqVo3 = new TerminalPositionReqVo();
        terminalPositionReqVo3.setImei("333");
        terminalPositionReqVo3.setLongitude(new BigDecimal("116.333"));
        terminalPositionReqVo3.setLatitude(new BigDecimal("39.333"));
        terminalPositionReqVo3.setCarType(0);
        terminalLatestInfoService.setPosition(terminalPositionReqVo3);

        TerminalPositionReqVo terminalPositionReqVo4 = new TerminalPositionReqVo();
        terminalPositionReqVo4.setImei("444");
        terminalPositionReqVo4.setLongitude(new BigDecimal("116.444"));
        terminalPositionReqVo4.setLatitude(new BigDecimal("39.444"));
        terminalPositionReqVo4.setCarType(1);
        terminalLatestInfoService.setPosition(terminalPositionReqVo4);

        TerminalPositionReqVo terminalPositionReqVo5 = new TerminalPositionReqVo();
        terminalPositionReqVo5.setImei("111");
        terminalPositionReqVo5.setLongitude(new BigDecimal("116.555"));
        terminalPositionReqVo5.setLatitude(new BigDecimal("39.555"));
        terminalPositionReqVo5.setCarType(0);
        terminalLatestInfoService.setPosition(terminalPositionReqVo5);
    }


    @Test
    public void getTerminalByRange() throws Exception {
        BigDecimal longitude = new BigDecimal("116.111");
        BigDecimal latitude = new BigDecimal("39.111");
        int radius = 20 * 1000;

        terminalLatestInfoService.getTerminalByRange(longitude, latitude, radius);
    }

    @Test
    public void testStringSeparate() {
        String key = "111_0";
        int pos = key.indexOf(Constants.REDIS_MEMBER_SEPARATE);
        System.out.println("pos = " + pos);

        System.out.println("prefix = " + key.substring(0, pos));
        System.out.println("key = " + key);
        System.out.println("subfix = " + key.substring(pos + 1));
    }

    @Test
    public void testRedis() {
        stringRedisTemplate.opsForValue().set("sub_msg_123", "0", 3, TimeUnit.MINUTES);
        Long cnt = stringRedisTemplate.boundValueOps("sub_msg_123").increment(1);

        System.out.println("cnt = " + cnt);
    }

}
