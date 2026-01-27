import { common, OpenLinkOptions } from '@kit.AbilityKit';
import { BusinessError } from '@kit.BasicServicesKit';
import { hilog } from '@kit.PerformanceAnalysisKit';

const TAG: string = '[ArkTSDeepLinkingStartup]';
const DOMAIN_NUMBER: number = 0xFF00;

@Entry
@Component
struct Index {
  @State message: string = '启动目标应用';

  build() {
    RelativeContainer() {
      Text(this.message)
        .id('HelloWorld')
        .fontSize($r('app.float.page_text_font_size'))
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
        .onClick(() => {
          let context: common.UIAbilityContext = getContext(this) as common.UIAbilityContext;
          let link: string = "link://waylau.com";
          let openLinkOptions: OpenLinkOptions = {
            //跳转目标应用（如果有多个符合条件的应用时，展示应用选择弹框）
            appLinkingOnly: false
          };

          try {
            context.openLink(link, openLinkOptions)
              .then(() => {
                hilog.info(DOMAIN_NUMBER, TAG, 'open link success.');
              }).catch((err: BusinessError) => {
              hilog.error(DOMAIN_NUMBER, TAG, `open link failed. Code is ${err.code}, message is ${err.message}`);
            });
          } catch (paramError) {
            hilog.error(DOMAIN_NUMBER, TAG, `Failed to start link. Code is ${paramError.code}, message is ${paramError.message}`);
          }
        })
    }
    .height('100%')
    .width('100%')
  }
}