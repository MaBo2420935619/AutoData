package com.mabo.example;

import com.mabo.sql.utils.JdbcTemplate;

import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
//        String test = CreateSqlReflect.createSql(TestEntity.class, "TEST", 10);
//        System.out.println(test);


        List<HashMap<String, Object>> hashMaps = JdbcTemplate.queryForList("select * from history");
        System.out.println(hashMaps);
        int update = JdbcTemplate.update("update history set ip='231213213' where ip= '111'");
        System.out.println(update);
        JdbcTemplate.update("insert into history VALUES ('231213213', '123', '2022-06-29 11:03:40', 1)");
    }
}
