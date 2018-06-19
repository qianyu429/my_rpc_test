package com.rpctest.rpc.suites;

import com.rpctest.annotation.ConcurrentRunner;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.runner.RunWith;

/**
 * Created by duanzonghai on 2018/3/23.
 */
@RunWith(ConcurrentRunner.class)
@ClasspathSuite.ClassnameFilters({
//           tradeService
        "com/rpctest/rpc/finance/cts/PurchaseByVaccoTest"
}
)
public interface CTSTests {
}
