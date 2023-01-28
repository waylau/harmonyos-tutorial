package com.waylau.hmos.shortvideo;

import com.waylau.hmos.shortvideo.slice.VideoPublishPageAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class VideoPublishPageAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(VideoPublishPageAbilitySlice.class.getName());
    }
}
