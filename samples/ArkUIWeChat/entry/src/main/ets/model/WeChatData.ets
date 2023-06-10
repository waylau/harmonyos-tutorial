import {Person} from './Person'

const ContactInfo: any[] = [
  {
    "WeChatImage": "person (1).jpg",
    "WeChatName": "枫",
    "ChatInfo": "缓缓飘落的枫叶像思念，我点燃烛光温暖岁末的秋天",
    "time": "18:30"
  },
  {
    "WeChatImage": "person (2).jpg",
    "WeChatName": "珊瑚海",
    "ChatInfo": "转身离开，分手说不出来",
    "time": "17:29"
  },
  {
    "WeChatImage": "person (3).jpg",
    "WeChatName": "听妈妈的话",
    "ChatInfo": "听妈妈的话别让她受伤，想快快长大才能保护她",
    "time": "17:28"
  },
  {
    "WeChatImage": "person (4).jpg",
    "WeChatName": "给我一首歌的时间",
    "ChatInfo": "能不能给我一首歌的时间，紧紧的把那拥抱变成永远",
    "time": "16:27"
  },
  {
    "WeChatImage": "person (5).jpg",
    "WeChatName": "夜曲",
    "ChatInfo": "为你弹奏萧邦的夜曲，纪念我死去的爱情",
    "time": "15:26"
  },
  {
    "WeChatImage": "person (6).jpg",
    "WeChatName": "不能说的秘密",
    "ChatInfo": "最美的不是下雨天，是曾与你躲过雨的屋檐",
    "time": "14:25"
  },
  {
    "WeChatImage": "person (7).jpg",
    "WeChatName": "千里之外",
    "ChatInfo": "我送你离开千里之外你无声黑白",
    "time": "13:24"
  },
  {
    "WeChatImage": "person (8).jpg",
    "WeChatName": "世界末日",
    "ChatInfo": "天灰灰会不会，让我忘了你是谁",
    "time": "11:23"
  },
  {
    "WeChatImage": "person (9).jpg",
    "WeChatName": "园游会",
    "ChatInfo": "我顶着大太阳，只想为你撑伞",
    "time": "10:22"
  },
  {
    "WeChatImage": "person (10).jpg",
    "WeChatName": "简单爱",
    "ChatInfo": "我想就这样牵着你的手不放开",
    "time": "10:21"
  },
  {
    "WeChatImage": "person (11).jpg",
    "WeChatName": "回到过去",
    "ChatInfo": "想回到过去，试着让故事继续",
    "time": "10:20"
  },
  {
    "WeChatImage": "person (12).jpg",
    "WeChatName": "彩虹",
    "ChatInfo": "哪里有彩虹告诉我",
    "time": "10:19"
  },
  {
    "WeChatImage": "person (13).jpg",
    "WeChatName": "七里香",
    "ChatInfo": "雨下整夜我的爱溢出就像雨水",

    "time": "10:18"
  },
  {
    "WeChatImage": "person (14).jpg",
    "WeChatName": "告白气球",
    "ChatInfo": "亲爱的爱上你从那天起，甜蜜的很轻易",
    "time": "10:17"
  },
  {
    "WeChatImage": "person (15).jpg",
    "WeChatName": "一路向北",
    "ChatInfo": "我一路向北，离开有你的季节",
    "time": "10:16"
  }
]

export function getContactInfo(): Array<Person> {
  let contactList: Array<Person> = []
  ContactInfo.forEach(item => {
    contactList.push(new Person(item.WeChatImage, item.WeChatName, item.ChatInfo, item.time))
  })

  return contactList;
}

export const WeChatColor:string = "#ededed"