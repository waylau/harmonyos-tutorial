// 导入webview
import  webview from '@ohos.web.webview'


@Entry
@Component
struct Index {

  // 创建WebviewController
  controller: webview.WebviewController = new webview.WebviewController();

  build() {
    Column() {
      // 添加Web组件
      Web({ src: 'https://waylau.com/', controller: this.controller })
    }
  }
}