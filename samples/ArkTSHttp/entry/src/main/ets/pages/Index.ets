// 导入http模块。
import http from '@ohos.net.http';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(38)
          .fontWeight(FontWeight.Bold)

        // 请求
        Button(('请求'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            this.httpReq()
          })
      }
      .width('100%')
    }
    .height('100%')
  }

  private httpReq() {
    // 创建httpRequest对象。
    let httpRequest = http.createHttp();

    let url = "https://waylau.com/data/people.json";
    let promise = httpRequest.request(
      // 请求url地址
      url,
      {
        // 请求方式
        method: http.RequestMethod.GET,
        // 可选，默认为60s
        connectTimeout: 60000,
        // 可选，默认为60s
        readTimeout: 60000,
        // 开发者根据自身业务需要添加header字段
        header: {
          'Content-Type': 'application/json'
        }
      });

    // 处理响应结果。
    promise.then((data) => {
      if (data.responseCode === http.ResponseCode.OK) {
        console.info('Result:' + data.result);
        console.info('code:' + data.responseCode);
        this.message = JSON.stringify(data.result);
      }
    }).catch((err) => {
      console.info('error:' + JSON.stringify(err));
    });
  }
}