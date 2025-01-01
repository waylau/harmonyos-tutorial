import { BaseConstants as Constants, BreakpointType } from '@ohos/commons';
import PictureViewConstants from '../constants/PictureViewConstants';

/**
 * 尺寸适配
 */
export class Adaptive {
  static PICTURE_HEIGHT = (currentBreakpoint: string): string => {
    return new BreakpointType(
      PictureViewConstants.PICTURE_HEIGHT_SM,
      PictureViewConstants.PICTURE_HEIGHT_MD,
      PictureViewConstants.PICTURE_HEIGHT_LG,
    ).GetValue(currentBreakpoint);
  };
  static PICTURE_WIDTH = (currentBreakpoint: string): string => {
    return new BreakpointType(
      PictureViewConstants.PICTURE_WIDTH_SM,
      PictureViewConstants.PICTURE_WIDTH_MD,
      PictureViewConstants.PICTURE_WIDTH_LG,
    ).GetValue(currentBreakpoint);
  };
}