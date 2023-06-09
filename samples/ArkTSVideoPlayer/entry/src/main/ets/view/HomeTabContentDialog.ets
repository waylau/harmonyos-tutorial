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
import { HomeDialogModel } from '../viewmodel/HomeDialogModel';
import { CommonConstants } from '../common/constants/CommonConstants';
import { HomeConstants } from '../common/constants/HomeConstants';

@CustomDialog
export struct HomeTabContentDialog {
  private dialogModel: HomeDialogModel = new HomeDialogModel(this);
  @State linkCheck: Resource = $r('app.string.link_check');
  @State confirmAdd: Resource = $r('app.string.confirm_add');
  @State loadColor: Resource = $r('app.color.index_tab_selected_font_color');
  @Link name: string;
  @Link src: string;
  @Link videoList: Array<VideoBean>;
  controller: CustomDialogController;
  confirm: () => void;

  build() {
    Column() {
      TextInput({ placeholder: $r('app.string.link_placeholder'), text: this.src })
        .height(HomeConstants.INTERNET_ADD_DIALOG.TEXT_HEIGHT)
        .width(CommonConstants.NINETY_PERCENT)
        .margin({ top: HomeConstants.INTERNET_ADD_DIALOG.TEXT_MARGIN_TOP })
        .onChange((value: string) => {
          this.src = value;
        })
      TextInput({ placeholder: $r('app.string.name_placeholder'), text: this.name })
        .height(HomeConstants.INTERNET_ADD_DIALOG.TEXT_HEIGHT)
        .width(CommonConstants.NINETY_PERCENT)
        .margin({ top: HomeConstants.INTERNET_ADD_DIALOG.TEXT_MARGIN_TOP })
        .onChange((value: string) => {
          this.name = value;
        })
      Flex({ justifyContent: FlexAlign.SpaceAround }) {
        Text(this.linkCheck)
          .fontSize($r('app.float.dialog_font_size'))
          .fontColor(this.loadColor)
          .onClick(() => {
            if (this.dialogModel.checkSrcNull()) {
              this.dialogModel.checkSrcValidity(0);
            }
          })
        Divider()
          .vertical(true)
          .height($r('app.float.tab_dialog_divider_height'))
          .color($r('app.color.divider_color'))
          .opacity($r('app.float.tab_dialog_divider_opacity'))
          .margin({
            left: $r('app.float.dialog_divider_margin_left'),
            right: $r('app.float.dialog_divider_margin_left')
          })
        Text(this.confirmAdd)
          .fontSize($r('app.float.dialog_font_size'))
          .fontColor(this.loadColor)
          .onClick(() => {
            if (this.dialogModel.checkSrcNull() && this.dialogModel.checkNameNull()) {
              this.dialogModel.checkSrcValidity(1);
            }
          })
      }
      .margin({
        top: $r('app.float.dialog_column_margin_top'),
        bottom: $r('app.float.dialog_column_margin_bottom')
      })
    }
  }
}