@Entry
@Component
struct SubWindowPage {
  @State message: string = 'Sub Window Page';

  build() {
    RelativeContainer() {
      Text(this.message)
        .id('SubWindowPageHelloWorld')
        .fontSize(30)
        .fontWeight(FontWeight.Bold)
        .width('100%')
        .height('100%')
        .backgroundColor(Color.Orange)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
    }
    .height('100%')
    .width('100%')
  }
}