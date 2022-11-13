import router from '@system.router';
import {ArsData} from '../model/ArsData'
import {getArs,getDetailImages} from '../model/GoodsDataModels'
import prompt from '@system.prompt';

@Entry
@Component
struct ShoppingDetail {
  private arsItems: ArsData[] =  getArs()
  private detailImages: string[] = getDetailImages()
  build() {
    Column() {
      DetailTop()
      Scroll() {
        Column() {
          SwiperTop()
          DetailText()
          DetailArsList({ arsItems: this.arsItems })
          Image($rawfile('computer/computer1.png'))
            .height(220)
            .width('100%')
            .margin({top:30})
          Image($rawfile('computer/computer2.png'))
            .height(220)
            .width('100%')
            .margin({top:30})
          Image($rawfile('computer/computer3.png'))
            .height(220)
            .width('100%')
            .margin({top:30})
          Image($rawfile('computer/computer4.png'))
            .height(220)
            .width('100%')
            .margin({top:30})
          Image($rawfile('computer/computer5.png'))
            .height(220)
            .width('100%')
            .margin({top:30})
          Image($rawfile('computer/computer6.png'))
            .height(220)
            .width('100%')
            .margin({top:30})
        }
        .width('100%')
        .flexGrow(1)
      }
      .scrollable(ScrollDirection.Vertical)
      DetailBottom()
    }
    .height(630)


  }
}

@Component
struct DetailTop{
  build(){
    Column(){
      Row(){
        Image($rawfile('detail/icon-return.png'))
          .height(20)
          .width(20)
          .margin({ left: 20, right: 250 })
          .onClick(() => {
            router.push({
              uri: "pages/HomePage"
            })
          })

      }
      .width('100%')
      .height(25)
      .backgroundColor('#FF87CEEB')
    }
    .width('100%')
    .height(30)
  }
}

@Component
struct SwiperTop{
  build() {
    Column() {
      Swiper() {
        Image($rawfile('computer/computer1.png'))
          .height(220)
          .width('100%')
        Image($rawfile('computer/computer2.png'))
          .height(220)
          .width('100%')
        Image($rawfile('computer/computer3.png'))
          .height(220)
          .width('100%')
        Image($rawfile('computer/computer4.png'))
          .height(220)
          .width('100%')
        Image($rawfile('computer/computer5.png'))
          .height(220)
          .width('100%')
        Image($rawfile('computer/computer6.png'))
          .height(220)
          .width('100%')

      }
      .index(0)
      .autoPlay(true)
      .interval(3000)
      .indicator(true)
      .loop(true)
      .height(250)
      .width('100%')
    }
    .height(250)
    .width('100%')
  }
}

@Component
struct DetailText{
  build() {
    Column() {
      Row(){
        Image($rawfile('computer/icon-promotion.png'))
          .height(30)
          .width(30)
          .margin({left:10})
        Text('Special Offer: ￥9999')
          .fontColor(Color.White)
          .fontSize(20)
          .margin({left:10})

      }
      .width('100%')
      .height(35)
      .backgroundColor(Color.Red)
      Column(){
        Text('New Arrival: HUAWEI MateBook X Pro 2021')
          .fontSize(15)
          .margin({left:10})
          .alignSelf(ItemAlign.Start)
        Text('13.9-Inch, 11th Gen Intel® Core™ i7, 16 GB of Memory, 512 GB of Storage, Ultra-slim Business Laptop, 3K FullView Display, Multi-screen Collaboration, Emerald Green')
          .fontSize(10)
          .margin({left:10})
        Row(){
          Image($rawfile('nav/icon-buy.png'))
            .height(15)
            .width(15)
            .margin({left:10})
          //TODO 暂不支持跑马灯组件，用Text代替
          Text('Limited offer')
            .fontSize(10)
            .fontColor(Color.Red)
            .margin({left:100})

        }
        .backgroundColor(Color.Pink)
        .width('100%')
        .height(25)
        .margin({top:10})
        Text(' Shipment:         2-day shipping')
          .fontSize(13)
          .fontColor(Color.Red)
          .margin({left:10,top:5})
          .alignSelf(ItemAlign.Start)
        Text('    Ship To:         Hubei,Wuhan,China')
          .fontSize(13)
          .fontColor(Color.Red)
          .margin({left:10,top:5})
          .alignSelf(ItemAlign.Start)
          .onClick(()=>{
            prompt.showDialog({title:'select address',})

          })
        Text('Guarantee:         Genuine guaranteed')
          .fontSize(13)
          .margin({left:10,top:5})
          .alignSelf(ItemAlign.Start)
      }
      .height(150)
      .width('100%')
    }
    .height(160)
    .width('100%')
  }
}


@Component
struct DetailArsList{
  private arsItems: ArsData[]
  build() {
    Scroll() {
      Column() {
        List() {
          ForEach(this.arsItems, item => {
            ListItem() {
              ArsListItem({ arsItem: item })
            }
          }, item => item.id.toString())
        }
        .height('100%')
        .width('100%')
        .margin({ top: 5 })
        .listDirection(Axis.Vertical)
      }
      .height(200)
    }
  }
}

@Component
struct ArsListItem {
  private arsItem: ArsData

  build() {
    Row() {
      Text(this.arsItem.title+" :")
        .fontSize(11)
        .margin({left:20})
        .flexGrow(1)
      Text( this.arsItem.content)
        .fontSize(11)
        .margin({right:20})

    }
    .height(14)
    .width('100%')
    .backgroundColor(Color.White)
  }
}


@CustomDialog
struct DialogExample {
  @Consume
  private value : number
  controller: CustomDialogController;
  action: () => void;
  build() {
    Column() {
      Progress({value: this.value++ >=100?100:this.value, total: 100, style: ProgressStyle.Eclipse})
        .height(50)
        .width(100)
        .margin({top:5})

    }
    .height(60)
    .width(100)


  }
}

@Component
struct DetailBottom{
  @Provide
  private value : number=1
  dialogController : CustomDialogController = new CustomDialogController({
    builder: DialogExample({action: this.onAccept}),
    cancel: this.existApp,
    autoCancel: true
  });

  onAccept() {

  }
  existApp() {

  }
  build(){
    Column(){
      Text('Buy')
        .width(40)
        .height(25)
        .fontSize(20)
        .fontColor(Color.White)
        .onClick(()=>{
          this.value=1
          this.dialogController.open()
        })
    }
    .alignItems(HorizontalAlign.Center)
    .backgroundColor(Color.Red)
    .width('100%')
    .height(40)
  }
}