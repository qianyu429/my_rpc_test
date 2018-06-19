package com.rpctest.listener;

import com.pentos.kaitest.core.AbstractTestListener;
import com.pentos.kaitest.core.TestContext;
import com.pentos.kaitest.spring.context.SpringContextManager;
import com.rpctest.annotation.TestDataConfig;
import com.rpctest.annotation.TestDataItem;
import com.rpctest.annotation.TestDataRule;
import com.rpctest.utils.ContextUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duanzonghai on 2018/3/15.
 */

@Log4j2
public class TestDataInitializeListeners extends AbstractTestListener {

    //m.beforeTestMethod(testContext) 不是很懂
    @Override
    public void beforeTestMethod(TestContext testContext){
        loadDataFromAnnotation(testContext);
//        Map<String,ATBeforeMethod> map =(HashMap<String ,ATBeforeMethod>) SpringContextManager.getApplicationContext()
//                .getBeansOfType(ATBeforeMethod.class);
//
//        for(ATBeforeMethod m : map.values()){
//            m.beforeTestMethod(testContext);
//        }
    }

    @Override
    public void afterTestMethod(TestContext testContext){
        log.info("after test method");
//        if (ContextUtils.getDataPool() != null){
//            //testContext.getTestMethod().getName() 获取方法名
//            log.info("clean the data after method:{}",testContext.getTestMethod().getName());
//            //清除数据
//            ContextUtils.getDataPool().cleanAfterMethod();
//        }
//        Map<String, ATAfterMethod> map = (HashMap<String ,ATAfterMethod>) SpringContextManager.getApplicationContext()
//                .getBeansOfType(ATAfterMethod.class);
//        for (ATAfterMethod am : map.values()){
//            am.afterTestMethod(testContext);
//        }
    }

    public void loadDataFromAnnotation(TestContext testContext){
        TestDataConfig configAnnt = (TestDataConfig) AnnotationMethodUtil.findAnnotation(testContext.getTestMethod(), TestDataConfig.class);

        if (configAnnt != null) {
            TestDataItem[] items = configAnnt.values();

            for (TestDataItem item : items) {
                String beanId = item.beanId();
                String dataFile = item.dataFile();
                String beanName = item.beanName();

                String dataFilePath = determineLocations(testContext, item);

                if (StringUtils.isNotEmpty(beanName) && StringUtils.isNotEmpty(beanId)
                        && StringUtils.isNotEmpty(dataFile)) {

                    TestDataRule ruleDef = item.filter();
                    if (ruleDef != null) {
                        ContextUtils.getDataPool().setDataFilter(ContextUtils.getFilter(ruleDef));
                    }
                    ContextUtils.getDataPool().loadData(beanId, beanName, dataFilePath);
                }
            }
        }
    }

    private String determineLocations(TestContext testContext,TestDataItem annotation){
        Class<?> testClass = testContext.getTestInstance().getClass();
        String filePath = annotation.dataFile();
        String path = "";
        if (StringUtils.isEmpty(filePath)) {
            String msg = String.format("Test class [%s] has not been configured with @TestDataItem' and 'datafile' [%s] attributes. Use both.", testClass, filePath);
            throw new RuntimeException(msg);
        }

        String classpath = generateLocations(testClass, filePath);

        try {

            path = new DefaultResourceLoader().getResource(classpath).getFile().getPath();
        } catch (Exception e) {
            log.error(e);
        }
        return path;
    }

    private static String generateLocations(Class <?> clazz, String filePath){
        //ResourceUtils.CLASSPATH_URL_PREFIX  classpath:路径
        //cleanPath方法将路径做了转换, 该方法适用于绝对路径
        return ResourceUtils.CLASSPATH_URL_PREFIX + "/" + org.springframework.util.StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(clazz)) + "/" + filePath;
    }



}
