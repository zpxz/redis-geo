package com.fufu.service.impl;

import com.fufu.entity.vo.TerminalPositionReqVo;
import com.fufu.entity.vo.TerminalPositionRspVo;
import com.fufu.service.TerminalLatestInfoService;
import com.fufu.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fufu
 * @version 0.1.0
 * @date 2018-07-30
 */
@Service
public class TerminalLatestInfoServiceImpl implements TerminalLatestInfoService{

    Logger logger = LoggerFactory.getLogger(TerminalLatestInfoServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void setPosition(TerminalPositionReqVo terminalPositionReqVo) {
        stringRedisTemplate.opsForGeo().geoAdd(Constants.REDIS_GEO_KEY, new Point(terminalPositionReqVo.getLongitude().doubleValue(),
                        terminalPositionReqVo.getLatitude().doubleValue()), terminalPositionReqVo.generateRedisGeoMember());
    }

    /**
     * 获取范围类的终端
     * @param longitude 经度
     * @param latitude 纬度
     * @param radius 半径，单位：米
     */
    @Override
    public List<TerminalPositionRspVo> getTerminalByRange(BigDecimal longitude, BigDecimal latitude, int radius) {
        // 设置geo查询参数
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
        // 查询返回结果包括距离和坐标
        geoRadiusCommandArgs.includeCoordinates().includeDistance();
        // 不排序
        // geoRadiusCommandArgs.sortAscending();
        // 不限制查询数量
        // geoRadiusCommandArgs.limit(10);
        GeoResults<RedisGeoCommands.GeoLocation<String>> radiusGeo = stringRedisTemplate.opsForGeo().geoRadius(Constants.REDIS_GEO_KEY,
                new Circle(new Point(longitude.doubleValue(), latitude.doubleValue()), new Distance(radius, RedisGeoCommands.DistanceUnit.METERS)), geoRadiusCommandArgs);

        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> results = radiusGeo.getContent();
        if (results.size() == 0 ) {
            return null;
        } else {
            List<TerminalPositionRspVo> terminalPositionRspVoList = new ArrayList<>();
            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
                TerminalPositionRspVo terminalPositionRspVo = new TerminalPositionRspVo();
                terminalPositionRspVo.setDistance(result.getDistance().getValue());

                RedisGeoCommands.GeoLocation<String> content = result.getContent();
                terminalPositionRspVo.setLongitude(content.getPoint().getX());
                terminalPositionRspVo.setLatitude(content.getPoint().getY());
                String key = content.getName();
                int pos = key.indexOf(Constants.REDIS_MEMBER_SEPARATE);
                terminalPositionRspVo.setImei(key.substring(0, pos));
                terminalPositionRspVo.setCarType(key.substring(pos + 1));

                logger.info("terminalPositionRspVo = {}", terminalPositionRspVo);
                terminalPositionRspVoList.add(terminalPositionRspVo);
            }

            return terminalPositionRspVoList;
        }
    }

}
