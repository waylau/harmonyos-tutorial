import { SongItem } from '../common/bean/SongItem';
import { PlayerConstants } from '../common/constants/PlayerConstants';
import { StyleConstants } from '../common/constants/StyleConstants';
import { BreakpointType } from '../common/media/BreakpointSystem';
import { MusicList } from '../common/media/MusicList';

@Preview
@Component
export struct Player {
  @StorageProp('selectIndex') selectIndex: number = 0;
  @StorageLink('isPlay') isPlay: boolean = false;
  songList: SongItem[] = MusicList;
  @Link currentBreakpoint: string;

  build() {
    Row() {
      Row() {
        Image(this.songList[this.selectIndex]?.label)
          .height($r('app.float.cover_height'))
          .width($r('app.float.cover_width'))
          .borderRadius($r('app.float.label_border_radius'))
          .margin({ right: $r('app.float.cover_margin') })
          .rotate({ angle: this.isPlay ? PlayerConstants.ROTATE : 0 })
          .animation({
            duration: PlayerConstants.ANIMATION_DURATION,
            iterations: PlayerConstants.ITERATIONS,
            curve: Curve.Linear
          })
        Column() {
          Text(this.songList[this.selectIndex].title)
            .fontColor($r('app.color.song_name'))
            .fontSize(new BreakpointType({
              sm: $r('app.float.song_title_sm'),
              md: $r('app.float.song_title_md'),
              lg: $r('app.float.song_title_lg')
            }).getValue(this.currentBreakpoint))
          Row() {
            Image($r('app.media.ic_vip'))
              .height($r('app.float.vip_icon_height'))
              .width($r('app.float.vip_icon_width'))
              .margin({ right: $r('app.float.vip_icon_margin') })
            Text(this.songList[this.selectIndex].singer)
              .fontColor($r('app.color.singer'))
              .fontSize(new BreakpointType({
                sm: $r('app.float.singer_title_sm'),
                md: $r('app.float.singer_title_md'),
                lg: $r('app.float.singer_title_lg')
              }).getValue(this.currentBreakpoint))
              .opacity($r('app.float.singer_opacity'))
          }
        }
        .alignItems(HorizontalAlign.Start)
      }
      .layoutWeight(PlayerConstants.LAYOUT_WEIGHT_PLAYER_CONTROL)

      Blank()

      Row() {
        Image($r('app.media.ic_previous'))
          .height($r('app.float.control_icon_height'))
          .width($r('app.float.control_icon_width'))
          .margin({ right: $r('app.float.control_icon_margin') })
          .displayPriority(PlayerConstants.DISPLAY_PRIORITY_TWO)

        Image(this.isPlay ? $r('app.media.ic_play') : $r('app.media.ic_pause'))
          .height($r('app.float.control_icon_height'))
          .width($r('app.float.control_icon_width'))
          .displayPriority(PlayerConstants.DISPLAY_PRIORITY_THREE)

        Image($r('app.media.ic_next'))
          .height($r('app.float.control_icon_height'))
          .width($r('app.float.control_icon_width'))
          .margin({
            right: $r('app.float.control_icon_margin'),
            left: $r('app.float.control_icon_margin')
          })
          .displayPriority(PlayerConstants.DISPLAY_PRIORITY_TWO)
        Image($r('app.media.ic_music_list'))
          .height($r('app.float.control_icon_height'))
          .width($r('app.float.control_icon_width'))
          .displayPriority(PlayerConstants.DISPLAY_PRIORITY_ONE)
      }
      .width(new BreakpointType({
        sm: $r('app.float.play_width_sm'),
        md: $r('app.float.play_width_sm'),
        lg: $r('app.float.play_width_lg')
      }).getValue(this.currentBreakpoint))
      .justifyContent(FlexAlign.End)
    }
    .width(StyleConstants.FULL_WIDTH)
    .height($r('app.float.player_area_height'))
    .backgroundColor($r('app.color.player_background'))
    .padding({
      left: $r('app.float.player_padding'),
      right: $r('app.float.player_padding')
    })
    .position({
      x: 0,
      y: StyleConstants.FULL_HEIGHT
    })
    .translate({
      x: 0,
      y: StyleConstants.TRANSLATE_PLAYER_Y
    })
  }
}