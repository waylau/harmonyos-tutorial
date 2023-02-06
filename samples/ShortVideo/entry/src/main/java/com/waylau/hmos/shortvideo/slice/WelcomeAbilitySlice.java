/*
 * Copyright (c) waylau.com, 2023. All rights reserved.
 */

package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.ResourceTable;

import com.waylau.hmos.shortvideo.bean.UserInfo;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.store.UserInfoRepository;
import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

/**
 * 视频发布页面
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-26
 */
public class WelcomeAbilitySlice extends AbilitySlice {
    private static final String TAG = WelcomeAbilitySlice.class.getSimpleName();

    private UserInfo userInfo = new UserInfo();

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_welcome);

        // 初始化数据存储
        userInfo = UserInfoRepository.query(userInfo.getUsername());
        // 检查登陆
        checkForUse();
    }

    private void checkForUse() {
        if (userInfo.getUsername() == null) {
            presentForResult(new RegisterAbilitySlice(), new Intent(), 0);
        } else {
            // 展示主页
            Intent intent = new Intent();
            intent.setParam(Constants.LOGIN_USERNAME, userInfo.getUsername());
            intent.setParam(Constants.IMAGE_SELECTION, userInfo.getPortraitPath());

            // 更新到存储
            UserInfoRepository.insert(userInfo);

            present(new MainAbilitySlice(), intent);

            terminate();
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

    @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        LogUtil.info(TAG, "onResult requestCode:" + requestCode + "; resultIntent:" + resultIntent);
        if (requestCode == 0) {
            userInfo.setUsername(resultIntent.getStringParam(Constants.LOGIN_USERNAME));
            userInfo.setPortraitPath(resultIntent.getStringParam(Constants.IMAGE_SELECTION));
            // 再次检查登陆
            checkForUse();
        } else {
            terminate();
        }
    }
}
