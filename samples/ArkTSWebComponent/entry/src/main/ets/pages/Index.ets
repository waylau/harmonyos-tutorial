// 导入模块
import { webview } from '@kit.ArkWeb';

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