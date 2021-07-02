package com.waylau.hmos.abilityservicewidget.widget.controller;

import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Form controller manager.
 */
public class FormControllerManager {
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, FormControllerManager.class.getName());
    private static final String PACKAGE_PATH = "com.waylau.hmos.abilityservicewidget.widget";
    private static final String SHARED_SP_NAME = "form_info_sp.xml";
    private static final String FORM_NAME = "formName";
    private static final String DIMENSION = "dimension";
    private static FormControllerManager managerInstance = null;
    private final HashMap<Long, FormController> controllerHashMap = new HashMap<>();

    private final Context context;

    private final Preferences preferences;

    /**
     * Constructor with context.
     *
     * @param context instance of Context.
     */
    private FormControllerManager(Context context) {
        this.context = context;
        DatabaseHelper databaseHelper = new DatabaseHelper(this.context.getApplicationContext());
        preferences = databaseHelper.getPreferences(SHARED_SP_NAME);
    }

    /**
     * Singleton mode.
     *
     * @param context instance of Context.
     * @return FormControllerManager instance.
     */
    public static FormControllerManager getInstance(Context context) {
        if (managerInstance == null) {
            synchronized (FormControllerManager.class) {
                if (managerInstance == null) {
                    managerInstance = new FormControllerManager(context);
                }
            }
        }
        return managerInstance;
    }

    /**
     * Save the form id and form name.
     *
     * @param formId    form id.
     * @param formName  form name.
     * @param dimension form dimension
     * @return FormController form controller
     */
    public FormController createFormController(long formId, String formName, int dimension) {
        synchronized (controllerHashMap) {
            if (formId < 0 || formName.isEmpty()) {
                return null;
            }
            HiLog.info(TAG,
                    "saveFormId() formId: " + formId + ", formName: " + formName + ", preferences: " + preferences);
            if (preferences != null) {
                ZSONObject formObj = new ZSONObject();
                formObj.put(FORM_NAME, formName);
                formObj.put(DIMENSION, dimension);
                preferences.putString(Long.toString(formId), ZSONObject.toZSONString(formObj));
                preferences.flushSync();
            }

            // Create controller instance.
            FormController controller = newInstance(formName, dimension, context);

            // Cache the controller.
            if (controller != null) {
                if (!controllerHashMap.containsKey(formId)) {
                    controllerHashMap.put(formId, controller);
                }
            }

            return controller;
        }
    }

    /**
     * Get the form controller instance.
     *
     * @param formId form id.
     * @return the instance of form controller.
     */
    public FormController getController(long formId) {
        synchronized (controllerHashMap) {
            if (controllerHashMap.containsKey(formId)) {
                return controllerHashMap.get(formId);
            }
            Map<String, ?> forms = preferences.getAll();
            String formIdString = Long.toString(formId);
            if (forms.containsKey(formIdString)) {
                ZSONObject formObj = ZSONObject.stringToZSON((String) forms.get(formIdString));
                String formName = formObj.getString(FORM_NAME);
                int dimension = formObj.getIntValue(DIMENSION);
                FormController controller = newInstance(formName, dimension, context);
                controllerHashMap.put(formId, controller);
            }
            return controllerHashMap.get(formId);
        }
    }

    private FormController newInstance(String formName, int dimension, Context context) {
        FormController ctrInstance = null;
        if (formName == null || formName.isEmpty()) {
            HiLog.error(TAG, "newInstance() get empty form name");
            return ctrInstance;
        }
        try {
            String className = PACKAGE_PATH + "." + formName.toLowerCase(Locale.ROOT) + "."
                    + getClassNameByFormName(formName);
            Class<?> clazz = Class.forName(className);
            if (clazz != null) {
                Object controllerInstance = clazz.getConstructor(Context.class, String.class, Integer.class)
                        .newInstance(context, formName, dimension);
                if (controllerInstance instanceof FormController) {
                    ctrInstance = (FormController) controllerInstance;
                }
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalArgumentException | InvocationTargetException
                | IllegalAccessException | ClassNotFoundException | SecurityException exception) {
            HiLog.error(TAG, "newInstance() get exception: " + exception.getMessage());
        }
        return ctrInstance;
    }

    /**
     * Get all form id from the share preference
     *
     * @return form id list
     */
    public List<Long> getAllFormIdFromSharePreference() {
        List<Long> result = new ArrayList<>();
        Map<String, ?> forms = preferences.getAll();
        for (String formId : forms.keySet()) {
            result.add(Long.parseLong(formId));
        }
        return result;
    }

    /**
     * Delete a form controller
     *
     * @param formId form id
     */
    public void deleteFormController(long formId) {
        synchronized (controllerHashMap) {
            preferences.delete(Long.toString(formId));
            preferences.flushSync();
            controllerHashMap.remove(formId);
        }
    }

    private String getClassNameByFormName(String formName) {
        String[] strings = formName.split("_");
        StringBuilder result = new StringBuilder();
        for (String string : strings) {
            result.append(string);
        }
        char[] charResult = result.toString().toCharArray();
        charResult[0] = (charResult[0] >= 'a' && charResult[0] <= 'z') ? (char) (charResult[0] - 32) : charResult[0];
        return String.copyValueOf(charResult) + "Impl";
    }
}
