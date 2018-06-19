package com.rpctest.rpc.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.experimental.categories.Categories;

/**
 * Created by duanzonghai on 2018/3/14.
 */

@RunWith(Categories.class)
@Suite.SuiteClasses(
        CIFTests.class
)
public class CifSuite {
}
