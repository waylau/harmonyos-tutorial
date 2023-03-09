import media from '@ohos.multimedia.media'
import  { AudioDemo } from '../AudioDemo'

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'
  build() {
    Row() {
      Column() {
        // 控制按钮
        Row() {
          Button("开始").onClick(() => {
            let audioDemo:AudioDemo =new AudioDemo()
            audioDemo.audioPlayerDemo()
          })
 
        }
      }
      .width('100%')
    }
    .height('100%')
  }
}