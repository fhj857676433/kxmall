package com.kxmall.market.app.api.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Description:
 * User: admin
 * Date: 2019/12/27
 * Time: 21:04
 */
public class GlobalExecutor {

    private static final ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

}
