package com.waylau.hmos.douyin.provider;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.model.DeviceModel;
import com.waylau.hmos.douyin.utils.AppUtil;

import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.app.Context;

import java.util.List;

/**
 * Device information list processing class
 */
public class DeviceItemProvider extends BaseItemProvider {
    private final Context context;
    private final List<DeviceModel> list;

    /**
     * Initialization
     */
    public DeviceItemProvider(Context context, List<DeviceModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list != null && position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return new DeviceModel();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer componentContainer) {
        final Component cpt;
        if (convertComponent == null) {
            cpt = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_remote_device_item, null, false);
        } else {
            cpt = convertComponent;
        }
        DeviceModel deviceItem = list.get(position);
        Text deviceName = (Text) cpt.findComponentById(ResourceTable.Id_device_item_name);
        deviceName.setText(deviceItem.getDeviceName());
        Image deviceIcon = (Image) cpt.findComponentById(ResourceTable.Id_device_item_icon);
        AppUtil.setDeviceIcon(deviceItem.getDeviceType(), deviceIcon);

        if (position == list.size() - 1) {
            Component divider = cpt.findComponentById(ResourceTable.Id_device_item_divider);
            divider.setVisibility(Component.INVISIBLE);
        }

        return cpt;
    }
}
