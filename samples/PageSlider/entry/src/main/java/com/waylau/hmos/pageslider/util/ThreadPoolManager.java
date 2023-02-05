/*
 * Copyright (c) 2021 Huawei Device Co., Ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.waylau.hmos.pageslider.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Thread pool manager.
 */
public class ThreadPoolManager {
    private static final int CPU_COUNT = 20;

    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    private static final int KEEP_ALIVE = 1;

    private static ThreadPoolManager instance;

    private ThreadPoolExecutor executor;

    private ThreadPoolManager() {
        if (executor == null) {
            executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(20), Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
        }
    }

    /**
     * Create ThreadPoolManager object.
     *
     * @return ThreadPoolManager.
     */
    public static synchronized ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }

    /**
     * Start a thread that does not return any information.
     *
     * @param runnable Runnable.
     */
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * Remove runnable.
     *
     * @param runnable Runnable.
     */
    public void cancel(Runnable runnable) {
        if (runnable != null) {
            executor.getQueue().remove(runnable);
        }
    }
}