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

/**
 * Home constants for all features.
 */
export class HomeConstants {
  /**
   * Constants on the tab page of the main interface.
   */
  static readonly HOME_TAB = {
    CURRENT_INDEX: 0,
    TAB_BAR_FIRST: 0,
    TAB_BAR_SECOND: 1,
    BAR_WIDTH: 360,
    BAR_HEIGHT: 60,
    FONT_WEIGHT_SELECT: 500,
    FONT_WEIGHT_UNSELECT: 400,
    LINE_HEIGHT: 22,
    MARGIN_TOP_TWO: 17,
    MARGIN_BOTTOM: 7,
    STROKE_WIDTH: 2
  }

  /**
   * Constant of the video list.
   */
  static readonly HOME_TAB_LIST = {
    COLUMN_WIDTH: '86.7%',
    LIST_SPACE: 20,
    LIST_INITIAL_INDEX: 0,
    IMAGE_HEIGHT: '84.8%',
    IMAGE_WIDTH: '26.7%',
    DIVIDER_STROKE_WIDTH: 1,
    LIST_ITEM_ROW_COLUMN_WIDTH: '73.3%',
    LIST_ITEM_ROW_HEIGHT: '12.3%',
    WIDTH: 720,
    HEIGHT: 720
  }

  /**
   * Scan local video and add network video buttons.
   */
  static readonly HOME_TAB_BUTTON = {
    HEIGHT: '51%',
    COLUMN_HEIGHT: '10%'
  }

  /**
   * Add Network Video dialog box.
   */
  static readonly INTERNET_ADD_DIALOG= {
    OFFSET_DX: 0,
    OFFSET_DY: -20,
    GRID_COUNT: 4,
    TEXT_HEIGHT: '6.7%',
    TEXT_MARGIN_TOP: '3.1%',
    DURATION: 1000,
    DURATION_ONE: -1,
    DURATION_TWO: 0
  }

  /**
   * The video player is used to verify the video link.
   */
  static readonly X_COMPONENT = {
    ID: '',
    TYPE: 'surface',
    LIBRARY_NAME: '',
  }
}
