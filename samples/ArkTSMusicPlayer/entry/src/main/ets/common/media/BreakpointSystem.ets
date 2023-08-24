import mediaQuery from '@ohos.mediaquery';
import { BreakpointConstants } from '../constants/BreakpointConstants';

export class BreakpointSystem {
  private currentBreakpoint: string = BreakpointConstants.BREAKPOINT_SM;
  private smListener: mediaQuery.MediaQueryListener;
  private mdListener: mediaQuery.MediaQueryListener;
  private lgListener: mediaQuery.MediaQueryListener;

  private updateCurrentBreakpoint(breakpoint: string): void {
    if (this.currentBreakpoint !== breakpoint) {
      this.currentBreakpoint = breakpoint;
      AppStorage.SetOrCreate<string>(BreakpointConstants.CURRENT_BREAKPOINT, this.currentBreakpoint);
    }
  }

  private isBreakpointSM = (mediaQueryResult: mediaQuery.MediaQueryResult): void => {
    if (mediaQueryResult.matches) {
      this.updateCurrentBreakpoint(BreakpointConstants.BREAKPOINT_SM);
    }
  }
  private isBreakpointMD = (mediaQueryResult: mediaQuery.MediaQueryResult): void => {
    if (mediaQueryResult.matches) {
      this.updateCurrentBreakpoint(BreakpointConstants.BREAKPOINT_MD);
    }
  }
  private isBreakpointLG = (mediaQueryResult: mediaQuery.MediaQueryResult): void => {
    if (mediaQueryResult.matches) {
      this.updateCurrentBreakpoint(BreakpointConstants.BREAKPOINT_LG);
    }
  }

  public register(): void {
    this.smListener = mediaQuery.matchMediaSync(BreakpointConstants.RANGE_SM);
    this.smListener.on('change', this.isBreakpointSM);
    this.mdListener = mediaQuery.matchMediaSync(BreakpointConstants.RANGE_MD);
    this.mdListener.on('change', this.isBreakpointMD);
    this.lgListener = mediaQuery.matchMediaSync(BreakpointConstants.RANGE_LG);
    this.lgListener.on('change', this.isBreakpointLG);
  }

  public unregister(): void {
    this.smListener.off('change', this.isBreakpointSM);
    this.mdListener.off('change', this.isBreakpointMD);
    this.lgListener.off('change', this.isBreakpointLG);
  }
}


declare interface BreakPointTypeOption<T> {
  sm?: T,
  md?: T,
  lg?: T,
  xl?: T,
  xxl?: T
}

export class BreakpointType<T> {
  options: BreakPointTypeOption<T>;

  constructor(option: BreakPointTypeOption<T>) {
    this.options = option;
  }

  getValue(currentPoint: string): T {
    return this.options[currentPoint] as T;
  }
}
