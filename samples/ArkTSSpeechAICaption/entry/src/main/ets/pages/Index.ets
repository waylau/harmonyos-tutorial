// 引入相关的类
import { AICaptionComponent, AICaptionController, AICaptionOptions, AudioData } from '@kit.SpeechKit'
import { BusinessError } from '@kit.BasicServicesKit';

@Entry
@Component
struct Index {
  private videoController: VideoController = new VideoController()
  @State isCaptionShown: boolean = false;
  private captionController: AICaptionController = new AICaptionController()
  private captionOptions: AICaptionOptions = {
    initialOpacity: 0.8,
    onPrepared: () => {
      console.info('onPrepared')
    },
    onError: (error) => {
      console.error(`onError code: ${error.code}, message: ${error.message}`)
    }
  }

  build() {
    Column({ space: 16 }) {
      // 视频播放器组件
      Video({
        src: $r('app.media.huawei_mate_40'),
        controller: this.videoController
      })
        .width('100%')
        .height(300)

      // AI 字幕组件
      AICaptionComponent({
        isShown: this.isCaptionShown,
        controller: this.captionController,
        options: this.captionOptions
      })
        .width('100%')
        .height(80)

      // 字幕控制按钮
      Button(this.isCaptionShown ? '关闭字幕' : '开启字幕')
        .onClick(() => {
          this.isCaptionShown = !this.isCaptionShown

          // 开启字幕的时候，就开启识别
          if (this.isCaptionShown) {
            this.startCaptionRecognition()
          }
        })
    }
    .height('100%')
    .width('100%')
    .padding(20)
  }

  // 开启字幕识别
  private startCaptionRecognition() {
    // 读取本地视频的音频数据
    this.getUIContext().getHostContext()?.resourceManager.getMediaContent($r('app.media.huawei_mate_40').id, 0)
      .then((value: Uint8Array) => {
        const audioBuffer = value

        // 分块传入音频数据，避免一次性加载卡顿
        // 每块大小为1280字节
        const bufferSize = 1280
        let offset = 0
        const totalLength = audioBuffer.byteLength

        while (offset < totalLength) {
          const endOffset = Math.min(offset + bufferSize, totalLength)
          const audioChunk = audioBuffer.slice(offset, endOffset)

          // 封装为AudioData，传入控制器
          const audioData: AudioData = {
            data: audioChunk
          }

          this.captionController.writeAudio(audioData)

          offset = endOffset
        }
      })
      .catch((error: BusinessError) => {
        console.error(`getMediaContent failed, code: ${error.code}, message: ${error.message}`)
      })
  }
}