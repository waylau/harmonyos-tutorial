@Entry
@Component
struct Index {
  @State message: string = '父亲节我想对父亲说'
  @State say: string = '燃烧的岁月，已将父亲的青春焚尽，' +
  '但那坚强的信念，仍在父亲额头闪光，' +
  '父亲在我心目中永远高大伟岸，' +
  '父亲的爱护、关怀和勉励将伴我信步风雨人生。' +
  '祝父亲快乐！爸爸我爱您。'

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(50)
          .fontWeight(FontWeight.Bold)
        Text(this.say)
          .fontSize(25).fontColor(Color.Orange)
      }
      .width('100%')
    }
    .height('100%')
  }
}