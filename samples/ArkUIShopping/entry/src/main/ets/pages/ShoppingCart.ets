import {GoodsData} from '../model/GoodsData'
import {initializeOnStartup} from '../model/GoodsDataModels'
import prompt from '@system.prompt';

@Entry
@Component
export struct ShoppingCart {
  @Provide totalPrice : number =0
  private goodsItems: GoodsData[] = initializeOnStartup()
  build() {
    Column() {
      Column() {
        Text('ShoppingCart')
          .fontColor(Color.Black)
          .fontSize(25)
          .margin({ left: 60,right:60 })
          .align(Alignment.Center)
      }
      .backgroundColor('#FF00BFFF')
      .width('100%')
      .height(30)

      ShopCartList({ goodsItems: this.goodsItems });
      ShopCartBottom()
    }
    .alignItems(HorizontalAlign.Start)
  }
}

@Component
struct ShopCartList {

  private goodsItems: GoodsData[]

  build() {
    Column() {
      List() {
        ForEach(this.goodsItems, item => {
          ListItem() {
            ShopCartListItem({ goodsItem: item })
          }
        }, item => item.id.toString())
      }
      .height('100%')
      .width('100%')
      .align(Alignment.Top)
      .margin({top: 5})
    }
    .height(570)
  }
}

@Component
struct ShopCartListItem {
  @Consume totalPrice: number
  private goodsItem: GoodsData

  build() {
    Row() {
      Toggle({type: ToggleType.Checkbox})
        .width(10)
        .height(10)
        .onChange((isOn:boolean) => {
          if(isOn){
            this.totalPrice +=parseInt(this.goodsItem.price+'',0)
          }else{
            this.totalPrice -= parseInt(this.goodsItem.price+'',0)
          }
        })
      Image(this.goodsItem.imgSrc)
        .objectFit(ImageFit.ScaleDown)
        .height(100)
        .width(100)
        .renderMode(ImageRenderMode.Original)
      Column() {
        Text(this.goodsItem.title)
          .fontSize(14)
        Text('￥' + this.goodsItem.price)
          .fontSize(14)
          .fontColor(Color.Red)
      }
    }
    .height(100)
    .width(180)
    .margin({left: 20})
    .alignItems(VerticalAlign.Center)
    .backgroundColor(Color.White)
  }
}

@Component
struct ShopCartBottom {
  @Consume totalPrice: number
  build() {
    Row() {
      Text('Total:  ￥'+this.totalPrice)
        .fontColor(Color.Red)
        .fontSize(18)
        .margin({left:20})
        .width(150)
      Text('Check Out')
        .fontColor(Color.Black)
        .fontSize(18)
        .margin({right:20,left:100})
        .onClick(()=>{
          prompt.showToast({
            message: 'Checking Out',
            duration: 10,
            bottom:100
          })
        })
    }
    .height(30)
    .width('100%')
    .backgroundColor('#FF7FFFD4')
    .alignItems(VerticalAlign.Bottom)
  }
}
