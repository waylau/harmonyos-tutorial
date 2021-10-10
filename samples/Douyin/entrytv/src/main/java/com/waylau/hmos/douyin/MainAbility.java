package com.waylau.hmos.douyin;

import com.waylau.hmos.douyin.slice.VideoPlayAbilitySlice;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * MainAbility
 */
public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        requestPermissionsFromUser(new String[]{"ohos.permission.DISTRIBUTED_DATASYNC"}, 0);

        super.onStart(intent);
        super.setMainRoute(VideoPlayAbilitySlice.class.getName());
        super.addActionRoute("action.video.play", VideoPlayAbilitySlice.class.getName());
    }
}
