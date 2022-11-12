/*
 * Copyright (c) 2022 Huawei Device Co., Ltd.
 * Licensed under the Apache License,Version 2.0 (the "License");
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

/**
 * 错误提示弹框组件
 */
import { FontSize, DialogStyle } from '../common/constants/Constants';

@CustomDialog
struct ErrorDialog {
  controller: CustomDialogController;
  confirm?: () => void;
  textStr: ResourceStr;
  title: ResourceStr;

  build() {
    Column() {
      Flex({
        direction: FlexDirection.Row,
        justifyContent: FlexAlign.SpaceBetween,
        alignItems: ItemAlign.Center
      }) {
        Text('')
          .width(DialogStyle.TEXT_EMPTY_TITLE_WIDTH)
        Text(this.title)
          .fontSize(FontSize.MIDDLE)
          .fontColor(Color.Black)
          .fontWeight(FontWeight.Bolder)
          .lineHeight(DialogStyle.TEXT_EMPTY_LINE_HEIGHT)
        Image($r('app.media.ic_public_cancel'))
          .width(DialogStyle.IMAGE_CANCEL_WIDTH)
          .height(DialogStyle.IMAGE_CANCEL_HEIGHT)
          .margin({ right: DialogStyle.IMAGE_CANCEL_MARGIN_RIGHT })
          .onClick(() => {
            this.controller.close();
          })
      }
      .backgroundImage($r('app.media.dialog_BG'))
      .backgroundImageSize(ImageSize.Cover)
      .linearGradient({
        angle: DialogStyle.FLEX_GRADIENT_ANGLE,
        colors: [[0xFDBF35, DialogStyle.FLEX_GRADIENT_START], [0xFF8C00, DialogStyle.FLEX_GRADIENT_END]]
      })
      .width(DialogStyle.FLEX_GRADIENT_WIDTH)
      .height(DialogStyle.FLEX_GRADIENT_HEIGHT)

      Image($r('app.media.ic_nft_gone'))
        .width(DialogStyle.IMAGE_GONE_WIDTH)
        .height(DialogStyle.IMAGE_GONE_HEIGHT)
        .margin({ top: DialogStyle.IMAGE_GONE_MARGIN_TOP })
      Text(this.textStr)
        .fontSize(FontSize.MIDDLE)
        .margin(DialogStyle.TEXT_CONTENT_MARGIN)
      Flex({ justifyContent: FlexAlign.SpaceAround }) {
        Button($r('app.string.button_text_confirm'))
          .fontSize(FontSize.MIDDLE)
          .backgroundColor(0xffffff)
          .fontColor($r('app.color.nft_orange'))
          .onClick(() => {
            this.controller.close();
            if (this.confirm) {
              this.confirm();
            }
          })
      }.margin({ bottom: DialogStyle.FLEX_BUTTON_BOX_MARGIN_BOTTOM })
    }
  }
}

export default ErrorDialog;