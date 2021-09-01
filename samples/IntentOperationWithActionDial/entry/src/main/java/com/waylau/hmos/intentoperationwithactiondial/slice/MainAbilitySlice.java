package com.waylau.hmos.intentoperationwithactiondial.slice;

import com.waylau.hmos.intentoperationwithactiondial.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.IntentConstants;
import ohos.utils.net.Uri;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发请求
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        // text.setClickedListener(listener -> this.doCall());
        text.setClickedListener(listener -> this.doCall("18088888888"));
    }

    private void doCall() {
        this.doCall(null);
    }

    private void doCall(String destinationNum) {
        HiLog.info(LABEL_LOG, "before doCall");
        Operation operation;

        if (destinationNum == null) {
            operation = new Intent.OperationBuilder()
                    .withAction(IntentConstants.ACTION_DIAL) // 系统应用拨号盘
                    .build();
        } else {
            operation = new Intent.OperationBuilder()
                    .withAction(IntentConstants.ACTION_DIAL) // 系统应用拨号盘
                    .withUri(Uri.parse("tel:" + destinationNum)) // 设置号码
                    .build();
        }

        Intent intent = new Intent();
        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);

        HiLog.info(LABEL_LOG, "after doCall");
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
