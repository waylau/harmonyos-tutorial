import media from '@ohos.multimedia.media'
import fileIO from '@ohos.fileio'
export class AudioDemo {
  // 设置播放器回调函数
  setCallBack(audioPlayer) {
    audioPlayer.on('dataLoad', () => {              //设置'dataLoad'事件回调，src属性设置成功后，触发此回调
      console.info('audio set source success');
      audioPlayer.play();                           //调用play方法开始播放，触发'play'事件回调
    });
    audioPlayer.on('play', () => {                  //设置'play'事件回调
      console.info('audio play success');
    });
    audioPlayer.on('finish', () => {                //设置'finish'事件回调，播放完成触发
      console.info('audio play finish');
      audioPlayer.release();                        //audioPlayer资源被销毁
      audioPlayer = undefined;
    });
  }

  async audioPlayerDemo() {
    //创建一个音频播放实例
    let audioPlayer = media.createAudioPlayer();
    //设置事件回调
    this.setCallBack(audioPlayer);
    let fdPath = 'fd://'
    // path路径的码流可通过"hdc file send D:\xxx\01.mp3 /data/app/el1/bundle/public/ohos.acts.multimedia.audio.audioplayer/ohos.acts.multimedia.audio.audioplayer/assets/entry/resources/rawfile" 命令，将其推送到设备上
    let path = globalThis.fileDir.concat("/music_01.mp3")
    await fileIO.open(path).then((fdNumber) => {
      fdPath = fdPath + '' + fdNumber;
      console.info('open fd success fd is' + fdPath);
    }, (err) => {
      console.info('open fd failed err is' + err);
    }).catch((err) => {
      console.info('open fd failed err is' + err);
    });
    audioPlayer.src = fdPath
  }
}