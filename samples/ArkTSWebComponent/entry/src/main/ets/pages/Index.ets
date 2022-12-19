@Entry
@Component
struct Index {

  // 创建WebController
  controller: WebController = new WebController();
  build() {
    Column() {
      // 添加Web组件
      Web({ src: 'https://waylau.com/', controller: this.controller })
    }
  }
}