package com.rpctest.annotation;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;

import java.util.Iterator;
import java.util.List;

/**
 * Created by duanzonghai on 2018/3/16.
 */

@Log4j2
public class TestDataFilter {

    private String id;

    private String purpose;

    //不带参数的构造方法
    public TestDataFilter(){
    }

    //带参数的构造方法
    public TestDataFilter(String flag, String purpose){
        super();
        this.id =flag;
        this.purpose =purpose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    //过滤数据
    public void filterData(List beanList, List<TestDataFilter> f1){
        log.info("data from csv:" + beanList.toString());
        log.info("过滤后的数据" + f1.toString());
        if (beanList.size() != f1.size()){
            log.info("csv导入的数据和过滤之后的数据不一致. beanList:{},filterList:{}" + beanList.size(),f1.size());
            return;
        }

        int index=0;
        log.info("测试的purpose:{}",purpose);
        //迭代器
        Iterator<Class<T>> it = beanList.iterator();

        //迭代器用于while循环
        while (it.hasNext()){
            it.next();
            if (!StringUtils.equalsIgnoreCase(f1.get(index).getPurpose(),purpose)){
                it.remove();
            }
            index++;
        }
        log.info("filter之后的数据为:{}",beanList.toString());
    }
}
