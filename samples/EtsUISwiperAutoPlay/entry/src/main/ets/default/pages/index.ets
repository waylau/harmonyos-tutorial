import { ImageData } from '../model/imageData.ets';

@Entry
@Component
struct Index {
  private imageSrc: ImageData[] = initializeImageData()

  build() {
    Flex({ direction: FlexDirection.Column, alignItems: ItemAlign.Center, justifyContent: FlexAlign.Center }) {
      Swiper() {
        ForEach(this.imageSrc, item => {
          Image(item.img)
        }, item => item.name)
      }.autoPlay(true) // 自动播放
    }
  }
}


export function initializeImageData(): Array<ImageData> {
  let imageDataArray: Array<ImageData> = [
    { "id": "0", "img": $r('app.media.pic01'), "name": '麻辣香锅' },
    { "id": "1", "img": $r('app.media.pic02'), "name": '香辣毛血旺' },
    { "id": "2", "img": $r('app.media.pic03'), "name": '关东煮' },
    { "id": "3", "img": $r('app.media.pic04'), "name": '菠萝咕噜肉' },
    { "id": "4", "img": $r('app.media.pic05'), "name": '可乐鸡翅' },
    { "id": "5", "img": $r('app.media.pic06'), "name": '宫保鸡丁' }
  ]
  return imageDataArray
}
