package com.waylau.hmos.adaptiveboxlayout.slice;

import com.waylau.hmos.adaptiveboxlayout.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.AdaptiveBoxLayout;
import ohos.agp.components.Button;
import ohos.agp.components.Text;

public class MainAbilitySlice extends AbilitySlice {
  @Override
  public void onStart(Intent intent) {
    super.onStart(intent);
    super.setUIContent(ResourceTable.Layout_ability_main);

    // 获取布局
    AdaptiveBoxLayout adaptiveBoxLayout = findComponentById(ResourceTable.Id_adaptive_box_layout);

    // 获取Button组件
    Button buttonAddRule = findComponentById(ResourceTable.Id_add_rule_btn);
    buttonAddRule.setClickedListener(
        (component -> {
          // 添加规则
          adaptiveBoxLayout.addAdaptiveRule(100, 2000, 3);

          // 更新布局
          adaptiveBoxLayout.postLayout();
        }));

    // 获取Button组件
    Button buttonRemoveRule = findComponentById(ResourceTable.Id_remove_rule_btn);
    buttonRemoveRule.setClickedListener(
        (component -> {
          // 移除规则
          adaptiveBoxLayout.removeAdaptiveRule(100, 2000, 3);

          // 更新布局
          adaptiveBoxLayout.postLayout();
        }));
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
