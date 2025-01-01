@Entry
@Component
struct Index {

  private currentVideoSrc: string = 'video_01.mp4'
  private currentPreviewUri: string = 'app.media.video_cover_01'
  @State videoSrc: Resource = $rawfile(this.currentVideoSrc)
  @State previewUri: Resource = $r(this.currentPreviewUri)
  @State curRate: PlaybackSpeed = PlaybackSpeed.Speed_Forward_1_00_X
  @State isAutoPlay: boolean = false
  @State showControls: boolean = true
  controller: VideoController = new VideoController()

  build() {
    Column() {
      Video({
        src: this.videoSrc,
        previewUri: this.previewUri,
        currentProgressRate: this.curRate,
        controller: this.controller
      })
        .width('100%')
        .height(600)
        .autoPlay(this.isAutoPlay)
        .controls(this.showControls)
        .onStart(() => {
          console.info('onStart')
        })
        .onPause(() => {
          console.info('onPause')
        })
        .onFinish(() => {
          console.info('onFinish')
        })
        .onError(() => {
          console.info('onError')
        })
        .onStop(() => {
          console.info('onStop')
        })
        .onPrepared((e?: DurationObject) => {
          if (e != undefined) {
            console.info('onPrepared is ' + e.duration)
          }
        })
        .onSeeking((e?: TimeObject) => {
          if (e != undefined) {
            console.info('onSeeking is ' + e.time)
          }
        })
        .onSeeked((e?: TimeObject) => {
          if (e != undefined) {
            console.info('onSeeked is ' + e.time)
          }
        })
        .onUpdate((e?: TimeObject) => {
          if (e != undefined) {
            console.info('onUpdate is ' + e.time)
          }
        })

      Row() {
        Button('src').onClick(() => {
          if (this.currentVideoSrc === 'video_01.mp4') {
            this.currentVideoSrc = 'video_00.mp4'
            this.currentPreviewUri = 'app.media.video_cover_00'
          } else {
            this.currentVideoSrc = 'video_01.mp4'
            this.currentPreviewUri = 'app.media.video_cover_01'
          }

          this.videoSrc = $rawfile(this.currentVideoSrc) // 切换视频源
          this.previewUri = $r(this.currentPreviewUri) // 切换视频预览海报
        }).margin(5)

        Button('controls').onClick(() => {
          this.showControls = !this.showControls // 切换是否显示视频控制栏
        }).margin(5)
      }

      Row() {
        Button('start').onClick(() => {
          this.controller.start() // 开始播放
        }).margin(2)
        Button('pause').onClick(() => {
          this.controller.pause() // 暂停播放
        }).margin(2)
        Button('stop').onClick(() => {
          this.controller.stop() // 结束播放
        }).margin(2)
        Button('reset').onClick(() => {
          this.controller.reset() // 重置AVPlayer
        }).margin(2)
        Button('setTime').onClick(() => {
          this.controller.setCurrentTime(10, SeekMode.Accurate) // 精准跳转到视频的10s位置
        }).margin(2)
      }

      Row() {
        Button('rate 0.75').onClick(() => {
          this.curRate = PlaybackSpeed.Speed_Forward_0_75_X // 0.75倍速播放
        }).margin(5)
        Button('rate 1').onClick(() => {
          this.curRate = PlaybackSpeed.Speed_Forward_1_00_X // 原倍速播放
        }).margin(5)
        Button('rate 2').onClick(() => {
          this.curRate = PlaybackSpeed.Speed_Forward_2_00_X // 2倍速播放
        }).margin(5)
      }
    }
  }
}

interface DurationObject {
  duration: number;
}

interface TimeObject {
  time: number;
}