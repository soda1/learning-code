package com.soda.learn.util;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.Executor;

/**
 * @author eric
 * @date 6/4/2023
 */
public class TransactionUtils {

    /**
     * 在事务提交后同步执行
     * @param runnable
     */
    public static void afterCommitSyncExecute(Runnable runnable){
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

    /**
     * 在事务提交后异步执行
     * @param runnable
     */
    public static void afterCommitAsyncExecute(Executor executor, Runnable runnable){
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    executor.execute(runnable);
                }
            });
        } else {
            executor.execute(runnable);
        }
    }
}
