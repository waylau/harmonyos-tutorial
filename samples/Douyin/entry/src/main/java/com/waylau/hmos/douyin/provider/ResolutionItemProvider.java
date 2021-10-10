package com.waylau.hmos.douyin.provider;

import com.waylau.hmos.douyin.ResourceTable;
import com.waylau.hmos.douyin.model.ResolutionModel;

import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.app.Context;

import java.util.List;

/**
 * Resolution information list processing class
 */
public class ResolutionItemProvider extends BaseItemProvider {
    private final Context context;
    private final List<ResolutionModel> list;

    public ResolutionItemProvider(Context context, List<ResolutionModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int index) {
        if (list != null && index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return new ResolutionModel();
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public Component getComponent(int index, Component component, ComponentContainer componentContainer) {
        final Component cpt;
        if (component == null) {
            cpt = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_remote_video_quality_item, null, false);
        } else {
            cpt = component;
        }
        ResolutionModel item = list.get(index);
        Text qualityName = (Text) cpt.findComponentById(ResourceTable.Id_video_quality_name);
        qualityName.setText(item.getName());

        if (index == list.size() - 1) {
            Component divider = cpt.findComponentById(ResourceTable.Id_device_item_divider);
            divider.setVisibility(Component.INVISIBLE);
        }

        return cpt;
    }
}
