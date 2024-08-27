
import router from '@ohos.router';
import Logger from '../common/util/Logger';
import { VideoBean } from '../common/bean/VideoBean';
import HomeVideoListModel from '../viewmodel/HomeVideoListModel';
import { HomeConstants } from '../common/constants/HomeConstants';
import { CommonConstants } from '../common/constants/CommonConstants';
import { HomeTabContentListItem } from './HomeTabContentListItem';

@Component
export struct HomeTabContentList {
  private currIndex: number;
  @Consume videoList: Array<VideoBean>;
  @State item: VideoBean = undefined;

  async aboutToAppear() {
    if (this.currIndex === CommonConstants.TYPE_LOCAL) {
      this.videoList = await HomeVideoListModel.getLocalVideo();
    } else {
      this.videoList = globalThis.videoInternetList;
    }
  }

  build() {
    Column() {
      List({
        space: HomeConstants.HOME_TAB_LIST.LIST_SPACE,
        initialIndex: HomeConstants.HOME_TAB_LIST.LIST_INITIAL_INDEX
      }) {
        ForEach(this.videoList, (item: VideoBean, index: number) => {
          ListItem() {
            HomeTabContentListItem({item: item});
          }.onClick(() => {
            globalThis.videoList = this.videoList;
            router.pushUrl({
              url: CommonConstants.PAGE,
              params: {
                src: item.src,
                index: index,
                type: this.currIndex
              }
            }).catch(err => {
              Logger.error('[IndexTabLocalList] router error: ' + JSON.stringify(err))
            });
          })
        }, item => JSON.stringify(item))
      }
      .backgroundColor(Color.White)
      .borderRadius($r('app.float.list_border_radius'))
    }
    .width(HomeConstants.HOME_TAB_LIST.COLUMN_WIDTH)
    .height(CommonConstants.NINETY_PERCENT)
  }
}