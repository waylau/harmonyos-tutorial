import { ImageData } from '../model/ImageData';
@Entry
@Component
struct Index {
  private imageSrc: ImageData[] = initializeImageData()
  @State imageIndex:number = 0;
  @State itemClicked: boolean = false;

  build() {
    Column() {
      Row() {
        Image(this.imageSrc[this.imageIndex].img)
          .rotate({
            x: 0,
            y: 1,
            z: 0,
            angle: this.itemClicked ? 360 : 0
          })
          .scale(
            this.itemClicked
              ? { x: 1.4, y: 1.4 }
              : { x: 1, y: 1 }
          )
          .opacity(this.itemClicked ? 0.6 : 1)
          .animation(
            {
              delay: 10,
              duration: 1000,
              iterations: 1,
              curve: Curve.Smooth,
              playMode: PlayMode.Normal
            }
          );
      }.height('93%').border({ width: 1 })

      Row() {
        Row(){
          Button('点赞', { type: ButtonType.Normal, stateEffect: true }).borderRadius(8).backgroundColor(0x317aff).width(90)
        }.width('30%').height(50).onClick(()=>{
          this.itemClicked = !this.itemClicked;
        })

        Row(){
          Button('更换', { type: ButtonType.Normal, stateEffect: true }).borderRadius(8).backgroundColor(0x317aff).width(90)
        }.width('30%').height(50).onClick(()=>{
          this.imageIndex ++;
          if (this.imageIndex>=this.imageSrc.length) {
            this.imageIndex = 0;
          }

          this.itemClicked = false;
        })
      }.height('7%').border({ width: 1 })
    }
  }
}


export function initializeImageData(): Array<ImageData> {
  let imageDataArray: Array<ImageData> = [
    { "id": "0", "img": $r('app.media.pic01'), "name": '女1' },
    { "id": "1", "img": $r('app.media.pic02'), "name": '女2' },
    { "id": "2", "img": $r('app.media.pic03'), "name": '女3' },
  ]
  return imageDataArray
}
