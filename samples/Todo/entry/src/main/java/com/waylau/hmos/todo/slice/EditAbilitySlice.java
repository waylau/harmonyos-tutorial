package com.waylau.hmos.todo.slice;

import com.waylau.hmos.todo.ResourceTable;
import com.waylau.hmos.todo.custom.data.CustomData;
import com.waylau.hmos.todo.datamodel.Category;
import com.waylau.hmos.todo.views.adapter.CategoryListItemProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;

import java.util.List;

public class EditAbilitySlice extends AbilitySlice {
     @Override
    public void onStart(Intent intent) {
         super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_editor_ablilty);

         // 点击事件
         Text timeText = (Text)findComponentById(ResourceTable.Id_time);
         timeText.setClickedListener(listener -> showDatePicker());
    }

    private void showDatePicker() {
        DatePicker datePicker = (DatePicker)findComponentById(ResourceTable.Id_date_pick);
        datePicker.setVisibility(DatePicker.VISIBLE);

        datePicker.setValueChangedListener(
                new DatePicker.ValueChangedListener() {
                    @Override
                    public void onValueChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Text timeText = (Text)findComponentById(ResourceTable.Id_time);
                        timeText.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                    }
                }
        );


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
