// 引用相关的类
import { speechRecognizer } from '@kit.CoreSpeechKit'
import { BusinessError } from '@kit.BasicServicesKit'
import { PromptAction } from '@kit.ArkUI'
import AudioCapturer from '../service/AudioCapturer'

let asrEngine: speechRecognizer.SpeechRecognitionEngine

const TAG = 'AsrDemo'

@Entry
@Component
struct Index {
  @State outputText: string = ''
  @State uiContext: UIContext = this.getUIContext()
  @State promptAction: PromptAction = this.uiContext.getPromptAction()
  @State sessionId: string = '123456'
  private mAudioCapturer: AudioCapturer = new AudioCapturer()
  @State sessionId2: string = '1234567'

  build() {
    Column() {
      // 展示生成的文本
      Text(this.outputText)
        .width('90%')
        .backgroundColor('#d2d2d2')
        .height(100)
        .padding(20)
        .align(Alignment.TopStart)
        .borderRadius(5)
        .margin({ top: 20 })

      // 操作按钮
      Button('创建引擎')
        .fontSize(20)
        .type(ButtonType.Capsule)
        .width('80%')
        .height(50)
        .margin(10)
        .onClick(() => {
          try {
            // 创建引擎
            this.createEngine()

            // 设置延迟
            this.sleep(500).then(() => {
              // 设置监听器
              this.setListener()

              // 提示信息
              this.promptAction.showToast({
                message: 'CreateEngine succeeded!',
                duration: 2000
              })
            })
          } catch (error) {
            this.outputText = `Failed to CreateEngine, message: ${error.message}`

            // 提示信息
            this.promptAction.showToast({
              message: 'CreateEngine failed!',
              duration: 2000
            })
          }

        })

      Button('开始录音')
        .fontSize(20)
        .type(ButtonType.Capsule)
        .width('80%')
        .height(50)
        .margin(10)
        .onClick(() => {
          // 执行录音
          this.startRecording()
            .then(() => {
              this.promptAction.showToast({
                message: 'start Recording',
                duration: 2000
              })
            })
            .catch((error: BusinessError) => {
              this.promptAction.showToast({
                message: `start Recording failed, code: ${error.code}, message: ${error.message}}`,
                duration: 2000
              })
            })
        })

      Button('关闭录音')
        .fontSize(20)
        .type(ButtonType.Capsule)
        .width('80%')
        .height(50)
        .margin(10)
        .onClick(() => {
          try {
            asrEngine.shutdown()

            this.promptAction.showToast({
              message: `shutdown succeeded!`,
              duration: 2000
            })
          } catch (error) {
            this.promptAction.showToast({
              message: `shutdown failed, code: ${error.code}, message: ${error.message}}`,
              duration: 2000
            })
          }
        })
    }
    .height('100%')
    .width('100%')
  }

  // 调用speechRecognizer.createEngine接口创建引擎
  private createEngine() {
    let extraParams: Record<string, Object> = {
      'locate': 'CN',
      'recognizerMode': 'short'
    }

    let createEngineParams: speechRecognizer.CreateEngineParams = {
      language: 'zh-CN',
      online: 1,
      extraParams: extraParams
    }

    speechRecognizer.createEngine(createEngineParams)
      .then((value: speechRecognizer.SpeechRecognitionEngine) => {
        asrEngine = value
        console.info(TAG, 'succeeded in creating engine.')
      })
      .catch((error: BusinessError) => {
        console.error(TAG, `failed in creating engine, code: ${error.code}, message: ${error.message}`)
      })
  }

  // 延迟
  private sleep(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms))
  }

  // 设置监听器
  private setListener() {
    let listener: speechRecognizer.RecognitionListener = {
      // 开始
      onStart:(sessionId: string, eventMessage: string) => {
        console.info(TAG, `onStart sessionId: ${sessionId}, eventMessage: ${eventMessage}`)
        this.outputText = ''
      },
      // 事件
      onEvent(sessionId: string, eventCode: number, eventMessage: string) {
        console.info(TAG, `onEvent sessionId: ${sessionId}, eventCode: ${eventCode}, eventMessage: ${eventMessage}`)
      },
      // 识别结果
      onResult:(sessionId: string, result: speechRecognizer.SpeechRecognitionResult) => {
        console.info(TAG, `onResult sessionId: ${sessionId}, result: ${JSON.stringify(result)}`)
        this.outputText = result.result
      },
      // 识别完成
      onComplete(sessionId: string, eventMessage: string) {
        console.info(TAG, `onComplete sessionId: ${sessionId}, eventMessage: ${eventMessage}`)
      },
      // 错误
      onError(sessionId: string, eventCode: number, eventMessage: string) {
        console.info(TAG, `onError sessionId: ${sessionId}, eventCode: ${eventCode}, eventMessage: ${eventMessage}`)
      }
    }

    // 在引擎上设置监听器
    asrEngine.setListener(listener)
  }

  // 执行录音
  private async startRecording() {
    // 设置监听器
    this.setListener()

    let audioInfo: speechRecognizer.AudioInfo = {
      audioType: 'pcm',
      sampleRate: 16000,
      soundChannel: 1,
      sampleBit: 16
    }

    let extraParams: Record<string, Object> = {
      'recognitionMode': 0,
      'vadBegin': 2000,
      'vadEnd': 3000,
      'maxAudioDuration': 20000
    }

    let startParams: speechRecognizer.StartParams = {
      sessionId: this.sessionId,
      audioInfo: audioInfo,
      extraParams: extraParams
    }

    // 调用asrEngine.startListening接口执行录音
    try {
      asrEngine.startListening(startParams)
    } catch (error) {
      this.outputText = `Message: ${error.message}`
    }

    // 获取音频数据
    let data: ArrayBuffer;
    this.mAudioCapturer.init((dataBuffer: ArrayBuffer) => {
      data = dataBuffer
      let uint8Array: Uint8Array = new Uint8Array(data)

      // 将音频流写入引擎
      try {
        asrEngine.writeAudio(this.sessionId2, uint8Array)
      } catch (error) {
        this.outputText = `Message: ${error.message}`
      }
    })

  }
}