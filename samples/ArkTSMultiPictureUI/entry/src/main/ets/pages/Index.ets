import { BreakpointConstants } from '../constants/BreakpointConstants';
import { ActionList } from '../view/ActionList';
import { CenterPart } from '../view/CenterPart';
import { PreviewList } from '../view/PreviewList';
import { TopBar } from '../view/TopBar';

/**
 * 图片查看器主页
 */
@Entry
@Component
struct Index {
  @StorageLink('currentBreakpoint') currentBreakpoint: string = BreakpointConstants.BREAKPOINT_MD

  build() {
    Column() {
      Flex({
        direction: FlexDirection.Column,
        alignItems: ItemAlign.Center
      }) {
        // 顶部区域
        TopBar()

        // 中部图片显示区
        CenterPart( )

        // 图片预览列表
        PreviewList( )

        // 非大设备，则显示底部操作栏
        if (this.currentBreakpoint !== BreakpointConstants.BREAKPOINT_LG) {
          ActionList()
        }
      }
    }
    .height('100%')
    .width('100%')
  }
}