let personId = 0;

export class Person {
  id: string;
  WeChatImage: string;
  WeChatName: string;
  ChatInfo: string;
  time: string;

  constructor(WeChatImage: string, WeChatName: string, ChatInfo: string, time: string) {
    this.id = `${personId++}`
    this.WeChatImage = WeChatImage;
    this.WeChatName = WeChatName;
    this.ChatInfo = ChatInfo;
    this.time = time;
  }
}