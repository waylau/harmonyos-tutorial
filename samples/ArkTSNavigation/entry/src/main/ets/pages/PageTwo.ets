@Builder
export function PageTwoBuilder(name: string, param: Object) {
  PageTwo()
}

@Component
export struct PageTwo {
  @State message: string = '第二页';
  pageInfos: NavPathStack = new NavPathStack();

  build() {
    NavDestination() {
      RelativeContainer() {
        Text(this.message)
          .fontSize($r('app.float.page_text_font_size'))
          .fontWeight(FontWeight.Bold)
          .alignRules({
            center: { anchor: '__container__', align: VerticalAlign.Center },
            middle: { anchor: '__container__', align: HorizontalAlign.Center }
          })
      }
      .height('100%')
      .width('100%')
    }
    .title('PageTwo')
    .onReady((context: NavDestinationContext) => {
      this.pageInfos = context.pathStack
      this.message = context.pathInfo.param as string;
    })
  }
}