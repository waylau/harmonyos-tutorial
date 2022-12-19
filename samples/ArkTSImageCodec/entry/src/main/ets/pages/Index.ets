// 导入image模块
import image from '@ohos.multimedia.image'
import http from '@ohos.net.http';

@Entry
@Component
struct Index {
  // 先创建一个PixelMap状态变量用于接图片
  @State image: PixelMap = undefined

  build() {
    Row() {
      Column() {
        // 图片
        Image($r('app.media.waylau_181_181'))
          .width(181)
          .height(181)
          .margin({ top: 20, bottom: 20 })


        // 图片
        Image(this.image)
          .width(181)
          .height(181)
          .margin({ top: 20, bottom: 20 })

        Row() {
          // 编码
          Button(('编码'), { type: ButtonType.Capsule })
            .width(140)
            .fontSize(40)
            .fontWeight(FontWeight.Medium)
            .margin({ top: 20, bottom: 20 })
            .onClick(() => {
              this.decode()
            })

          // 解码
          Button(('解码'), { type: ButtonType.Capsule })
            .width(140)
            .fontSize(40)
            .fontWeight(FontWeight.Medium)
            .margin({ top: 20, bottom: 20 })
        }


      }
      .width('100%')
    }
    .height('100%')
  }


  // 解码
  private decode(){
    let uri = 'https://waylau.com/images/waylau_181_181.jpg'; // 设置创建imagesource的路径
    let imageSource = image.createImageSource(uri)
    let options = {alphaType: 0,                     // 透明度
      editable: false,                  // 是否可编辑
      pixelFormat: 3,                   // 像素格式
      scaleMode: 1,                     // 缩略值
      size: {height: 100, width: 100}}  // 创建图片大小
    imageSource.createPixelMap(options).then((pixelMap) => {
      this.image = pixelMap
    })
  }

  private encode(){

  }
}