/**
 * 常量
 */
export interface ActionInterface {
  icon: Resource
  icon_name: string
}

export default class PictureViewConstants {
  /**
   * Picture size.
   */
  static PICTURE_HEIGHT_SM = '88%';
  static PICTURE_HEIGHT_MD = '100%';
  static PICTURE_HEIGHT_LG = '100%';
  static PICTURE_WIDTH_SM = '100%';
  static PICTURE_WIDTH_MD = '84.5%';
  static PICTURE_WIDTH_LG = '46.9%';
  static readonly ICON_LIST_WIDTH = "18%";
  /**
   * Actions.
   */
  static ACTIONS: ActionInterface[] = [
    {
      icon: $r('app.media.ic_public_share'), icon_name: "分享"
    },
    {
      icon: $r('app.media.ic_public_favor'), icon_name: "收藏"
    },
    {
      icon: $r("app.media.ic_gallery_public_details_4"), icon_name: "编辑"
    },
    {
      icon: $r("app.media.ic_gallery_public_details_5"), icon_name: "删除"
    },
    {
      icon: $r('app.media.ic_public_more'), icon_name: "更多"
    }
  ];
  /**
   * Pictures.
   */
  static readonly PICTURES: Resource[] = [
    $r('app.media.photo1'),
    $r('app.media.photo2'),
    $r('app.media.photo3'),
    $r('app.media.photo4'),
    $r('app.media.photo5'),
    $r('app.media.photo6'),
    $r('app.media.photo7'),
    $r('app.media.photo8'),
    $r('app.media.photo9'),
    $r('app.media.photo1'),
    $r('app.media.photo2'),
    $r('app.media.photo3'),
    $r('app.media.photo4'),
    $r('app.media.photo5'),
    $r('app.media.photo6'),
    $r('app.media.photo7'),
    $r('app.media.photo8'),
    $r('app.media.photo9'),
    $r('app.media.photo1'),
    $r('app.media.photo2'),
    $r('app.media.photo3'),
    $r('app.media.photo4'),
    $r('app.media.photo5'),
    $r('app.media.photo6'),
    $r('app.media.photo7'),
    $r('app.media.photo8'),
    $r('app.media.photo9'),
    $r('app.media.photo1'),
    $r('app.media.photo2'),
    $r('app.media.photo3'),
    $r('app.media.photo4'),
    $r('app.media.photo5'),
    $r('app.media.photo6'),
    $r('app.media.photo7'),
    $r('app.media.photo8'),
    $r('app.media.photo9'),
    $r('app.media.photo1'),
    $r('app.media.photo2'),
    $r('app.media.photo3'),
    $r('app.media.photo4'),
    $r('app.media.photo5'),
    $r('app.media.photo6'),
    $r('app.media.photo7'),
    $r('app.media.photo8'),
    $r('app.media.photo9'),
    $r('app.media.photo1'),
    $r('app.media.photo2'),
    $r('app.media.photo3'),
    $r('app.media.photo4'),
    $r('app.media.photo5'),
    $r('app.media.photo6'),
    $r('app.media.photo7'),
    $r('app.media.photo8'),
    $r('app.media.photo9')
  ];
}