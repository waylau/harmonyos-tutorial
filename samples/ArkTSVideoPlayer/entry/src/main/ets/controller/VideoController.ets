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

import media from '@ohos.multimedia.media';
import prompt from '@ohos.promptAction';
import Logger from '../common/util/Logger';
import DateFormatUtil from '../common/util/DateFormatUtil';
import { CommonConstants, AvplayerStatus, Events } from '../common/constants/CommonConstants';
import { PlayConstants } from '../common/constants/PlayConstants';

@Observed
export class VideoController {
  private avPlayer;
  private duration: number = 0;
  private status: number;
  private loop: boolean = false;
  private index: number;
  private url: string;
  private surfaceId: number;
  private playSpeed: number = PlayConstants.PLAY_PAGE.PLAY_SPEED;
  private seekTime: number = PlayConstants.PLAY_PROGRESS.SEEK_TIME;
  private progressThis;
  private playerThis;
  private playPageThis;
  private titleThis;
  private positionX: number = PlayConstants.PLAY_PAGE.POSITION_X;
  private positionY: number = PlayConstants.PLAY_PAGE.POSITION_Y;

  constructor() {
    this.createAVPlayer();
  }

  /**
   * 创建 videoPlayer对象
   */
  createAVPlayer() {
    media.createAVPlayer().then((avPlayer) => {
      if (avPlayer) {
        this.avPlayer = avPlayer;
        this.bindState();
      } else {
        Logger.error('[PlayVideoModel] createAvPlayer fail!');
      }
    });
  }

  /**
   * AVPlayer 绑定事件.
   */
  bindState() {
    this.avPlayer.on(Events.STATE_CHANGE, async (state) => {
      switch (state) {
        case AvplayerStatus.IDLE:
          this.resetProgress();
          this.avPlayer.url = this.url;
          break;
        case AvplayerStatus.INITIALIZED:
          this.avPlayer.surfaceId = this.surfaceId;
          this.avPlayer.prepare();
          break;
        case AvplayerStatus.PREPARED:
          this.avPlayer.videoScaleType = 0;
          this.setVideoSize();
          this.avPlayer.play();
          this.duration = this.avPlayer.duration;
          break;
        case AvplayerStatus.PLAYING:
          this.avPlayer.setVolume(this.playerThis.volume);
          this.setBright();
          this.status = CommonConstants.STATUS_START;
          this.watchStatus();
          break;
        case AvplayerStatus.PAUSED:
          this.status = CommonConstants.STATUS_PAUSE;
          this.watchStatus();
          break;
        case AvplayerStatus.COMPLETED:
          this.titleThis.playSpeed = 1;
          this.duration = PlayConstants.PLAY_PLAYER.DURATION;
          if (!this.loop) {
            let curIndex = this.index + PlayConstants.PLAY_PLAYER.NEXT;
            this.index = (curIndex === globalThis.videoList.length) ?
            PlayConstants.PLAY_PLAYER.FIRST : curIndex;
            this.url = globalThis.videoList[this.index].src;
          } else {
            this.url = this.avPlayer.url;
          }
          this.avPlayer.reset();
          break;
        case AvplayerStatus.RELEASED:
          this.avPlayer.release();
          this.status = CommonConstants.STATUS_STOP;
          this.watchStatus();
          Logger.info('[PlayVideoModel] state released called')
          break;
        default:
          Logger.info('[PlayVideoModel] unKnown state: ' + state);
          break;
      }
    });
    this.avPlayer.on(Events.TIME_UPDATE, (time: number) => {
      this.initProgress(time);
    });
    this.avPlayer.on(Events.ERROR, (error) => {
      this.playError();
    })
  }

  /**
   * This method is triggered when the video playback page is displayed on the video list page.
   *
   * @param index Video object subscript in the video list.
   * @param url Playback Path.
   * @param surfaceId Indicates the surface ID of the surfaceId.
   */
  firstPlay(index: number, url: string, surfaceId: number) {
    this.index = index;
    this.url = url;
    this.surfaceId = surfaceId;
    this.avPlayer.url = this.url;
  }

  /**
   * Release the video player.
   */
  release() {
    this.avPlayer.release();
  }

  /**
   * Pause Playing.
   */
  pause() {
    this.avPlayer.pause();
  }

  /**
   * Playback mode. The options are as follows: true: playing a single video; false: playing a cyclic video.
   */
  setLoop() {
    this.loop = !this.loop;
  }

