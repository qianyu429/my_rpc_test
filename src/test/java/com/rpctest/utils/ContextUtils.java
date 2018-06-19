package com.rpctest.utils;

import com.rpctest.annotation.TestDataFilter;
import com.rpctest.annotation.TestDataRule;
import com.rpctest.pool.DataPool;
import lombok.extern.log4j.Log4j2;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


/**
 * Created by duanzonghai on 2018/3/16.
 */
@Log4j2
public class ContextUtils {

    public static DataPool dataPool = null;

    public static synchronized DataPool getDataPool(){

        if (dataPool == null){
            log.info("dataPoll 初始化");
            dataPool = new DataPool();
        }
        return  dataPool;
    }

    //根据TestDataRule规则设置过滤数据
    public static  TestDataFilter getFilter(TestDataRule ruleAnnotation){
        log.info("set data filter!");

        TestDataFilter filter = new TestDataFilter();
        filter.setId(ruleAnnotation.id());
        filter.setPurpose(ruleAnnotation.purpose());
        return filter;
    }

}
