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
import { HomeTabContentList } from './HomeTabContentList';
import { HomeTabContentButton } from './HomeTabContentButton';
import { CommonConstants } from '../common/constants/CommonConstants';

@Component
export struct HomeTabContent {
  private currIndex: number;
  @Provide videoList: Array<VideoBean> = [];

  build() {
    Column() {
      HomeTabContentList({currIndex: this.currIndex});
      HomeTabContentButton({currIndex: this.currIndex});
    }
    .width(CommonConstants.FULL_PERCENT)
    .height(CommonConstants.FULL_PERCENT)
  }
}