@Entry
@Component
struct BackExample {
  build() {
    Column() {
      Navigator({ target: 'pages/NavigatorExample', type: NavigationType.Back }) {
        Text('Return to Navigator Page').width('100%').textAlign(TextAlign.Center)
          .fontSize(30)
      }
    }.width('100%').height(200).padding({ left: 35, right: 35, top: 35 })
  }
}