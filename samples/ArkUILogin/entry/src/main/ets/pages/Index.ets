@Entry
@Component
struct Index {
  build() {
    // 子组件都按照垂直方向排列
    Column() {
      // 页面的标志是图片
      Image($r('app.media.waylau_181_181'))
        .width(181)
        .height(181)
        .margin({ top: 80, bottom: 80 })

      // 账号
      TextInput({ placeholder: '请输入账号' })
        .maxLength(80)
        .type(InputType.Number)

      // 密码
      TextInput({ placeholder: '请输入密码' })
        .maxLength(80)
        .type(InputType.Password)

      // 登录
      Button(('登录'), { type: ButtonType.Capsule })
        .width(140)
        .fontSize(40)
        .fontWeight(FontWeight.Medium)
        .margin({ top: 80, bottom: 20 })

      // 注册
      Text('注册')
        .fontColor(Color.Blue)
        .fontSize(40)
        .fontWeight(FontWeight.Medium)
    }
    .width('100%')
  }
}