import { BreakpointConstants } from '@ohos/commons';
import { Adaptive } from '../viewmodel/Adaptive'

const FINGER_NUM: number = 2

/**
 * 中部图片显示区
 */
@Component
export struct CenterPart {
  @StorageLink('currentBreakpoint') currentBreakpoint: string = BreakpointConstants.BREAKPOINT_MD;
  @State scaleValue: number = 1;
  @State pinchValue: number = 1;
  @Link selectedPhoto: Resource;

  build() {
    Flex({ direction: FlexDirection.Column }) {
      Blank()
      Row() {
        Column() {
          // 绑定选中的图片
          Image(this.selectedPhoto)
            .autoResize(true)
        }
      }
      .height(Adaptive.PICTURE_HEIGHT(this.currentBreakpoint))
      .width(Adaptive.PICTURE_WIDTH(this.currentBreakpoint))
      .scale({ x: this.scaleValue, y: this.scaleValue, z: 1 })
      // 设置2指缩放
      .gesture(PinchGesture({ fingers: FINGER_NUM })
        .onActionUpdate((event: GestureEvent | undefined) => {
          if (event) {
            this.scaleValue = this.pinchValue * event.scale;
          }
        })
        .onActionEnd(() => {
          this.pinchValue = this.scaleValue;
        }))

      Blank()// 针对小设备设置
        .height(this.currentBreakpoint === BreakpointConstants.BREAKPOINT_SM ? $r('app.float.center_blank_height_lg') :
          0)
    }
    .margin({
      top: $r('app.float.center_margin_top'),
      bottom: $r('app.float.center_margin_bottom')
    })
  }
}