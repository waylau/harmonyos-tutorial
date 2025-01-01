import {ContactItemStyle, WeChatTitle} from '../model/CommonStyle'
import {Person} from '../model/Person'
import { CONTACTS, WE_CHAT_COLOR} from '../model/WeChatData'

@Component
export struct ContactPage {
  build() {
    Column() {
      // 标题
      WeChatTitle({ text: "通讯录" })

      // 列表
      Scroll() {
        Column() {
          // 固定列表
          ContactItemStyle({ imageSrc: "new_friend.png", text: "新的朋友" })
          ContactItemStyle({ imageSrc: "group.png", text: "群聊" })
          ContactItemStyle({ imageSrc: "biaoqian.png", text: "标签" })
          ContactItemStyle({ imageSrc: "gonzh.png", text: "公众号" })

          // 企业联系人
          Text("      我的企业及企业联系人").fontSize('12fp').backgroundColor(WE_CHAT_COLOR).height('80px').width('100%')
          ContactItemStyle({ imageSrc: "qiye.png", text: "企业微信联系人" })

          // 微信好友
          Text("      我的微信好友").fontSize('12fp').backgroundColor(WE_CHAT_COLOR).height('80px').width('100%')
          List() {
            ForEach(CONTACTS, (item: Person) => {
              ListItem() {
                ContactItemStyle({ imageSrc: item.weChatImage, text: item.weChatName })
              }
            }, (item: Person) => item.id.toString())
          }
        }
      }

    }.alignItems(HorizontalAlign.Start)
    .width('100%')
    .height('100%')
  }
}