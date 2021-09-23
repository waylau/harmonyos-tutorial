package com.waylau.hmos.intentoperationwithuri.slice;

import com.waylau.hmos.intentoperationwithuri.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
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
        text.setClickedListener(listener -> this.goToSearch());
    }

    private void goToSearch() {
        HiLog.info(LABEL_LOG, "before goToSearch");

        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withUri(Uri.parse("https://www.baidu.com/ ")) // 指定要访问的URI
                .build();

        intent.setOperation(operation);

        // 启动Ability
        startAbility(intent);
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