package com.waylau.hmos.liuweiweiaiimagesearch.util;

import com.waylau.hmos.liuweiweiaiimagesearch.slice.MainAbilitySlice;
import ohos.ai.nlu.NluClient;
import ohos.ai.nlu.NluRequestType;
import ohos.ai.nlu.OnResultListener;
import ohos.ai.nlu.ResponseResult;
import ohos.app.Context;
import ohos.eventhandler.InnerEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordSegment {
    private static final boolean IS_ASYNC = true;
    private static final String WORDS = "words";
    private static final int ZERO = 0;
    private static final int TWO = 2;
    private static final int STEP = 8;
    private Context slice;
    private MainAbilitySlice.MyEventHandle handle;

    public void wordSegment(Context context, String requestData, MainAbilitySlice.MyEventHandle myEventHandle) {
        slice = context;
        handle = myEventHandle;

        // 使用NluClient静态类进行初始化，通过异步方式获取服务的连接。
        NluClient.getInstance().init(context, new OnResultListener<Integer>() {
            @Override
            public void onResult(Integer resultCode) {
                if (!IS_ASYNC) {
                    // 同步
                    ResponseResult responseResult = NluClient.getInstance().getWordSegment(requestData,
                            NluRequestType.REQUEST_TYPE_LOCAL);
                    sendResult(responseResult.getResponseResult());
                    release();
                } else {
                    // 异步
                    wordSegmentAsync(requestData);
                }
            }
        }, true);
    }

    private void wordSegmentAsync(String requestData) {
        ResponseResult responseResult = NluClient.getInstance().getWordSegment(requestData,
                NluRequestType.REQUEST_TYPE_LOCAL, new OnResultListener<ResponseResult>() {
                    @Override
                    public void onResult(ResponseResult asyncResult) {
                        sendResult(asyncResult.getResponseResult());
                        release();
                    }
                });
    }

    private void sendResult(String result) {
        List lists = null; // 分词识别结果
        // 将result中分词结果转换成list
        if (result.contains("\"message\":\"success\"")) {
            String words = result.substring(result.indexOf(WORDS) + STEP,
                    result.lastIndexOf("]")).replaceAll("\"", "");
            if ((words == null) || ("".equals(words))) {
                lists = new ArrayList(1);
                lists.add("no keywords"); // 未识别到分词结果，返回"no keywords"
            } else {
                lists = Arrays.asList(words.split(","));
            }
        }

        InnerEvent event = InnerEvent.get(TWO, ZERO, lists);
        handle.sendEvent(event);
    }

    private void release() {
        NluClient.getInstance().destroy(slice);
    }
}