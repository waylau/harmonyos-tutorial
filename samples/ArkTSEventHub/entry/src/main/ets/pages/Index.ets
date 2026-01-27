import { common } from '@kit.AbilityKit';
@Entry
@Component
struct Index {
  private context = getContext(this) as common.UIAbilityContext;


  build() {
    RelativeContainer(){
      Button('点击触发事件')
        .id('HelloWorld')
        .fontSize($r('app.float.page_text_font_size'))
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
        .onClick(() => {
          this.eventHubFunc();
        })
    }
    .height('100%')
    .width('100%')
  }

  eventHubFunc(): void {
    // 不带参数触发自定义“event-test”事件
    this.context.eventHub.emit('event-test');
    // 带1个参数触发自定义“event-test”事件
    this.context.eventHub.emit('event-test', 1);
    // 带2个参数触发自定义“event-test”事件
    this.context.eventHub.emit('event-test', 2, 'test');
    // 开发者可以根据实际的业务场景设计事件传递的参数
  }
}