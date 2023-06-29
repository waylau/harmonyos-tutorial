@Entry
@Component
struct WidgetCard {
  /*
   * The max lines.
   */
  readonly MAX_LINES: number = 1;

  /*
   * The action type.
   */
  readonly ACTION_TYPE: string = 'router';

  /*
   * The message.
   */
  readonly MESSAGE: string = 'add detail';

  /*
   * The ability name.
   */
  readonly ABILITY_NAME: string = 'EntryAbility';

  /*
   * The with percentage setting.
   */
  readonly FULL_WIDTH_PERCENT: string = '100%';

  /*
   * The height percentage setting.
   */
  readonly FULL_HEIGHT_PERCENT: string = '100%';

  build() {
    Stack() {
      Image($r("app.media.ic_widget"))
        .width(this.FULL_WIDTH_PERCENT)
        .height(this.FULL_HEIGHT_PERCENT)
        .objectFit(ImageFit.Cover)
      Column() {
        Text($r('app.string.title_immersive'))
          .fontSize($r('app.float.title_immersive_font_size'))
          .textOverflow({ overflow: TextOverflow.Ellipsis })
          .fontColor($r('app.color.text_font_color'))
          .maxLines(this.MAX_LINES)
        Text($r('app.string.detail_immersive'))
          .fontSize($r('app.float.detail_immersive_font_size'))
          .opacity($r('app.float.detail_immersive_opacity'))
          .margin({ top: $r('app.float.detail_immersive_margin_top') })
          .textOverflow({ overflow: TextOverflow.Ellipsis })
          .fontColor($r('app.color.text_font_color'))
          .maxLines(this.MAX_LINES)
      }
      .width(this.FULL_WIDTH_PERCENT)
      .height(this.FULL_HEIGHT_PERCENT)
      .alignItems(HorizontalAlign.Start)
      .justifyContent(FlexAlign.End)
      .padding($r('app.float.column_padding'))
    }
    .width(this.FULL_WIDTH_PERCENT)
    .height(this.FULL_HEIGHT_PERCENT)
    .onClick(() => {
      postCardAction(this, {
        "action": this.ACTION_TYPE,
        "abilityName": this.ABILITY_NAME,
        "params": {
          "message": this.MESSAGE
        }
      });
    })
  }
}