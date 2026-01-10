import { CardRecognition, CardRecognitionResult, CardType, CardSide, ShootingMode } from "@kit.VisionKit"
import { hilog } from '@kit.PerformanceAnalysisKit';

const TAG: string = 'PageBankCard'


// 跳转页面入口函数
@Builder
export function PageBankCardBuilder(name: string, param: string) {
  PageBankCard();
}

@Entry
@Component
struct PageBankCard {
  @State type: string = '';
  @State cardDataSource: Record<string, string>[] = []
  pageInfos: NavPathStack = new NavPathStack();

  build() {
    NavDestination() {
      Stack({ alignContent: Alignment.Top }) {
        Stack() {
          this.cardDataShowBuilder()
        }
        .width('80%')
        .height('80%')

        CardRecognition({
          // 此处选择银行卡类型作为示例
          supportType: CardType.CARD_BANK,
          // 银行卡为单面识别
          cardSide: CardSide.FRONT,
          cardRecognitionConfig: {
            defaultShootingMode: ShootingMode.MANUAL,
            isPhotoSelectionSupported: true,
            cardContentConfig: { bankCard: { isBankNumberDialogShown: true } }
          },
          onResult: ((params: CardRecognitionResult) => {
            hilog.info(0x0001, TAG, `params code: ${params.code}`)
            if (params.code !== 200) {
              this.pageInfos.pop()
            }
            hilog.info(0x0001, TAG, `params cardType: ${params.cardType}`)
            if (params.cardInfo?.front !== undefined) {
              this.cardDataSource.push(params.cardInfo?.front)
            }

            if (params.cardInfo?.back !== undefined) {
              this.cardDataSource.push(params.cardInfo?.back)
            }

            if (params.cardInfo?.main !== undefined) {
              this.cardDataSource.push(params.cardInfo?.main)
            }
            hilog.info(0x0001, TAG, `params cardInfo front: ${JSON.stringify(params.cardInfo?.front)}`)
            hilog.info(0x0001, TAG, `params cardInfo back: ${JSON.stringify(params.cardInfo?.back)}`)
          })
        })
      }
      .width('100%')
      .height('100%')
    }
    .title(this.type)
    .onReady((ctx: NavDestinationContext) => {
      // NavDestinationContext获取当前所在的导航控制器
      this.pageInfos = ctx.pathStack;

      // 获取参数
      this.type = ctx?.pathInfo?.param as string;
    })
  }

  @Builder
  cardDataShowBuilder() {
    List() {
      ForEach(this.cardDataSource, (cardData: Record<string, string>) => {
        ListItem() {
          Column() {
            Image(cardData.cardImageUri)
              .objectFit(ImageFit.Contain)
              .width(100)
              .height(100)

            Text(JSON.stringify(cardData))
              .width('100%')
              .fontSize(12)
          }
        }
      })
    }
    .listDirection(Axis.Vertical)
    .alignListItem(ListItemAlign.Center)
    .margin({
      top: 50
    })
    .width('100%')
    .height('100%')
  }
}
