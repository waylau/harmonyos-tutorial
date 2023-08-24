import { GridConstants } from '../common/constants/GridConstants';
import { StyleConstants } from '../common/constants/StyleConstants';
import { AlbumCover } from './AlbumCover';
import { PlayList } from './PlayList';

@Preview
@Component
export struct Content {
  @Link currentBreakpoint: string;

  build() {
    GridRow() {

      // 封面
      GridCol({ span: { sm: GridConstants.SPAN_TWELVE, md: GridConstants.SPAN_SIX, lg: GridConstants.SPAN_FOUR } }) {
        AlbumCover({ currentBreakpoint: $currentBreakpoint })
      }
      .backgroundColor($r('app.color.album_background'))

      // 歌曲列表
      GridCol({ span: { sm: GridConstants.SPAN_TWELVE, md: GridConstants.SPAN_SIX, lg: GridConstants.SPAN_EIGHT } }) {
        PlayList({ currentBreakpoint: $currentBreakpoint })
      }
      .borderRadius($r('app.float.playlist_border_radius'))
    }
    .height(StyleConstants.FULL_HEIGHT)
    .onBreakpointChange((breakpoints: string) => {
      this.currentBreakpoint = breakpoints;
    })
  }
}