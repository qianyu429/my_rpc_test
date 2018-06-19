package com.rpctest.rpc.finance.cts;

import com.pentos.kaitest.annotation.KaiTestDataSet;
import com.rpctest.RpcTestCase;
import com.shhxzq.fin.cts.service.TradeService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by duanzonghai on 2018/3/23.
 */
@Log4j2
public class PurchaseByVaccoTest extends RpcTestCase {

    @Autowired
    private TradeService tradeService;

    @Test
    @DataProvider({

    })

    @KaiTestDataSet(value = {""},dsNames = {"ctsDataSource","cifDataSource","pdcDataSource"},teardownOperation = "None")

}
