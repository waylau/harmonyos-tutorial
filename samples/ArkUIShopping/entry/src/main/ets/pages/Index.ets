import {GoodsData} from '../model/GoodsData'
import {initializeOnStartup,getIconPath,getIconPathSelect} from '../model/GoodsDataModels'
import {ShoppingCart} from './ShoppingCart'
import {MyInfo} from './MyPage'

@Entry
@Component
struct Index {
  @Provide currentPage: number = 1
  private goodsItems: GoodsData[] = initializeOnStartup()
  build() {
    Column() {
      Scroll() {
        Column() {
          if (this.currentPage == 1) {
            GoodsHome({ goodsItems: this.goodsItems })
          } else if (this.currentPage == 2) {
            //购物车列表
            ShoppingCart()
          } else {
            //我的
            MyInfo()
          }
        }
        .height(700)
      }
      .flexGrow(1)
      HomeBottom()
    }
    .backgroundColor("white")


  }
}

@Component
struct GoodsHome {
  private goodsItems: GoodsData[]

  build() {
    Column() {
      Tabs() {
        TabContent() {
          GoodsList({ goodsItems: this.goodsItems });
        }
        .tabBar("Top Sellers")
        .backgroundColor(Color.White)
        TabContent() {
          GoodsList({ goodsItems: this.goodsItems });
        }
        .tabBar("Recommended")
        .backgroundColor(Color.White)
        TabContent() {
          GoodsList({ goodsItems: this.goodsItems });
        }
        .tabBar("Lifestyle")
        .backgroundColor(Color.White)
        TabContent() {
          GoodsList({ goodsItems: this.goodsItems });
        }
        .tabBar("Deals")
        .backgroundColor(Color.White)
      }
      .barWidth(500)
      .barHeight(25)
      .scrollable(true)
      .barMode(BarMode.Scrollable)
      .backgroundColor('#007DFF')
      .height(700)

    }
    .alignItems(HorizontalAlign.Start)
    .width('100%')
  }
}

@Component
struct GoodsList {
  private goodsItems: GoodsData[]

  build() {
    Column() {
      List() {
        ForEach(this.goodsItems, item => {
          ListItem() {
            GoodsListItem({ goodsItem: item })
          }
        }, item => item.id.toString())
      }
      .height('100%')
      .width('100%')
      .align(Alignment.Top)
      .margin({top: 5})
    }
  }
}


@Component
struct GoodsListItem {
  private goodsItem: GoodsData

  build() {
    Navigator({ target: 'pages/ShoppingDetail' }) {
      Row() {
        Column() {
          Text(this.goodsItem.title)
            .fontSize(14)
          Text(this.goodsItem.content)
            .fontSize(10)
          Text('￥' + this.goodsItem.price)
            .fontSize(14)
            .fontColor(Color.Red)
        }
        .height(100)
        .width('50%')
        .margin({ left: 20 })
        .alignItems(HorizontalAlign.Start)

        Image(this.goodsItem.imgSrc)
          .objectFit(ImageFit.ScaleDown)
          .height(100)
          .width('40%')
          .renderMode(ImageRenderMode.Original)
          .margin({ right: 10, left: 10 })

      }
      .backgroundColor(Color.White)

    }
    .params({ goodsData: this.goodsItem })
    .margin({ right: 5 })
  }
}

@Component
struct HomeBottom {
  @Consume currentPage: number
  private iconPathTmp: string[] =  getIconPath()
  private iconPathSelectsTmp: string[] =  getIconPathSelect()
  @State iconPath: string[] =  getIconPath()
  build() {
    Row(){
      List() {
        ForEach(this.iconPath, item => {
          ListItem() {
            Image($rawfile(item))
              .objectFit(ImageFit.Cover)
              .height(30)
              .width(30)
              .renderMode(ImageRenderMode.Original)
              .onClick(()=>{
                if(item==this.iconPath[0]){
                  this.iconPath[0]=this.iconPathTmp[0]
                  this.iconPath[1]=this.iconPathTmp[1]
                  this.iconPath[2]=this.iconPathTmp[2]
                  this.currentPage=1
                }
                if(item==this.iconPath[1]){
                  this.iconPath[0]=this.iconPathSelectsTmp[0]
                  this.iconPath[1]=this.iconPathSelectsTmp[1]
                  this.iconPath[2]=this.iconPathTmp[2]
                  this.currentPage=2
                }
                if(item==this.iconPath[2]){
                  this.iconPath[0]=this.iconPathSelectsTmp[0]
                  this.iconPath[1]=this.iconPathTmp[1]
                  this.iconPath[2]=this.iconPathSelectsTmp[2]
                  this.currentPage=3
                }
              })
          }
          .width(120)
          .height(40)
        }, item => item)
      }
      .margin({left:10})
      .align(Alignment.BottomStart)
      .listDirection(Axis.Horizontal)
    }
    .alignItems(VerticalAlign.Bottom)
    .height(30)
    .margin({top:10 ,bottom:10})
  }
}
