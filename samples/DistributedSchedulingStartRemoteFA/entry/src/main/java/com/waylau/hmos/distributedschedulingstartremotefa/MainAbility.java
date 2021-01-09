package com.waylau.hmos.distributedschedulingstartremotefa;

import com.waylau.hmos.distributedschedulingstartremotefa.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
        requestPermission();
    }

    //获取权限
    //获取权限
    private void requestPermission() {
        String[] permission = {
                "ohos.permission.DISTRIBUTED_DATASYNC"};
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
}
