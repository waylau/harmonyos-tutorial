import {PhotoView} from '@ohos/photoview';

@Entry
@Component
struct Index {
  @State data: PhotoView.Model = new PhotoView.Model();
  @State data1: PhotoView.Model = new PhotoView.Model();
  @State data2: PhotoView.Model = new PhotoView.Model();
  private swiperController: SwiperController = new SwiperController()

  aboutToAppear() {
    this.data
      .setImageResource($r('app.media.harmony'))
    this.data1
      .setImageResource($r('app.media.harmony1'))
    this.data2
      .setImageResource($r('app.media.harmony2'))

  }

  build() {
    Column() {
      Swiper(this.swiperController) {
        PhotoView({model: this.data})
        PhotoView({model: this.data1})
        PhotoView({model: this.data2})
      }
      .index(0)
      .autoPlay(true) // 自动播放
      .indicator(true) // 默认开启指示点
      .loop(true) // 默认开启循环播放
      .duration(50)
      .vertical(true) // 默认横向切换
      .itemSpace(0)
      .onChange((index: number) => {
        this.data.resetMatrix()
        this.data1.resetMatrix()
        this.data2.resetMatrix()
        console.info("ViewPager"+index.toString())
      })
    }.height('100%').width('100%').backgroundColor(0x3d3d3d)
  }
}