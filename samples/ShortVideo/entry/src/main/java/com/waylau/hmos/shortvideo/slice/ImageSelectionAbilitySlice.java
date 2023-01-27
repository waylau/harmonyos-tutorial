package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.PortraitInfo;
import com.waylau.hmos.shortvideo.constant.Constants;
import com.waylau.hmos.shortvideo.util.CommonUtil;

import com.waylau.hmos.shortvideo.util.LogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.TableLayout;
import ohos.agp.components.Text;
import ohos.utils.zson.ZSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择页面
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-01-27
 */
public class ImageSelectionAbilitySlice extends AbilitySlice {
    private static final String TAG = ImageSelectionAbilitySlice.class.getSimpleName();
    private TableLayout tableLayout;
    private Text textNoImageTip, text;
    private List<PortraitInfo> portraitList = new ArrayList<>();

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_image_selection);

        initUi();
        initData();

        showImages();
    }

    private void initUi() {
        tableLayout = (TableLayout)findComponentById(ResourceTable.Id_tablelayout_image_list);
        textNoImageTip = (Text)findComponentById(ResourceTable.Id_text_no_image_tip);
    }

    private void initData() {
        String resourcePath = "resources/rawfile/portraitinfo.json";
        String jsonString = CommonUtil.getJsonFileToString(this, resourcePath);

        // json字符串转成对象集合
        List<PortraitInfo> portraitInfos = ZSONArray.stringToClassList(jsonString, PortraitInfo.class);
        portraitList.clear();
        portraitList.addAll(portraitInfos);
    }

    public void showImages() {
        tableLayout.removeAllComponents();
        if (portraitList.isEmpty()) {
            textNoImageTip.setVisibility(Component.VISIBLE);
            textNoImageTip.setText("No image.");
        } else {
            textNoImageTip.setVisibility(Component.HIDE);
        }

        for (PortraitInfo portraitInfo : portraitList) {
            Image img = new Image(this);
            img.setId(portraitInfo.getId());
            img.setHeight(108);
            img.setWidth(300);
            img.setMarginTop(20);
            img.setMarginLeft(20);
            img.setPixelMap(CommonUtil.getImageSource(this.getContext(), portraitInfo.getPortraitPath()));
            img.setScaleMode(Image.ScaleMode.ZOOM_CENTER);

            // 设置点击事件
            img.setClickedListener(component -> {
                int imageId = component.getId();
                LogUtil.info(TAG, "Image onSelected, imageId: " + imageId);

                String getPortraitPath = findPathById(imageId);

                // 回到发起页
                Intent intent = new Intent();
                intent.setParam(Constants.IMAGE_SELECTION, getPortraitPath);
                setResult(intent);

                // 销毁当前页面
                terminate();
            });
            tableLayout.addComponent(img);
        }
    }

    private String findPathById(int imageId) {
        for (PortraitInfo portraitInfo : portraitList) {
            if (imageId == portraitInfo.getId()) {
                return portraitInfo.getPortraitPath();
            }
        }

        return null;
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
