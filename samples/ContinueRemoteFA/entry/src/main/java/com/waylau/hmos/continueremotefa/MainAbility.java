package com.waylau.hmos.continueremotefa;

import com.waylau.hmos.continueremotefa.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;

import java.util.ArrayList;
import java.util.List;

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
                "ohos.permission.DISTRIBUTED_DATASYNC",
                "ohos.permission.DISTRIBUTED_DEVICE_STATE_CHANGE",
                "ohos.permission.GET_DISTRIBUTED_DEVICE_INFO",
                "ohos.permission.GET_BUNDLE_INFO"};
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
        return false;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        return false;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        return false;
    }

    @Override
    public void onCompleteContinuation(int i) {

    }
}
