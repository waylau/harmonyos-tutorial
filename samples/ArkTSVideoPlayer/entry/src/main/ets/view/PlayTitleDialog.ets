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

import { CommonConstants } from '../common/constants/CommonConstants';
import { PlayConstants } from '../common/constants/PlayConstants';

@CustomDialog
export struct PlayTitleDialog {
  @Link playSpeed: number;
  controller: CustomDialogController;

  build() {
    Column() {
      Row() {
        Text($r('app.string.speed_play'))
          .fontSize($r('app.float.title_font_size'))
      }
      .height(PlayConstants.PLAY_TITLE_DIALOG.ROW_HEIGHT)
      .width(PlayConstants.PLAY_TITLE_DIALOG.ROW_WIDTH)

      Column() {
        Grid() {
          ForEach(CommonConstants.SPEED_ARRAY, (item) => {
            GridItem() {
              Text(item.text)
                .fontSize($r('app.float.title_dialog_font_size'))
                .backgroundColor($r('app.color.speed_text_color'))
                .width(CommonConstants.FULL_PERCENT)
                .height(CommonConstants.FULL_PERCENT)
                .textAlign(TextAlign.Center)
                .borderRadius($r('app.float.text_border_radius'))
            }
            .onClick(() => {
              this.playSpeed = item.value;
              this.controller.close();
            })
          }, item => JSON.stringify(item))
        }
        .columnsTemplate(PlayConstants.PLAY_TITLE_DIALOG.COLUMNS_TEMPLATE)
        .rowsTemplate(PlayConstants.PLAY_TITLE_DIALOG.ROWS_TEMPLATE)
        .columnsGap(PlayConstants.PLAY_TITLE_DIALOG.COLUMNS_GAP)
        .rowsGap(PlayConstants.PLAY_TITLE_DIALOG.ROWS_GAP)
        .width(PlayConstants.PLAY_TITLE_DIALOG.ROW_WIDTH)
        .height(CommonConstants.FULL_PERCENT)
      }
      .height(CommonConstants.FIFTY_PERCENT)

      Row() {
        Text($r('app.string.cancel'))
          .fontColor($r('app.color.index_tab_selected_font_color'))
          .fontSize($r('app.float.title_dialog_font_size'))
      }
      .height(PlayConstants.PLAY_TITLE_DIALOG.ROW_HEIGHT)
      .width(PlayConstants.PLAY_TITLE_DIALOG.ROW_WIDTH)
      .justifyContent(FlexAlign.Center)
      .onClick(() => {
        this.controller.close();
      })
    }
    .height(PlayConstants.PLAY_TITLE_DIALOG.COLUMN_WIDTH)
    .width(CommonConstants.FULL_PERCENT)
    .backgroundColor(Color.White)
  }
}