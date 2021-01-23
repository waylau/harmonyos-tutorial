package com.waylau.hmos.eventhandler;

import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.concurrent.TimeUnit;

public class MyEventHandler extends EventHandler {
    private static final String TAG = MyEventHandler.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    public MyEventHandler(EventRunner runner) throws IllegalArgumentException {
        super(runner);
    }

    @Override
    public void processEvent(InnerEvent event) {
        super.processEvent(event);
        if (event == null) {
            HiLog.info(LABEL_LOG, "before processEvent event is null");
            return;
        }
        int eventId = event.eventId;
        HiLog.info(LABEL_LOG, "before processEvent eventId: %{public}s", eventId);

        int task1Result = getRandomInt();
        try {
            // 模拟一个耗时的操作
            TimeUnit.MILLISECONDS.sleep(task1Result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HiLog.info(LABEL_LOG, "after processEvent eventId %{public}s", eventId);
    }

    // 返回随机整数
    private int getRandomInt() {
        // 获取[0, 1000)之间的int整数。方法如下：
        double a = Math.random();
        int result = (int) (a * 1000);
        return result;
    }
}
