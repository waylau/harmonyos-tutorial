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
export struct PlayControl {
  private playVideoModel: VideoController;
  @Consume status: number;

  build() {
    Column() {
      Row() {
        Image($r('app.media.ic_previous'))
          .width($r('app.float.control_image_width'))
          .aspectRatio(CommonConstants.ASPECT_RATIO)
          .onClick(async () => {
            this.playVideoModel.previousVideo();
            this.status = CommonConstants.STATUS_START;
          })
        Column() {
          Image(this.status === CommonConstants.STATUS_START ?
            $r('app.media.ic_pause') : $r('app.media.ic_play'))
            .width($r('app.float.control_image_width'))
            .aspectRatio(CommonConstants.ASPECT_RATIO)
            .onClick(async () => {
              let curStatus = (this.playVideoModel.getStatus() === CommonConstants.STATUS_START);
              this.status = curStatus ? CommonConstants.STATUS_PAUSE : CommonConstants.STATUS_START;
              this.playVideoModel.switchPlayOrPause();
            })
        }
        .layoutWeight(1)
        Image($r('app.media.ic_next'))
          .width($r('app.float.control_image_width'))
          .aspectRatio(CommonConstants.ASPECT_RATIO)
          .onClick(() => {
            this.playVideoModel.nextVideo();
            this.status = CommonConstants.STATUS_START;
          })
      }
      .width(PlayConstants.PLAY_CONTROL.ROW_WIDTH)
    }
    .width(CommonConstants.FULL_PERCENT)
    .height(CommonConstants.FULL_PERCENT)
    .justifyContent(FlexAlign.Center)
  }
}