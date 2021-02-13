package com.waylau.hmos.nfccontroller.slice;

import com.waylau.hmos.nfccontroller.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.nfc.NfcController;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private NfcController nfcController;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 查询本机是否支持NFC
        if (this.getContext() != null) {
            nfcController = NfcController.getInstance(this.getContext());
        } else {
            return;
        }

        boolean isAvailable = nfcController.isNfcAvailable();
        if (isAvailable) {
            // 调用查询NFC是否打开接口，返回值为NFC是否是打开的状态
            boolean isOpen = nfcController.isNfcOpen();
            HiLog.info(LABEL_LOG, "isOpen: %{public}s", isOpen);
        } else {
            HiLog.info(LABEL_LOG, "NFC is not available");
        }

        HiLog.info(LABEL_LOG, "openNfc");
        boolean isOpenNfc = nfcController.openNfc();
        HiLog.info(LABEL_LOG, "openNfc: %{public}s", isOpenNfc);
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
