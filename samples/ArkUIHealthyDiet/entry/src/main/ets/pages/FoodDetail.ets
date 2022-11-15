import router from '@ohos.router'
import { FoodInfo } from '../model/DataModels'

@Component
struct PageTitle {
  build() {
    Flex({ alignItems: ItemAlign.Start }) {
      Image($r('app.media.back'))
        .width(21.8)
        .height(19.6)
      Text('Food Detail')
        .fontSize(21.8)
        .margin({left: 17.4})
    }
    .height(61)
    .backgroundColor('#FFedf2f5')
    .padding({ top: 13, bottom: 15, left: 28.3 })
    .onClick(() => {
      router.back()
    })
  }
}

@Component
struct FoodImageDisplay {
  private foodItem: FoodInfo
  build() {
    Stack({ alignContent: Alignment.BottomStart }) {
      Image(this.foodItem.image)
        .objectFit(ImageFit.Contain)
      Text(this.foodItem.name)
        .fontSize(26)
        .fontWeight(500)
        .margin({ left: 26, bottom: 17.4 })
    }
    .height(357)
    .backgroundColor('#FFedf2f5')
  }
}

@Component
struct ContentTable {
  private foodItem: FoodInfo

  @Builder IngredientItem(title:string, name: string, value: string) {
    Flex() {
      Text(title)
        .fontSize(17.4)
        .fontWeight(FontWeight.Bold)
        .layoutWeight(1)
      Flex() {
        Text(name)
          .fontSize(17.4)
          .flexGrow(1)
        Text(value)
          .fontSize(17.4)
      }
      .layoutWeight(2)
    }
  }

  build() {
    Flex({ direction: FlexDirection.Column, justifyContent: FlexAlign.SpaceBetween, alignItems: ItemAlign.Start }) {
      this.IngredientItem('Calories', 'Calories', this.foodItem.calories + 'kcal')
      this.IngredientItem('Nutrition', 'Protein', this.foodItem.protein + 'g')
      this.IngredientItem('', 'Fat', this.foodItem.fat + 'g')
      this.IngredientItem('', 'Carbohydrates', this.foodItem.carbohydrates + 'g')
      this.IngredientItem('', 'VitaminC', this.foodItem.vitaminC + 'mg')
    }
    .height(280)
    .padding({ top: 30, right: 30, left: 30 })
  }
}

@Entry
@Component
struct FoodDetail {
  private foodItem: FoodInfo = router.getParams()['foodInfo']

  build() {
    Column() {
      Stack( { alignContent: Alignment.TopStart }) {
        FoodImageDisplay({ foodItem: this.foodItem })
        PageTitle()
      }
      ContentTable({ foodItem: this.foodItem })
    }
    .alignItems(HorizontalAlign.Center)
  }
}
