package com.waylau.hmos.continueremotefacollaboration;

import com.waylau.hmos.continueremotefacollaboration.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.security.SystemPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备迁移与回迁
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class MainAbility extends Ability implements IAbilityContinuation {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
        requestPermission();
    }

    //获取权限
    private void requestPermission() {
        String[] permission = {
                SystemPermission.DISTRIBUTED_DATASYNC
        };
        List<String> applyPermissions = new ArrayList<>();
        for (String element : permission) {
            if (verifySelfPermission(element) != 0) {
                if (canRequestPermission(element)) {
                    applyPermissions.add(element);
                }
            }
        }
        requestPermissionsFromUser(applyPermissions.toArray(new String[0]), 0);
    }


    @Override
    public boolean onStartContinuation() {
        // 重写
        return true;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        // 重写
        return true;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        // 重写
        return true;
    }

    @Override
    public void onCompleteContinuation(int i) {

    }

}
