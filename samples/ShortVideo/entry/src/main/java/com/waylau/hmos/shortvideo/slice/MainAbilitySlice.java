package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.TabList;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化UI组件
        initUi();
    }

    private void initUi() {
        // 初始化TabList标签栏
        TabList tabList = (TabList) findComponentById(ResourceTable.Id_tab_list);
        TabList.Tab tab = tabList.new Tab(getContext());
        tab.setText("首页");
        tabList.addTab(tab);
        TabList.Tab tab2 = tabList.new Tab(getContext());
        tab2.setText("✚");
        tabList.addTab(tab2);
        TabList.Tab tab3 = tabList.new Tab(getContext());
        tab3.setText("我");
        tabList.addTab(tab3);

        // 各个Tab的宽度也会根据TabList的宽度而平均分配
        tabList.setFixedMode(true);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
