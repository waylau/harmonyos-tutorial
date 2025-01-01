import { TopBar } from '../view/TopBar';
import { CenterPart } from '../view/CenterPart';
import { BottomBar } from '../view/BottomBar';
import { PreviewList } from '../view/PreviewList';
import { BaseConstants, BreakpointConstants } from '@ohos/commons';
import { deviceInfo } from '@kit.BasicServicesKit';

/**
 * 预览器主页
 */
@Entry
@Preview
@Component
export struct PictureViewIndex {
  @StorageLink('currentBreakpoint') currentBreakpoint: string = BreakpointConstants.BREAKPOINT_MD;
  @State selectedPhoto: Resource = $r('app.media.photo');

  build() {
    Column() {
      Flex({
        direction: FlexDirection.Column,
        alignItems: ItemAlign.Center
      }) {
        // 顶部区域
        TopBar()

        // 中部图片显示区
        CenterPart({ selectedPhoto: this.selectedPhoto })
          .flexGrow(1)

        // 图片预览列表
        PreviewList({ selectedPhoto: this.selectedPhoto })

        // 非大设备，则显示底部操作栏
        if (this.currentBreakpoint !== BreakpointConstants.BREAKPOINT_LG) {
          BottomBar()
        }
      }.padding({
        // 针对2in1设置
        top: deviceInfo.deviceType === BaseConstants.DEVICE_2IN1 ? $r('app.float.zero') :
        $r('app.float.device_padding_top'),

        // 针对非2in1设置
        bottom: deviceInfo.deviceType !== BaseConstants.DEVICE_2IN1 ? $r('app.float.tab_content_pb') :
        $r('app.float.zero')
      })
    }
    .height(BaseConstants.FULL_HEIGHT)
    .width(BaseConstants.FULL_WIDTH)
  }
}