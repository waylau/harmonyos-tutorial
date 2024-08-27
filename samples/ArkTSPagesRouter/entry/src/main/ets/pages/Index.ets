// 导入router模块
import router from '@ohos.router';

@Entry
@Component
struct Index {
  // 修改变量值以示区分
  @State message: string = 'Index页面'

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(50)
          .fontWeight(FontWeight.Bold)

        // 添加按钮，触发跳转
        Button('跳转')
          .fontSize(40)
          .onClick(() => {
            router.pushUrl({
              url: 'pages/Second',
              params: {
                src: 'Index页面传来的数据',
              }
            });
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}