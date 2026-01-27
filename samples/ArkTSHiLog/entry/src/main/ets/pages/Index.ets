// 导入模块
import { hilog } from '@kit.PerformanceAnalysisKit';

@Entry
@Component
struct Index {
  build() {
    Row() {
      Column() {
        // 添加按钮，以响应用户点击
        Button() {
          Text('打印日志')
            .fontSize(30)
            .fontWeight(FontWeight.Bold)
        }
        .type(ButtonType.Capsule)
        .margin({
          top: 20
        })
        .backgroundColor('#0D9FFB')
        .width('40%')
        .height('5%')
        // 跳转按钮绑定onClick事件，点击时打印日志
        .onClick(() => {
          hilog.isLoggable(0xFF00, "testTag", hilog.LogLevel.INFO);
          hilog.info(0xFF00, "testTag", "%{public}s World %{public}d", "hello", 3);
          // 设置应用日志最低打印级别，设置完成后，低于Warn级别的日志将无法打印
          hilog.info(0x0000, 'testTag', 'this is an info level log');
          hilog.error(0x0000, 'testTag', 'this is an error level log');
        })
      }
      .width('100%')
    }
    .height('100%')
  }
}