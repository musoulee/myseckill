package com.musoulee.myseckill.util;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/26 10:53
 */
public class Driver {
    private String name;
    private int age;

    public Driver(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Length(min = 8, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Range(min = 18, max = 70)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
