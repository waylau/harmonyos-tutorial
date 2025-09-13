@Entry
@Component
struct Index {
  @State message: string = '主页';

  // 创建一个导航控制器对象并传入Navigation
  pageInfos: NavPathStack = new NavPathStack();

  build() {
    Navigation(this.pageInfos) {
      Column() {
        Text(this.message)
          .fontSize($r('app.float.page_text_font_size'))
          .fontWeight(FontWeight.Bold)
          .alignRules({
            center: { anchor: '__container__', align: VerticalAlign.Center },
            middle: { anchor: '__container__', align: HorizontalAlign.Center }
          })

        Button('导航到第一页')
          .width(128).height(28)
          .onClick(() => {
            // 跳转页面时不携带参数
            this.pageInfos.pushPathByName('pageOne', null);
          })

        Button('导航到第二页')
          .width(128).height(28)
          .onClick(() => {
            // 跳转页面时携带参数
            this.pageInfos.pushPathByName('pageTwo', "Page Two");
          })
      }
      .height('100%')
      .width('100%')
    }
    .mode(NavigationMode.Auto)
  }
}