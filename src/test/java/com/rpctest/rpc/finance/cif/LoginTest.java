package com.rpctest.rpc.finance.cif;

import com.pentos.kaitest.annotation.KaiTestDataSet;
import com.rpctest.RpcTestCase;
import com.shhxzq.fin.cif.service.LoginService;
import com.shhxzq.fin.cif.vo.LoginArg;
import com.shhxzq.fin.cif.vo.LoginRet;
import com.tngtech.java.junit.dataprovider.DataProvider;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by duanzonghai on 2018/3/15.
 */
public class LoginTest extends RpcTestCase {
    @Autowired
    private LoginService loginService;

    @Test
    @DataProvider({
            "18019281731, qq789123, true",
            "18019281731, qq789123,flase"
    })
    @KaiTestDataSet(value = {"login/ciflogin.xml"},dsNames = {"cifDataSource"},teardownOperation = "None")
    public void testLogin(String loginName, String password, boolean captchaCorrect){
        LoginArg loginArg = new LoginArg();
        loginArg.setLoginName(loginName);
        loginArg.setPasswd(password);
        loginArg.setCaptchaCorrect(captchaCorrect);

        LoginRet loginRet = loginService.login(loginArg);

        String custNo = loginRet.getCustNo();
        String errNo = loginRet.getErrNo();
        String errMsg = loginRet.getErrMsg();
        String token = loginRet.getToken();

        Request custBaseRequest = new Request(cifJdbcTemplate.getDataSource(),
                "select * from cif_cust_base where cust_no =" + custNo);

        //检查custBaseRequest返回的cust_no列的值和custNO是否一致
        assertThat(custBaseRequest).column("cust_no").value().isEqualTo(custNo);
        assertThat(custBaseRequest).column("mobile").value().isEqualTo("18019281731");
        assertThat(custBaseRequest).column("cert_no").value().isEqualTo("310101199001016617");
        assertThat(custBaseRequest).column("status").value().isEqualTo("N");

        assertThat(token).isEqualTo(token).isNotEmpty();
        assertThat(errNo).isEqualTo("000000");
        assertThat(errMsg).isNull();
        assertThat(loginRet.isSuccess());



    }
}
