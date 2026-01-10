import { audio } from "@kit.AudioKit";

const TAG = 'AudioCapturer'

interface AudioStreamInfo {
  samplingRate: audio.AudioSamplingRate;
  channels: audio.AudioChannel;
  sampleFormat: audio.AudioSampleFormat;
  encodingType: audio.AudioEncodingType;
}

interface AudioCapturerInfo {
  source: audio.SourceType;
  capturerFlags: number;
}

interface AudioCapturerOptions {
  streamInfo: AudioStreamInfo;
  capturerInfo: AudioCapturerInfo
}

export default class AudioCapturer {
  // 采集工具对象
  private mAudioCapturer: audio.AudioCapturer | null = null;
  // 音频流信息
  private audioStreamInfo: AudioStreamInfo = {
    samplingRate: audio.AudioSamplingRate.SAMPLE_RATE_16000,
    channels: audio.AudioChannel.CHANNEL_1,
    sampleFormat: audio.AudioSampleFormat.SAMPLE_FORMAT_S16LE,
    encodingType: audio.AudioEncodingType.ENCODING_TYPE_RAW
  }
  // 音频采集器信息
  private audioCapturerInfo: AudioCapturerInfo = {
    source: audio.SourceType.SOURCE_TYPE_MIC,
    capturerFlags: 0
  }
  // 音频采集器选型信息
  private audioCapturerOptions: AudioCapturerOptions = {
    streamInfo: this.audioStreamInfo,
    capturerInfo: this.audioCapturerInfo
  }

  // 初始化采集器
  public async init(dataCallback: (data: ArrayBuffer) => void) {
    if (null != this.mAudioCapturer) {
      console.error(TAG, 'AudioCapturer already init');
      return
    }

    try {
      this.mAudioCapturer = await audio.createAudioCapturer(this.audioCapturerOptions)
    } catch (error) {
      console.error(TAG, `AudioCapturer init failed, code: ${error.code}, message: ${error.message}`);
    }

  }
}