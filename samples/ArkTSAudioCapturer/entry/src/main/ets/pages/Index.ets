import { audio } from '@kit.AudioKit';
import { fileIo } from '@kit.CoreFileKit';

// 录音文件缓存位置
const filePath: string = getContext().cacheDir + '/result_48000_1.pcm';

@Entry
@Component
struct Index {
  @State message: string = '待开始';
  private audioRendererInfo: audio.AudioRendererInfo = {
    usage: audio.StreamUsage.STREAM_USAGE_MOVIE,
    rendererFlags: 0
  };

  // 音频渲染器实例
  private audioRenderer: audio.AudioRenderer | null = null

  private audioStreamInfo: audio.AudioStreamInfo = {
    samplingRate: audio.AudioSamplingRate.SAMPLE_RATE_48000, // 采样率
    channels: audio.AudioChannel.CHANNEL_2, // 通道
    sampleFormat: audio.AudioSampleFormat.SAMPLE_FORMAT_S16LE, // 采样格式
    encodingType: audio.AudioEncodingType.ENCODING_TYPE_RAW // 编码格式
  };

  private audioCapturerInfo: audio.AudioCapturerInfo = {
    source: audio.SourceType.SOURCE_TYPE_MIC,
    capturerFlags: 0
  };

  // 音频采集器实例
  private audioCapturer: audio.AudioCapturer | null = null

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(50)
          .fontWeight(FontWeight.Bold)

        Button(('开始录音'), { type: ButtonType.Capsule })
          .width(240)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            this.startCapturer(filePath)
          })

        Button(('结束录音'), { type: ButtonType.Capsule })
          .width(240)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            // 获取结果
            this.stopCapturer();
          })

        Button(('播放录音'), { type: ButtonType.Capsule })
          .width(240)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {

            this.startRenderer(filePath)
          })

        Button(('停止播放'), { type: ButtonType.Capsule })
          .width(240)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            this.stopRenderer();
          })
      }
      .width('100%')
    }
    .height('100%')
  }

  // 获取音频采集器实例
  async getAudioCapturer() {
    // 如果已经存在，直接返回
    if (this.audioCapturer) {
      return this.audioCapturer
    }
    // 创建音频采集器
    const audioCapturer = await audio.createAudioCapturer({
      streamInfo: this.audioStreamInfo,
      capturerInfo: this.audioCapturerInfo
    })
    // 保存方便下次直接获取
    this.audioCapturer = audioCapturer
    // 返回音频采集器
    return audioCapturer
  }

  // 开始录音
  async startCapturer(filePath: string) {
    // 根据 filePath 打开文件，可读可写模式，如果文件不存在自动创建
    const file = fileIo.openSync(filePath, fileIo.OpenMode.READ_WRITE | fileIo.OpenMode.CREATE)
    // 1. 获取音频采集器
    const audioCapturer = await this.getAudioCapturer()
    // 偏移值
    let bufferSize: number = 0
    // 2. 调用on('readData')方法，订阅监听音频数据读入回调
    audioCapturer.on('readData', (buffer) => {
      // 把采集的音频信息写入到打开的文件中
      fileIo.writeSync(file.fd, buffer, { offset: bufferSize, length: buffer.byteLength })
      // 累加偏移值
      bufferSize += buffer.byteLength
      // 测试用的
      this.message = bufferSize.toString();
    })
    // 3. 开始录音采集
    audioCapturer.start()
  }

  async stopCapturer(){
    // 获取音频采集器
    const audioCapturer = await this.getAudioCapturer()
    await audioCapturer.stop() // 停止采集
    audioCapturer.release() // 释放资源
    this.audioCapturer = null // 重置采集器变量

    // 测试用的
    this.message = '停止录音';
  }

  // 获取音频渲染器（播放器）
  async getAudioRenderer() {
    if (this.audioRenderer) {
      return this.audioRenderer
    }
    this.audioRenderer = await audio.createAudioRenderer({
      streamInfo: this.audioStreamInfo,
      rendererInfo: this.audioRendererInfo
    })
    return this.audioRenderer
  }

  // 播放录音
  async startRenderer(filePath: string) {
    // 根据路径打开文件
    const file = fileIo.openSync(filePath)
    // 获取文件信息，如果读取时已经超出文件大小，自动停止
    const stat = fileIo.statSync(file.fd)
    // 1. 获取音频渲染器（播放器）
    const audioRenderer = await this.getAudioRenderer()
    // 偏移值
    let bufferSize: number = 0
    // 2. 调用on('writeData')方法，订阅监听音频数据写入回调
    let writeDataCallback = (buffer: ArrayBuffer) => {
      fileIo.readSync(file.fd, buffer, { offset: bufferSize, length: buffer.byteLength })
      bufferSize += buffer.byteLength

      this.message = bufferSize.toString()
      if (bufferSize >= stat.size) {
        // 停止渲染器（播放器）
        audioRenderer.stop() // 停止
        audioRenderer.release() // 释放资源
        this.audioRenderer = null // 清理变量
      }
    }

    audioRenderer.on('writeData', writeDataCallback)

    // 3. 启动音频渲染器（播放器）
    audioRenderer.start()
  }

  // 停止播放录音
  async stopRenderer() {
    // 获取音频渲染器（播放器）
    const audioRenderer = await this.getAudioRenderer()
    if (
      audioRenderer.state === audio.AudioState.STATE_RUNNING
        ||
        audioRenderer.state === audio.AudioState.STATE_PAUSED
    ) {
      await audioRenderer.stop() // 停止
      audioRenderer.release() // 释放资源
      this.audioRenderer = null // 清理变量
    }

    this.message = '停止播放'
  }
}