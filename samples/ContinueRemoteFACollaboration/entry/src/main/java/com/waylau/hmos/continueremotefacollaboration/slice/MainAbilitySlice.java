package com.waylau.hmos.continueremotefacollaboration.slice;

import com.waylau.hmos.continueremotefacollaboration.DeviceUtils;
import com.waylau.hmos.continueremotefacollaboration.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.agp.components.Button;
import ohos.agp.components.TextField;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * 设备迁移与回迁
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MainAbilitySlice extends AbilitySlice implements IAbilityContinuation {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final String MESSAGE_KEY =
            "com.waylau.hmos.continueremotefacollaboration.slice.MESSAGE_KEY";

    private String message;

    private boolean isContinued;

    private TextField messageTextField;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 监听跨端迁移FA的事件
        Button buttonContinueRemoteFA = (
                Button) findComponentById(ResourceTable.Id_button_continue_remote_fa);
        buttonContinueRemoteFA.setClickedListener(listener -> continueRemoteFA());

        // 设置输入框内容
        messageTextField = (TextField) findComponentById(ResourceTable.Id_message_textfield);
        if (isContinued && message != null) {
            messageTextField.setText(message);
        }
    }

    private void continueRemoteFA() {
        HiLog.info(LABEL_LOG, "before startRemoteFA");

        String deviceId = DeviceUtils.getDeviceId();

        HiLog.info(LABEL_LOG, "get deviceId: %{public}s", deviceId);

        if (deviceId != null) {
            // 发起迁移流程
            //  continueAbility()是不可回迁的
            //  continueAbilityReversibly() 是可以回迁的
            continueAbility(deviceId);
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    public boolean onStartContinuation() {
        // 重写
        return true;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        // 重写
        // 保存回迁后恢复状态必须的数据
        intentParams.setParam(MESSAGE_KEY, messageTextField.getText());
        return true;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        // 重写
        // 传递此前保存的数据
        if (intentParams.getParam(MESSAGE_KEY) instanceof String) {
            message = (String) intentParams.getParam(MESSAGE_KEY);
            isContinued = true;
        }
        return true;
    }

    @Override
    public void onCompleteContinuation(int i) {
        // 终止
        terminate();
    }

}
