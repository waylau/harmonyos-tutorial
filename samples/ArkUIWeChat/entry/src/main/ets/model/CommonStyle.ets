import {WeChatColor} from './WeChatData'
import router from '@system.router';

@Component
export struct WeChatItemStyle {
  private imageSrc: string
  private text: string
  private arrow: string = "arrow.png"

  build() {
    Column() {
      Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.Center }) {
        Image($rawfile(this.imageSrc)).width('75px').height('75px').margin({ left: '50px' })
        Text(this.text).fontSize('15vp').margin({ left: '40px' }).flexGrow(1)
        Image($rawfile(this.arrow))
          .margin({ right: '40px' })
          .width('75px')
          .height('75px')
      }
      .height('150px')
      .width('100%')
    }.onClick(() => {
      if (this.text === "视频号") {
        router.push({ uri: 'pages/VideoPage' })
      }
    })
  }
}


@Component
export struct WeChatTitle {
  private text: string

  build() {
    Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.Center }) {
      Text(this.text).fontSize('18fp').padding('20px')
    }.height('120px').backgroundColor(WeChatColor)
  }
}


@Component
export struct ContactItemStyle {
  private imageSrc: string
  private text: string

  build() {
    Column() {
      Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.Center }) {
        Image($rawfile(this.imageSrc)).width('100px').height('100px').margin({ left: '50px' })
        Text(this.text).fontSize('15vp').margin({ left: '40px' }).flexGrow(1)
      }
      .height('150px')
      .width('100%')

      Row() {
        Text().width('190px').height('3px')
        Divider()
          .vertical(false)
          .color(WeChatColor)
          .strokeWidth('3px')
      }
      .height('3px')
      .width('100%')
    }
  }
}

@Component
export struct ChatItemStyle {
  WeChatImage: string;
  WeChatName: string;
  ChatInfo: string;
  time: string;

  build() {
    Column() {
      Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.Start }) {
        Image($rawfile(this.WeChatImage)).width('120px').height('120px').margin({ left: '50px', right: "50px" })

        Column() {
          Text(this.WeChatName).fontSize('16fp')
          Text(this.ChatInfo).fontSize('12fp').width('620px').fontColor("#c2bec2").maxLines(1)
        }.alignItems(HorizontalAlign.Start).flexGrow(1)

        Text(this.time).fontSize('12fp')
          .margin({ right: "50px" }).fontColor("#c2bec2")

      }
      .height('180px')
      .width('100%')

      Row() {
        Text().width('190px').height('3px')
        Divider()
          .vertical(false)
          .color(WeChatColor)
          .strokeWidth('3px')
      }
      .height('3px')
      .width('100%')
    }

  }
}


@Component
export struct MyDivider {
  private style: string = ""

  build() {
    Row() {
      Divider()
        .vertical(false)
        .color(WeChatColor)
        .strokeWidth(this.style == "1" ? '3px' : '23px')
    }
    .height(this.style == "1" ? '3px' : '23px')
    .width('100%')
  }
}