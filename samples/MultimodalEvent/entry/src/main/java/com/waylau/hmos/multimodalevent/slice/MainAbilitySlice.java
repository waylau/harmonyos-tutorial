package com.waylau.hmos.multimodalevent.slice;

import com.waylau.hmos.multimodalevent.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.multimodalinput.event.KeyEvent;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Text text =
                (Text) findComponentById(ResourceTable.Id_text_helloworld);

        // 为按钮设置键盘事件回调
        text.setKeyEventListener(onKeyEvent);
    }

    private Component.KeyEventListener onKeyEvent = new Component.KeyEventListener()
    {
        @Override
        public boolean onKeyEvent(Component component, KeyEvent keyEvent) {
            if (keyEvent.isKeyDown()) {
                // 检测到按键被按下，开发者根据自身需求进行实现
                HiLog.info(LABEL_LOG, "isKeyDown");
            }
            int keycode = keyEvent.getKeyCode();

            HiLog.info(LABEL_LOG, "keycode: %{public}s", keycode);

            switch (keycode) {
                case KeyEvent.KEY_DPAD_CENTER:
                    // 检测到KEY_DPAD_CENTER被按下，开发者根据自身需求进行实现
                    HiLog.info(LABEL_LOG, "KeyEvent.KEY_DPAD_CENTER");
                    break;
                case KeyEvent.KEY_DPAD_LEFT:
                    // 检测到KEY_DPAD_LEFT被按下，开发者根据自身需求进行实现
                    HiLog.info(LABEL_LOG, "KeyEvent.KEY_DPAD_LEFT");
                    break;
                case KeyEvent.KEY_DPAD_UP:
                    // 检测到KEY_DPAD_UP被按下，开发者根据自身需求进行实现
                    HiLog.info(LABEL_LOG, "KeyEvent.KEY_DPAD_UP");
                    break;
                case KeyEvent.KEY_DPAD_RIGHT:
                    // 检测到KEY_DPAD_RIGHT被按下，开发者根据自身需求进行实现
                    HiLog.info(LABEL_LOG, "KeyEvent.KEY_DPAD_RIGHT");
                    break;
                case KeyEvent.KEY_DPAD_DOWN:
                    // 检测到KEY_DPAD_DOWN被按下，开发者根据自身需求进行实现
                    HiLog.info(LABEL_LOG, "KeyEvent.KEY_DPAD_DOWN");
                    break;
                default:
                    HiLog.info(LABEL_LOG, "KeyEvent default");
                    break;
            }

            return true;
        }
    };

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
