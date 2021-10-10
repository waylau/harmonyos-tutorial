package com.waylau.hmos.douyin.provider;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.constant.Constants;
import com.waylau.hmos.douyin.model.SettingComponentTag;
import com.waylau.hmos.douyin.model.SettingModel;

import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;

import java.util.List;

/**
 * Video setting list processing class
 */
public class VideoSettingProvider extends SettingProvider {
    private final List<SettingModel> settingList;
    private int focusFlag = 0;
    private final Component.FocusChangedListener focusChangedListener =
            (component, hasFocus) -> {
                SettingComponentTag tag = (SettingComponentTag) component.getTag();
                if (hasFocus) {
                    if (Constants.SETTING_OPTION_LEVER2.equals(tag.getLever())) {
                        focusFlag = 1;
                        Button optionName = (Button) component.findComponentById(
                                ResourceTable.Id_video_setting_option_button);
                        optionName.setTextColor(new Color(Color.getIntColor("#E5000000")));
                        ShapeElement background = new ShapeElement();
                        background.setCornerRadius(80);
                        background.setRgbColor(new RgbColor(241, 243, 245, 204));
                        background.setStroke(2, new RgbColor(255, 255, 255));
                        component.setBackground(background);
                    }
                    if (Constants.SETTING_OPTION_LEVER1.equals(tag.getLever())) {
                        ListContainer parentList =
                                (ListContainer) slice.findComponentById(ResourceTable.Id_video_setting_container);
                        parentList.executeItemClick(
                                component, parentList.getIndexForComponent(component), component.getId());
                    }
                } else {
                    if (Constants.SETTING_OPTION_LEVER2.equals(tag.getLever())) {
                        Button optionName = (Button) component.findComponentById(
                                ResourceTable.Id_video_setting_option_button);
                        optionName.setTextColor(new Color(Color.getIntColor("#E5FFFFFF")));
                        ShapeElement background = new ShapeElement();
                        background.setCornerRadius(80);
                        background.setRgbColor(new RgbColor(241, 243, 245, 51));
                        background.setStroke(2, new RgbColor(255, 255, 255));
                        component.setBackground(background);
                    }
                }
            };

    public VideoSettingProvider(
            AbilitySlice slice,
            List<SettingModel> list,
            int parentType,
            Component.KeyEventListener itemOnKeyListener) {
        super(slice, parentType, itemOnKeyListener);
        this.settingList = list;
    }

    @Override
    public int getCount() {
        return settingList == null ? 0 : settingList.size();
    }

    @Override
    public Object getItem(int index) {
        if (settingList != null && index >= 0 && index < settingList.size()) {
            return settingList.get(index);
        }
        return null;
    }

    @Override
    public void removeAllItem() {
        this.settingList.clear();
        this.notifyDataChanged();
    }

    @Override
    public Component getComponent(int index, Component convertComponent, ComponentContainer componentContainer) {
        int xmlId;
        SettingModel item = settingList.get(index);
        if (item.getLever().equals(Constants.SETTING_OPTION_LEVER2)) {
            xmlId = ResourceTable.Layout_video_common_item;
        } else {
            xmlId = ResourceTable.Layout_video_setting_item;
        }
        Component cpt;
        if (convertComponent == null) {
            cpt = LayoutScatter.getInstance(slice).parse(xmlId, null, false);
        } else {
            cpt = convertComponent;
        }

        if (item.getLever().equals(Constants.SETTING_OPTION_LEVER1)) {
            Text optionName = (Text) cpt.findComponentById(ResourceTable.Id_video_setting_option_name);
            optionName.setText(item.getOptionName());
            optionName.setAlpha(0.9f);
            optionName.setTextSize(AttrHelper.fp2px(20, slice));
        } else {
            Button optionName = (Button) cpt.findComponentById(ResourceTable.Id_video_setting_option_button);
            optionName.setText(item.getOptionName());
            optionName.setAlpha(0.8f);
            optionName.setTextSize(AttrHelper.fp2px(24, slice));
        }
        cpt.setTag(new SettingComponentTag(item.getLever(), parentType));

        cpt.setFocusChangedListener(focusChangedListener);
        cpt.setKeyEventListener(itemOnKeyListener);
        if (index == 0 && item.getLever().equals(Constants.SETTING_OPTION_LEVER2) && focusFlag == 0) {
            cpt.requestFocus();
        }

        return cpt;
    }
}
