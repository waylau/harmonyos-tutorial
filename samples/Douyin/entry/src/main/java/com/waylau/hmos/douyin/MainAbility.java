package com.waylau.hmos.douyin;

import com.waylau.hmos.douyin.constant.RouteRegister;
import com.waylau.hmos.douyin.ability.VideoPlayAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.continuation.IContinuationRegisterManager;
import ohos.aafwk.content.Intent;
import ohos.bundle.IBundleManager;

/**
 * Entry to the main interface of the program
 */
public class MainAbility extends Ability {
    private static final int REQUEST_CODE = 1;
    private final String[] permissionLists = new String[]{"ohos.permission.READ_MEDIA", "ohos.permission.WRITE_MEDIA"};

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(VideoPlayAbilitySlice.class.getName());
        super.addActionRoute(RouteRegister.SLICE_SAMPLE, VideoPlayAbilitySlice.class.getName());

        verifyPermissions();
    }

    private void verifyPermissions() {
        for (String permissionList : permissionLists) {
            int result = verifySelfPermission(permissionList);
            if (result != IBundleManager.PERMISSION_GRANTED) {
                requestPermissionsFromUser(permissionLists, REQUEST_CODE);
            }
        }
    }

    @Override
    public IContinuationRegisterManager getContinuationRegisterManager() {
        return super.getContinuationRegisterManager();
    }
}
