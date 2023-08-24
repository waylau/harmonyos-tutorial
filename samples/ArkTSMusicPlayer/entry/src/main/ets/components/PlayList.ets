
import { SongItem } from '../common/bean/SongItem';
import { BreakpointConstants } from '../common/constants/BreakpointConstants';
import { ContentConstants } from '../common/constants/ContentConstants';
import { StyleConstants } from '../common/constants/StyleConstants';
import { BreakpointType } from '../common/media/BreakpointSystem';
import { MusicList } from '../common/media/MusicList';
import { SongDataSource } from '../viewmodel/SongDataSource';
@Component
export struct PlayList {
  @Link currentBreakpoint: string;
  songList: SongItem[] = MusicList;

  @Builder
  PlayAll() {
    Row() {
      Image($r('app.media.ic_play_all'))
        .height($r('app.float.play_all_icon_size'))
        .width($r('app.float.play_all_icon_size'))
      Text($r('app.string.play_all', this.songList.length))
        .maxLines(ContentConstants.PLAY_ALL_MAX_LINES)
        .padding({ left: $r('app.float.play_all_text_padding') })
        .fontColor(Color.Black)
        .fontSize(new BreakpointType({
          sm: $r('app.float.play_font_sm'),
          md: $r('app.float.play_font_md'),
          lg: $r('app.float.play_font_lg')
        }).getValue(this.currentBreakpoint))
      Blank()
      Image($r('app.media.ic_order_play'))
        .width($r('app.float.order_icon_size'))
        .height($r('app.float.order_icon_size'))
        .margin({ right: $r('app.float.order_icon_margin') })
      Image($r('app.media.ic_sort_list'))
        .height($r('app.float.order_icon_size'))
        .width($r('app.float.order_icon_size'))
    }
    .height($r('app.float.play_all_area_height'))
    .width(StyleConstants.FULL_WIDTH)
    .backgroundColor(Color.White)
    .padding({
      left: $r('app.float.play_all_area_padding'),
      right: $r('app.float.play_all_area_padding')
    })
    .borderRadius({
      topRight: $r('app.float.play_all_border_radius'),
      topLeft: $r('app.float.play_all_border_radius')
    })
    .position({ x: 0, y: 0 })
  }

  @Builder
  SongItem(item: SongItem, index: number) {
    Row() {
      Column() {
        Text(item.title)
          .fontColor(Color.Black)
          .fontSize(new BreakpointType({
            sm: $r('app.float.item_font_sm'),
            md: $r('app.float.item_font_md'),
            lg: $r('app.float.item_font_lg')
          }).getValue(this.currentBreakpoint))
          .margin({ bottom: $r('app.float.list_item_title_margin') })
        Row() {
          Image(item.mark)
            .width($r('app.float.list_item_image_size'))
            .height($r('app.float.list_item_image_size'))
            .margin({ right: $r('app.float.list_item_image_margin') })
          Text(item.singer)
            .opacity($r('app.float.singer_opacity'))
            .fontColor(Color.Black)
            .fontSize(new BreakpointType({
              sm: $r('app.float.singer_title_sm'),
              md: $r('app.float.singer_title_md'),
              lg: $r('app.float.singer_title_lg')
            }).getValue(this.currentBreakpoint))
        }
      }
      .alignItems(HorizontalAlign.Start)

      Blank()
      Image($r('app.media.ic_list_more'))
        .height($r('app.float.order_icon_size'))
        .width($r('app.float.order_icon_size'))
    }
    .height($r('app.float.list_item_height'))
    .width(StyleConstants.FULL_WIDTH)
  }

  build() {
    Column() {
      // 播放全部
      this.PlayAll()

      // 歌单列表
      List() {
        LazyForEach(new SongDataSource(this.songList), (item: SongItem, index: number) => {
          ListItem() {
            Column() {
              this.SongItem(item, index)
            }
            .padding({
              left: $r('app.float.list_item_padding'),
              right: $r('app.float.list_item_padding')
            })
          }
        }, (item, index) => JSON.stringify(item) + index)
      }
      .width(StyleConstants.FULL_WIDTH)
      .backgroundColor(Color.White)
      .margin({ top: $r('app.float.list_area_margin_top') })
      .lanes(this.currentBreakpoint === BreakpointConstants.BREAKPOINT_LG ?
      ContentConstants.COL_TWO : ContentConstants.COL_ONE)
      .layoutWeight(1)
      .divider({
        color: $r('app.color.list_divider'),
        strokeWidth: $r('app.float.stroke_width'),
        startMargin: $r('app.float.list_item_padding'),
        endMargin: $r('app.float.list_item_padding')
      })
    }
    .padding({
      top: this.currentBreakpoint === BreakpointConstants.BREAKPOINT_SM ? 0 : $r('app.float.list_area_padding_top'),
      bottom: $r('app.float.list_area_padding_bottom')
    })
  }
}