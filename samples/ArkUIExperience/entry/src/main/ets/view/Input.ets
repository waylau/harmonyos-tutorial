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
 * 自定义输入框组件
 */
import { FontSize, InputStyle } from '../common/constants/Constants';

@Component
struct Input {
  @Link inputText: String;
  confirm?: () => void;

  build() {
    Row() {
      TextInput({ placeholder: '请输入名字' })
        .placeholderColor(Color.Black)
        .opacity(InputStyle.TEXT_INPUT_OPACITY)
        .placeholderFont({
          size: InputStyle.TEXT_INPUT_PLACEHOLDER_SIZE,
          weight: InputStyle.TEXT_INPUT_PLACEHOLDER_WEIGHT,
          style: FontStyle.Italic
        })
        .caretColor(Color.Blue)
        .backgroundColor(Color.White)
        .height(InputStyle.TEXT_INPUT_PLACEHOLDER_HEIGHT)
        .fontSize(FontSize.LARGE)
        .borderRadius(InputStyle.TEXT_INPUT_PLACEHOLDER_RADIUS)
        .fontWeight(FontWeight.Bold)
        .fontStyle(FontStyle.Normal)
        .fontColor(Color.Black)
        .padding({ right: InputStyle.TEXT_INPUT_PLACEHOLDER_PADDING_RIGHT })
        .margin({
          right: InputStyle.TEXT_INPUT_PLACEHOLDER_MARGIN_RIGHT,
          left: InputStyle.TEXT_INPUT_PLACEHOLDER_MARGIN_LEFT
        })
        .onChange((value: string) => {
          this.inputText = value;
        })
      Button('确定', { type: ButtonType.Normal, stateEffect: true })
        .offset({ x: InputStyle.BUTTON_CONFIRM_OFFSET_X, y: 0 })
        .width(InputStyle.BUTTON_CONFIRM_WIDTH)
        .height(InputStyle.BUTTON_CONFIRM_HEIGHT)
        .fontColor(Color.White)
        .backgroundColor($r('app.color.input_button_confirm'))
        .borderRadius(InputStyle.BUTTON_CONFIRM_RADIUS)
        .onClick(() => {
          if (this.confirm) {
            this.confirm();
          }
        })
    }
    .width(InputStyle.ROW_WIDTH)
    .height(InputStyle.ROW_HEIGHT)
    .backgroundColor(Color.White)
    .borderRadius(InputStyle.ROW_RADIUS)
  }
}

export default Input;