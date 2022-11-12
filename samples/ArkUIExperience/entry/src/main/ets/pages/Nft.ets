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
 * 应用抽取数字藏品页面
 */
import router from '@ohos.router';
import { axios } from '@ohos/axios';
import LogUtil from '../common/utils/LogUtil';
import InfoDialog from '../view/InfoDialog';
import ErrorDialog from '../view/ErrorDialog';
import NftResponse from '../common/bean/NftResponse';
import { Sever, DacUrl, FontSize, NftStyle } from '../common/constants/Constants';

const TAG = 'nft.ets';

@Entry
@Component
struct Nft {
  private preDecode = 5; // 预解码
  private iterations = 1;
  @State nftImgUrl: string = ''; // nft地址
  @State quality: string = '无'; // 稀有度
  @State qrValue: string = DacUrl; // 二维码内容
  @State nftIndex: string = ''; // nfc序号
  @State name: string = 'name'; // index页面输入的名字
  @State state: AnimationStatus = AnimationStatus.Initial; // 初始状态
  // 图片帧信息集合
  @State animatorImageArr: Array<{
    src: string;
    width?: number | string;
    height?: number | string;
    top?: number | string;
    left?: number | string;
    duration?: number;
  }> = [];
  @State isAnimatorFinish: boolean = false; // 是否播放完动画
  @State isShowNFTGone: boolean = false; // 是否显示奖品抢光的提示

  // 稀有度弹框
  nfcInfoDialog: CustomDialogController = new CustomDialogController({
    builder: InfoDialog({
      textStr: $r('app.string.dialog_content_quality'),
      title: '',
    }),
    autoCancel: true
  });

