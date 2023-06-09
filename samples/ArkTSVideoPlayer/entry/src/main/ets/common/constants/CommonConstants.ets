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

import { VideoBean } from '../bean/VideoBean';

/**
 * Common constants for all features.
 */
export class CommonConstants {
  /**
   * Full percent.
   */
  static readonly FULL_PERCENT: string = '100%';

  /**
   * Ninety percent.
   */
  static readonly NINETY_PERCENT: string = '90%';

  /**
   * Fifty percent.
   */
  static readonly FIFTY_PERCENT: string = '50%';

  /**
   * Playback page path.
   */
  static readonly PAGE: string = 'pages/PlayPage';

  /**
   * Local video ID.
   */
  static readonly TYPE_LOCAL: number = 0;

  /**
   * Network video ID.
   */
  static readonly TYPE_INTERNET: number = 1;

  /**
   * Start playing.
   */
  static readonly STATUS_START: number = 1;

  /**
   * Playing Pause.
   */
  static readonly STATUS_PAUSE: number = 2;

  /**
   * Stop Playing.
   */
  static readonly STATUS_STOP: number = 3;

  /**
   * Width-height ratio.
   */
  static readonly ASPECT_RATIO: number = 1;

  /**
   * One hundred.
   */
  static readonly ONE_HUNDRED: number = 100;

  /**
   * A thousand.
   */
  static readonly A_THOUSAND: number = 1000;

  /**
   * Speed set.
   */
  static readonly SPEED_ARRAY = [
    { text: '0.75X', value: 0 },
    { text: '1.0X', value: 1 },
    { text: '1.25X', value: 2 },
    { text: '1.75X', value: 3 },
    { text: '2.0X', value: 4 }
  ];

  /**
   * time system, Hour-minute-second conversion.
   */
  static readonly TIME_UNIT: number = 60;

  /**
   * Initial Time UNIT.
   */
  static readonly INITIAL_TIME_UNIT: string = '00';

  /**
   * Zero padding, 2 bits.
   */
  static readonly PADDING_LENGTH: number = 2;

  /**
   * String zero padding.
   */
  static readonly PADDING_STR: string = '0';

  /**
   * Breath screen status.
   */
  static readonly SCREEN_OFF: string = 'usual.event.SCREEN_OFF';

  /**
   * Operation status of video player 4.
   */
  static readonly OPERATE_STATE: Array<string> = ['prepared','playing', 'paused', 'completed'];
}

/**
 * Player component status.
 */
export enum AvplayerStatus {
  IDLE = 'idle',
  INITIALIZED = 'initialized',
  PREPARED = 'prepared',
  PLAYING = 'playing',
  PAUSED = 'paused',
  COMPLETED = 'completed',
  STOPPED = 'stopped',
  RELEASED = 'released',
  ERROR = 'error'
}

/**
 * AVPlayer binding event.
 */
export enum Events {
  STATE_CHANGE = 'stateChange',
  TIME_UPDATE = 'timeUpdate',
  ERROR = 'error'
}

/**
 * Video object collection.
 */
export const VIDEO_DATA: VideoBean[] = [
  {
    'name': 'video1',
    'src': 'video1.mp4'
  },
  {
    'name': 'video2',
    'src': 'video2.mp4'
  }
]
