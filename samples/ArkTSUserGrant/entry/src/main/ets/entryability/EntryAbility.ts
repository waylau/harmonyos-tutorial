import hilog from '@ohos.hilog';
import Ability from '@ohos.application.Ability'
import Window from '@ohos.window'

export default class EntryAbility extends Ability {
    onCreate(want, launchParam) {
        hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
        hilog.info(0x0000, 'testTag', '%{public}s', 'Ability onCreate');
        hilog.info(0x0000, 'testTag', '%{public}s', 'want param:' + JSON.stringify(want) ?? '');
        hilog.info(0x0000, 'testTag', '%{public}s', 'launchParam:' + JSON.stringify(launchParam) ?? '');
    }

    onDestroy() {
        hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
        hilog.info(0x0000, 'testTag', '%{public}s', 'Ability onDestroy');
    }

    onWindowStageCreate(windowStage: Window.WindowStage) {
        // Main window is created, set main page for this ability
        hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
        hilog.info(0x0000, 'testTag', '%{public}s', 'Ability onWindowStageCreate');

        // 权限校验
        var context = this.context
        let array: Array<string> = ["ohos.permission.CAMERA"];
        //requestPermissionsFromUser会判断权限的授权状态来决定是否唤起弹窗
        context.requestPermissionsFromUser(array).then(function (data) {
            console.log("data type:" + typeof (data));
            console.log("data:" + data);
            console.log("data permissions:" + data.permissions);
            console.log("data result:" + data.authResults);

            // 0代表授权成功；-1代表授权失败
            if (data.authResults == [0]) {
                // 加载界面
                windowStage.loadContent('pages/Index', (err, data) => {
                    if (err.code) {
                        hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.ERROR);
                        hilog.error(0x0000, 'testTag', 'Failed to load the content. Cause: %{public}s', JSON.stringify(err) ?? '');
                        return;
                    }
                    hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
                    hilog.info(0x0000, 'testTag', 'Succeeded in loading the content. Data: %{public}s', JSON.stringify(data) ?? '');
                });
            }

        }, (err) => {
            console.error('Failed to start ability', err.code);
        });
    }

    onWindowStageDestroy() {
        // Main window is destroyed, release UI related resources
        hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
        hilog.info(0x0000, 'testTag', '%{public}s', 'Ability onWindowStageDestroy');
    }

    onForeground() {
        // Ability has brought to foreground
        hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
        hilog.info(0x0000, 'testTag', '%{public}s', 'Ability onForeground');
    }

    onBackground() {
        // Ability has back to background
        hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
        hilog.info(0x0000, 'testTag', '%{public}s', 'Ability onBackground');
    }
}
