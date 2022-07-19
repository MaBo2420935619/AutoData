package com.mabo.sql;

import com.mabo.sql.utils.RandUtils;

public class DefaultList {
    private static String[] deptList=new String[]{"产品管理模块","天猫信息服务有限","技术管理科","研发部","制造部","产品开发部","资材部"
            ,"设备部","客服部","进出口部","资讯部","企划部","公共关系部","人事部","金融业务模块"};


    public DefaultList() {
    }

    public static String getDept() {
        return deptList[RandUtils.num(0,deptList.length-1)];
    }

}
