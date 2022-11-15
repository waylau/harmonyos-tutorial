import { FoodInfo } from '../model/DataModels'
import { getFoods } from '../model/DataUtil'

@Component
struct FoodListItem {
  private foodItem: FoodInfo

  build() {
    // 增加路由导航
    Navigator({ target: 'pages/FoodDetail' }) {
      Flex({ justifyContent: FlexAlign.Start, alignItems: ItemAlign.Center }) {
        Image(this.foodItem.image)
          .objectFit(ImageFit.Contain)
          .height(40)
          .width(40)
          .backgroundColor('#FFf1f3f5')
          .margin({ right: 16 })
        Text(this.foodItem.name)
          .fontSize(14)
          .flexGrow(1)
        Text(this.foodItem.calories + ' kcal')
          .fontSize(14)
      }
      .height(64)
    }
    // 页面间数据传递
    .params({ foodInfo: this.foodItem })
    .margin({ right: 24, left:32 })
  }
}

@Entry
@Component
struct FoodList {
  private foodItems: FoodInfo[] = getFoods()

  build() {
    Column() {
      Flex({ justifyContent: FlexAlign.Start, alignItems: ItemAlign.Center }) {
        Text('Food List')
          .fontSize(20)
          .margin({ left: 20 })
      }
      .height('7%')
      .backgroundColor('#FFf1f3f5')

      List() {
        ForEach(this.foodItems, item => {
          ListItem() {
            FoodListItem({ foodItem: item })
          }
        }, item => item.id.toString())
      }
      .height('93%')
    }
  }
}
