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
 * 应用首页
 */
import router from '@ohos.router';
import LogUtil from '../common/utils/LogUtil';
import InfoDialog from '../view/InfoDialog';
import Input from '../view/Input';
import ErrorDialog from '../view/ErrorDialog';
import { FontSize, IndexStyle } from '../common/constants/Constants';

const TAG = 'index.ets';

@Entry
@Component
struct Index {
  @State name: string = '';
  @State isShowInput: boolean = true;
  dialogController: CustomDialogController = new CustomDialogController({
    builder: InfoDialog({
      textStr: $r('app.string.dialog_content_avatar'),
      title: $r('app.string.dialog_title_avatar')
    }),
    autoCancel: true
  });

  build() {
    Column() {
      Image($r('app.media.home_title'))
        .width(IndexStyle.IMAGE_TITLE_WIDTH)
        .height(IndexStyle.IMAGE_TITLE_HEIGHT)
        .margin({ top: IndexStyle.IMAGE_TITLE_MARGIN_TOP })
      Image('/common/animation/animation1.png')
        .width(IndexStyle.IMAGE_ANIMATION_WIDTH)
        .height(IndexStyle.IMAGE_ANIMATION_HEIGHT)
      // 输入框
      if (this.isShowInput) {
        Input({ inputText: $name, confirm: () => this.confirm() })
      }
      Text($r('app.string.text_nft_explain'))
        .fontSize(FontSize.LARGE)
        .fontColor($r('app.color.index_text_explain'))
        .margin({ top: IndexStyle.IMAGE_EXPLAIN_MARGIN_TOP })
        .onClick(() => {
          this.dialogController.open();
        })
      Blank().margin({ bottom: IndexStyle.BLANK_EMPTY_MARGIN_BOTTOM })
      Button($r('app.string.button_start_Lottery'), { stateEffect: true })
        .backgroundColor($r('app.color.index_button_background'))
        .opacity(IndexStyle.BUTTON_LOTTERY_OPACITY)
        .width(IndexStyle.BUTTON_LOTTERY_WIDTH)
        .height(IndexStyle.BUTTON_LOTTERY_HEIGHT)
        .borderRadius(IndexStyle.BUTTON_LOTTERY_RADIUS)
        .visibility(Visibility.Visible)
        .linearGradient({
          angle: IndexStyle.BUTTON_LOTTERY_ANGLE,
          colors: [[0xFDBF35, IndexStyle.BUTTON_LOTTERY_GRADIENT_START], [0xFF8C00, IndexStyle.BUTTON_LOTTERY_GRADIENT_END]]
        })
        .margin({ bottom: IndexStyle.BUTTON_LOTTERY_MARGIN_BOTTOM })
        .onClick(() => {
          this.startLottery();
        })
    }
    .backgroundImage($r('app.media.home_BG'))
    .backgroundImageSize({
      width: IndexStyle.BUTTON_BACKGROUND_IMAGE_WIDTH,
      height: IndexStyle.BUTTON_BACKGROUND_IMAGE_WIDTH
    })
    .width(IndexStyle.BUTTON_MAIN_WIDTH)
    .height(IndexStyle.BUTTON_MAIN_HEIGHT)

  }

  // 确定事件
  confirm() {
    LogUtil.info(TAG, 'click confirm:startLottery');
    this.startLottery();
  }

  // 跳转抽奖页面
  startLottery() {
    LogUtil.info(TAG, 'startLottery');
    // 获取输入框组件文本值name，传递给抽奖页面
    if (!this.name) {
      this.showFailDialog($r('app.string.dialog_title_code_fail'), $r('app.string.dialog_content_name'));
    } else {
      router.replace({
        url: 'pages/Nft',
        params:{
          name:this.name
        }
      });
    }
  }

  // 显示错误提示弹框
  showFailDialog(title: ResourceStr, text: ResourceStr, callback?: () => void) {
    LogUtil.info(TAG, 'showFailDialog');
    let failDialog: CustomDialogController;
    failDialog = new CustomDialogController({
      builder: ErrorDialog({ confirm: callback,
        textStr: text,
        title: title,
        controller: failDialog
      }),
      cancel: callback,
      autoCancel: true
    });
    failDialog.open();
  }
}