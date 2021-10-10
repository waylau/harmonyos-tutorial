package com.waylau.hmos.douyin.provider;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.model.SettingComponentTag;
import com.waylau.hmos.douyin.model.VideoModel;

import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;

import java.util.List;

/**
 * Video episode selection list processing class
 */
public class VideoEpisodesSelectProvider extends SettingProvider {
    private final List<VideoModel> episodesList;
    private int focusFlag = 0;
    private final Component.FocusChangedListener focusChangedListener =
            (component, hasFocus) -> {
                Text descTxt = (Text) component.findComponentById(ResourceTable.Id_video_list_item_desc);
                if (hasFocus) {
                    focusFlag = 1;
                    descTxt.setAlpha(0.9f);
                } else {
                    descTxt.setAlpha(0.6f);
                }
            };

    public VideoEpisodesSelectProvider(
            AbilitySlice slice, List<VideoModel> list, int parentType, Component.KeyEventListener itemOnKeyListener) {
        super(slice, parentType, itemOnKeyListener);
        this.episodesList = list;
    }

    @Override
    public int getCount() {
        return episodesList == null ? 0 : episodesList.size();
    }

    @Override
    public Object getItem(int index) {
        if (episodesList != null && index >= 0 && index < episodesList.size()) {
            return episodesList.get(index);
        }
        return null;
    }

    @Override
    public void removeAllItem() {
        this.episodesList.clear();
        this.notifyDataChanged();
    }

    @Override
    public Component getComponent(int index, Component convertComponent, ComponentContainer componentContainer) {
        final Component cpt;
        if (convertComponent == null) {
            cpt =
                    LayoutScatter.getInstance(slice)
                            .parse(ResourceTable.Layout_video_episodes_item, null, false);
        } else {
            cpt = convertComponent;
        }
        VideoModel item = episodesList.get(index);
        Image previewImg = (Image) cpt.findComponentById(ResourceTable.Id_video_list_item_preview_image);
        previewImg.setPixelMap(item.getVideoImage());
        Text totalTimeTxt = (Text) cpt.findComponentById(ResourceTable.Id_video_list_item_total_time);
        totalTimeTxt.setText(item.getVideoTitleTime());
        Text descTxt = (Text) cpt.findComponentById(ResourceTable.Id_video_list_item_desc);
        descTxt.setText(item.getVideoDesc());

        cpt.setTag(new SettingComponentTag(item.getLever(), parentType));
        cpt.setFocusChangedListener(focusChangedListener);
        cpt.setKeyEventListener(itemOnKeyListener);
        if (index == 0 && focusFlag == 0) {
            cpt.requestFocus();
        }
        return cpt;
    }
}
