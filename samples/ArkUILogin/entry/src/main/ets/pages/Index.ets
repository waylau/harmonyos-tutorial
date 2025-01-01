@Entry
@Component
struct Index {

  build() {
    Row() {
      // 子组件都按照垂直方向排列
      Column() {
        // 头像
        Image($r('app.media.waylau_181_181'))
          .height(108)
          .width(108)

        // 账号输入框
        TextInput({ placeholder: '请输入账号' })
          .width(320)
          .height(50)
          .borderRadius(8)
          .backgroundColor('#f9f9f9')
          .margin({ top: '10' })
          .fontSize(27)
          .type(InputType.Normal) // 输入框类型：平常

        // 密码输入框
        TextInput({ placeholder: '请输入密码' })
          .width(320)
          .height(50)
          .borderRadius(8)
          .backgroundColor('#f9f9f9')
          .margin({ top: '10' })
          .fontSize(27)
          .type(InputType.Password) // 输入框类型：密码

        // 登录按钮
        Button('登录', { type: ButtonType.Normal })
          .width('320')
          .height('50')
          .borderRadius(8)
          .backgroundColor('#ffd0da')
          .margin({ top: '10' })
          .fontSize(24)

        // 注册按钮
        Text('注册')
          .fontColor(Color.Black)
          .margin({ top: '10' })
          .fontSize(24)
      }
      .width('100%')
    }
    .height('100%')
  }
}