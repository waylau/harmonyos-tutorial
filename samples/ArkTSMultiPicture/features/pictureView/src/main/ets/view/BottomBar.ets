import PictureViewConstants, { ActionInterface } from '../constants/PictureViewConstants';
import { BaseConstants } from '@ohos/commons';

/**
 * 底部操作栏
 */
@Component
export struct BottomBar {
  build() {
    Flex({
      justifyContent: FlexAlign.Center,
      direction: FlexDirection.Row
    }) {
      ForEach(PictureViewConstants.ACTIONS, (item: ActionInterface) => {
        Column() {
          Image(item.icon)
            .height(BaseConstants.DEFAULT_ICON_SIZE)
            .width(BaseConstants.DEFAULT_ICON_SIZE)
          Text(item.icon_name)
            .fontFamily(BaseConstants.FONT_FAMILY_NORMAL)
            .fontSize(BaseConstants.FONT_SIZE_TEN)
            .fontWeight(BaseConstants.FONT_WEIGHT_FOUR)
            .padding({ top: $r('app.float.icon_padding_top') })
        }
        .width(PictureViewConstants.ICON_LIST_WIDTH)
      }, (item: ActionInterface, index: number) => index + JSON.stringify(item))
    }
    .height($r('app.float.icon_list_height'))
  }
}