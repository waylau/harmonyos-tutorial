package com.waylau.hmos.geoconvert.slice;

import com.waylau.hmos.geoconvert.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.location.*;
import ohos.utils.zson.ZSONObject;

import java.io.IOException;
import java.util.List;


public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);


        // 为按钮设置点击事件回调
        Button buttonOn =
                (Button) findComponentById(ResourceTable.Id_button_get);
        buttonOn.setClickedListener(listener -> {
            try {
                getInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        text = (Text) findComponentById(ResourceTable.Id_text);
    }

    private void getInfo() throws IOException {
        HiLog.info(LABEL_LOG, "before getInfo");

        // 实例化GeoConvert对象，所有与(逆)地理编码转化能力相关的功能API，都是通过GeoConvert提供的
        GeoConvert geoConvert = new GeoConvert();

        // 坐标转化地理位置信息
        int maxItems = 3;
        double latitude = 23.048062D;
        double longitude = 114.211902D;
        List<GeoAddress> addressList = geoConvert.getAddressFromLocation(latitude, longitude, maxItems);

        // 显示地理位置信息
        showAddress(addressList);

        // 位置描述转化坐标
        addressList = geoConvert.getAddressFromLocationName("华为", maxItems);

        // 显示地理位置信息
        showAddress(addressList);

        HiLog.info(LABEL_LOG, "end getInfo");
    }

    private void showAddress(List<GeoAddress> addressList) {
        HiLog.info(LABEL_LOG, "showAddress");

        for (GeoAddress address : addressList) {
            String addressInfo = ZSONObject.toZSONString(address);

            text.append(addressInfo + "\n");

            HiLog.info(LABEL_LOG, "addressInfo:%{public}s", addressInfo);
        }
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
