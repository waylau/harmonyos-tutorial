// 导入模块
import hiTraceMeter from '@ohos.hiTraceMeter';

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
          let traceCount = 0;
          // 第一个跟踪任务开始
          hiTraceMeter.startTrace("myTraceTest", 1001);
          // 开始计数任务
          traceCount++;
          hiTraceMeter.traceByValue("myTestCount", traceCount);
          // 业务流程
          console.log(`myTraceTest running, taskid: 1001`);

          // 第二个跟踪任务开始，同时第一个跟踪的同名任务还没结束，出现了并行执行，对应接口的taskId需要不同。
          hiTraceMeter.startTrace("myTraceTest", 1002);
          // 开始计数任务
          traceCount++;
          hiTraceMeter.traceByValue("myTestCount", traceCount);
          // 业务流程
          console.log(`myTraceTest running, taskid: 1002`);

          // 结束taskId为1001的跟踪任务
          hiTraceMeter.finishTrace("myTraceTest", 1001);
          // 结束taskId为1002的跟踪任务
          hiTraceMeter.finishTrace("myTraceTest", 1002);
        })
    }
    .height('100%')
    .width('100%')
  }
}