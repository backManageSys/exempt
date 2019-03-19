package njurestaurant.njutakeout.data.dao;

import njurestaurant.njutakeout.entity.SystemLog;

import java.util.Date;

/***
 * 左连接映射结果接口类
 */
public interface SystemLogLeftJoinModel {
    String getUsername();

    Integer getId();

    String getRequestip(); //操作IP

    String getType();//  操作类型 1 操作记录 2异常记录

    String getDescription();// 操作描述

    Date getActiondate() ;// 操作时间

    Integer getExceptioncode() ;// 异常code

    String getExceptiondetail();// 异常详情

    String getActionmethod();//请求方法

    String getParams();//请求参数

}
