// 导入context
import context from '@ohos.application.context';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(50)
          .fontWeight(FontWeight.Bold)

        // 添加按钮，启动Ability
        Button('启动')
          .fontSize(40)
          .onClick(this.explicitStartAbility) // 显示启动Ability
      }
      .width('100%')
    }
    .height('100%')
  }

  // 显示启动Ability
  async explicitStartAbility() {
    try {
      // 在启动Ability时指定了abilityName和bundleName
      let want = {
        deviceId: "",
        bundleName: "com.waylau.hmos.arkuiwantstartability",
        abilityName: "SecondAbility"
      };
      let context = getContext(this) as context.AbilityContext;
      await context.startAbility(want);
      console.info(`explicit start ability succeed`);
    } catch (error) {
      console.info(`explicit start ability failed with ${error.code}`);
    }
  }
}

