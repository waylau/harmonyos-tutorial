package com.waylau.hmos.dataabilityhelperaccessfile.slice;

import com.waylau.hmos.dataabilityhelperaccessfile.FileUtils;
import com.waylau.hmos.dataabilityhelperaccessfile.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

import java.io.*;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发访问数据
        Text text = (Text) findComponentById(ResourceTable.Id_text_helloworld);
        text.setClickedListener(listener -> this.getFile());
    }

    private void getFile() {
        DataAbilityHelper helper = DataAbilityHelper.creator(this);

        // 访问数据用的URI，注意用三个斜杠
        Uri uri =
                Uri.parse("dataability:///com.waylau.hmos.dataabilityhelperaccessfile.UserDataAbility");

        //  DataAbilityHelper的openFile方法来访问文件
        try {
            FileDescriptor fd = helper.openFile(uri, "r");

            HiLog.info(LABEL_LOG, "fd: %{public}s", fd);
            HiLog.info(LABEL_LOG, "file content: %{public}s", FileUtils.getFileContent(fd));
        } catch (DataAbilityRemoteException | IOException e) {
            e.printStackTrace();
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
}
