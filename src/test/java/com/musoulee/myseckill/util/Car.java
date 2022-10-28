package com.musoulee.myseckill.util;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/26 10:51
 */

@ValidSeatCount // 用在类上
public class Car {

    private String brand;
    private int passengers;
    private int seats;
    @NotNull // 用在字段属性上
    @Valid
    private Driver driver;
    // 用在容器的参数上
    private Map<@NotEmpty String, List<@NotNull String>> parts = new HashMap<>();

    public String getBrand() {
        return brand;
    }
    @NotNull // 用在getter方法上
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Map<String, List<String>> getParts() {
        return parts;
    }

    public void setParts(Map<String, List<String>> parts) {
        this.parts = parts;
    }
}
