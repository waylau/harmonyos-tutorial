import {getMenu,getTrans,getMore} from '../model/GoodsDataModels'
import {Menu, ImageItem} from '../model/Menu'
@Entry
@Component
export struct MyInfo {

  build() {
    Column() {
      Row(){
        Image($rawfile('nav/icon-user.png'))
          .margin({left:20})
          .objectFit(ImageFit.Cover)
          .height(50)
          .width(50)
          .renderMode(ImageRenderMode.Original)
          .margin({left:40,right:40})
        Column(){
          Text('John Doe')
            .fontSize(15)
          Text('Member Name : John Doe                     >')
        }
        .height(60)
        .margin({left:40,top:10})
        .alignItems(HorizontalAlign.Start)
      }
      TopList()
      MyTransList()
      MoreGrid()

    }
    .alignItems(HorizontalAlign.Start)
    .width('100%')
    .flexGrow(1)
 }
}


@Component
struct TopList {
  private menus: Menu[] = getMenu()

  build() {
    Row() {
      List() {
        ForEach(this.menus, item => {
          ListItem() {
            MenuItem({ menu: item })
          }
        }, item => item.id.toString())
      }
      .height('100%')
      .width('100%')
      .margin({top: 5})
      .edgeEffect(EdgeEffect.None)
      .listDirection(Axis.Horizontal)
    }
    .width('100%')
    .height(50)
  }
}

@Component
struct MyTransList {
  private imageItems: ImageItem[] = getTrans()
  build() {
    Column() {
      Text('My Transaction')
        .fontSize(20)
        .margin({ left: 10 })
        .width('100%')
        .height(30)
      Row() {
        List() {
          ForEach(this.imageItems, item => {
            ListItem() {
              DataItem({ imageItem: item })
            }
          }, item => item.id.toString())
        }
        .height(70)
        .width('100%')
        .align(Alignment.Top)
        .margin({ top: 5})
        .listDirection(Axis.Horizontal)
      }
    }
    .height(120)
  }
}

@Component
struct MoreGrid {
  private gridRowTemplate: string = ''
  private imageItems: ImageItem[] = getMore()
  private heightValue: number

  aboutToAppear() {
    var rows = Math.round(this.imageItems.length / 3);
    this.gridRowTemplate = '1fr '.repeat(rows);
    this.heightValue = rows * 75 ;
  }

  build() {
    Column() {
      Text('More')
        .fontSize(20)
        .margin({ left: 10 })
        .width('100%')
        .height(30)
      Scroll() {
        Grid() {
          ForEach(this.imageItems, (item: ImageItem) => {
            GridItem() {
              DataItem({ imageItem: item })
            }
          }, (item: ImageItem) => item.id.toString())
        }
        .rowsTemplate(this.gridRowTemplate)
        .columnsTemplate('1fr 1fr 1fr')
        .columnsGap(8)
        .rowsGap(8)
        .height(this.heightValue)
      }
      .padding({ left: 16, right: 16 })
    }
    .height(400)
  }
}


@Component
struct DataItem {
  private imageItem: ImageItem

  build() {
    Column() {
      Image(this.imageItem.imageSrc)
        .objectFit(ImageFit.Contain)
        .height(50)
        .width(50)
        .renderMode(ImageRenderMode.Original)
        Text(this.imageItem.title)
          .fontSize(15)


    }
    .height(70)
    .width(80)
    .margin({left: 15,right:15})
    .backgroundColor(Color.White)
  }
}

@Component
struct MenuItem {
  private menu: Menu

  build() {
    Column() {
      Text(this.menu.title)
        .fontSize(15)
      Text(this.menu.num+'')
        .fontSize(13)

    }
    .height(50)
    .width(80)
    .margin({left: 8,right:8})
    .alignItems(HorizontalAlign.Start)
    .backgroundColor(Color.White)
  }
}

