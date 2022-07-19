package com.mabo.example;

import com.mabo.example.entity.TestEntity;
import com.mabo.sql.CreateSqlReflect;

public class Test {
    public static void main(String[] args) throws Exception {
        String test = CreateSqlReflect.createSql(TestEntity.class, "TEST", 10);
        System.out.println(test);
    }
}
