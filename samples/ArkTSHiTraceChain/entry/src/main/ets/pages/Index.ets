import { BusinessError } from '@kit.BasicServicesKit';
import { hiAppEvent, hilog, hiTraceChain } from '@kit.PerformanceAnalysisKit';

@Entry
@Component
struct Index {

  build() {
    RelativeContainer() {
      Button('点击')
        .fontSize($r('app.float.page_text_font_size'))
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
        .onClick(() => {
          try {
            // 业务开始前，开启分布式跟踪。
            let traceId = hiTraceChain.begin("Write a new app event", hiTraceChain.HiTraceFlag.INCLUDE_ASYNC)
            // 在按钮点击函数中进行事件打点，以记录按钮点击事件
            let eventParams: Record<string, number> = { 'click_time': 100 }
            let eventInfo: hiAppEvent.AppEventInfo = {
              // 事件领域定义
              domain: "button",
              // 事件名称定义
              name: "click",
              // 事件类型定义
              eventType: hiAppEvent.EventType.BEHAVIOR,
              // 事件参数定义
              params: eventParams,
            }
            hiAppEvent.write(eventInfo).then(() => {
              hilog.info(0x0000, 'testTag', `Succeed to write an app event`)
              // 业务结束，关闭分布式跟踪。
              hiTraceChain.end(traceId)
            }).catch((err: BusinessError) => {
              hilog.error(0x0000, 'testTag', `HiAppEvent err.code: ${err.code}, err.message: ${err.message}`)
            })
          } catch (err) {
            console.error(`error message is ${(err as BusinessError).message}`)
          }
        })
    }
    .height('100%')
    .width('100%')
  }
}