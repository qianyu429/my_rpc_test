package com.rpctest.pool;

import au.com.bytecode.opencsv.CSVReader;
import com.rpctest.annotation.TestDataFilter;
import com.rpctest.utils.ContextUtils;
import com.shhxzq.fin.commons.util.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by duanzonghai on 2018/3/16.
 */
@Log4j2
public class DataPool {

    private TestDataFilter dataFilter=null;
    private Map<String, List<?>> pool = new ConcurrentHashMap<>();

    public static synchronized DataPool getInstance(){return ContextUtils.dataPool;}

    private static ThreadLocal<List<TestDataFilter>> dataFilterList = new ThreadLocal<List<TestDataFilter>>(){
        @Override
        protected List<TestDataFilter> initialValue(){
            return  new ArrayList<TestDataFilter>();
        }
    };

    public List<TestDataFilter> getDataFilter() {
        return dataFilterList.get();
    }

    public synchronized void setDataFilter(TestDataFilter dataFilter) {
        this.dataFilter = dataFilter;
    }

    public void loadData(String beanId,String beanName,String dataFilePath){
        try{
            List beanList = new ArrayList<>();
            //如果文件目录包含.csv则把目录存放到List中
            if (dataFilePath.contains(".csv")){
                beanList.addAll(readBeans(dataFilePath,Class.forName(beanName)));
            }

            if (dataFilter != null){
                //filterData方法看csv的数据和过滤之后的数据是否一致
                dataFilter.filterData(beanList,dataFilterList.get());
            }
            pool.put(beanId,beanList);
        }catch (Exception e){
            System.out.print(e);
        }
    }

    public List readBeans(String filePath, Class<?> tClass) throws Exception{
        CSVReader reader = null;
        List beans = null;
        FileReader fileReader = new FileReader(filePath);
        try {
            reader = new CSVReader(fileReader);
            String fields[] = reader.readNext(); //fields from csv

            Object object = null;
            beans = new ArrayList();

            String[] dataLine = null;
            while ((dataLine = reader.readNext()) != null) {
                String purpose = "";
                if (fields != null && fields.length > 0) {
                    object = tClass.newInstance();
                    // The first and second field in the CSV is 'purpose' which are used to filter the data for test case.
                    if (StringUtils.equalsIgnoreCase(fields[0], "purpose")) {
                        purpose = dataLine[0];
                    }
                    for (int i = 1; i < fields.length; i++) {
                        if (StringUtils.isNotEmpty(fields[i])) {
                            String value = dataLine[i];

                            if (StringUtils.isEmpty(value)) {
                                Class type = PropertyUtils.getPropertyType(object, fields[i]);
                                if (type != null && List.class.isAssignableFrom(type)) {
                                    PropertyUtils.setProperty(object, fields[i], new ArrayList());
                                } else if (type != null && (type.equals(Boolean.class) || type.equals(boolean.class))) {
                                    PropertyUtils.setProperty(object, fields[i], Boolean.FALSE);
                                } else {
                                    PropertyUtils.setProperty(object, fields[i], "");
                                }

                            } else {
                                if (StringUtils.equalsIgnoreCase(value, "null")) {
                                    continue;
                                }

                                if (!StringUtils.contains(value, "random(") && !StringUtils.contains(value, "randomAlphanumeric(") && !StringUtils.contains(value, "date()")) {
                                    BeanUtils.setProperty(object, fields[i], value);
                                } else {
                                    String val = "";
                                    if (StringUtils.contains(value, "&")) {
                                        //需要两个值绑定在一起.
                                        String[] cb = value.split("&");
                                        String cb1 = "";
                                        String cb2 = "";

                                        if (cb.length == 2) {
                                            cb1 = generateRandom(cb[0]);
                                            cb2 = generateRandom(cb[1]);
                                        } else {
                                            cb1 = generateRandom(cb[0]);
                                        }

                                        val = cb1 + cb2;

                                    } else {
                                        val = generateRandom(value);

                                    }
                                    BeanUtils.setProperty(object, fields[i], val);
                                }

                            }
                        }
                    }
                    log.info("load data from csv.");
                    TestDataFilter f1 = new TestDataFilter();
                    f1.setPurpose(purpose);

                    dataFilterList.get().add(f1);

                    beans.add(object);
                }
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return beans;
    }

    public synchronized List getData(String beanId) {
        return pool.get(beanId);
    }


    public void cleanAfterMethod() {
        pool.clear();
        dataFilter = null;
        dataFilterList.get().clear();
    }

    private String generateRandom(String value) {
        String random = null;

        if (value.contains("randomAlphanumeric(")) {
            if (!(value.contains("(") && value.contains(")"))) {
                log.info("value: " + value + "did not contain (). if the field is needed to use random value to generate, please put value in the csv like 'random(20)'");
                return null;
            }
            int size = Integer.valueOf(value.split("\\(")[1].split("\\)")[0]);
            random = RandomStringUtils.randomAlphanumeric(size);
            log.info("字母数字随机生成: " + random);
        } else if (value.contains("random(")) {
            if (!(value.contains("(") && value.contains(")"))) {
                log.info("value: " + value + "did not contain (). if the field is needed to use random value to generate, please put value in the csv like 'random(20)'");
                return null;
            }
            int size = Integer.valueOf(value.split("\\(")[1].split("\\)")[0]);
            random = RandomStringUtils.randomNumeric(size);
            log.info("数字随机生成: " + random);
        } else if (value.contains("date()")) {
            random = DateUtil.getToday();
            log.info("当天日期生成: " + random);
        } else {
            random = value;
            log.info("取原生的值: " + random);
        }
        return random;

    }
}
