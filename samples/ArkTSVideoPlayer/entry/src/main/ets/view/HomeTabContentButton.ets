
import { HomeTabContentDialog } from './HomeTabContentDialog';
import { VideoBean } from '../common/bean/VideoBean';
import HomeVideoListModel from '../viewmodel/HomeVideoListModel';
import { CommonConstants } from '../common/constants/CommonConstants';
import { HomeConstants } from '../common/constants/HomeConstants';

@Component
export struct HomeTabContentButton {
  private currIndex: number;
  @Consume videoList: Array<VideoBean>;
  @State name: string = '';
  @State src: string = '';
  dialogController: CustomDialogController = new CustomDialogController({
    builder: HomeTabContentDialog({
      confirm: this.confirm,
      name: $name,
      src: $src,
      videoList: $videoList
    }),
    autoCancel: true,
    alignment: DialogAlignment.Default,
    offset: {
      dx: HomeConstants.INTERNET_ADD_DIALOG.OFFSET_DX,
      dy: HomeConstants.INTERNET_ADD_DIALOG.OFFSET_DY
    },
    gridCount: HomeConstants.INTERNET_ADD_DIALOG.GRID_COUNT,
    customStyle: false
  });

  confirm() {
    HomeVideoListModel.setInternetVideo(this.name, this.src);
    this.videoList = globalThis.videoInternetList;
    this.src = '';
    this.name = '';
  }

  build() {
    Column() {
      Button(this.currIndex === 0 ? $r('app.string.scan_local_video') : $r('app.string.add_internet_video'), {
        type: ButtonType.Normal,
        stateEffect: true
      })
        .borderRadius($r('app.float.tab_border_radius'))
        .fontSize($r('app.float.button_font_size'))
        .height(HomeConstants.HOME_TAB_BUTTON.HEIGHT)
        .backgroundColor($r('app.color.button_back_ground_color'))
        .onClick(async () => {
          if (this.currIndex === 0) {
            this.videoList = await HomeVideoListModel.getLocalVideo();
          } else {
            this.dialogController.open();
          }
        })
    }
    .width(CommonConstants.FULL_PERCENT)
    .height(HomeConstants.HOME_TAB_BUTTON.COLUMN_HEIGHT)
    .justifyContent(FlexAlign.Center)
  }
}