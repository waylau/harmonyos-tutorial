package com.waylau.hmos.tablist.slice;

import com.waylau.hmos.tablist.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.TabList;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 获取TabList
        TabList tabList = (TabList) findComponentById(ResourceTable.Id_tab_list);

        // TabList中添加Tab
        TabList.Tab tab1 = tabList.new Tab(getContext());
        tab1.setText("Tab1");
        tabList.addTab(tab1);

        TabList.Tab tab2 = tabList.new Tab(getContext());
        tab2.setText("Tab2");
        tabList.addTab(tab2);

        TabList.Tab tab3 = tabList.new Tab(getContext());
        tab3.setText("Tab3");
        tabList.addTab(tab3);

        TabList.Tab tab4 = tabList.new Tab(getContext());
        tab4.setText("Tab4");
        tabList.addTab(tab4);

        // 设置FixedMode
        tabList.setFixedMode(true);

        // 初始化选中的Tab
        tabList.selectTab(tab1);

        // 设置响应焦点变化
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                // 当某个Tab从未选中状态变为选中状态时的回调
                HiLog.info(LABEL_LOG, "%{public}s, onSelected", tab.getText());
            }

            @Override
            public void onUnselected(TabList.Tab tab) {
                // 当某个Tab从选中状态变为未选中状态时的回调
                HiLog.info(LABEL_LOG, "%{public}s, onUnselected", tab.getText());
            }

            @Override
            public void onReselected(TabList.Tab tab) {
                // 当某个Tab已处于选中状态，再次被点击时的状态回调
                HiLog.info(LABEL_LOG, "%{public}s, onReselected", tab.getText());
            }
        });    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
