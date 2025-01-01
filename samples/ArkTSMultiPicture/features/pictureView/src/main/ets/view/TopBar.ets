import { BaseConstants, BreakpointConstants } from '@ohos/commons';
import PictureViewConstants, { ActionInterface } from '../constants/PictureViewConstants';

const TITLE: string = '图片预览器';

/**
 * 顶部区域
 */
@Preview
@Component
export struct TopBar {
  @StorageLink('currentBreakpoint') currentBp: string = BreakpointConstants.BREAKPOINT_MD;

  build() {
    Flex({
      direction: FlexDirection.Row,
      alignItems: ItemAlign.Center,
    }) {
      Column() {
        Flex({
          justifyContent: FlexAlign.SpaceBetween,
          direction: FlexDirection.Row,
          alignItems: ItemAlign.Stretch
        }) {
          Row() {
            Column() {
              Text(TITLE)
                .fontFamily(BaseConstants.FONT_FAMILY_MEDIUM)
                .fontSize(BaseConstants.FONT_SIZE_TWENTY)
                .fontWeight(BaseConstants.FONT_WEIGHT_FIVE)
            }
            .alignItems(HorizontalAlign.Start)
          }

          Row() {
            // 仅在大设备上显示操作按钮
            if (this.currentBp === BreakpointConstants.BREAKPOINT_LG) {
              ForEach(PictureViewConstants.ACTIONS, (item: ActionInterface) => {
                Image(item.icon)
                  .height(BaseConstants.DEFAULT_ICON_SIZE)
                  .width(BaseConstants.DEFAULT_ICON_SIZE)
                  .margin({ left: $r('app.float.detail_image_left') })
              }, (item: ActionInterface, index: number) => index + JSON.stringify(item))
            }
          }
        }
      }
    }
    .height($r('app.float.top_bar_height'))
    .margin({
      top: $r('app.float.top_bar_top'),
      bottom: $r('app.float.top_bar_bottom'),
      left: $r('app.float.top_bar_left'),
      right: $r('app.float.top_bar_right')
    })
  }
}