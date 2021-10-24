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
      }
    }
  }
}

export function initializeImageData(): Array<ImageData> {
  let imageDataArray: Array<ImageData> = [
    { "id": "0", "img": $r('app.media.pic01'), "name": '1024程序员节' },
    { "id": "1", "img": $r('app.media.pic02'), "name": '程序员日常' },
    { "id": "2", "img": $r('app.media.pic03'), "name": 'HDC2021' },
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