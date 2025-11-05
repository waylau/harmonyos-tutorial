// 引入相关的类
import { BusinessError } from '@kit.BasicServicesKit'
import { common, OpenLinkOptions } from '@kit.AbilityKit';
import { hilog } from '@kit.PerformanceAnalysisKit';

const TAG: string = 'ArkTSOpenLink'
const DOMAIN: number = 0x000000

@Entry
@Component
struct Index {
  @State url: string = 'https://github.com/waylau/harmonyos-tutorial';

  build() {
    Column() {
      Text(this.url)
        .id('HelloWorld')
        .fontSize(23)
        .fontWeight(FontWeight.Bold)

      Button('访问链接')
        .type(ButtonType.Capsule)
        .fontColor(Color.White)
        .margin(10)
        .onClick(() => {
          // 获取上下文
          let context = this.getUIContext().getHostContext() as common.UIAbilityContext;
          let link: string = this.url;
          let openLinkOptions: OpenLinkOptions = {
            // 将appLinkingOnly参数设为false或者不传，
            // 若有App Linking匹配的应用，则直接打开目标应用。
            // 若无App Linking匹配的应用，则尝试以浏览器打开链接的方式打开应用
            appLinkingOnly: false,
            parameters: {}
          };

          try {
            context.openLink(
              link,
              openLinkOptions,
              (err, result) => {
                hilog.error(DOMAIN, TAG, `openLink callback error.code: ${JSON.stringify(err)}`);
                hilog.info(DOMAIN, TAG, `openLink callback result: ${JSON.stringify(result.resultCode)}`);
                hilog.info(DOMAIN, TAG, `openLink callback result data: ${JSON.stringify(result.want)}`);
              }
            ).then(() => {
              hilog.info(DOMAIN, TAG, `open link success.`);
            }).catch((err: BusinessError) => {
              hilog.error(DOMAIN, TAG, `open link failed, errCode ${JSON.stringify(err.code)}`);
            });
          } catch (e) {
            hilog.error(DOMAIN, TAG, `exception occured, errCode ${JSON.stringify(e.code)}`);
          }
        })
    }
    .height('100%')
    .width('100%')
    .justifyContent(FlexAlign.Center)
  }
}