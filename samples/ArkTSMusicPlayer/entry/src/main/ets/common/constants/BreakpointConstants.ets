/**
 * Constants for breakpoint.
 */
export class BreakpointConstants {
  /**
   * Breakpoints that represent small device types.
   */
  static readonly BREAKPOINT_SM: string = 'sm';

  /**
   * Breakpoints that represent middle device types.
   */
  static readonly BREAKPOINT_MD: string = 'md';

  /**
   * Breakpoints that represent large device types.
   */
  static readonly BREAKPOINT_LG: string = 'lg';

  /**
   * The break point value.
   */
  static readonly BREAKPOINT_VALUE: Array<string> = ['320vp', '600vp', '840vp'];

  /**
   * The number of columns for SM device.
   */
  static readonly COLUMN_SM: number = 4;

  /**
   * The number of columns for MD device.
   */
  static readonly COLUMN_MD: number = 8;

  /**
   * The number of columns for LG device.
   */
  static readonly COLUMN_LG: number = 12;

  /**
   * The number of gutter X for device.
   */
  static readonly GUTTER_X: number = 12;

  /**
   * The number of span for SM device.
   */
  static readonly SPAN_SM: number = 4;

  /**
   * The number of span for MD device.
   */
  static readonly SPAN_MD: number = 6;

  /**
   * The number of span for LG device.
   */
  static readonly SPAN_LG: number = 8;

  /**
   * The number of offset for MD device.
   */
  static readonly OFFSET_MD: number = 1;

  /**
   * The number of offset for LG device.
   */
  static readonly OFFSET_LG: number = 2;

  /**
   * Current breakpoints that to query the device types.
   */
  static readonly CURRENT_BREAKPOINT: string = 'currentBreakpoint';

  /**
   * Font size of the small device type.
   */
  static readonly FONT_SIZE_SM: number = 14;

  /**
   * Font size of the middle device type.
   */
  static readonly FONT_SIZE_MD: number = 16;

  /**
   * Font size of the large device type.
   */
  static readonly FONT_SIZE_LG: number = 18;

  /**
   * Cover margin of the small device type.
   */
  static readonly COVER_MARGIN_SM: number = 10;

  /**
   * Cover margin of the middle device type.
   */
  static readonly COVER_MARGIN_MD: number = 30;

  /**
   * Cover margin of the large device type.
   */
  static readonly COVER_MARGIN_LG: number = 40;

  /**
   * Range of the small device width.
   */
  static readonly RANGE_SM: string = '(320vp<=width<600vp)';

  /**
   * Range of the middle device width.
   */
  static readonly RANGE_MD: string = '(600vp<=width<840vp)';

  /**
   * Range of the large device width.
   */
  static readonly RANGE_LG: string = '(840vp<=width)';
}