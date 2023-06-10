import {ContactItemStyle, WeChatTitle} from '../model/CommonStyle'
import {Person} from '../model/Person'
import {getContactInfo, WeChatColor} from '../model/WeChatData'

@Component
export struct ContactPage {
  private contactList: Person[] = getContactInfo()

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
          Text("      我的企业及企业联系人").fontSize('12fp').backgroundColor(WeChatColor).height('80px').width('100%')
          ContactItemStyle({ imageSrc: "qiye.png", text: "企业微信联系人" })

          // 微信好友
          Text("      我的微信好友").fontSize('12fp').backgroundColor(WeChatColor).height('80px').width('100%')
          List() {
            ForEach(this.contactList, item => {
              ListItem() {
                ContactItemStyle({ imageSrc: item.WeChatImage, text: item.WeChatName })
              }
            }, item => item.id.toString())
          }
        }
      }

    }.alignItems(HorizontalAlign.Start)
    .width('100%')
    .height('100%')
  }
}