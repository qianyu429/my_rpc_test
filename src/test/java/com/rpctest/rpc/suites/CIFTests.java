package com.rpctest.rpc.suites;

import com.pentos.kaitest.util.ConcurrentSuite;
import com.rpctest.annotation.ConcurrentRunner;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.runner.RunWith;

/**
 * Created by duanzonghai on 2018/3/14.
 */

@RunWith(ConcurrentRunner.class)
@ClasspathSuite.ClassnameFilters({
        //loginService
        "com/rpctest/rpc/finance/cif.LoginTest",
        "com/rpctest/rpc/finance/cif.LogoutTest"
        })

public interface CIFTests {
}
