package com.mabo.example.entity;

import com.mabo.sql.DefaultList;
import com.mabo.sql.StringRule;
import com.mabo.sql.annotation.DateConfigure;
import com.mabo.sql.annotation.IntConfigure;
import com.mabo.sql.annotation.StringConfigure;

import java.util.Date;


public class TestEntity {
    @StringConfigure(createRule = StringRule.NAME)
    private String name;

    @IntConfigure(max = 50,min=20)
    private Integer age;

    @StringConfigure(fieldName = "telePhone",createRule = StringRule.TELNUMBER)
    private String tel;

    @StringConfigure(createRule = StringRule.METHOD,aClass = DefaultList.class, method = "getDept")
    private String dept;

    @DateConfigure(start = "2021-07-19 00:00:00",end =  "2022-07-19 00:00:00")
    private Date date;


}
