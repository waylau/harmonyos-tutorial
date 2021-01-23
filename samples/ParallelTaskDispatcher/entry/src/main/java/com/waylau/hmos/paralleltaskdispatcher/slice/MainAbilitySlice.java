package com.waylau.hmos.paralleltaskdispatcher.slice;

import com.waylau.hmos.paralleltaskdispatcher.MyTask;
import com.waylau.hmos.paralleltaskdispatcher.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.app.dispatcher.Group;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发
        Text textStartDispatcher =
                (Text) findComponentById(ResourceTable.Id_text_start_parallel_task_dispatcher);
        textStartDispatcher.setClickedListener(listener -> startDispatcher());
    }

    // 指定任务排发
    private void startDispatcher() {
        String dispatcherName = "MyDispatcher";

        TaskDispatcher dispatcher =
                this.getContext().createParallelTaskDispatcher(dispatcherName, TaskPriority.DEFAULT);

        // 创建任务组
        Group group = dispatcher.createDispatchGroup();

        // 将任务1加入任务组
        dispatcher.asyncGroupDispatch(group, new MyTask("task1"));

        // 将与任务1相关联的任务2加入任务组
        dispatcher.asyncGroupDispatch(group, new MyTask("task2"));

        // task3必须要等任务组中的所有任务执行完成后才会执行
        dispatcher.groupDispatchNotify(group, new MyTask("task3"));
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