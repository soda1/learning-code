package com.soda.learn.validator;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author eric
 * @date 2/21/2023
 */
public class Person {


    private String id;

    @Min(1)
    @Max(100)
    private int age;

    @NonNull
    @Size(min = 2, max = 4, message = "名字长度不符")
    private String name;
}
