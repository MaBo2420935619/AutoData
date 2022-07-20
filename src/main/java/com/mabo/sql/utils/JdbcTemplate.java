package com.mabo.sql.utils;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class JdbcTemplate {
    static Connection connection = null;
    static ResultSet rs =null;
    static DataSource dataSource= new DruidDataSource();
    static {
        try {
            String url = "jdbc:mysql://localhost:3306/downloadsdk?useSSL=false&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF8&autoReconnect=true&failOverReadOnly=false";
            String username="root";
            String password="368541186MaBo123";
            //2.创建数据源对象
            /*DataSource:是SUN公司声明的接口 。DruidDataSource：是阿里巴巴对应实现的类*/
            //3.设置属性
            DruidDataSource ds = (DruidDataSource)dataSource;
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setInitialSize(new Integer(1));
            ds.setMaxActive(new Integer(20));
            ds.setMaxWait(new Long(60000));
            //4.拿连接。直接通过数据源获取可用的连接
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }


    }

    public static int update(String sql, Object... args) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int updateCount = 0;
        //程序执行可能会发生异常，故我们使用try-catch进行对异常的处理
        try {
            //预编译对象
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //获取元数据，即数据库表的字段
            ParameterMetaData parameterMetaData = preparedStatement.getParameterMetaData();
            //通过ParameterMetaData中的getParameterCount()方法，可以获取sql语句中？的个数，即可以确定所需参数的个数
            int parameterCount = parameterMetaData.getParameterCount();
            //通过for循环去动态设置所需参数
            for (int i = 0; i < parameterCount; i++) {
                //setObject()方法一般只会在写框架底层中使用
                preparedStatement.setObject(i + 1, args[i]);
            }
            //通过preparedStatement去执行executeUpdate()方法返回受影响的行数
            updateCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //最后方法返回一个int类型数据，将受影响的行数返回
        return updateCount;
    }

    public static List<HashMap<String, Object>> queryForList(String sql, Object... args) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<HashMap<String, Object>> list = new ArrayList<>();
        //程序执行可能会发生异常，故我们使用try-catch进行对异常的处理
        try {
            //预编译对象
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //获取元数据，即数据库表的字段
            ParameterMetaData parameterMetaData = preparedStatement.getParameterMetaData();
            //通过ParameterMetaData中的getParameterCount()方法，可以获取sql语句中？的个数，即可以确定所需参数的个数
            int parameterCount = parameterMetaData.getParameterCount();
            //通过for循环去动态设置所需参数
            for (int i = 0; i < parameterCount; i++) {
                //setObject()方法一般只会在写框架底层中使用
                preparedStatement.setObject(i + 1, args[i]);
            }
            //通过preparedStatement去执行executeUpdate()方法返回受影响的行数
            rs = preparedStatement.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            int columnCount = data.getColumnCount();
            while(rs.next()){
                HashMap<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = data.getColumnName(i);
                    Object object = rs.getObject(i);
                    map.put(columnName,object);
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close();
        }
        return list;
    }

    private static void close(){
        if (connection!=null){
            try {
                connection.close();

            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
        if (rs!=null){
            try {
                rs.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
    }
}


