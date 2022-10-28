package com.musoulee.myseckill.util;

import com.musoulee.myseckill.entity.User;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;


/**
 * @description:
 * @author: musou
 * @Date: 2022/10/25 19:25
 */

public class HibernateValidatorTest {
    private static Validator validator;

    /**
     *
     */
    @BeforeClass
    public static void setValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testBean(){

    }
}
