package com.waylau.hmos.douyin.ability;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.constant.Constants;
import com.waylau.hmos.douyin.data.VideoInfoService;
import com.waylau.hmos.douyin.model.DeviceModel;
import com.waylau.hmos.douyin.provider.DeviceItemProvider;
import com.waylau.hmos.douyin.utils.AppUtil;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;

import java.util.List;

/**
 * Remote Device Selection Ability
 */
public class DevicesSelectAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        requestPermissionsFromUser(new String[]{"ohos.permission.DISTRIBUTED_DATASYNC"}, 0);

        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_remote_ability_select_devices);

        this.initPage(intent);
    }

    private void initPage(Intent intent) {
        VideoInfoService videoService = new VideoInfoService(this);
        int currentPlayingIndex = intent.getIntParam(Constants.PARAM_VIDEO_INDEX, 0) + 1;
        Text appName = (Text) findComponentById(ResourceTable.Id_devices_head_app_name);
        appName.setText(ResourceTable.String_entry_MainAbility);
        Text videoName = (Text) findComponentById(ResourceTable.Id_devices_head_video_name);
        String playingEpisodes =
                AppUtil.getStringResource(this, ResourceTable.String_control_playing_episodes)
                        .replaceAll("\\?", String.valueOf(currentPlayingIndex));
        videoName.setText(videoService.getAllVideoInfo().getVideoName() + " " + playingEpisodes);

        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_devices_container);
        List<DeviceModel> devices = AppUtil.getDevicesInfo();
        DeviceItemProvider provider = new DeviceItemProvider(this, devices);
        listContainer.setItemProvider(provider);
        listContainer.setItemClickedListener(
                (container, component, position, id) -> {
                    DeviceModel item = (DeviceModel) listContainer.getItemProvider().getItem(position);
                    Intent intentResult = new Intent();
                    intentResult.setParam(Constants.PARAM_DEVICE_TYPE, item.getDeviceType());
                    intentResult.setParam(Constants.PARAM_DEVICE_ID, item.getDeviceId());
                    intentResult.setParam(Constants.PARAM_DEVICE_NAME, item.getDeviceName());
                    setResult(0, intentResult);
                    this.terminateAbility();
                });
    }
}
