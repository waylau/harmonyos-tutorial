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

import image from '@ohos.multimedia.image';
import { VideoBean } from '../common/bean/VideoBean';
import { VIDEO_DATA } from '../common/constants/CommonConstants';

export class HomeVideoListModel {
  private videoLocalList: Array<VideoBean> = [];
  private videoInternetList: Array<VideoBean> = [];

  /**
   * Scan the local video.
   *
   * @return Local video list data
   */
  async getLocalVideo() {
    this.videoLocalList = [];
    await this.assemblingVideoBean();
    globalThis.videoLocalList = this.videoLocalList;
    return this.videoLocalList;
  }

  /**
   * Assembling the video object
   */
  async assemblingVideoBean () {
    VIDEO_DATA.forEach(async (item: VideoBean) => {
      let videoBean =  await globalThis.resourceManager.getRawFd(item.src);
      let uri = `fd://${videoBean.fd}`;
      this.videoLocalList.push(new VideoBean(item.name, uri));
    });
  }

  /**
   * Scan the internet video.
   *
   * @param name Video Name.
   * @param pixelMap pixelMap object.
   * @param src Playback Path.
   * @param duration Video duration.
   * @return Network video list data.
   */
  async setInternetVideo(name: string, src: string, pixelMap?: image.PixelMap) {
    this.videoInternetList.push(new VideoBean(name, src, pixelMap));
    globalThis.videoInternetList = this.videoInternetList;
    return globalThis.videoInternetList;
  }
}

let homeVideoListModel = new HomeVideoListModel();
export default homeVideoListModel as HomeVideoListModel;