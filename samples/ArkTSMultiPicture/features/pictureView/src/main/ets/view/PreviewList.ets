import { BreakpointConstants } from '@ohos/commons';
import PictureViewConstants from '../constants/PictureViewConstants';

const IMAGE_ASPECT_RATIO: number = 0.5;

/**
 * 图片预览列表
 */
@Component
export struct PreviewList {
  @StorageLink('currentBreakpoint') currentBreakpoint: string = BreakpointConstants.BREAKPOINT_MD;
  @Link selectedPhoto: Resource;

  build() {
    List({ initialIndex: 1 }) {
      ForEach(PictureViewConstants.PICTURES, (item: Resource) => {
        ListItem() {
          Image(item)
            .height($r('app.float.list_image_height'))
            .aspectRatio(IMAGE_ASPECT_RATIO)
            .autoResize(true)
            .margin({ left: $r('app.float.list_image_margin_left') })
            .onClick(()=>{
              this.selectedPhoto = item;
            })
        }
      }, (item: Resource, index: number) => index + JSON.stringify(item))
    }
    .height($r('app.float.list_image_height'))
    .padding({
      top: $r('app.float.list_margin_top'),
      bottom: $r('app.float.list_margin_bottom')
    })
    .listDirection(Axis.Horizontal)
    .scrollSnapAlign(ScrollSnapAlign.CENTER)
    .scrollBar(BarState.Off)
  }
}