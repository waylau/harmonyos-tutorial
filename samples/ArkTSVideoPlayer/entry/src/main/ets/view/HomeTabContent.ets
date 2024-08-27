
import { VideoBean } from '../common/bean/VideoBean';
import { HomeTabContentList } from './HomeTabContentList';
import { HomeTabContentButton } from './HomeTabContentButton';
import { CommonConstants } from '../common/constants/CommonConstants';

@Component
export struct HomeTabContent {
  private currIndex: number;
  @Provide videoList: Array<VideoBean> = [];

  build() {
    Column() {
      HomeTabContentList({currIndex: this.currIndex});
      HomeTabContentButton({currIndex: this.currIndex});
    }
    .width(CommonConstants.FULL_PERCENT)
    .height(CommonConstants.FULL_PERCENT)
  }
}