@Entry
@Component
struct Index {
  @State message: string = 'Hello World'

  build() {
    Row() {
      Column() {
        Video({
          src: $rawfile('video_11.mp4'), // 视频播放源的路径
          previewUri: $r('app.media.book01'), // 视频未播放时的预览图片路径
          currentProgressRate: 0.75, //视频播放倍速
          controller: new VideoController()
        }).width(400).height(600)
          .autoPlay(true) // 自动播放
          .controls(true) // 显示视频控制器
      }
      .width('100%')
    }
    .height('100%')
  }
}