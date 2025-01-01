// 导入Emitter
import { emitter } from '@kit.BasicServicesKit';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'
  //用于接收事件数据
  @State eventData: string = ''
  // 定义一个eventId为1的事件
  private event: emitter.InnerEvent = {
    eventId: 1
  };

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(25)
          .fontWeight(FontWeight.Bold)

        // 订阅事件
        Button(('订阅事件'), { type: ButtonType.Capsule })
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 10, bottom: 10 })
          .onClick(() => {
            this.subscriberEvent()
          })

        // 发送事件
        Button(('发送事件'), { type: ButtonType.Capsule })
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 10, bottom: 10 })
          .onClick(() => {
            this.emitEvent()
          })

        // 接收到的事件数据
        Text(this.eventData)
          .fontSize(25)
          .fontWeight(FontWeight.Bold)
      }
      .width('100%')
    }
    .height('100%')
  }

  private subscriberEvent() {
    // 收到eventId为1的事件后执行该回调
    let callback = (eventData: emitter.EventData): void => {
      // 接收到事件
      this.eventData = "subscribe event success: " + JSON.stringify(eventData);
    };

    // 订阅eventId为1的事件
    emitter.on(this.event, callback);

    this.message = "subscriber already created";
  }

  private emitEvent() {
    let eventData: emitter.EventData = {
      data: {
        content: 'waylau.com',
        id: 1,
        isEmpty: false
      }
    };

    // 发送eventId为1的事件，事件内容为eventData
    emitter.emit(this.event, eventData);
  }

}