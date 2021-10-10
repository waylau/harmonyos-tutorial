package com.waylau.hmos.douyin.components;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.utils.AppUtil;

import ohos.agp.components.AttrHelper;
import ohos.agp.components.Component;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.LayoutScatter;
import ohos.app.Context;

/**
 * Episode Selection Dialog
 */
public class EpisodesSelectionDialog extends DependentLayout {
    public EpisodesSelectionDialog(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        setVisibility(INVISIBLE);
        DependentLayout episodesDialog =
                (DependentLayout)
                        LayoutScatter.getInstance(getContext())
                                .parse(ResourceTable.Layout_remote_ability_episodes, null, false);

        int popupHeight = AppUtil.getScreenInfo(getContext()).getPointYToInt()
                - AttrHelper.vp2px(56 * 2, getContext())
                - AttrHelper.vp2px(16, getContext())
                - AttrHelper.vp2px(64, getContext());
        DependentLayout anthologyRoot =
                (DependentLayout) episodesDialog.findComponentById(ResourceTable.Id_episodes_root);
        anthologyRoot.setHeight(popupHeight);
        Component closeBtn = episodesDialog.findComponentById(ResourceTable.Id_episodes_close);
        closeBtn.setClickedListener(component -> setVisibility(INVISIBLE));
        episodesDialog.setClickedListener(
                component -> findComponentById(ResourceTable.Id_video_quality_list_parent).setVisibility(INVISIBLE));
        addComponent(episodesDialog);
        setQualitySelectPopup(anthologyRoot);
    }

    private void setQualitySelectPopup(DependentLayout parent) {
        Component qualitySelectText = parent.findComponentById(ResourceTable.Id_episodes_quality);
        qualitySelectText.setClickedListener(component -> {
            Component listParent = findComponentById(ResourceTable.Id_video_quality_list_parent);
            int isVisible = listParent.getVisibility() == VISIBLE ? INVISIBLE : VISIBLE;
            listParent.setVisibility(isVisible);
        });
        Component qualitySelectBtn = parent.findComponentById(ResourceTable.Id_episodes_quality_select);
        qualitySelectBtn.setClickedListener(
                component -> {
                    Component listParent = findComponentById(ResourceTable.Id_video_quality_list_parent);
                    int isVisible = listParent.getVisibility() == VISIBLE ? INVISIBLE : VISIBLE;
                    listParent.setVisibility(isVisible);
                });
    }
}
