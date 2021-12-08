@Entry
@Component
struct Index {
  private clickReady: Resource = $r('app.media.pic_01') // 可以打卡
  private clickCompleted: Resource = $r('app.media.pic_02') // 打卡完成
  @State imageSrc: Resource = this.clickReady // 设置打卡按钮区状态

  private timeReady: string = '08:08:08' // 可以打卡
  private timeCompleted: string = '华为坂田基地 08:08:08' // 打卡完成
  @State timeSrc: string = this.timeReady // 设置打卡状态区状态

  build() {
    Flex({ direction: FlexDirection.Column, alignItems: ItemAlign.Center, justifyContent: FlexAlign.Center }) {
      Image(this.imageSrc)
        .width(150).height(150)
        .onClick(() => {
            // 打卡切换状态
            this.imageSrc = this.clickCompleted
            this.timeSrc = this.timeCompleted
        })

      Text(this.timeSrc).fontSize(39).fontColor(0xCCCCCC)

    }.width('100%').height('100%')
  }
}