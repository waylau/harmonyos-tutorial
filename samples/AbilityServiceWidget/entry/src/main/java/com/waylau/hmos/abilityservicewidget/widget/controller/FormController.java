package com.waylau.hmos.abilityservicewidget.widget.controller;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.app.Context;

/**
 * The api set for form controller.
 */
public abstract class FormController {
    /**
     * Context of ability
     */
    protected final Context context;

    /**
     * The name of current form service widget
     */
    protected final String formName;

    /**
     * The dimension of current form service widget
     */
    protected final int dimension;

    public FormController(Context context, String formName, Integer dimension) {
        this.context = context;
        this.formName = formName;
        this.dimension = dimension;
    }

    /**
     * Bind data for a form
     *
     * @return ProviderFormInfo
     */
    public abstract ProviderFormInfo bindFormData();

    /**
     * Update form data
     *
     * @param formId the id of service widget to be updated
     * @param vars   the data to update for service widget, this parameter is optional
     */
    public abstract void updateFormData(long formId, Object... vars);

    /**
     * Called when receive service widget message event
     *
     * @param formId  form id
     * @param message the message context sent by service widget message event
     */
    public abstract void onTriggerFormEvent(long formId, String message);

    /**
     * Get the destination ability slice to route
     *
     * @param intent intent of current page slice
     * @return the destination ability slice name to route
     */
    public abstract Class<? extends AbilitySlice> getRoutePageSlice(Intent intent);
}
