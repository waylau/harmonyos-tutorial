package com.waylau.hmos.paralleltaskdispatcher;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.concurrent.TimeUnit;

public class MyTask implements Runnable {
    private static final String TAG = MyTask.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private String taskName;

    public MyTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        HiLog.info(LABEL_LOG, "before %{public}s run", taskName);
        int task1Result = getRandomInt();
        try {
            // 模拟一个耗时的操作
            TimeUnit.MILLISECONDS.sleep(task1Result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HiLog.info(LABEL_LOG, "after %{public}s run, result is: %{public}s", taskName, task1Result);
    }

    // 返回随机整数
    private int getRandomInt() {
        // 获取[0, 1000)之间的int整数。方法如下：
        double a = Math.random();
        int result = (int) (a * 1000);
        return result;
    }

}