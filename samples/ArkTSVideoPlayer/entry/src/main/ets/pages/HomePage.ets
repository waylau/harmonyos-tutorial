import ScreenUtil from '../common/util/ScreenUtil';
import { HomeConstants } from '../common/constants/HomeConstants';
import { CommonConstants } from '../common/constants/CommonConstants';
import { HomeTabContent } from '../view/HomeTabContent';

@Entry
@Component
struct HomePage {
  @State currentIndex: number = HomeConstants.HOME_TAB.CURRENT_INDEX;
  private controller: TabsController = new TabsController();

  aboutToAppear() {
    ScreenUtil.setScreenSize();
  }

  @Builder TabBuilder(index: number, name: Resource) {
    Column() {
      Text(name)
        .fontColor(this.currentIndex === index ?
          $r('app.color.index_tab_selected_font_color') : $r('app.color.index_tab_font_color'))
        .fontSize($r('app.float.home_page_font_size'))
        .fontWeight(this.currentIndex === index ?
        HomeConstants.HOME_TAB.FONT_WEIGHT_SELECT : HomeConstants.HOME_TAB.FONT_WEIGHT_UNSELECT)
        .lineHeight(HomeConstants.HOME_TAB.LINE_HEIGHT)
        .margin({
          top: HomeConstants.HOME_TAB.MARGIN_TOP_TWO,
          bottom: HomeConstants.HOME_TAB.MARGIN_BOTTOM
        })
      Divider()
        .strokeWidth(HomeConstants.HOME_TAB.STROKE_WIDTH)
        .color($r('app.color.index_tab_selected_font_color'))
        .opacity(this.currentIndex === index ?
        HomeConstants.HOME_TAB.TAB_BAR_SECOND : HomeConstants.HOME_TAB.TAB_BAR_FIRST)
    }
    .width(CommonConstants.FULL_PERCENT)
  }

  build() {
    Column() {
      Tabs({ barPosition: BarPosition.Start, controller: this.controller }) {
        TabContent() {
          HomeTabContent({ currIndex: HomeConstants.HOME_TAB.TAB_BAR_FIRST });
        }.tabBar(this.TabBuilder(HomeConstants.HOME_TAB.TAB_BAR_FIRST, $r('app.string.index_tab_local_video')))

        TabContent() {
          HomeTabContent({ currIndex: HomeConstants.HOME_TAB.TAB_BAR_SECOND });
        }.tabBar(this.TabBuilder(HomeConstants.HOME_TAB.TAB_BAR_SECOND, $r('app.string.index_tab_internet_video')))
      }
      .vertical(false)
      .barMode(BarMode.Fixed)
      .barWidth(HomeConstants.HOME_TAB.BAR_WIDTH)
      .barHeight(HomeConstants.HOME_TAB.BAR_HEIGHT)
      .onChange((index: number) => {
        this.currentIndex = index;
      })
      .width(CommonConstants.FULL_PERCENT)
      .height(CommonConstants.FULL_PERCENT)
      .margin({ top: $r('app.float.home_tab_margin_top') })
    }
    .width(CommonConstants.FULL_PERCENT)
    .height(CommonConstants.FULL_PERCENT)
    .backgroundColor($r('app.color.index_background'))
  }
}