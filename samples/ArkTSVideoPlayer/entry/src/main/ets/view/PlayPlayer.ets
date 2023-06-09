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

import { VideoController } from '../controller/VideoController';
import { CommonConstants } from '../common/constants/CommonConstants';
import { PlayConstants } from '../common/constants/PlayConstants';

@Component
export struct PlayPlayer {
  private playVideoModel: VideoController;
  @Consume src: string;
  @Consume index: number;
  @State volume: number = PlayConstants.PLAY_PAGE.VOLUME;
  @State volumeShow: boolean = PlayConstants.PLAY_PAGE.VOLUME_SHOW;
  @State bright: number = PlayConstants.PLAY_PAGE.BRIGHT;
  @State brightShow: boolean = PlayConstants.PLAY_PAGE.BRIGHT_SHOW;
  private xComponentController;
  private surfaceID: number;

  aboutToAppear() {
    if (this.playVideoModel !== null) {
      this.playVideoModel.initPlayerThis(this);
    }
    this.xComponentController = new XComponentController();
  }

  build() {
    Stack() {
      XComponent({
        id: PlayConstants.PLAY_PLAYER.ID,
        type: PlayConstants.PLAY_PLAYER.TYPE,
        libraryname: PlayConstants.PLAY_PLAYER.LIBRARY_NAME,
        controller: this.xComponentController
      })
        .onLoad(async () => {
          this.xComponentController.setXComponentSurfaceSize({
            surfaceWidth: PlayConstants.PLAY_PLAYER.SURFACE_WIDTH,
            surfaceHeight: PlayConstants.PLAY_PLAYER.SURFACE_HEIGHT
          });
          this.surfaceID = this.xComponentController.getXComponentSurfaceId();
          this.playVideoModel.firstPlay(this.index, this.src, this.surfaceID);
        })
        .width(CommonConstants.FULL_PERCENT)
        .height(CommonConstants.FULL_PERCENT)

      Stack() {
        Progress({
          value: Math.floor(this.volume * CommonConstants.ONE_HUNDRED),
          type: ProgressType.Ring
        })
          .width(CommonConstants.FULL_PERCENT)
          .aspectRatio(CommonConstants.ASPECT_RATIO)
        Image($r('app.media.ic_volume'))
          .width(PlayConstants.PLAY_PLAYER.IMAGE_WIDTH)
          .aspectRatio(CommonConstants.ASPECT_RATIO)
      }
      .width(PlayConstants.PLAY_PLAYER.STACK_WIDTH)
      .aspectRatio(CommonConstants.ASPECT_RATIO)
      .visibility(this.volumeShow ? Visibility.Visible : Visibility.Hidden)

      Stack() {
        Progress({
          value: Math.floor(this.bright * CommonConstants.ONE_HUNDRED),
          type: ProgressType.Ring
        })
          .width(CommonConstants.FULL_PERCENT)
          .aspectRatio(CommonConstants.ASPECT_RATIO)
        Image($r('app.media.ic_brightness'))
          .width(PlayConstants.PLAY_PLAYER.IMAGE_WIDTH)
          .aspectRatio(CommonConstants.ASPECT_RATIO)
      }
      .width(PlayConstants.PLAY_PLAYER.STACK_WIDTH)
      .aspectRatio(CommonConstants.ASPECT_RATIO)
      .visibility(this.brightShow ? Visibility.Visible : Visibility.Hidden)
    }
    .width(CommonConstants.FULL_PERCENT)
    .height(CommonConstants.FULL_PERCENT)
  }
}