// 导入公共事件管理器
import commonEventManager from '@ohos.commonEventManager';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'
  //用于接收事件数据
  @State eventData: string = ''
  //用于保存创建成功的订阅者对象，后续使用其完成订阅及退订的动作
  private subscriber ;

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(25)
          .fontWeight(FontWeight.Bold)

        // 创建订阅者
        Button(('创建订阅者'), { type: ButtonType.Capsule })
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 10, bottom: 10 })
          .onClick(() => {
            this.createSubscriber()
          })

        // 订阅事件
        Button(('订阅事件'), { type: ButtonType.Capsule })
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 10, bottom: 10 })
          .onClick(() => {
            this.subscriberCommonEvent()
          })

        // 发送事件
        Button(('发送事件'), { type: ButtonType.Capsule })
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 10, bottom: 10 })
          .onClick(() => {
            this.publishCommonEvent()
          })

        // 发送事件
        Button(('取消订阅'), { type: ButtonType.Capsule })
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 10, bottom: 10 })
          .onClick(() => {
            this.unsubscribeCommonEvent()
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

  private createSubscriber() {
    if (this.subscriber) {
      this.message = "subscriber already created";
    } else {
      commonEventManager.createSubscriber({ // 创建订阅者
        events: ["testEvent"] // 指定订阅的事件名称
      }, (err, subscriber) => { // 创建结果的回调
        if (err) {
          this.message = "create subscriber failure"
        } else {
          this.subscriber = subscriber; // 创建订阅成功
          this.message = "create subscriber success";
        }
      })
    }
  }

  private subscriberCommonEvent() {
    if (this.subscriber) {
      // 根据创建的subscriber开始订阅事件
      commonEventManager.subscribe(this.subscriber, (err, data) => {
        if (err) {
          // 异常处理
          this.eventData = "subscribe event failure: " + err;
        } else {
          // 接收到事件
          this.eventData = "subscribe event success: " + JSON.stringify(data);
        }
      })
    } else {
      this.message = "please create subscriber";
    }
  }

  private publishCommonEvent() {
    //发布公共事件
    commonEventManager.publish("testEvent", (err) => { // 结果回调
      if (err) {
        this.message = "publish event error: " + err;
      } else {
        this.message = "publish event with data success";
      }
    })
  }

  private unsubscribeCommonEvent() {
    if (this.subscriber) {
      commonEventManager.unsubscribe(this.subscriber, (err) => { // 取消订阅事件
        if (err) {
          this.message = "unsubscribe event failure: " + err;
        } else {
          this.subscriber = null;
          this.message = "unsubscribe event success";
        }
      })
    } else {
      this.message = "already subscribed";
    }
  }
}