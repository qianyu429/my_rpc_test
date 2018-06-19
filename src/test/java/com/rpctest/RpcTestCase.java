package com.rpctest;

import com.pentos.kaitest.KaiTestSpringContextBaseCase;
import com.pentos.kaitest.annotation.ExternalFile;
import com.pentos.kaitest.annotation.KaiTestSpringContext;
import com.pentos.kaitest.core.TestListeners;
import com.pentos.kaitest.dbunit.DbUnitTools;
import com.pentos.kaitest.util.CsvUtil;
import com.rpctest.listener.TestDataInitializeListeners;
import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * Created by duanzonghai on 2018/3/14.
 */


@TestListeners({
        TestDataInitializeListeners.class
})

@KaiTestSpringContext({"/spring-dubbo.xml", "/spring-dubbo-customer.xml", "/datasource-uat.xml"})
public class RpcTestCase extends KaiTestSpringContextBaseCase {

    @Autowired
    protected JdbcTemplate ctsJdbcTemplate;

    @Autowired
    protected JdbcTemplate cifJdbcTemplate;

    @Autowired
    protected JdbcTemplate beJdbcTemplate;

    @Autowired
    protected JdbcTemplate pdcJdbcTemplate;

    @Autowired
    protected JdbcTemplate beidouJdbcTemplate;

    @Autowired
    protected JdbcTemplate lifeAppJdbcTemplate;

    @Autowired
    protected JdbcTemplate pointsJdbcTemplate;

    @Autowired
    protected JdbcTemplate supergwJdbcTemplate;

    @Autowired
    protected JdbcTemplate qyJdbcTemplate;

    @DataProvider
    public static Object [] [] loadFromExternalFile(FrameworkMethod testMethod){
        //ExternalFile.class 的value方法是空的
        String filePath = testMethod.getAnnotation(ExternalFile.class).value();
        filePath ="target/test-classes/" + filePath;
        return CsvUtil.getParasFromCsv(filePath,testMethod.getName());
    }

    @Test
    //用来生成xml或者excel的数据文件
    public void testCreateXml(){
        String xmlFilePath = "/Users/duanzonghai/my_rpc_test/src/test/resources/com/rpctest/rpc/finance/cts/purchaseByVacco/ciflogin2.xml";
        String querySql = "SELECT * FROM cif_uat.cif_cust_base where cust_no in ('0001001924');";

        //DbUnitTools.generateDataSetFile(xmlFilePath,cifJdbcTemplate.getDataSource(),querySql);
    }

}
