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
