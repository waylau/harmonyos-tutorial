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
import { PlayTitle } from '../view/PlayTitle';
import { PlayPlayer } from '../view/PlayPlayer';
import { PlayControl } from '../view/PlayControl';
import { PlayProgress } from '../view/PlayProgress';
import { VideoController } from '../controller/VideoController';
import { CommonConstants } from '../common/constants/CommonConstants';
import { PlayConstants } from '../common/constants/PlayConstants';

@Entry
@Component
struct PlayPage {
  @State videoHeight: string = PlayConstants.PLAY_PAGE.PLAY_PLAYER_HEIGHT;
  @State videoWidth: string = CommonConstants.FULL_PERCENT;
  @State videoMargin: string = PlayConstants.PLAY_PAGE.MARGIN_ZERO;
  @State videoPosition: FlexAlign  = FlexAlign.Center;
  private playVideoModel: VideoController = new VideoController();
  @Provide src: string = router.getParams()['src'];
  @Provide index: number = router.getParams()['index'];
  @Provide type: number = router.getParams()['type'];
  @Provide status: number = CommonConstants.STATUS_START;
  private panOptionBright: PanGestureOptions = new PanGestureOptions({ direction: PanDirection.Vertical });
  private panOptionVolume: PanGestureOptions = new PanGestureOptions({ direction: PanDirection.Horizontal });

  aboutToAppear() {
    this.playVideoModel.initPlayPageThis(this);
  }

  aboutToDisappear() {
    this.playVideoModel.release();
  }

  onPageHide() {
    this.status = CommonConstants.STATUS_PAUSE;
    this.playVideoModel.pause();
  }

  build() {
    Stack() {
      Column () {
        Column(){
        }
        .height(this.videoMargin)
        PlayPlayer({ playVideoModel: this.playVideoModel })
          .width(this.videoWidth)
          .height(this.videoHeight)
      }
      .height(CommonConstants.FULL_PERCENT)
      .width(CommonConstants.FULL_PERCENT)
      .justifyContent(this.videoPosition)
      .zIndex(0)
      Column() {
        PlayTitle({ playVideoModel: this.playVideoModel })
          .width(CommonConstants.FULL_PERCENT)
          .height(PlayConstants.PLAY_PAGE.HEIGHT)
        Column()
          .width(CommonConstants.FULL_PERCENT)
          .height(PlayConstants.PLAY_PAGE.COLUMN_HEIGHT_ONE)
          .gesture(
            PanGesture(this.panOptionBright)
              .onActionStart((event: GestureEvent) => {
                this.playVideoModel.onBrightActionStart(event);
              })
              .onActionUpdate((event: GestureEvent) => {
                this.playVideoModel.onBrightActionUpdate(event);
              })
              .onActionEnd(() => {
                this.playVideoModel.onActionEnd();
              })
          )
        Column() {
        }
        .width(CommonConstants.FULL_PERCENT)
        .height(PlayConstants.PLAY_PAGE.PLAY_PLAYER_HEIGHT)
        Column()
          .width(CommonConstants.FULL_PERCENT)
          .height(PlayConstants.PLAY_PAGE.COLUMN_HEIGHT_TWO)
          .gesture(
            PanGesture(this.panOptionVolume)
              .onActionStart((event: GestureEvent) => {
                this.playVideoModel.onVolumeActionStart(event);
              })
              .onActionUpdate((event: GestureEvent) => {
                this.playVideoModel.onVolumeActionUpdate(event);
              })
              .onActionEnd(() => {
                this.playVideoModel.onActionEnd();
              })
          )
        PlayControl({ playVideoModel: this.playVideoModel })
          .width(CommonConstants.FULL_PERCENT)
          .height(PlayConstants.PLAY_PAGE.HEIGHT)
        PlayProgress({ playVideoModel: this.playVideoModel })
          .width(CommonConstants.FULL_PERCENT)
          .height(PlayConstants.PLAY_PAGE.PLAY_PROGRESS_HEIGHT)
      }
      .height(CommonConstants.FULL_PERCENT)
      .width(CommonConstants.FULL_PERCENT)
      .zIndex(1)
    }
    .height(CommonConstants.FULL_PERCENT)
    .width(CommonConstants.FULL_PERCENT)
    .backgroundColor(Color.Black)
  }
}