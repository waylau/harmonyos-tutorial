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
 * 获取NFT请求状态响应类
 */
export default class NftResponse {
  public success: boolean;
  public message: string;
  public errCode: string;
  public result: NftResult;

  constructor(success: boolean, message: string, errCode: string, result: NftResult) {
    this.success = success;
    this.message = message;
    this.errCode = errCode;
    this.result = result;
  }
};

/**
 * 获取NFT结果响应类
 *
 */
class NftResult {
  public id: number;
  public imageId: string;
  public ip: string;
  public name: string;
  public tokenId: string;
  public assertId: string;
  public createTime: string;
  public mintTime: string;
  public hash: string;
  public quality: string;

  constructor() {}
};