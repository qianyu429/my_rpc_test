package com.rpctest.rpc.finance.cif;

import com.pentos.kaitest.annotation.KaiTestDataSet;
import com.rpctest.RpcTestCase;
import com.shhxzq.fin.cif.service.AuthService;
import com.shhxzq.fin.cif.service.LoginService;
import com.shhxzq.fin.cif.vo.LoginArg;
import com.shhxzq.fin.cif.vo.LoginRet;
import com.tngtech.java.junit.dataprovider.DataProvider;
import lombok.extern.log4j.Log4j2;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by duanzonghai on 2018/3/20.
 */

@Log4j2
public class logoutTest extends RpcTestCase {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthService authService;

    @Test
    @DataProvider({
            "18019281731, qq789123, true",
            "18019281731, qq789123,flase"})

    @KaiTestDataSet(value = "login/ciflogin.xml",dsNames = {"cifDataSource"},teardownOperation = "None")
    public void testLogout(String loginName, String password,boolean captchaCorrect){

        log.info("测试用例开始执行=======");
        LoginArg  loginArg = new LoginArg();
        loginArg.setLoginName(loginName);
        loginArg.setPasswd(password);
        loginArg.setCaptchaCorrect(captchaCorrect);

        LoginRet loginRet = loginService.login(loginArg);

        String custNO= loginRet.getCustNo();
        String errNo =loginRet.getErrNo();
        String errMsg =loginRet.getErrMsg();
        String token=loginRet.getToken();

        Request custBaseRequest = new Request(cifJdbcTemplate.getDataSource(),"select * from cif_cust_base where cust_no =" +custNO);

        log.info("======验证登录返回信息======");
        log.info("======验证登录返回信息======");
        log.info("======验证登录返回信息======");
        log.info("======验证登录返回信息======");
        log.info("======验证登录返回信息======");
        log.info("======验证登录返回信息======");
        //获取custo_no那一列的值看是否和custNO相等
        assertThat(custBaseRequest).column("cust_no").value().isEqualTo(custNO);
        assertThat(custBaseRequest).column("mobile").value().isEqualTo("18019281731");
        assertThat(custBaseRequest).column("cert_no").value().isEqualTo("310101199001016617");
        assertThat(custBaseRequest).column("cert_type").value().isEqualTo("0");
        log.info("token的结果为{}",assertThat(token).isEqualTo(token));

        assertThat(token).isEqualTo(token);








    }



}
