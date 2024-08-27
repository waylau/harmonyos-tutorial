import router from '@ohos.router'

@Entry
@Component
struct DetailExample {
  // 接收NavigatorExample.ets的传参
  @State text: any = router.getParams()['text']

  build() {
    Flex({ direction: FlexDirection.Column, alignItems: ItemAlign.Start, justifyContent: FlexAlign.SpaceBetween }) {
      Navigator({ target: 'pages/BackExample', type: NavigationType.Push }) {
        Text('Go to back page').width('100%')
          .fontSize(30)
      }

      Text('This is ' + this.text['name'] + ' page')
        .width('100%').textAlign(TextAlign.Center)
        .fontSize(30)
    }
    .width('100%').height(200).padding({ left: 35, right: 35, top: 35 })
  }
}