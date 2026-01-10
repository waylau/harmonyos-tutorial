// 导入common、Want
import { Want, common } from '@kit.AbilityKit';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World';

  build() {
    RelativeContainer() {
      Text(this.message)
        .id('HelloWorld')
        .fontSize($r('app.float.page_text_font_size'))
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
        .onClick(() => {
          // 显示启动Ability
          this.explicitStartAbility()
        })
    }
    .height('100%')
    .width('100%')
  }

  // 显示启动Ability
  private explicitStartAbility() {
    try {
      // 在启动Ability时指定bundleName和abilityName
      let want: Want = {
        deviceId: "",
        bundleName: "com.waylau.hmos.arktswantstartability",
        abilityName: "SecondAbility"
      }

      // 获取UIAbility的上下文信息
      let context = this.getUIContext().getHostContext() as common.UIAbilityContext;

      // 启动UIAbility实例
      context.startAbility(want);

      console.info("explicitStartAbility succeed")
    } catch (error) {
      console.error(`explicitStartAbility failed: ${error.code}`)
    }
  }
}