  /**
   * Set the playback speed.
   *
   * @param playSpeed Current playback speed.
   */
  setSpeed(playSpeed: number) {
    if (CommonConstants.OPERATE_STATE.indexOf(this.avPlayer.state) === -1) {
      return;
    }
    this.playSpeed = playSpeed;
    this.avPlayer.setSpeed(this.playSpeed);
  }

  /**
   * Previous video.
   */
  previousVideo() {
    if (CommonConstants.OPERATE_STATE.indexOf(this.avPlayer.state) === -1) {
      return;
    }
    this.titleThis.playSpeed = 1;
    let curIndex = this.index - PlayConstants.PLAY_CONTROL.NEXT;
    this.index = (curIndex === -PlayConstants.PLAY_CONTROL.NEXT) ?
      (globalThis.videoList.length - PlayConstants.PLAY_CONTROL.NEXT) : curIndex;
    this.url = globalThis.videoList[this.index].src;
    this.avPlayer.reset();
  }

  /**
   * Next video.
   */
  nextVideo() {
    if (CommonConstants.OPERATE_STATE.indexOf(this.avPlayer.state) === -1) {
      return;
    }
    this.titleThis.playSpeed = 1;
    let curIndex = this.index + PlayConstants.PLAY_CONTROL.NEXT;
    this.index = (curIndex === globalThis.videoList.length) ?
    PlayConstants.PLAY_CONTROL.FIRST : curIndex;
    this.url = globalThis.videoList[this.index].src;
    this.avPlayer.reset();
  }

  /**
   * Switching Between Video Play and Pause.
   */
  switchPlayOrPause() {
    if (this.status === CommonConstants.STATUS_START) {
      this.avPlayer.pause();
    } else {
      this.avPlayer.play();
    }
  }

  /**
   * Slide the progress bar to set the playback progress.
   *
   * @param value Value of the slider component.
   * @param mode Slider component change event.
   */
  setSeekTime(value: number, mode: SliderChangeMode) {
    if (mode === SliderChangeMode.Moving) {
      // The current time is changed during dragging, and other parameters remain unchanged.
      this.progressThis.progressVal = value;
      this.progressThis.currentTime = DateFormatUtil.secondToTime(Math.floor(value * this.duration /
      CommonConstants.ONE_HUNDRED / CommonConstants.A_THOUSAND));
    }
    if (mode === SliderChangeMode.End) {
      this.seekTime = value * this.duration / CommonConstants.ONE_HUNDRED;
      this.avPlayer.seek(this.seekTime, media.SeekMode.SEEK_PREV_SYNC);
    }
  }

  /**
   * Setting the brightness.
   */
  setBright() {
    globalThis.windowClass.setWindowBrightness(this.playerThis.bright)
  }

  /**
   * Obtains the current video playing status.
   */
  getStatus() {
    return this.status;
  }

  /**
   * Transfer this on the playProgress page.
   *
   * @param progressThis PlayProgress this object.
   */
  initProgressThis(progressThis) {
    this.progressThis = progressThis;
  }

  /**
   * Transfer this on the playTitle page.
   *
   * @param progressThis PlayTitle this object.
   */
  initTitleThis(titleThis) {
    this.titleThis = titleThis;
  }

  /**
   * Pass this on the playback page.
   *
   * @param playerThis PlayPlayer this object.
   */
  initPlayerThis(playerThis) {
    this.playerThis = playerThis;
  }

  /**
   * Initialization progress bar.
   *
   * @param time Current video playback time.
   */
  initProgress(time: number) {
    let nowSeconds = Math.floor(time / CommonConstants.A_THOUSAND);
    let totalSeconds = Math.floor(this.duration / CommonConstants.A_THOUSAND);
    this.progressThis.currentTime = DateFormatUtil.secondToTime(nowSeconds);
    this.progressThis.totalTime = DateFormatUtil.secondToTime(totalSeconds);
    this.progressThis.progressVal = Math.floor(nowSeconds * CommonConstants.ONE_HUNDRED / totalSeconds);
  }

  /**
   * Reset progress bar data.
   */
  resetProgress() {
    this.seekTime = PlayConstants.PLAY_PROGRESS.SEEK_TIME;
    this.progressThis.currentTime = PlayConstants.PLAY_PROGRESS.CURRENT_TIME;
    this.progressThis.progressVal = PlayConstants.PLAY_PROGRESS.PROGRESS_VAL;
  }

  /**
   * Volume gesture method onActionStart.
   *
   * @param event Gesture event.
   */
  onVolumeActionStart(event: GestureEvent) {
    this.positionX = event.offsetX;
  }

