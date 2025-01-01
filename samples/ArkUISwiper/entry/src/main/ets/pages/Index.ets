@Entry
@Component
struct Index {
  private imageSrc: ImageData[] = initializeImageData()

  build() {
    Flex({ direction: FlexDirection.Column, alignItems: ItemAlign.Center, justifyContent: FlexAlign.Center }) {
      Swiper() {
        ForEach(this.imageSrc, (item: ImageData) => {
          Image(item.img)
        }, (item: ImageData) => item.name)
      }.autoPlay(true) //自动轮播
    }
  }
}

export function initializeImageData(): Array<ImageData> {
  let imageDataArray: Array<ImageData> = [
    new ImageData( '0',  $r('app.media.pic01'),  '1024程序员节'),
    new ImageData( '1',  $r('app.media.pic02'),  '程序员日常'),
    new ImageData( '2',  $r('app.media.pic03'),  'HDC2021')
  ]
  return imageDataArray
}

export class ImageData {
  id: string
  img: Resource
  name: string

  constructor(id: string, img: Resource, name: string) {
    this.id = id // 图片唯一表示
    this.img = img // 图片资源
    this.name = name // 图片名称
  }
}