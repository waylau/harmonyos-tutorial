import Window from '@ohos.window';
import Logger from '../util/Logger';

class ScreenUtil {
  setScreenSize(): void {
    Window.getLastWindow(getContext(this))
      .then((windowClass) => {
        globalThis.screenWidth = px2fp(windowClass.getWindowProperties().windowRect.width);
        globalThis.screenHeight = px2fp(windowClass.getWindowProperties().windowRect.height);
        globalThis.windowClass = windowClass;
      })
      .catch((error) => {
        Logger.error('[ScreenUtil] Failed to obtain the window size. Cause: ' + JSON.stringify(error));
      })
  }
}

export default new ScreenUtil();
