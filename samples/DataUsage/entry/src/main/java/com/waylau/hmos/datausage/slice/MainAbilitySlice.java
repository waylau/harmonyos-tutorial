package com.waylau.hmos.datausage.slice;

import com.waylau.hmos.datausage.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.data.usage.DataUsage;
import ohos.data.usage.MountState;
import ohos.data.usage.Volume;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONObject;

import java.util.List;
import java.util.Optional;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Text text;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 添加点击事件来触发访问数据
        Button buttonQuery = (Button) findComponentById(ResourceTable.Id_button_query);
        buttonQuery.setClickedListener(listener -> this.doQuery());

        text = (Text) findComponentById(ResourceTable.Id_text);
    }

    private void doQuery() {
        HiLog.info(LABEL_LOG, "before doQuery");

        // 查询
        // 获取默认存储设备挂载状态
        MountState status = DataUsage.getDiskMountedStatus();
        String statusString = ZSONObject.toZSONString(status);
        text.append("MountState: " + statusString + "\n");

        // 默认存储设备是否为可插拔设备
        boolean isDiskPluggable = DataUsage.isDiskPluggable();
        text.append("isDiskPluggable: " + isDiskPluggable + "\n");

        // 默认存储设备是否为虚拟设备
        boolean isDiskEmulated = DataUsage.isDiskEmulated();
        text.append("isDiskEmulated: " + isDiskEmulated + "\n");

        // 获取存储设备列表
        Optional<List<Volume>> listOptional = DataUsage.getVolumes();
        if (listOptional.isPresent()) {
            text.append("Volume:\n");

            listOptional.get().forEach(volume -> {
                // 查询Volume的信息
                String volUuid = volume.getVolUuid();
                String description = volume.getDescription();
                boolean isEmulated = volume.isEmulated();
                boolean isPluggable = volume.isPluggable();

                String volumeString = ZSONObject.toZSONString(volume);
                text.append(volumeString + "\n");

                HiLog.info(LABEL_LOG, "volUuid: %{public}s, description: %{public}s, " +
                                "isEmulated: %{public}s, isPluggable: %{public}s",
                        volUuid, description, isEmulated, isPluggable);
            });
        }

        HiLog.info(LABEL_LOG, "end doQuery");
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
