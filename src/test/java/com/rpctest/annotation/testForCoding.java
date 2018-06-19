package com.rpctest.annotation;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

/**
 * Created by duanzonghai on 2018/3/16.
 */
@Log4j2
public class testForCoding {

    @Test
    public void testDemo(){
        String sr = "fdsferwrefs/fdfd/wr";
        int num = sr.indexOf(":");
        System.out.println(num);
        log.info("classpath路径为:{}",ResourceUtils.CLASSPATH_URL_PREFIX);
    }

}
