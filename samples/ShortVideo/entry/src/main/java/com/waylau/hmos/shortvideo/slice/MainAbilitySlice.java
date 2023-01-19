package com.waylau.hmos.shortvideo.slice;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.VideoInfoBean;
import com.waylau.hmos.shortvideo.provider.VideoPageSliderProvider;
import com.waylau.hmos.shortvideo.util.Tools;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.PageSlider;
import ohos.agp.components.TabList;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.utils.TextTool;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, MainAbilitySlice.class.getSimpleName());

    // 视频信息列表
    private final List<VideoInfoBean> listVideoInfo = new ArrayList<>();
    private PageSlider pageSlider;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化UI组件
        initUi();
    }

    private void initUi() {
        // 初始化TabList标签栏
        TabList tabList = (TabList) findComponentById(ResourceTable.Id_tab_list);
        TabList.Tab tab = tabList.new Tab(getContext());
        tab.setText("首页");
        tabList.addTab(tab, true); // 默认选中
        TabList.Tab tab2 = tabList.new Tab(getContext());
        tab2.setText("✚");
        tabList.addTab(tab2);
        TabList.Tab tab3 = tabList.new Tab(getContext());
        tab3.setText("我");
        tabList.addTab(tab3);

        // 各个Tab的宽度也会根据TabList的宽度而平均分配
        tabList.setFixedMode(true);

        // 初始化轮播图
        /*
        ShootMainTopBanner shootMainTopBanner = new ShootMainTopBanner(this);
        shootMainTopBanner.setImages(ResourceTable.Media_pic01,
                        ResourceTable.Media_pic02,
                        ResourceTable.Media_pic03);
        shootMainTopBanner.start();

         */
        pageSlider = (PageSlider) findComponentById(ResourceTable.Id_page_slider);

        // 判断数据是否为空，为空使用初始的数据，否则就是迁移的数据。
        // json数据
        String resourcePath = "resources/rawfile/videoinfo.json";
        String  videosJson = Tools.getInstance().getJsonFileToString(this, resourcePath);

        // json字符串转成对象集合
        List<VideoInfoBean> videoInfoBeans = ZSONArray.stringToClassList(videosJson, VideoInfoBean.class);
        listVideoInfo.clear();
        listVideoInfo.addAll(videoInfoBeans);

        // 设置页面适配器
        pageSlider.setProvider(new VideoPageSliderProvider(this, pageSlider, listVideoInfo));
        // 添加监听
        pageSlider.addPageChangedListener(new ChangedListener());
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    /**
     * 切换监听
     */
    class ChangedListener implements PageSlider.PageChangedListener{

        /**
         *  选择新页面的回调
         *
         * @param i     指示所显示页的位置索引。
         * @param v     指示页的位置偏移量。值范围是(0,1]。0表示显示相同的页面; 1表示显示目标页面。
         * @param i1    指示所显示页面的位置偏移像素数。
         */
        @Override
        public void onPageSliding(int i, float v, int i1) {
            HiLog.info(LABEL_LOG, "onPageSliding: i: %{public}s; v: %{public}s; i1: %{public}s;", i, v, i1);
        }

        /**
         * 页面幻灯片状态更改时回调
         * @param i     指示页面状态。该值可以是0、1或2，分别表示页处于空闲状态、拖动状态或滑动状态。
         */
        @Override
        public void onPageSlideStateChanged(int i) {
            HiLog.info(LABEL_LOG, "onPageSlideStateChanged: i: %{public}s", i);
        }

        /**
         * 页面滑动时回调
         *
         * @param i     指示所选页的索引。
         */
        @Override
        public void onPageChosen(int i) {
            HiLog.info(LABEL_LOG, "onPageChosen: i: %{public}s", i);

            // 无限滑动
            int index = i;
            int dataSize = listVideoInfo.size();
            if(index == dataSize - 1){
                index = 1;
                pageSlider.setCurrentPage(index, false);
            }
            if(index == 0){
                index = dataSize - 2;
                pageSlider.setCurrentPage(index, false);
            }

        }
    }
}
