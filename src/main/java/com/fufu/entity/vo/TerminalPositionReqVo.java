package com.fufu.entity.vo;

import com.fufu.util.Constants;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author fufu
 * @version 0.1.0
 * @date 2018-07-30
 */
public class TerminalPositionReqVo implements Serializable{

    private static final long serialVersionUID = -1L;

    private String imei;
    private Integer carType;
    private BigDecimal longitude;
    private BigDecimal latitude;

    public String generateRedisGeoMember() {
        return imei + Constants.REDIS_MEMBER_SEPARATE + carType;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

}
