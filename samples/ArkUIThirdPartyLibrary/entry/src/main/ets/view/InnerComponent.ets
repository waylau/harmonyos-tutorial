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

import { Buttons } from '@ohos/library';
import InnerViewModel from '../viewmodel/InnerViewModel'
import { ButtonList } from '../common/bean/ButtonList';
import { CommonConstants } from '../common/constants/CommonConst';

@Component
export struct Inner {
  @State buttonList: ButtonList[] = InnerViewModel.getButtonListData();
  scroller: Scroller = new Scroller();

  build() {
    Scroll(this.scroller) {
      Column({ space: CommonConstants.SPACE_12 }) {
        ForEach(this.buttonList, (item) => {
          Column() {
            Flex({
              direction: FlexDirection.Column,
              justifyContent: FlexAlign.SpaceBetween,
              alignItems: ItemAlign.Start
            }) {
              Column() {
                Text(item.title)
                  .height($r('app.float.default_21'))
                  .fontSize($r('app.float.fontSize_16'))
                  .fontColor($r('app.color.common_color'))
                  .fontWeight(FontWeight.Bold)
                Text(item.subtitle)
                  .height($r('app.float.default_16'))
                  .fontSize($r('app.float.fontSize_12'))
                  .fontColor($r('app.color.common_color'))
                  .fontWeight(CommonConstants.FONT_WEIGHT_400)
                  .margin({ top: $r('app.float.default_4') })
                  .opacity(CommonConstants.OPACITY_6)
              }
              .alignItems(HorizontalAlign.Start)

              Column() {
                Buttons({
                  buttonText: item.buttonText,
                  buttonShape: item.buttonShape,
                  buttonType: item.buttonType,
                  stateEffect: item.stateEffect,
                  fontColor: item.fontColor
                })
                  .alignSelf(ItemAlign.Center)
                  .margin({ bottom: $r('app.float.default_21') })
              }
              .width($r('app.float.default_260'))
              .height($r('app.float.default_90'))
              .backgroundImage($r('app.media.mobile'))
              .backgroundImageSize(ImageSize.Contain)
              .justifyContent(FlexAlign.End)
              .alignSelf(ItemAlign.Center)
              .align(Alignment.End)
            }
            .padding({
              bottom: $r('app.float.default_24')
            })
            .width(CommonConstants.CONTAINER_WIDTH)
            .height(CommonConstants.CONTAINER_HEIGHT)
          }
          .width(CommonConstants.CONTAINER_WIDTH)
          .aspectRatio(CommonConstants.ASPECT_RATIO_176)
          .padding({
            top: $r('app.float.default_12'),
            left: $r('app.float.default_8')
          })
          .backgroundColor($r('app.color.white'))
          .borderRadius($r('app.float.default_24'))
        })
      }
      .width(CommonConstants.CONTAINER_WIDTH)
      .padding({
        left: $r('app.float.default_12'),
        right: $r('app.float.default_12'),
        top: $r('app.float.default_12')
      })
    }
    .scrollable(ScrollDirection.Vertical)
    .scrollBar(BarState.Off)
    .margin({bottom: $r('app.float.default_24')})
  }
}