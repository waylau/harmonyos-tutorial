package com.waylau.hmos.shortvideo;

import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.Image;
import ohos.agp.components.PageSliderProvider;

import java.util.List;

/**
 * Image组件适配器
 */
public class BannerProvider extends PageSliderProvider {

    private List<Image> listData;


    public BannerProvider(List<Image> listData) {
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
        Object obj = listData.get(i);
        componentContainer.addComponent((Component) obj);
        return obj;
    }

    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        componentContainer.removeComponent(listData.get(i));
    }

    @Override
    public boolean isPageMatchToObject(Component component, Object o) {
        return component == o;
    }
}