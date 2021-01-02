package com.waylau.hmos.hellophone2.slice;

import com.waylau.hmos.hellophone2.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

import java.io.FileDescriptor;
import java.io.FileInputStream;

public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00001, "MainAbilitySlice");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发导航
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        text.setClickedListener(listener -> this.showFile());
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void showFile() {
        DataAbilityHelper helper = DataAbilityHelper.creator(this);
        Uri uri = Uri.parse("dataability:///com.waylau.hmos.hellophone2.UserDataAbility");

        FileDescriptor fd = null;
        try {
            fd = helper.openFile(uri, "r");

            HiLog.info(LABEL_LOG, "fd: %{public}s", fd);

            FileInputStream fis = new FileInputStream(fd);

            int f = 0;
            while ((f=fis.read()) != -1) {
                System.out.print((char)f);
            }

            HiLog.info(LABEL_LOG, "file content: %{public}s", fis.read());

        }  catch (Exception e) {
            e.printStackTrace();
        }

    }
}
