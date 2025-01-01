import { BreakpointConstants } from '../constants/BreakpointConstants';
import { ActionList } from './ActionList';


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
              Text('应用标题')
                .fontSize('24fp')
            }
            .alignItems(HorizontalAlign.Start)
          }

          Row() {
            // 仅在大设备上显示操作按钮
            if (this.currentBp === BreakpointConstants.BREAKPOINT_LG) {
              ActionList();
            }
          }
        }
      }
      .borderColor(Color.Black)
      .borderWidth('2vp')
    }
    .height('52vp')
  }
}