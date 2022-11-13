/*
 * Copyright (c) 2022 Huawei Device Co., Ltd.
 * Licensed under the Apache License,Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import lottie from '@ohos/lottieETS';
import { Logger } from '../common/utils/log/logger';
import { CommonConstants } from '../common/constants/CommonConst';


@Component
export struct Outer {
  private renderingSettings: RenderingContextSettings = new RenderingContextSettings(true);
  private renderingContext: CanvasRenderingContext2D = new CanvasRenderingContext2D(this.renderingSettings);
  private animateName: string = CommonConstants.ANIMATE_NAME;
  private animateItem: any = null;
  @State canvasTitle: Resource = undefined;

  aboutToDisappear(): void {
    Logger.info(CommonConstants.OUTER_TAG, `aboutToDisappear`);
    lottie.destroy();
  }

  onPageShow(): void {
    Logger.info(CommonConstants.OUTER_TAG, `onPageShow`);
    lottie.play();
  }

  onPageHide(): void {
    Logger.info(CommonConstants.OUTER_TAG, `onPageShow`);
    lottie.pause();
  }

  build() {
    Flex({ direction: FlexDirection.Column, justifyContent: FlexAlign.SpaceBetween }) {

      // Canvas area
      Column() {
        Canvas(this.renderingContext)
          .width(CommonConstants.CONTAINER_WIDTH)
          .aspectRatio(CommonConstants.ASPECT_RATIO_176)
          .backgroundImage($r('app.media.canvasBg'))
          .backgroundImageSize(ImageSize.Cover)
          .onDisAppear(() => {
            lottie.destroy(this.animateName);
          })
        Text(this.canvasTitle)
          .width(CommonConstants.CONTAINER_WIDTH)
          .fontSize($r('app.float.fontSize_14'))
          .textAlign(TextAlign.Center)
          .fontWeight(FontWeight.Bold)
          .fontColor($r('app.color.outer_canvas_title'))
          .margin({ top: $r('app.float.default_12') })
          .opacity(CommonConstants.OPACITY_4)
      }
      .margin({
        top: $r('app.float.default_10'),
        left: $r('app.float.default_10'),
        right: $r('app.float.default_10')
      })

      // Buttons area
      Column({ space: CommonConstants.SPACE_12 }) {
        Button() {
          Text($r('app.string.outer_button_load'))
            .fontSize($r('app.float.fontSize_16'))
            .fontColor($r('app.color.outer_button_font'))
            .fontWeight(FontWeight.Bold)
        }
        .width(CommonConstants.CONTAINER_WIDTH)
        .height($r('app.float.default_40'))
        .backgroundColor($r('app.color.outer_button_bg'))
        .onClick(() => {
          this.canvasTitle = $r('app.string.outer_button_load');
          this.animateItem = lottie.loadAnimation({
            container: this.renderingContext,
            renderer: 'canvas',
            loop: 10,
            autoplay: true,
            name: this.animateName,
            path: 'common/lottie/data.json'
          });
        })

        Button() {
          Text($r('app.string.outer_button_end'))
            .fontSize($r('app.float.fontSize_16'))
            .fontColor($r('app.color.outer_button_font'))
            .fontWeight(FontWeight.Bold)
        }
        .width(CommonConstants.CONTAINER_WIDTH)
        .height($r('app.float.default_40'))
        .backgroundColor($r('app.color.outer_button_bg'))
        .onClick(() => {
          this.canvasTitle = $r('app.string.outer_button_end');
          this.animateItem.goToAndPlay(CommonConstants.ZERO_FRAME, true);
        })

        Flex({ justifyContent: FlexAlign.SpaceBetween }) {
          Button() {
            Text($r('app.string.outer_button_start'))
              .fontSize($r('app.float.fontSize_16'))
              .fontColor($r('app.color.outer_button_font'))
              .fontWeight(FontWeight.Bold)
          }
          .width(CommonConstants.CONTAINER_HALF_WIDTH)
          .height($r('app.float.default_40'))
          .backgroundColor($r('app.color.outer_button_bg'))
          .onClick(() => {
            this.canvasTitle = $r('app.string.outer_button_start');
            lottie.play();
          })

          Button() {
            Text($r('app.string.outer_button_pause'))
              .fontSize($r('app.float.fontSize_16'))
              .fontColor($r('app.color.outer_button_font'))
              .fontWeight(FontWeight.Bold)
          }
          .width(CommonConstants.CONTAINER_HALF_WIDTH)
          .height($r('app.float.default_40'))
          .backgroundColor($r('app.color.outer_button_bg'))
          .onClick(() => {
            this.canvasTitle = $r('app.string.outer_button_pause');
            lottie.pause();
          })
        }
      }
      .padding({
        left: $r('app.float.default_23'),
        right: $r('app.float.default_23'),
        bottom: $r('app.float.default_41')
      })
    }
    .height(CommonConstants.CONTAINER_HEIGHT)
  }
}