// 导入common、Want
import { common, Want } from '@kit.AbilityKit';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World';

  build() {
    RelativeContainer() {
      Text(this.message)
        .id('HelloWorld')
        .fontSize(50)
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
        .onClick(this.explicitStartAbility) // 设置点击事件，显示启动Ability
    }
    .height('100%')
    .width('100%')
  }

  // 显示启动Ability
  explicitStartAbility() {
    try {
      // 在启动Ability时指定了abilityName和bundleName
      let want: Want  = {
        deviceId: "",
        bundleName: "com.waylau.hmos.arkuiwantstartability",
        abilityName: "SecondAbility"
      };

      // 获取UIAbility的上下文信息
      let context = getContext(this) as common.UIAbilityContext;

      // 启动UIAbility实例
      context.startAbility(want);
      console.info(`explicit start ability succeed`);
    } catch (error) {
      console.info(`explicit start ability failed with ${error.code}`);
    }
  }
}