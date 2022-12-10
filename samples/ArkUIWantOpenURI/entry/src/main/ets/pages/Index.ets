// 导入context
import context from '@ohos.application.context';
import wantConstant from '@ohos.ability.wantConstant';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'

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
        "action": "ohos.want.action.viewData", // 等同于ACTION_VIEW_DATA
        "entities": [ "entity.system.browsable" ], // 等同于ENTITY_BROWSABLE
        "uri": "https://www.test.com:8080/query/student",
        "type": "text/plain"
      }
      let context = getContext(this) as context.AbilityContext;
      await context.startAbility(want)
      console.info(`implicit start ability succeed`)
    } catch (error) {
      console.info(`implicit start ability failed with ${error.code}`)
    }
  }
}