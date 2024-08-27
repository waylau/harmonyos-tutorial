@Entry
@Component
struct NavigatorExample {
  @State active: boolean = false
  @State Text: object = {name: 'news'}

  build() {
    Flex({ direction: FlexDirection.Column, alignItems: ItemAlign.Start, justifyContent: FlexAlign.SpaceBetween }) {
      Navigator({ target: 'pages/DetailExample', type: NavigationType.Push }) {
        Text('Go to ' + this.Text['name'] + ' page')
          .width('100%').textAlign(TextAlign.Center)
          .fontSize(30)
      }.params({ text: this.Text }) // 传参数到Detail页面

      Navigator() {
        Text('Back to previous page').width('100%').textAlign(TextAlign.Center)
          .fontSize(26)
      }.active(this.active)
      .onClick(() => {
        this.active = true
      })
    }.height(250).width('100%').padding(35)
  }
}