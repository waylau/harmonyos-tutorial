@Entry
@Component
struct Second {
  // 修改变量值为Second
  @State message: string = 'Second';

  build() {
    RelativeContainer() {
      Text(this.message)
        .id('SecondHelloWorld')
        .fontSize(50)
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
    }
    .height('100%')
    .width('100%')
  }
}