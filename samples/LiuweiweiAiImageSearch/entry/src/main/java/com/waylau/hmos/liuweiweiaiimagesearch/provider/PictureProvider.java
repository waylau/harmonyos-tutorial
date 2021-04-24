package com.waylau.hmos.liuweiweiaiimagesearch.provider;

import com.waylau.hmos.liuweiweiaiimagesearch.ResourceTable;
import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.app.Context;

import java.util.Optional;

public class PictureProvider extends BaseItemProvider {
    private int[] pictureLists;
    private Context context;

    /**
     *  picture provider
     *
     * @param pictureLists pictureLists
     * @param context context
     */
    public PictureProvider(int[] pictureLists, Context context) {
        this.pictureLists = pictureLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pictureLists == null ? 0 : pictureLists.length;
    }

    @Override
    public Object getItem(int position) {
        return Optional.of(this.pictureLists[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int var1, Component var2, ComponentContainer var3) {
        ViewHolder viewHolder = null;
        Component component = var2;
        if (component == null) {
            component = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_item_image_layout,
                    null, false);
            viewHolder = new ViewHolder();
            Component componentImage = component.findComponentById(ResourceTable.Id_select_picture_list);
            if (componentImage instanceof Image) {
                viewHolder.image = (Image) componentImage;
            }
            component.setTag(viewHolder);
        } else {
            if (component.getTag() instanceof ViewHolder) {
                viewHolder = (ViewHolder) component.getTag();
            }
        }
        if (viewHolder != null) {
            viewHolder.image.setPixelMap(pictureLists[var1]);
        }
        return component;
    }

    private static class ViewHolder {
        Image image;
    }
}