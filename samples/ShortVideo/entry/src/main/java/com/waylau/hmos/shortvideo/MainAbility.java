/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo;

import com.waylau.hmos.shortvideo.slice.WelcomeAbilitySlice;
import com.waylau.hmos.shortvideo.util.DatabaseUtil;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * 主入口
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-23
 */
public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(WelcomeAbilitySlice.class.getName());

        // 初始化数据存储
       DatabaseUtil.initStore(this);
    }

    @Override
    protected void onStop() {
        DatabaseUtil.deleteStore();
        super.onStop();
    }

}