  build() {
    Stack() {
      // 先显示开箱动画
      if (!this.isAnimatorFinish) {
        Column() {
          ImageAnimator()
            .images(this.animatorImageArr)
            .state(this.state)
            .reverse(false)
            .fixedSize(false)
            .preDecode(this.preDecode)
            .fillMode(FillMode.None)
            .iterations(this.iterations)
            .width(NftStyle.ANIMATOR_WIDTH)
            .height(NftStyle.ANIMATOR_HEIGHT)
            .margin({ top: NftStyle.ANIMATOR_MARGIN_TOP })
            .onStart(() => { // 当帧动画开始播放后触发
              LogUtil.info(TAG, 'ImageAnimator Start');
            })
            .onPause(() => {
              LogUtil.info(TAG, 'ImageAnimator Pause');
            })
            .onRepeat(() => {
              LogUtil.info(TAG, 'ImageAnimator Repeat');
            })
            .onCancel(() => {
              LogUtil.info(TAG, 'ImageAnimator Cancel');
            })
            .onFinish(() => { // 当帧动画播放完成后触发
              this.animatorFinish();
            })
        }
        .backgroundColor($r('app.color.nft_animator_background'))
        .height(NftStyle.ANIMATOR_LAYOUT_WIDTH)
        .width(NftStyle.ANIMATOR_LAYOUT_HEIGHT)
      } else {
        // 开箱结果
        // 背景图
        Image($r('app.media.nft_BG'))
          .width(NftStyle.IMAGE_BACKGROUND_WIDTH)
          .height(NftStyle.IMAGE_BACKGROUND_HEIGHT)
          .objectFit(ImageFit.Fill)
        Column() {
          // 数字藏品已抢光
          if (this.isShowNFTGone) {
            Text($r('app.string.text_title_sorry'))
              .fontWeight(FontWeight.Bolder)
              .fontSize(FontSize.MIDDLE)
              .margin({ top: NftStyle.TEXT_GONE_MARGIN_TOP })
            Column() {
              Image($r('app.media.ic_nft_gone'))
                .aspectRatio(NftStyle.IMAGE_GONE_ASPECT_RATIO)
                .width(NftStyle.IMAGE_GONE_WIDTH)
              Text($r('app.string.text_no_prize'))
                .fontColor($r('app.color.nft_text_no_prize'))
                .fontSize(FontSize.MINI)
                .margin({ top: NftStyle.TEXT_NO_PRIZE_MARGIN_TOP })
            }
            .width(NftStyle.COLUMN_GONE_WIDTH)
            .aspectRatio(NftStyle.COLUMN_GONE_ASPECT_RATIO)
            .border({
              radius: NftStyle.COLUMN_GONE_BORDER_RADIUS,
              width: NftStyle.COLUMN_GONE_BORDER_WIDTH,
              color: $r('app.color.nft_border')
            }).margin({ top: NftStyle.COLUMN_GONE_MARGIN_TOP })
          } else {
            // 中奖提示
            Text($r('app.string.text_title_congratulations'))
              .fontWeight(FontWeight.Bolder)
              .fontSize(FontSize.MIDDLE)
              .margin({ top: NftStyle.TEXT_CONGRATULATIONS_MARGIN_TOP })
            Text($r('app.string.text_title_avatar', this.name))
              .fontSize(FontSize.SMALL)
              .margin({ top: NftStyle.TEXT_AVATAR_MARGIN_TOP })
            Image(this.nftImgUrl)
              .width(NftStyle.IMAGE_NFT_WIDTH)
              .aspectRatio(NftStyle.IMAGE_NFT_ASPECT_RATIO)
              .borderRadius(NftStyle.IMAGE_NFT_RADIUS)
              .margin({ top: NftStyle.IMAGE_NFT_MARGIN_TOP })
          }
          Flex({
            direction: FlexDirection.Row,
            justifyContent: FlexAlign.Center,
            alignItems: ItemAlign.Center
          }) {
            // 序号
            Text($r('app.string.text_serial_number'))
              .fontColor($r('app.color.nft_subtitle'))
              .fontSize(FontSize.SMALL)
              .margin({ right: NftStyle.TEXT_INDEX_MARGIN_RIGHT })
            Text(this.nftIndex)
              .fontColor($r('app.color.nft_orange'))
              .fontSize(FontSize.SMALL)
            Text()
              .width(NftStyle.TEXT_EMPTY_WIDTH) // 中间间隔
            // 品质
            Text($r('app.string.text_quality'))
              .fontColor($r('app.color.nft_subtitle'))
              .fontSize(FontSize.SMALL)
              .margin({ right: NftStyle.TEXT_QUALITY_MARGIN_RIGHT })
            Image($r('app.media.ic_about'))
              .width(NftStyle.IMAGE_ABOUT_WIDTH)
              .height(NftStyle.IMAGE_ABOUT_HEIGHT)
              .margin({ right: NftStyle.IMAGE_ABOUT_MARGIN_RIGHT })
            Text(this.quality)
              .fontColor($r('app.color.nft_subtitle'))
              .fontSize(FontSize.MINI)
              .width(NftStyle.TEXT_QUALITY_WIDTH)
              .textAlign(TextAlign.Center)
              .backgroundColor($r('app.color.nft_text_quality'))
              .borderRadius(2)
          }
          .margin({
            top: NftStyle.FLEX_NFT_INFO_MARGIN_TOP,
            bottom: NftStyle.FLEX_NFT_INFO_MARGIN_BOTTOM
          })
          .onClick(() => {
            LogUtil.info(TAG, 'open nfcInfoDialog');
            this.nfcInfoDialog.open();
          })
          // 二维码
          Flex({ direction: FlexDirection.Column, justifyContent: FlexAlign.Center }) {
            QRCode(this.qrValue).color(0x000000).width(NftStyle.QRCODE_WIDTH).aspectRatio(1)
            Text($r('app.string.text_content_qrcode'))
              .width(NftStyle.TEXT_QRCODE_WIDTH)
              .fontColor($r('app.color.nft_text_qrcode'))
              .fontSize(FontSize.MINI)
              .textAlign(TextAlign.Center)
              .margin({ top: NftStyle.TEXT_QRCODE_MARGIN_TOP })
          }.layoutWeight(NftStyle.FLEX_QRCODE_LAYOUT_WEIGHT)
        }
        .width(NftStyle.COLUMN_RESULT_WIDTH)
        .height(NftStyle.COLUMN_RESULT_WIDTH)
        .backgroundColor($r('app.color.nft_main_background'))
        .borderRadius(NftStyle.COLUMN_RESULT_RADIUS)
      }
    }
    .width(NftStyle.NFT_WIDTH)
    .height(NftStyle.NFT_HEIGHT)
  }

