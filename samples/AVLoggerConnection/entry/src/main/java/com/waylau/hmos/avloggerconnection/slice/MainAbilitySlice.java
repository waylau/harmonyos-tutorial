package com.waylau.hmos.avloggerconnection.slice;

import com.waylau.hmos.avloggerconnection.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.app.Context;
import ohos.media.photokit.common.AVLoggerConnectionClient;
import ohos.media.photokit.metadata.AVLoggerConnection;
import ohos.utils.net.Uri;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Scanner scanner = new Scanner(this);
        
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private class Scanner implements AVLoggerConnectionClient {
        private AVLoggerConnection zScanConn;
        private String filePath;
        private String mimeType;

        public Scanner(Context context) {
            // 实例化
            zScanConn = new AVLoggerConnection(context, this);
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }


        @Override
        public void onLoggerConnected() {
            // 服务回调执行扫描
            zScanConn.performLoggerFile(filePath, mimeType);
        }

        @Override
        public void onLogCompleted(String s, Uri uri) {
            // 回调函数返回URI的值
            // 断开扫描服务
            zScanConn.disconnect();
        }

    }
}
