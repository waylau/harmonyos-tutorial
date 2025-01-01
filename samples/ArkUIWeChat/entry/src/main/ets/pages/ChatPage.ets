import {ChatItemStyle, WeChatTitle} from '../model/CommonStyle'
import { CONTACTS} from '../model/WeChatData'
import {Person} from '../model/Person'

@Component
export struct ChatPage {
  build() {
    Column() {
      // 标题
      WeChatTitle({ text: "微信" })

      // 列表
      List() {
        ForEach(CONTACTS, (item: Person) => {
          ListItem() {
            ChatItemStyle({
              weChatImage: item.weChatImage,
              weChatName: item.weChatName,
              chatInfo: item.chatInfo,
              time: item.time
            })
          }
        }, (item: Person) => item.id.toString())
      }
      .height('100%')
      .width('100%')
    }
  }
}