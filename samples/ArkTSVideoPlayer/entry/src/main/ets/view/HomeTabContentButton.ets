/*
 * Copyright (c) 2023 Huawei Device Co., Ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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