package com.waylau.hmos.todo;

import com.waylau.hmos.todo.slice.EditAbilitySlice;
import com.waylau.hmos.todo.slice.MainAbilitySlice;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * Category list ability
 */
public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

        // 使用addActionRounte方法添加路由
        addActionRoute("action.editor", EditAbilitySlice.class.getName());
    }
}