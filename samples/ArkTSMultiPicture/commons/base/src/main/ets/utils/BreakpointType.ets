import { BreakpointConstants } from '../constants/BreakpointConstants';

/**
 * 设备尺寸类型
 */
export class BreakpointType<T> {
  sm: T;
  md: T;
  lg: T;

  constructor(sm: T, md: T, lg: T) {
    this.sm = sm;
    this.md = md;
    this.lg = lg;
  }

  GetValue(currentBreakpoint: string): T {
    if (currentBreakpoint === BreakpointConstants.BREAKPOINT_MD) {
      return this.md;
    } else if (currentBreakpoint === BreakpointConstants.BREAKPOINT_LG) {
      return this.lg;
    } else {
      return this.sm;
    }
  }
}