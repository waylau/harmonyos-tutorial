import hilog from '@ohos.hilog';
import Ability from '@ohos.application.Ability'
import Window from '@ohos.window'

export default class EntryAbility extends Ability {
  onCreate(want, launchParam) {
    hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
    hilog.info(0x0000, 'testTag', '%{public}s', 'Ability onCreate');
    hilog.info(0x0000, 'testTag', '%{public}s', 'want param:' + JSON.stringify(want) ?? '');
    hilog.info(0x0000, 'testTag', '%{public}s', 'launchParam:'+ JSON.stringify(launchParam) ?? '');
  }

  onDestroy() {
    hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
    hilog.info(0x0000, 'testTag', '%{public}s', 'Ability onDestroy');
  }

  onWindowStageCreate(windowStage: Window.WindowStage) {
    // Main window is created, set main page for this ability
    hilog.isLoggable(0x0000, 'testTag', hilog.LogLevel.INFO);
    hilog.info(0x0000, 'testTag', '%{public}s', 'Ability onWindowStageCreate');

    /*
    // 获取应用主窗口
    let windowClass = null;
    windowStage.getMainWindow((err, data) => {
      if (err.code) {
        console.error('Failed to obtain the main window. Cause: ' + JSON.stringify(err));
        return;
      }
      windowClass = data;
      console.info('Succeeded in obtaining the main window. Data: ' + JSON.stringify(data));

      // 实现沉浸式效果，设置应用主窗口为全屏显示。
      let isFullScreen = true;
      windowClass.setFullScreen(isFullScreen, (err) => {
        if (err) {
          console.error('Failed to enable the full-screen mode. Cause:' + JSON.stringify(err));
          return;
        }
        console.info('Succeeded in enabling the full-screen mode.');
      });

    })
*/
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
