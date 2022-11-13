import {  GoodsData} from './GoodsData'

import {Menu, ImageItem} from './Menu'
import {ArsData} from './ArsData'

export function initializeOnStartup(): Array<GoodsData> {
  let GoodsDataArray: Array<GoodsData> = []
  GoodsComposition.forEach(item => {
    console.log(item.title);
    GoodsDataArray.push(new GoodsData(item.title, item.content, item.price, item.imgSrc));
  })
  return GoodsDataArray;
}

export function getIconPath(): Array<string> {
  let IconPath: Array<string> = ['nav/icon-buy.png','nav/icon-shopping-cart.png','nav/icon-my.png']

  return IconPath;
}

export function getIconPathSelect(): Array<string> {
  let IconPathSelect: Array<string> = ['nav/icon-home.png','nav/icon-shopping-cart-select.png','nav/icon-my-select.png']

  return IconPathSelect;
}

export function getDetailImages(): Array<string> {
  let detailImages: Array<string> = ['computer/computer1.png','computer/computer2.png','computer/computer3.png','computer/computer4.png','computer/computer5.png','computer/computer6.png']

  return detailImages;
}


export function getMenu(): Array<Menu> {
  let MenuArray: Array<Menu> = []
  MyMenu.forEach(item => {
    MenuArray.push(new Menu(item.title,item.num));
  })
  return MenuArray;
}

export function getTrans(): Array<ImageItem> {
  let ImageItemArray: Array<ImageItem> = []
  MyTrans.forEach(item => {
    ImageItemArray.push(new ImageItem(item.title,item.imageSrc));
  })
  return ImageItemArray;
}

export function getMore(): Array<ImageItem> {
  let ImageItemArray: Array<ImageItem> = []
  MyMore.forEach(item => {
    ImageItemArray.push(new ImageItem(item.title,item.imageSrc));
  })
  return ImageItemArray;
}

export function getArs(): Array<ArsData> {
  let ArsItemArray: Array<ArsData> = []
  ArsList.forEach(item => {
    ArsItemArray.push(new ArsData(item.title,item.content));
  })
  return ArsItemArray;
}

const GoodsComposition: any[] = [
  {
    "title": 'HUAWEI nova 8 Pro ',
    "content": 'Goes on sale: 10:08',
    "price": '3999',
    "imgSrc": $rawfile('picture/HW (1).png')
  },
  {
    "title": 'HUAWEI Mate 30E Pro 5G',
    "content": '3 interest-free payments ',
    "price": '5299',
    "imgSrc": $rawfile('picture/HW (2).png')
  },
  {
    "title": 'HUAWEI MatePad Pro',
    "content": 'Flagship ',
    "price": '3799',
    "imgSrc": $rawfile('picture/HW (3).png')
  },
  {
    "title": 'HUAWEI Nova 8 Pro',
    "content": 'New arrival ',
    "price": '3999',
    "imgSrc": $rawfile('picture/HW (4).png')
  },
  {
    "title": 'HUAWEI WATCH FIT',
    "content": 'Versatile',
    "price": '769',
    "imgSrc": $rawfile('picture/HW (5).png')
  },
  {
    "title": 'HUAWEI nova 8 Pro ',
    "content": 'Goes on sale: 10:08',
    "price": '3999',
    "imgSrc": $rawfile('picture/HW (6).png')
  },
  {
    "title": 'HUAWEI Mate 30E Pro 5G',
    "content": '3 interest-free payments ',
    "price": '5299',
    "imgSrc": $rawfile('picture/HW (7).png')
  },
  {
    "title": 'HUAWEI MatePad Pro',
    "content": 'Flagship ',
    "price": '3799',
    "imgSrc": $rawfile('picture/HW (8).png')
  },
  {
    "title": 'HUAWEI Nova 8 Pro',
    "content": 'New arrival ',
    "price": '3999',
    "imgSrc": $rawfile('picture/HW (9).png')
  },
  {
    "title": 'HUAWEI WATCH FIT',
    "content": 'Versatile',
    "price": '769',
    "imgSrc": $rawfile('picture/HW (10).png')
  },
]

const MyMenu: any[] = [
  {
    'title': 'Favorites',
    'num': '10'
  },
  {
    'title': 'Searched',
    'num': '1000'
  },
  {
    'title': 'Following',
    'num': '100'
  },
  {
    'title': 'Followers',
    'num': '10000'
  }
]




const MyTrans: any[] = [
  {
    'title': 'Post: 520',
    'imageSrc': $rawfile('nav/icon-menu-release.png')
  },
  {
    'title': 'Sold: 520',
    'imageSrc': $rawfile('nav/icon-menu-sell.png')
  },
  {
    'title': 'Bought: 10',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  }
]

const MyMore: any[] = [
  {
    'title': 'Guide',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  },
  {
    'title': 'Create',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  },
  {
    'title': 'Poster',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  },
  {
    'title': 'Games',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  },
  {
    'title': 'Jobber',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  },
  {
    'title': 'Myself',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  },
  {
    'title': 'About',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  },
  {
    'title': 'Rental',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  },
  {
    'title': 'Author',
    'imageSrc': $rawfile('nav/icon-menu-buy.png')
  },

]

const ArsList: any[] =[
  {
    'title': 'Display Size',
    'content': '13.9 inches',
  },
  {
    'title': 'Memory',
    'content': '16 GB',
  },
  {
    'title': 'Marketing Name',
    'content': 'HUAWEI MateBook X Pro',
  },
  {
    'title': 'Color Gamut',
    'content': '100% sRGB color gamut (Typical)',
  },
  {
    'title': 'Battery',
    'content': '56 Wh (rated capacity)',
  },
  {
    'title': 'Storage',
    'content': '512 GB',
  },
  {
    'title': 'Resolution',
    'content': '3000x2000',
  },
  {
    'title': 'Processor',
    'content': '11th Gen Intel® Core™ i7-1165G7 Processor',
  },
  {
    'title': 'CPU Cores',
    'content': '4',
  },
  {
    'title': 'Launch Time',
    'content': 'January 2021',
  }
]