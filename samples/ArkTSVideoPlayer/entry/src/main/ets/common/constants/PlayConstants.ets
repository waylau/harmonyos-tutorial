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
 * Play constants for all features.
 */
export class PlayConstants {
  /**
   * Playback page constant.
   */
  static readonly PLAY_PAGE = {
    PLAY_SPEED: 1,
    VOLUME: 0.5,
    VOLUME_SHOW: false,
    BRIGHT: 0.5,
    BRIGHT_SHOW: false,
    POSITION_X: 0,
    POSITION_Y: 0,
    HEIGHT: '7.2%',
    PLAY_PLAYER_HEIGHT: '25.6%',
    PLAY_PLAYER_HEIGHT_FULL: '75.4%',
    PLAY_PROGRESS_HEIGHT: '7.1%',
    COLUMN_HEIGHT_ONE: '26.9%',
    COLUMN_HEIGHT_TWO: '22.9%',
    MIN_ANGLE: 0,
    MAX_ANGLE: 30,
    MIN_VALUE: 0,
    MAX_VALUE: 1,
    DISAPPEAR_TIME: 200,
    MARGIN_ZERO: '0'
  }

  /**
   * Playback Page Header Constant.
   */
  static readonly PLAY_TITLE = {
    DX: 0,
    DY: -20,
    GRID_COUNT: 4,
    TEXT_MARGIN_LEFT: '4.4%',
    ROW_WIDTH: '86.6%',
    POPUP: {
      ROW_HEIGHT: '45.3%',
      ROW_MARGIN_TOP: '3.8%',
      DIVIDER_STROKE_WIDTH: 1,
      DIVIDER_MARGIN_RIGHT: '10.2%',
      COLUMN_WIDTH: '43.3%',
      COLUMN_HEIGHT: '13.5%',
      CLOSE_TIME: 500
    },
  }

  /**
   * Constants for setting the playback speed.
   */
  static readonly PLAY_TITLE_DIALOG = {
    ROW_HEIGHT: '25%',
    ROW_WIDTH: '86.6%',
    COLUMNS_TEMPLATE: '1fr 1fr 1fr',
    ROWS_TEMPLATE: '1fr 1fr',
    COLUMNS_GAP: 10,
    ROWS_GAP: 10,
    COLUMN_WIDTH: '39.2%'
  }

  /**
   * Video playback constant.
   */
  static  readonly PLAY_PLAYER = {
    ID: '',
    TYPE: 'surface',
    LIBRARY_NAME: '',
    SURFACE_WIDTH: 1920,
    SURFACE_HEIGHT: 1080,
    STACK_WIDTH: '16.7%',
    IMAGE_WIDTH: '95%',
    FIRST: 0,
    NEXT: 1,
    DURATION: 0
  }

  /**
   * Video playback control constant.
   */
  static readonly PLAY_CONTROL = {
    ROW_WIDTH: '68.8%',
    PLAY_START: 1,
    PLAY_PAUSE: 2,
    NEXT: 1,
    FIRST: 0
  }

  /**
   * Progress bar page constant.
   */
  static readonly PLAY_PROGRESS = {
    CURRENT_TIME: '00:00',
    TOTAL_TIME: '00:00',
    PROGRESS_VAL: 0,
    INTERVAL: -1,
    STEP: 1,
    TRACK_THICKNESS: 1,
    SLIDER_WIDTH: '68.9%',
    MARGIN_LEFT: '2.2%',
    SEEK_TIME: 0,
    ROW_WIDTH: '93.4%'
  }

  /**
   * Network video playback error notification duration
   */
  static readonly PLAY_ERROR_TIME = 3000;
}
