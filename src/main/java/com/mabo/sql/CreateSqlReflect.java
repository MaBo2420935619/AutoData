package com.mabo.sql;

import com.mabo.sql.annotation.DateConfigure;
import com.mabo.sql.annotation.IntConfigure;
import com.mabo.sql.annotation.StringConfigure;
import com.mabo.sql.utils.RandUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateSqlReflect {
    public static String createSql(Class aClass, String tableName, int num) throws Exception{
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i < num; i++) {
            String s = createSql(aClass, tableName);
            stringBuffer=stringBuffer.append(s+"\n");
        }
        return stringBuffer.toString();
    }

    public static String createSql(Class aClass, String tableName) throws Exception {
        Field[] fields = aClass.getDeclaredFields();
        Class<DefaultList> randListClass = DefaultList.class;
        Method[] methods = randListClass.getDeclaredMethods();
        StringBuffer sql = new StringBuffer("INSERT INTO " + tableName + " (");
        for (int i = 0; i < fields.length; i++) {
            Field field=fields[i];
            StringConfigure stringConfigure = field.getAnnotation(StringConfigure.class);
            DateConfigure dateConfigure = field.getAnnotation(DateConfigure.class);
            IntConfigure intConfigure = field.getAnnotation(IntConfigure.class);
            String fieldName=null;
            if (intConfigure != null) {
                fieldName=intConfigure.fieldName();
            } else if (dateConfigure != null) {
                fieldName=dateConfigure.fieldName();
            } else if (stringConfigure != null) {
                fieldName=stringConfigure.fieldName();
            }
            if (fieldName.equals("")){
                fieldName=field.getName();
            }
            if (i!= fields.length-1){
                sql=sql.append(fieldName+",");
            }else {
                sql=sql.append(fieldName+") values (");
            }

        }
        for (int i = 0; i < fields.length; i++) {
            Field field=fields[i];
            String appendS=null;
            StringConfigure stringConfigure = field.getAnnotation(StringConfigure.class);
            DateConfigure dateConfigure = field.getAnnotation(DateConfigure.class);
            IntConfigure intConfigure = field.getAnnotation(IntConfigure.class);
            if (intConfigure!=null){
                int min = intConfigure.min();
                int max = intConfigure.max();
                int num = RandUtils.num(min, max);
                appendS=String.valueOf(num);
            }else if(dateConfigure!=null){
                String end = dateConfigure.end();
                String start = dateConfigure.start();
                Date date = RandUtils.date(start, end,"yyyy-MM-dd HH:mm:ss");
                appendS="'"+date2String(date)+"'";
            }
            else if(stringConfigure!=null){
                StringRule rule = stringConfigure.createRule();
                if (rule.equals(StringRule.UUID)){
                    String s = RandUtils.uuId();
                    appendS="'"+s+"'";
                }
                else if (rule.equals(StringRule.UUID16)){
                    appendS="'"+RandUtils.uuId16()+"'";
                }
                else if (rule.equals(StringRule.NAME)){
                    appendS="'"+RandUtils.name()+"'";
                }
                else if (rule.equals(StringRule.TELNUMBER)){
                    appendS="'"+RandUtils.telNum()+"'";
                }
                else if (rule.equals(StringRule.IDCARD)){
                    appendS="'"+RandUtils.idCard()+"'";
                }
                else if (rule.equals(StringRule.MAIL)){
                    appendS="'"+RandUtils.mail()+"'";
                }
                else if (rule.equals(StringRule.METHOD)){
                    Class arrayClass = stringConfigure.aClass();
                    if (!arrayClass.equals(Class.class)){
                        aClass=arrayClass;
                    }
                    else {
                        aClass= DefaultList.class;
                    }
                    String arrayName = stringConfigure.method();
                    for (Method method : methods) {
                        String name = method.getName();
                        if (arrayName.equals(name)){
                            Object o = aClass.newInstance();
                            String strings = (String) method.invoke(o);
                            appendS="'"+strings+"'";
                            break;
                        }
                    }
                }
            }
            if (i!= fields.length-1){
                sql=sql.append(appendS+",");
            }else {
                sql=sql.append(appendS+");");
            }
        }
        return sql.toString();
    }
//    public static String createJson(Class aClass) throws Exception {
//        Field[] fields = aClass.getDeclaredFields();
//        Class<DefaultList> randListClass = DefaultList.class;
//        Method[] randListClassDeclaredMethods = randListClass.getDeclaredMethods();
//        for (int i = 0; i < fields.length; i++) {
//            Field field=fields[i];
//            String appendS=null;
//            StringConfigure stringConfigure = field.getAnnotation(StringConfigure.class);
//            DateConfigure dateConfigure = field.getAnnotation(DateConfigure.class);
//            IntConfigure intConfigure = field.getAnnotation(IntConfigure.class);
//            if (intConfigure!=null){
//                int min = intConfigure.min();
//                int max = intConfigure.max();
//                int num = RandUtils.num(min, max);
//                appendS=String.valueOf(num);
//            }else if(dateConfigure!=null){
//                String end = dateConfigure.end();
//                String start = dateConfigure.start();
//                Date date = RandUtils.date(start, end,"yyyy-MM-dd HH:mm:ss");
//                appendS="'"+date2String(date)+"'";
//            }
//            else if(stringConfigure!=null){
//                StringRule rule = stringConfigure.createRule();
//                if (rule.equals(StringRule.UUID)){
//                    String s = RandUtils.uuId16();
//                    appendS="'"+s+"'";
//                }
//                else if (rule.equals(StringRule.METHOD)){
//                    Class arrayClass = stringConfigure.aClass();
//                    if (!arrayClass.equals(Class.class)){
//                        aClass=arrayClass;
//                    }
//                    else {
//                        aClass= DefaultList.class;
//                    }
//                    String arrayName = stringConfigure.method();
//                    for (Method method : randListClassDeclaredMethods) {
//                        String name = method.getName();
//                        if (arrayName.equals(name)){
//                            Object o = aClass.newInstance();
//                            String strings = (String) method.invoke(o);
//                            appendS="'"+strings+"'";
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//    }



    public   static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String date2String(Date date){
        String format = sdf.format(date);
        return format;
    }
}
