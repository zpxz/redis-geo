package com.fufu.entity.vo;

import java.math.BigDecimal;

/**
 *
 * @author fufu
 * @version 0.1.0
 * @date 2018-07-30
 */
public class TerminalPositionRspVo {

    private String imei;
    private Integer carType;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private double distance;

    @Override
    public String toString() {
        return "{imei = " + imei +
                ", carType = " + carType +
                ", longitude = " + longitude +
                ", latitude = " + latitude +
                ", distance = " + distance +
                "}";
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

    public void setCarType(String carType) {
        this.carType = Integer.valueOf(carType);
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = new BigDecimal(String.valueOf(longitude));
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = new BigDecimal(String.valueOf(latitude));
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
