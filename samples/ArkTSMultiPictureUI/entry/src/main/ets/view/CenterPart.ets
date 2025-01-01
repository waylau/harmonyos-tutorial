/**
 * 中部图片显示区
 */
@Component
export struct CenterPart {

  build() {
    Row() {
      Column() {
        Text('中部图片显示区')
          .fontSize('24fp')
      }
    }
    .height('100%')
    .width('100%')
    .borderColor(Color.Black)
    .borderWidth('2vp')
  }
}