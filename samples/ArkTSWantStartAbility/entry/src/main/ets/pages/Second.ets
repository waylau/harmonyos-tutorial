@Entry
@Component
struct Second {
  // 修改变量值为Second
  @State message: string = 'Second'

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(50)
          .fontWeight(FontWeight.Bold)
      }
      .width('100%')
    }
    .height('100%')
  }
}