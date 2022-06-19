@Entry
@Component
struct Index {
  build() {
    Flex({ direction: FlexDirection.Column, alignItems: ItemAlign.Center, justifyContent: FlexAlign.Center }) {
      Text('爸爸在我心中就像旗帜；他教会我做人与处事的方向；在父亲节这个特别的日子里；我想对爸爸说长大以后我就要成为您')
        .fontSize(50)
        .fontWeight(FontWeight.Bold)
    }
    .width('100%')
    .height('100%')
  }
}