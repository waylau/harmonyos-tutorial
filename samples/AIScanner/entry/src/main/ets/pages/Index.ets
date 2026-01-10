@Entry
@Component
struct Index {
  private dataSource: Array<string> =
    ['身份证识别', '银行卡识别', '文档扫描'];
  @State searchContent: string = '';
  // 创建一个导航控制器对象并传入Navigation
  pageInfos: NavPathStack = new NavPathStack();
  scroller: Scroller = new Scroller();
  layoutOptions: GridLayoutOptions = {
    regularSize: [1, 1],
  };

  build() {
    Navigation(this.pageInfos) {
      Column() {
        Column() {
          Text('AI扫描')
            .fontColor($r('sys.color.font_primary'))
            .fontSize(24)
        }.height('30%')
        .width('100%')
        .justifyContent(FlexAlign.Center)
        .margin({ top: 10 })

        Grid(this.scroller, this.layoutOptions) {
          ForEach(this.dataSource, (item: string) => {
            GridItem() {
              Button(item)
                .width('100%')
                .height(48)
                .fontSize(24)
                .onClick(e => {
                  switch (item) {
                    case '身份证识别':
                      this.pageInfos.pushPathByName('PageIdCard', item);
                      break;
                    case '银行卡识别':
                      this.pageInfos.pushPathByName('PageBankCard', item);
                      break;
                    case '文档扫描':
                      this.pageInfos.pushPathByName('PageDocScan', item);
                      break;
                  }
                })
            }
          }, (item: string) => item
          )
        }
        .columnsGap(8)
        .rowsGap(8)
        .columnsTemplate('1fr 1fr')
        .width('100%')
        .height('100%')

      }
      .width('100%')
      .height('100%')
      .margin({ bottom: 10 })

    }
    .mode(NavigationMode.Stack)
    .hideTitleBar(true)

  }
}