package com.waylau.hmos.abilityservicewidget.widget.photowidget;

import com.waylau.hmos.abilityservicewidget.ResourceTable;
import com.waylau.hmos.abilityservicewidget.widget.controller.FormController;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.HashMap;
import java.util.Map;

public class PhotoWidgetImpl extends FormController {
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, PhotoWidgetImpl.class.getName());
    private static final int DEFAULT_DIMENSION_2X2 = 2;
    private static final Map<Integer, Integer> RESOURCE_ID_MAP = new HashMap<>();

    static {
        RESOURCE_ID_MAP.put(DEFAULT_DIMENSION_2X2, ResourceTable.Layout_form_image_with_information_photowidget_2_2);
    }

    public PhotoWidgetImpl(Context context, String formName, Integer dimension) {
        super(context, formName, dimension);
    }

    @Override
    public ProviderFormInfo bindFormData() {
        HiLog.info(TAG, "bind form data when create form");
        return new ProviderFormInfo(RESOURCE_ID_MAP.get(dimension), context);
    }

    @Override
    public void updateFormData(long formId, Object... vars) {
        HiLog.info(TAG, "update form data timing, default 30 minutes");
    }

    @Override
    public void onTriggerFormEvent(long formId, String message) {
        HiLog.info(TAG, "handle card click event.");
    }

    @Override
    public Class<? extends AbilitySlice> getRoutePageSlice(Intent intent) {
        HiLog.info(TAG, "get the default page to route when you click card.");
        return null;
    }
}