// 导入context
import context from '@ohos.application.context';
// 导入wantConstant
import wantConstant from '@ohos.ability.wantConstant';

@Entry
@Component
struct Index {
  build() {
    Row() {
      Column() {
        // 添加按钮，启动Ability
        Button('启动')
          .fontSize(40)
          .onClick(this.implicitStartAbility) // 隐示启动Ability
      }
      .width('100%')
    }
    .height('100%')
  }

  // 隐示启动Ability
  async implicitStartAbility() {
    try {
      let want = {
        // 调用应用管理
        "action": wantConstant.Action.ACTION_MANAGE_APPLICATIONS_SETTINGS
      }
      let context = getContext(this) as context.AbilityContext;
      await context.startAbility(want)
      console.info(`implicit start ability succeed`)
    } catch (error) {
      console.info(`implicit start ability failed with ${error.code}`)
    }
  }
}