  /**
   * Bright gesture method onActionStart.
   *
   * @param event Gesture event.
   */
  onBrightActionStart(event: GestureEvent) {
    this.positionY = event.offsetY;
  }

  /**
   * Gesture method onActionUpdate.
   *
   * @param event Gesture event.
   */
  onVolumeActionUpdate(event: GestureEvent) {
    if (CommonConstants.OPERATE_STATE.indexOf(this.avPlayer.state) === -1) {
      return;
    }
    if (!this.playerThis.brightShow) {
      this.playerThis.volumeShow = true;
      let changeVolume = (event.offsetX - this.positionX) / globalThis.screenWidth;
      let currentVolume = this.playerThis.volume + changeVolume;
      let volumeMinFlag = currentVolume <= PlayConstants.PLAY_PAGE.MIN_VALUE;
      let volumeMaxFlag = currentVolume > PlayConstants.PLAY_PAGE.MAX_VALUE;
      this.playerThis.volume = volumeMinFlag ? PlayConstants.PLAY_PAGE.MIN_VALUE :
        (volumeMaxFlag ? PlayConstants.PLAY_PAGE.MAX_VALUE : currentVolume);
      this.avPlayer.setVolume(this.playerThis.volume);
      this.positionX = event.offsetX;
    }
  }
  /**
   * Gesture method onActionUpdate.
   *
   * @param event Gesture event.
   */
  onBrightActionUpdate(event: GestureEvent) {
    if (!this.playerThis.volumeShow) {
      this.playerThis.brightShow = true;
      let changeBright = (this.positionY - event.offsetY) / globalThis.screenHeight;
      let currentBright = this.playerThis.bright + changeBright;
      let brightMinFlag = currentBright <= PlayConstants.PLAY_PAGE.MIN_VALUE;
      let brightMaxFlag = currentBright > PlayConstants.PLAY_PAGE.MAX_VALUE;
      this.playerThis.bright = brightMinFlag ? PlayConstants.PLAY_PAGE.MIN_VALUE :
        (brightMaxFlag ? PlayConstants.PLAY_PAGE.MAX_VALUE : currentBright);
      this.setBright();
      this.positionY = event.offsetY;
    }
  }

  /**
   * Gesture method onActionEnd.
   */
  onActionEnd() {
    setTimeout(() => {
      this.playerThis.volumeShow = false;
      this.playerThis.brightShow = false;
      this.positionX = PlayConstants.PLAY_PAGE.POSITION_X;
      this.positionY = PlayConstants.PLAY_PAGE.POSITION_Y;
    }, PlayConstants.PLAY_PAGE.DISAPPEAR_TIME);
  }

  /**
   * Sets whether the screen is a constant based on the playback status.
   */
  watchStatus() {
    if (this.status === CommonConstants.STATUS_START) {
      globalThis.windowClass.setWindowKeepScreenOn(true);
    } else {
      globalThis.windowClass.setWindowKeepScreenOn(false);
    }
  }

  /**
   * Sets the playback page size based on the video size.
   */
  setVideoSize() {
    if (this.avPlayer.height > this.avPlayer.width) {
      this.playPageThis.videoWidth = PlayConstants.PLAY_PAGE.PLAY_PLAYER_HEIGHT_FULL;
      this.playPageThis.videoHeight = PlayConstants.PLAY_PAGE.PLAY_PLAYER_HEIGHT_FULL;
      this.playPageThis.videoPosition = FlexAlign.Start;
      this.playPageThis.videoMargin = PlayConstants.PLAY_PAGE.HEIGHT;
    } else {
      this.playPageThis.videoWidth = CommonConstants.FULL_PERCENT;
      this.playPageThis.videoHeight = PlayConstants.PLAY_PAGE.PLAY_PLAYER_HEIGHT;
      this.playPageThis.videoPosition = FlexAlign.Center;
      this.playPageThis.videoMargin =  PlayConstants.PLAY_PAGE.MARGIN_ZERO;
    }
  }

  /**
   * Obtains the this object of the PlayPage.
   *
   * @param playPageThis This object of PlayPage.
   */
  initPlayPageThis(playPageThis) {
    this.playPageThis = playPageThis;
  }

  /**
   * An error is reported during network video playback.
   */
  playError() {
    prompt.showToast({
      duration: PlayConstants.PLAY_ERROR_TIME,
      message: $r('app.string.link_check_address_internet')
    });
  }
}