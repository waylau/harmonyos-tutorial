// 导入Emitter
import { emitter } from '@kit.BasicServicesKit';
import { HashMap, JSON } from '@kit.ArkTS';


class EventData {
  name: string = ""
}


@Entry
@Component
struct Index {
  //用于接收事件数据
  @State eventData: string = ''
  // 定义一个eventId为1的事件
  private addEvent: emitter.InnerEvent = {
    eventId: 1
  };
  // 定义一个eventId为0的事件
  private deleteEvent: emitter.InnerEvent = {
    eventId: 0
  };
  private goodsMap: HashMap<string, string> = new HashMap<string, string>();

  build() {
    Row() {
      Column() {
        Row() {
          Checkbox({ name: 'checkbox1', group: 'checkboxGroup' })
            .onChange((value: boolean) => { // 设置选中事件
              this.handleCheckbox('可乐', value);
            })
          Text('可乐').fontSize(20)
        }

        Row() {
          Checkbox({ name: 'checkbox2', group: 'checkboxGroup' })
            .onChange((value: boolean) => { // 设置选中事件
              this.handleCheckbox('鸡翅', value);
            })
          Text('鸡翅').fontSize(20)
        }

        Row() {
          Checkbox({ name: 'checkbox3', group: 'checkboxGroup' })
            .onChange((value: boolean) => { // 设置选中事件
              this.handleCheckbox('薯条', value);
            })
          Text('薯条').fontSize(20)
        }

        Row() {
          Checkbox({ name: 'checkbox4', group: 'checkboxGroup' })
            .onChange((value: boolean) => { // 设置选中事件
              this.handleCheckbox('汉堡', value);
            })
          Text('汉堡').fontSize(20)
        }

        // 接收到的事件数据
        Text(this.eventData)
          .fontSize(25)
          .fontWeight(FontWeight.Bold)
      }
      .width('100%')
    }
    .height('100%')
  }

  aboutToAppear() {
    console.log("aboutToAppear");
    this.subscriberEvent();
  }

  private handleCheckbox(name: string, value: boolean) {
    if (value) {
      this.addGoods(name);
    } else {
      this.deleteGoods(name);
    }
  }

  private subscriberEvent() {
    // 收到eventId为1的事件后执行该回调
    let addEventCallback = (eventData: emitter.EventData): void => {
      // 接收到事件
      let data: EventData =  eventData.data as EventData;
      let name  = data.name;
      this.goodsMap.set(name, name);

      this.eventData = "add: " + name + "; total: " + this.mapToSting(this.goodsMap);
    };

    // 订阅eventId为1的事件
    emitter.on(this.addEvent, addEventCallback);


    // 收到eventId为0的事件后执行该回调
    let deleteEventCallback = (eventData: emitter.EventData): void => {
      // 接收到事件
      let data: EventData =  eventData.data as EventData;
      let name  = data.name;
      this.goodsMap.remove(name);

      this.eventData = "delete: " + name + "; total: " + this.mapToSting(this.goodsMap);
    };

    // 订阅eventId为0的事件
    emitter.on(this.deleteEvent, deleteEventCallback);

    console.log("subscriber already created");
  }

  private addGoods(name: string) {
    let eventData: emitter.EventData = {
      data: {
        name: name
      }
    };

    // 发送eventId为1的事件，事件内容为eventData
    emitter.emit(this.addEvent, eventData);
  }

  private deleteGoods(name: string) {
    let eventData: emitter.EventData = {
      data: {
        name: name
      }
    };

    // 发送eventId为0的事件，事件内容为eventData
    emitter.emit(this.deleteEvent, eventData);
  }

  private mapToSting(hashMap : HashMap<string, string>): string {
    let str = '';
    hashMap.forEach((value?: string, key?: string) => {
      str = str + value + ' ';
    });

    return str;
  }

}