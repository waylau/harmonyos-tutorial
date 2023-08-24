import { BreakpointConstants } from '../common/constants/BreakpointConstants';

import { StyleConstants } from '../common/constants/StyleConstants';

import { Content } from '../components/Content';
import { Header } from '../components/Header';
import { Player } from '../components/Player';

@Entry
@Component
struct Index {

  @State currentBreakpoint: string = BreakpointConstants.BREAKPOINT_SM;
  build() {
    Stack({ alignContent: Alignment.Top }) {
      // 头部
      Header({ currentBreakpoint: $currentBreakpoint })

      // 中部
      Content({ currentBreakpoint: $currentBreakpoint })

      // 底部
      Player({ currentBreakpoint: $currentBreakpoint })
    }
    .width(StyleConstants.FULL_WIDTH)
  }

}