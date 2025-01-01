import { GoodsData } from './GoodsData'

import { Menu, ImageItem } from './Menu'
import { ArsData } from './ArsData'

export function initializeOnStartup(): Array<GoodsData> {
  return GoodsComposition;
}

export function getIconPath(): Array<string> {
  let IconPath: Array<string> = ['nav/icon-buy.png', 'nav/icon-shopping-cart.png', 'nav/icon-my.png']

  return IconPath;
}

export function getIconPathSelect(): Array<string> {
  let IconPathSelect: Array<string> =
    ['nav/icon-home.png', 'nav/icon-shopping-cart-select.png', 'nav/icon-my-select.png']

  return IconPathSelect;
}

export function getDetailImages(): Array<string> {
  let detailImages: Array<string> =
    ['computer/computer1.png', 'computer/computer2.png', 'computer/computer3.png', 'computer/computer4.png',
      'computer/computer5.png', 'computer/computer6.png']

  return detailImages;
}


export function getMenu(): Array<Menu> {
  return MyMenu;
}

export function getTrans(): Array<ImageItem> {
  return MyTrans;
}

export function getMore(): Array<ImageItem> {
  return MyMore;
}

export function getArs(): Array<ArsData> {
  return ArsList;
}

const GoodsComposition: GoodsData[] = [
  {
    "id": 1,
    "title": 'HUAWEI nova 8 Pro ',
    "content": 'Goes on sale: 10:08',
    "price": 3999,
    "imgSrc": 'picture/HW (1).png'
  },
  {
    "id": 2,
    "title": 'HUAWEI Mate 30E Pro 5G',
    "content": '3 interest-free payments ',
    "price": 5299,
    "imgSrc": 'picture/HW (2).png'
  },
  {
    "id": 3,
    "title": 'HUAWEI MatePad Pro',
    "content": 'Flagship ',
    "price": 3799,
    "imgSrc": 'picture/HW (3).png'
  },
  {
    "id": 4,
    "title": 'HUAWEI Nova 8 Pro',
    "content": 'New arrival ',
    "price": 3999,
    "imgSrc": 'picture/HW (4).png'
  },
  {
    "id": 5,
    "title": 'HUAWEI WATCH FIT',
    "content": 'Versatile',
    "price": 769,
    "imgSrc": 'picture/HW (5).png'
  },
  {
    "id": 6,
    "title": 'HUAWEI nova 8 Pro ',
    "content": 'Goes on sale: 10:08',
    "price": 3999,
    "imgSrc": 'picture/HW (6).png'
  },
  {
    "id": 7,
    "title": 'HUAWEI Mate 30E Pro 5G',
    "content": '3 interest-free payments ',
    "price": 5299,
    "imgSrc": 'picture/HW (7).png'
  },
  {
    "id": 8,
    "title": 'HUAWEI MatePad Pro',
    "content": 'Flagship ',
    "price": 3799,
    "imgSrc": 'picture/HW (8).png'
  },
  {
    "id": 9,
    "title": 'HUAWEI Nova 8 Pro',
    "content": 'New arrival ',
    "price": 3999,
    "imgSrc": 'picture/HW (9).png'
  },
  {
    "id": 10,
    "title": 'HUAWEI WATCH FIT',
    "content": 'Versatile',
    "price": 769,
    "imgSrc": 'picture/HW (10).png'
  }
]

const MyMenu: Menu[] = [
  {
    'id': 1,
    'title': 'Favorites',
    'num': 10
  },
  {
    'id': 2,
    'title': 'Searched',
    'num': 1000
  },
  {
    'id': 3,
    'title': 'Following',
    'num': 100
  },
  {
    'id': 4,
    'title': 'Followers',
    'num': 345
  }
]


const MyTrans: ImageItem[] = [
  {
    'id': 1,
    'title': 'Post: 520',
    'imageSrc': 'nav/icon-menu-release.png'
  },
  {
    'id': 2,
    'title': 'Sold: 520',
    'imageSrc': 'nav/icon-menu-sell.png'
  },
  {
    'id': 3,
    'title': 'Bought: 10',
    'imageSrc': 'nav/icon-menu-buy.png'
  }
]

const MyMore: ImageItem[] = [
  {
    'id': 1,
    'title': 'Guide',
    'imageSrc': 'nav/icon-menu-buy.png'
  },
  {
    'id': 2,
    'title': 'Create',
    'imageSrc': 'nav/icon-menu-buy.png'
  },
  {
    'id': 3,
    'title': 'Poster',
    'imageSrc': 'nav/icon-menu-buy.png'
  },
  {
    'id': 4,
    'title': 'Games',
    'imageSrc': 'nav/icon-menu-buy.png'
  },
  {
    'id': 5,
    'title': 'Jobber',
    'imageSrc': 'nav/icon-menu-buy.png'
  },
  {
    'id': 6,
    'title': 'Myself',
    'imageSrc': 'nav/icon-menu-buy.png'
  },
  {
    'id': 7,
    'title': 'About',
    'imageSrc': 'nav/icon-menu-buy.png'
  },
  {
    'id': 8,
    'title': 'Rental',
    'imageSrc': 'nav/icon-menu-buy.png'
  },
  {
    'id': 9,
    'title': 'Author',
    'imageSrc': 'nav/icon-menu-buy.png'
  },

]

const ArsList: ArsData[] = [
  {
    'id': 0,
    'title': 'Display Size',
    'content': '13.9 inches',
  },
  {
    'id': 1,
    'title': 'Memory',
    'content': '16 GB',
  },
  {
    'id': 2,
    'title': 'Marketing Name',
    'content': 'HUAWEI MateBook X Pro',
  },
  {
    'id': 3,
    'title': 'Color Gamut',
    'content': '100% sRGB color gamut (Typical)',
  },
  {
    'id': 4,
    'title': 'Battery',
    'content': '56 Wh (rated capacity)',
  },
  {
    'id': 5,
    'title': 'Storage',
    'content': '512 GB',
  },
  {
    'id': 6,
    'title': 'Resolution',
    'content': '3000x2000',
  },
  {
    'id': 7,
    'title': 'Processor',
    'content': '11th Gen Intel® Core™ i7-1165G7 Processor',
  },
  {
    'id': 8,
    'title': 'CPU Cores',
    'content': '4',
  },
  {
    'id': 9,
    'title': 'Launch Time',
    'content': 'January 2021',
  }
]