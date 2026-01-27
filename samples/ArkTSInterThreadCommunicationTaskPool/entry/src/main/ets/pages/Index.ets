import { taskpool } from '@kit.ArkTS';

@Entry
@Component
struct Index {
  @State message: string = '启动任务';

  build() {
    RelativeContainer() {
      Button(this.message)
        .fontSize($r('app.float.page_text_font_size'))
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
        .onClick(() => {
          let task: taskpool.Task = new taskpool.Task(sendData, 1);
          // 设置notice方法接收Task发送的消息
          task.onReceiveData(notice);

          // 执行Task
          taskpool.execute(task);
        })
    }
    .height('100%')
    .width('100%')
  }
}

// 通过Task的sendData方法，即时通知宿主线程信息
@Concurrent
function sendData(count: number): void {
  taskpool.Task.sendData(count + 1);
}

// 用来接收Task发送的消息。
function notice(data: number): void {
  console.info("子线程任务已执行完: ", data);
}