package com.rpctest.annotation;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Runner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.RunnerScheduler;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;


/**
 * Created by duanzonghai on 2018/3/14.
 */
public class ConcurrentRunner extends ClasspathSuite {

    public static Runner MulThread(Runner runner){
        //判断runner是否属于ParentRunner类,不属于的话则返回runner
        if (runner instanceof ParentRunner){
            ((ParentRunner) runner).setScheduler(new RunnerScheduler() {
                private final ExecutorService fService = Executors.newCachedThreadPool();

                //RunnerScheduler 必须要实现RunnerScheduler的抽象方法schedule
                public void schedule(Runnable childStatement){
                    this.fService.submit(childStatement);
                }

                public void finished(){
                    try{
                        this.fService.shutdown();
                        this.fService.awaitTermination(9223372036854775807L, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException e){
                        e.printStackTrace(System.err);
                    }
                }
                });
            }
        return runner;
        }

    public ConcurrentRunner(final Class<?> klass) throws InitializationError{
        super (klass , new AllDefaultPossibilitiesBuilder(true){
            @Override
            public Runner runnerForClass(Class<?> testClass) throws Throwable{
                List<RunnerBuilder> builders = Arrays.asList(new RunnerBuilder[]{
                        ignoredBuilder(),
                        annotatedBuilder(),
                        junit3Builder(),
                        junit4Builder()});

                for (RunnerBuilder each : builders){
                    Runner runner = each.safeRunnerForClass(testClass);
                    if (runner != null)
                        return  runner;
                }
                return null;
            }
        });


        setScheduler(new RunnerScheduler(){
            private final ExecutorService fService = Executors.newCachedThreadPool();

            @Override
            public void  schedule(Runnable paramRunnable){
                fService.submit(paramRunnable);
            }

            @Override
            //case结束时关闭线程池
            public void finished(){
                try{
                    fService.shutdown();
                    fService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                }catch (InterruptedException e){
                    e.printStackTrace(System.err);
                }
            }
        });
    }

}
