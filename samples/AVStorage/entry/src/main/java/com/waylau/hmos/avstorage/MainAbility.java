package com.waylau.hmos.avstorage;

import com.waylau.hmos.avstorage.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

        // 显式声明需要使用的权限
        requestPermission();
    }

    // 显式声明需要使用的权限
    private void requestPermission() {
        String[] permission = {
                "ohos.permission.READ_USER_STORAGE"};
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
