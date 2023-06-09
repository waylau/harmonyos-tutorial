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

import { VideoBean } from '../common/bean/VideoBean';
import { HomeConstants } from '../common/constants/HomeConstants';
import { CommonConstants } from '../common/constants/CommonConstants';

@Component
export struct HomeTabContentListItem {
  private item: VideoBean;

  build() {
    Row() {
      Image(this.item.pixelMap === undefined ? $r('app.media.ic_internet') : this.item.pixelMap)
        .height(HomeConstants.HOME_TAB_LIST.IMAGE_HEIGHT)
        .width(HomeConstants.HOME_TAB_LIST.IMAGE_WIDTH)
        .margin({ left: $r('app.float.item_image_margin_left') })
        .borderRadius($r('app.float.image_border_radius'))
      Column() {
        Column() {
          Text(this.item.name)
            .fontSize($r('app.float.item_font_size'))
            .margin({
              left: $r('app.float.item_text_margin_left'),
              right: $r('app.float.item_text_margin_right')
            })
        }
        .height(CommonConstants.FULL_PERCENT)
        .width(CommonConstants.FULL_PERCENT)
        .justifyContent(FlexAlign.Center)
        .alignItems(HorizontalAlign.Start)

        Divider()
          .strokeWidth(HomeConstants.HOME_TAB_LIST.DIVIDER_STROKE_WIDTH)
          .color($r('app.color.divider_color'))
          .margin({
            left: $r('app.float.item_divider_margin_left'),
            right: $r('app.float.item_divider_margin_right')
          })
      }
      .height(CommonConstants.FULL_PERCENT)
      .width(HomeConstants.HOME_TAB_LIST.LIST_ITEM_ROW_COLUMN_WIDTH)
    }
    .width(CommonConstants.FULL_PERCENT)
    .height(HomeConstants.HOME_TAB_LIST.LIST_ITEM_ROW_HEIGHT)
  }
}