/**
 * 图片预览列表
 */
@Component
export struct PreviewList {

  build() {
    Column() {
      Text('图片预览列表')
        .fontSize('24fp')
    }
    .height('47vp')
    .borderColor(Color.Black)
    .borderWidth('2vp')
  }
}