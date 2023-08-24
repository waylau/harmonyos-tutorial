import router from '@ohos.router';
import { StyleConstants } from '../common/constants/StyleConstants';
import { HeaderConstants } from '../common/constants/HeaderConstants';
import { BreakpointType } from '../common/media/BreakpointSystem';

@Preview
@Component
export struct Header {
  @Link currentBreakpoint: string;

  build() {
    Row() {
      // 返回按钮
      Image($r('app.media.ic_back'))
        .width($r('app.float.icon_width'))
        .height($r('app.float.icon_height'))
        .margin({ left: $r('app.float.icon_margin') })
        .onClick(() => {
          router.back()
        })

      // 播放器名称
      Text($r('app.string.play_list'))
        .fontSize(new BreakpointType({
          sm: $r('app.float.header_font_sm'),
          md: $r('app.float.header_font_md'),
          lg: $r('app.float.header_font_lg')
        }).getValue(this.currentBreakpoint))
        .fontWeight(HeaderConstants.TITLE_FONT_WEIGHT)
        .fontColor($r('app.color.title_color'))
        .opacity($r('app.float.title_opacity'))
        .letterSpacing(HeaderConstants.LETTER_SPACING)
        .padding({ left: $r('app.float.title_padding_left') })

      Blank()

      // 菜单
      Image($r('app.media.ic_more'))
        .width($r('app.float.icon_width'))
        .height($r('app.float.icon_height'))
        .margin({ right: $r('app.float.icon_margin') })
        //.bindMenu(this.getMenu())
    }
    .width(StyleConstants.FULL_WIDTH)
    .height($r('app.float.title_bar_height'))
    .zIndex(HeaderConstants.Z_INDEX)
  }

}