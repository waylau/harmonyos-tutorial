package com.waylau.hmos.eventhandler.slice;

import com.waylau.hmos.eventhandler.MyEventHandler;
import com.waylau.hmos.eventhandler.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.eventhandler.EventRunner;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    // 创建EventRunner
    private EventRunner eventRunner = EventRunner.create("MyEventRunner"); // 内部会新建一个线程
    private int eventId = 0; // 事件ID，递增的序列

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发
        Text textSendEvent =
                (Text) findComponentById(ResourceTable.Id_text_send_event);
        textSendEvent.setClickedListener(listener -> sendEvent());
    }

    private void sendEvent() {
        HiLog.info(LABEL_LOG, "before sendEvent");

        // 创建MyEventHandler实例
        MyEventHandler handler = new MyEventHandler(eventRunner);

        eventId++;

        // 向EventRunner发送事件
        handler.sendEvent(eventId);

        HiLog.info(LABEL_LOG, "end sendEvent eventId: %{public}s", eventId);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}