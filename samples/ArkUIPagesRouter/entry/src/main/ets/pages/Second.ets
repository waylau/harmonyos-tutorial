// 导入router模块
import router from '@ohos.router';

@Entry
@Component
struct Second {
  @State message: string = 'Second页面'
  @State src: string = router.getParams()?.['src'];

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(50)
          .fontWeight(FontWeight.Bold)

        // 显示传参的内容
        Text(this.src)
          .fontSize(30)

        // 添加按钮，触发返回
        Button('返回')
          .fontSize(40)
          .onClick(() => {
            router.back();
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}