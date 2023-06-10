import {WeChatItemStyle, MyDivider, WeChatTitle} from '../model/CommonStyle'

@Component
export struct DiscoveryPage {
  build() {
    Column() {
      // 标题
      WeChatTitle({ text: "发现" })

      // 列表
      WeChatItemStyle({ imageSrc: "moments.png", text: "朋友圈" })
      MyDivider()

      WeChatItemStyle({ imageSrc: "shipinghao.png", text: "视频号" })
      MyDivider({ style: '1' })
      WeChatItemStyle({ imageSrc: "zb.png", text: "直播" })
      MyDivider()

      WeChatItemStyle({ imageSrc: "sys.png", text: "扫一扫" })
      MyDivider({ style: '1' })
      WeChatItemStyle({ imageSrc: "yyy.png", text: "摇一摇" })
      MyDivider()

      WeChatItemStyle({ imageSrc: "kyk.png", text: "看一看" })
      MyDivider({ style: '1' })
      WeChatItemStyle({ imageSrc: "souyisou.png", text: "搜一搜" })
      MyDivider()

      WeChatItemStyle({ imageSrc: "fujin.png", text: "附近" })
      MyDivider()

      WeChatItemStyle({ imageSrc: "gw.png", text: "购物" })
      MyDivider({ style: '1' })
      WeChatItemStyle({ imageSrc: "game.png", text: "游戏" })
      MyDivider()

      WeChatItemStyle({ imageSrc: "xcx.png", text: "小程序" })
      MyDivider()
    }.alignItems(HorizontalAlign.Start)
    .width('100%')
    .height('100%')
  }
}
