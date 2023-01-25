package com.waylau.hmos.shortvideo;

import com.waylau.hmos.shortvideo.slice.VideoUploadPageAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class VideoUploadPageAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(VideoUploadPageAbilitySlice.class.getName());
    }
}
