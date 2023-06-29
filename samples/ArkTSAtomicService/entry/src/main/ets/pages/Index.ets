@Entry
@Component
struct Index {
  @State message: string = 'Hello'
  @State messageService: string = 'Atomic Service'

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(50)
          .fontWeight(FontWeight.Bold)

        Text(this.messageService)
          .fontSize(35)
          .fontWeight(Color.Orange)
      }
      .width('100%')
    }
    .height('100%')
  }
}