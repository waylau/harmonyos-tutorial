import { ChatPage } from './ChatPage'
import { ContactPage } from './ContactPage'
import { DiscoveryPage } from './DiscoveryPage'
import { MyPage } from './MyPage'

@Entry
@Component
struct Index {
  @Provide currentPage: number = 0
  @State currentIndex: number = 0;

  build() {
    Column() {
      Tabs({
        index: this.currentIndex,
        barPosition: BarPosition.End
      }) {
        TabContent() {
          ChatPage()
        }
        .tabBar(this.TabBuilder('微信', 0, $r('app.media.wechat2'), $r('app.media.wechat1')))

        TabContent() {
          ContactPage()
        }
        .tabBar(this.TabBuilder('联系人', 1, $r('app.media.contacts2'), $r('app.media.contacts1')))

        TabContent() {
          DiscoveryPage()
        }
        .tabBar(this.TabBuilder('发现', 2, $r('app.media.find2'), $r('app.media.find1')))

        TabContent() {
          MyPage()
        }
        .tabBar(
          this.TabBuilder('我', 3, $r('app.media.me2'), $r('app.media.me1'))
        )
      }
      .barMode(BarMode.Fixed)
      .onChange((index: number) => {
        this.currentIndex = index;
      })
    }
  }

  @Builder TabBuilder(title: string, targetIndex: number, selectedImg: Resource, normalImg: Resource) {
    Column() {
      Image(this.currentIndex === targetIndex ? selectedImg : normalImg)
        .size({ width: 25, height: 25 })
      Text(title)
        .fontColor(this.currentIndex === targetIndex ? '#1698CE' : '#6B6B6B')
    }
    .width('100%')
    .height(50)
    .justifyContent(FlexAlign.Center)
  }
}

@Component
struct HomeTopPage {
  @Consume currentPage: number

  build() {
    Swiper() {
      ChatPage()
      ContactPage()
      DiscoveryPage()
      MyPage()
    }
    .onChange((index: number) => {
      this.currentPage = index
    })
    .index(this.currentPage)
    .loop(false)
    .indicator(false)
    .width('100%')
    .height('100%')
  }
}
