package com.waylau.hmos.liuweiweiaiimagesearch.slice;

import com.waylau.hmos.liuweiweiaiimagesearch.ResourceTable;
import com.waylau.hmos.liuweiweiaiimagesearch.provider.PictureProvider;
import com.waylau.hmos.liuweiweiaiimagesearch.util.WordRecognition;
import com.waylau.hmos.liuweiweiaiimagesearch.util.WordSegment;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.TextField;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainAbilitySlice extends AbilitySlice {
    private static final int LIST_CONTAINER_ID_SHOW = ResourceTable.Id_picture_list_show;
    private static final int LIST_CONTAINER_ID_MATCH = ResourceTable.Id_picture_list_match;
    private static final int NEG_ONE = -1;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private Context slice;
    private EventRunner runner;
    private MyEventHandle myEventHandle;
    private int[] pictureLists = new int[]{ResourceTable.Media_1, ResourceTable.Media_2,
            ResourceTable.Media_3, ResourceTable.Media_4, ResourceTable.Media_5,
            ResourceTable.Media_6, ResourceTable.Media_7, ResourceTable.Media_8};
    private Component selectComponent;
    private int selectPosition;
    private Button button;
    private TextField textField;
    private Map<Integer, String> imageInfos;
    private int[] matchPictures;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        slice = MainAbilitySlice.this;

        // 展示图片列表
        setSelectPicture(pictureLists, LIST_CONTAINER_ID_SHOW);

        matchPictures = new int[]{ResourceTable.Media_2,
                ResourceTable.Media_3,ResourceTable.Media_4};
        // 展示图片
        setSelectPicture(matchPictures, LIST_CONTAINER_ID_MATCH);

        // 所有图片通用文字识别
        wordRecognition();

        // 设置需要分词的语句
        Component componentText = findComponentById(ResourceTable.Id_word_seg_text);
        if (componentText instanceof TextField) {
            textField = (TextField) componentText;
        }

        // 点击按钮进行文字识别
        Component componentSearch = findComponentById(ResourceTable.Id_button_search);
        if (componentSearch instanceof Button) {
            button = (Button) componentSearch;
            button.setClickedListener(listener -> wordSegment());
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

    // 设置图片选择区域
    private void setSelectPicture(int[] pictures, int id) {
        // 获取图片
        PictureProvider newsTypeAdapter = new PictureProvider(pictures, this);

        Component componentById = findComponentById(id);
        if (componentById instanceof ListContainer) {
            ListContainer listContainer = (ListContainer) componentById;
            listContainer.setItemProvider(newsTypeAdapter);
        }
    }

    // 通用文字识别
    private void wordRecognition() {
        initHandler();
        WordRecognition wordRecognition = new WordRecognition();
        wordRecognition.setParams(slice, pictureLists, myEventHandle);
        wordRecognition.sendResult(null);
    }

    // 分词
    private void wordSegment() {
        // 组装关键词，作为分词对象
        String requestData = "{\"text\":" + textField.getText() + ",\"type\":0}";
        initHandler();
        new WordSegment().wordSegment(slice, requestData, myEventHandle);
    }

    // 匹配图片
    private void matchImage(List<String> list) {
        Set<Integer> matchSets = new HashSet<>();
        for (String str: list) {
            for (Integer key : imageInfos.keySet()) {
                if (imageInfos.get(key).indexOf(str) != NEG_ONE) {
                    matchSets.add(key);
                }
            }
        }
        // 获得匹配的图片
        matchPictures = new int[matchSets.size()];
        int i = 0;
        for (int match: matchSets) {
            matchPictures[i] = match;
            i++;
        }
        // 展示图片
        setSelectPicture(matchPictures, LIST_CONTAINER_ID_MATCH);
    }

    private void initHandler() {
        runner = EventRunner.getMainEventRunner();
        if (runner == null) {
            return;
        }
        myEventHandle = new MyEventHandle(runner);
    }

    public class MyEventHandle extends EventHandler {
        MyEventHandle(EventRunner runner) throws IllegalArgumentException {
            super(runner);
        }

        @Override
        protected void processEvent(InnerEvent event) {
            super.processEvent(event);
            int eventId = event.eventId;
            if (eventId == ONE) {
                // 通用文字识别
                if (event.object instanceof Map) {
                    imageInfos = (Map) event.object;
                }
            }
            if (eventId == TWO) {
                // 分词
                if (event.object instanceof List) {
                    List<String> lists = (List) event.object;
                    if ((lists.size() > ZERO) && (!"no keywords".equals(lists.get(ZERO)))) {
                        // 根据输入关键词 匹配图片
                        matchImage(lists);
                    }
                }
            }
        }
    }
}