  aboutToAppear() {
    // 获取index页面参数
    let routerParams = router.getParams();
    LogUtil.info(TAG, 'routerParams = ' + JSON.stringify(routerParams));
    // 若路由未传递name参数则弹出提示框
    if (!routerParams || !routerParams['name']) {
      this.showFailDialog($r('app.string.dialog_title_code_fail'), $r('app.string.dialog_content_router_fail'), () => {
        router.replace({ url: 'pages/index' });
      })
      return;
    }
    this.name = router.getParams()['name'];

    // 插入播放动画代码
    this.playAnimator();

    // 检测是否有播放动画
    setTimeout(() => {
      if (this.state == AnimationStatus.Initial) {
        // 未播放动画弹框
        this.showFailDialog($r('app.string.dialog_title_code_fail'), $r('app.string.dialog_content_animation_fail'));
      }
    }, 3000);
  }

  // 显示错误提示弹框
  showFailDialog(title: ResourceStr, text: ResourceStr, callback?: () => void) {
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

  // 初始化动画并播放
  playAnimator() {
    // 初始化动画数据
    let animatorImageArr = [];
    for (let i = 0; i < 25; i++) {
      let src = `/common/animation/animation${i + 1}.png`
      let item = {
        src: src,
        duration: 250,
        width: 240,
        height: 240,
        top: 0,
        left: 0
      };
      animatorImageArr.push(item);
    }
    this.animatorImageArr = animatorImageArr;
    // 开始播放帧动画
    this.state = AnimationStatus.Running;
  }

  // 动画播放完成
  animatorFinish() {
    LogUtil.info(TAG, 'ImageAnimator Finish');
    this.state = AnimationStatus.Stopped; // 暂停动画
    this.isAnimatorFinish = true;
    this.getNFT(this.name); // 开始获取NFT
  }

  // 获取NFT数据
  async getNFT(name: string) {
    LogUtil.info(TAG, 'start getNFT');
    // 调用Axios进行网络请求，获取数字藏品数据
    axios({
      method: 'get',
      url: Sever.GET_NFT_URL,
      params: {
        name: name
      }
    }).then(response => {
      LogUtil.info(TAG, 'result:' + JSON.stringify(response));
      let res = response.data as NftResponse;
      // 若服务器返回结果为失败，则为数字藏品已抢完，并显示相应提示
      if (!res.success) {
        LogUtil.error(TAG, 'getNFT fail:' + JSON.stringify(res.message));
        this.isShowNFTGone = true;
        return;
      }
      ;
      // 获取数字藏品成功，相应组件设置对应数据
      this.isShowNFTGone = false;
      const imgPrefixUrl = Sever.PREFIX_URL;
      const webPageUrl = Sever.WEB_PAGE_URL; // web页面地址
      this.nftImgUrl = imgPrefixUrl + '/' + res.result.imageId; // 头像预览地址
      let nftIndex = res.result.imageId; // 设置序号
      this.nftIndex = nftIndex.substring(0, nftIndex.indexOf('.')); // 删除文件名后缀并赋值
      this.quality = res.result.quality; // 设置二维码内容
      this.qrValue = webPageUrl + `?assertId=${res.result.assertId}`; // 设置二维码内容
    }).catch(error => {
      LogUtil.error(TAG, 'getNFT error:' + error);
      // 显示NFT抢完提醒
      this.isShowNFTGone = true;
    })
  }
}