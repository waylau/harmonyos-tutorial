package com.waylau.hmos.jsfa;

import ohos.ace.ability.AceAbility;
import ohos.aafwk.content.Intent;

public class MainAbility extends AceAbility {
    @Override
    public void onStart(Intent intent) {
        // setInstanceName("JSComponentName");  // config.json配置文件中module.js.name的标签值。
        super.onStart(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
