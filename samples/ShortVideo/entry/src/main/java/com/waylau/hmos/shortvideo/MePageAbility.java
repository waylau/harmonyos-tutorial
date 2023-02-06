/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */
package com.waylau.hmos.shortvideo;

import com.waylau.hmos.shortvideo.slice.MePageAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * “我的”入口
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-06
 */
public class MePageAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MePageAbilitySlice.class.getName());
    }
}
