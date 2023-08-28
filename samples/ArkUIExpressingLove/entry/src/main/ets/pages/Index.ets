@Entry
@Component
struct Index {
  private swiperController: SwiperController = new SwiperController()


  build() {
    Column() {
      Swiper(this.swiperController) {
        Image($r('app.media.001'))

        Image($r('app.media.002'))

        Image($r('app.media.003'))
      }
      .index(0)
      .autoPlay(true) // 自动播放
      .indicator(true) // 默认开启指示点
      .loop(true) // 默认开启循环播放
      .duration(50)
      .vertical(true) // 默认横向切换
      .itemSpace(0)
    }.height('100%').width('100%').backgroundColor(0x3d3d3d)
  }
}