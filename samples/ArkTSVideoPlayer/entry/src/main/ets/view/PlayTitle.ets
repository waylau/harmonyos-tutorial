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

import router from '@ohos.router';
import { PlayTitleDialog } from '../view/PlayTitleDialog';
import { VideoController } from '../controller/VideoController';
import { CommonConstants } from '../common/constants/CommonConstants';
import { PlayConstants } from '../common/constants/PlayConstants';

@Component
export struct PlayTitle {
  private  playVideoModel: VideoController;
  @State @Watch('watchSpeed')playSpeed: number = 1;
  @State loop: boolean = false;
  @State customPopup: boolean = false;
  dialogController: CustomDialogController = new CustomDialogController({
    builder: PlayTitleDialog({
      playSpeed: $playSpeed
    }),
    autoCancel: true,
    alignment: DialogAlignment.Bottom,
    offset: { dx: PlayConstants.PLAY_TITLE.DX, dy: PlayConstants.PLAY_TITLE.DY },
    gridCount: PlayConstants.PLAY_TITLE.GRID_COUNT,
    customStyle: false
  })

  @Builder popupBuilder() {
    Column() {
      Row() {
        Image($r('app.media.ic_speed'))
          .width($r('app.float.title_popup_image_size'))
          .aspectRatio(CommonConstants.ASPECT_RATIO)
          .margin({ left: $r('app.float.title_popup_image_left') })
        Text($r('app.string.speed_play'))
          .fontSize($r('app.float.title_popup_font_size'))
          .margin({ left: $r('app.float.title_popup_text_left') })
      }
      .width(CommonConstants.FULL_PERCENT)
      .height(PlayConstants.PLAY_TITLE.POPUP.ROW_HEIGHT)
      .margin({ top: PlayConstants.PLAY_TITLE.POPUP.ROW_MARGIN_TOP })
      .onClick(() => {
        this.customPopup = !this.customPopup;
        this.dialogController.open();
      })

      Row() {
        Divider()
          .strokeWidth(PlayConstants.PLAY_TITLE.POPUP.DIVIDER_STROKE_WIDTH)
          .color($r('app.color.divider_color'))
          .margin({
            left: $r('app.float.title_popup_divider_left'),
            right: PlayConstants.PLAY_TITLE.POPUP.DIVIDER_MARGIN_RIGHT
          })
      }
      .width(CommonConstants.FULL_PERCENT)

      Row() {
        Image(this.loop ? $r('app.media.ic_single_loop') : $r('app.media.ic_sequence_play'))
          .width($r('app.float.title_popup_image_size'))
          .aspectRatio(CommonConstants.ASPECT_RATIO)
          .margin({ left: $r('app.float.title_popup_image_left') })
        Text(this.loop ? $r('app.string.monolithic_cycle') : $r('app.string.continuous_playback'))
          .fontSize($r('app.float.title_popup_font_size'))
          .margin({ left: $r('app.float.title_popup_text_left') })
      }
      .width(CommonConstants.FULL_PERCENT)
      .height(PlayConstants.PLAY_TITLE.POPUP.ROW_HEIGHT)
      .onClick(() => {
        this.loop = !this.loop;
        this.playVideoModel.setLoop();
        setTimeout(() => {
          this.customPopup = !this.customPopup;
        },  PlayConstants.PLAY_TITLE.POPUP.CLOSE_TIME);
      })
    }
    .justifyContent(FlexAlign.Center)
    .alignItems(HorizontalAlign.Center)
    .width(PlayConstants.PLAY_TITLE.POPUP.COLUMN_WIDTH)
    .height(PlayConstants.PLAY_TITLE.POPUP.COLUMN_HEIGHT)
  }

  aboutToAppear() {
    if (this.playVideoModel !== null) {
      this.playVideoModel.initTitleThis(this);
    }
  }

  watchSpeed() {
    this.playVideoModel.setSpeed(this.playSpeed);
  }

  build() {
    Column() {
      Row() {
        Image($r('app.media.ic_back'))
          .width($r('app.float.title_image_size'))
          .aspectRatio(CommonConstants.ASPECT_RATIO)
          .onClick(() => {
            router.back();
          })
        Text($r('app.string.video_playback'))
          .fontColor(Color.White)
          .fontSize($r('app.float.title_font_size'))
          .margin({ left: PlayConstants.PLAY_TITLE.TEXT_MARGIN_LEFT })
          .layoutWeight(1)
        Image($r('app.media.ic_more'))
          .width($r('app.float.title_image_size'))
          .aspectRatio(CommonConstants.ASPECT_RATIO)
          .bindPopup(this.customPopup, {
            builder: this.popupBuilder,
            placement: Placement.BottomRight,
            popupColor: Color.White,
            enableArrow: false
          })
          .onClick(() => {
            this.customPopup = !this.customPopup;
          })
      }
      .width(PlayConstants.PLAY_TITLE.ROW_WIDTH)
    }
    .width(CommonConstants.FULL_PERCENT)
    .height(CommonConstants.FULL_PERCENT)
    .justifyContent(FlexAlign.Center)
  }
}