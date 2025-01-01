import { common } from '@kit.AbilityKit';
import { media } from '@kit.MediaKit';

export default interface Song {
  id: number; // 索引
  title: string; // 标题
  author: string // 作者
  path: string; // 文件路径
}

// 资源放在resources目录下的rawfile文件夹中
const SONGS: Song[] = [
  {
    id: 1,
    title: '东方红',
    author: '李有源',
    path: '东方红.mp3'
  },
  {
    id: 2,
    title: '花海',
    author: '周杰伦',
    path: '花海.mp3'
  },
  {
    id: 3,
    title: '铁血丹心',
    author: '罗文',
    path: '铁血丹心.mp3'
  }]

@Entry
@Component
struct Index {
  private avPlayer: media.AVPlayer | null = null;
  private isPlaying: boolean = false;
  @State playerState: string = '暂停'
  @State selectedSong: Song = {
    id: -1,
    title: '',
    author: '',
    path: ''
  };

  async onPageShow() {
    // 创建avPlayer实例对象
    this.avPlayer = await media.createAVPlayer();
    // 创建状态机变化回调函数
    this.setAVPlayerCallback();
    console.info('播放器准备完成')
  }

  build() {
    Column() {
      Row() {
        Row() {
          Text('音乐播放器')
            .fontColor(Color.White).fontSize(32)
        }.margin({ left: 20 })
      }.backgroundColor(Color.Green).height('8%').width('100%')

      Column() {
        List() {
          ForEach(SONGS, (song: Song) => {
            ListItem() {
              Row() {
                Button({ type: ButtonType.Normal }) {
                  Row() {
                    Text(song.id + '')
                      .fontSize(32)
                    Column() {
                      Text(song.title).fontSize(20).fontWeight(700)
                      Text(song.author).fontSize(14)
                    }.alignItems(HorizontalAlign.Start)
                    .margin({ left: 20 })
                  }.justifyContent(FlexAlign.Start)
                  .width('90%')
                }
                .backgroundColor(Color.White)
                .width("100%")
                .height(50)
                .margin({ top: 10 })
                .onClick(() => {
                  this.playerState = '暂停';
                  this.isPlaying = true;
                  this.onPageShow();
                  this.changeSong(song);
                  this.selectedSong = song;
                })
              }
            }
          })
        }.width('100%')
      }.height('84%')

      Row() {
        Row() {
          if (this.selectedSong.id == -1) {
            Text('点击歌曲开始播放')
              .fontSize(20).fontColor(Color.White)
          } else {
            Column() {
              Text(this.selectedSong.title)
                .fontSize(20).fontColor(Color.White)
            }.width('70%').alignItems(HorizontalAlign.Start)

            Column() {
              Button({ type: ButtonType.Normal, stateEffect: true }) {
                Text(this.playerState)
                  .fontSize(20).fontColor(Color.White)
              }
              .borderRadius(8)
              .height(26)
              .width(70)
              .backgroundColor(Color.Orange)
              .onClick(() => {
                if (this.avPlayer !== null && this.isPlaying == true) {
                  this.avPlayer.pause()
                  this.playerState = '继续'
                  this.isPlaying = false
                } else {
                  this.avPlayer?.play()
                  this.playerState = '暂停'
                  this.isPlaying = true
                }
              })
            }.width('20%')
          }
        }.width('99%').margin({ left: 15 })
      }.backgroundColor(Color.Green).height('8%').width('100%')
    }.height('100%').width('100%')
  }

  // 以下为使用资源管理接口获取打包在HAP内的媒体资源文件并通过fdSrc属性进行播放
  async changeSong(song: Song) {
    if (this.avPlayer !== null) {
      this.avPlayer?.reset()
      // 创建状态机变化回调函数
      this.setAVPlayerCallback();
      // 通过UIAbilityContext的resourceManager成员的getRawFd接口获取媒体资源播放地址
      // 返回类型为{fd,offset,length},fd为HAP包fd地址，offset为媒体资源偏移量，length为播放长度
      let context = getContext(this) as common.UIAbilityContext;
      let fileDescriptor = await context.resourceManager.getRawFd(song.path);
      // 为fdSrc赋值触发initialized状态机上报
      this.avPlayer.fdSrc = fileDescriptor;
    }
  }

  setAVPlayerCallback() {
    if (this.avPlayer !== null) {
      this.avPlayer.on('error', (err) => {
        console.error(`播放器发生错误，错误码：${err.code}, 错误信息：${err.message}`);
        this.avPlayer?.reset();
      });

      this.avPlayer.on('stateChange', async (state, reason) => {
        switch (state) {
          case 'initialized':
            console.info('资源初始化完成');
            this.avPlayer?.prepare();
            break;
          case 'prepared':
            console.info('资源准备完成');
            this.avPlayer?.play();
            break;
          case 'completed':
            console.info('播放完成');
            this.avPlayer?.stop();
            break;
        }
      });
    }
  }
}
