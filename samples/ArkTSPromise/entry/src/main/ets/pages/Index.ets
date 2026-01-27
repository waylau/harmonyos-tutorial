@Entry
@Component
struct Index {
  @State message: string = '异步并发';

  build() {
    RelativeContainer() {
      Button(this.message)
        .id('HelloWorld')
        .fontSize($r('app.float.page_text_font_size'))
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
        .onClick(async () => {
          // 使用了await关键字来等待Promise对象的解析，并将其解析值存储在res变量中
          let res = await myAsyncFunction();
          console.info("res is: " + res);
        })
    }
    .height('100%')
    .width('100%')
  }
}

// 由于要等待异步操作完成，因此需要将整个操作包在async函数中
async function myAsyncFunction(): Promise<string> {
  // 使用try/catch块来捕获异步操作中的异常
  try {
    const result: string = await new Promise((resolve: Function) => {
      resolve('Welcome to waylau.com!');
    });

    return result;
  } catch (e) {
    console.error(`Get exception: ${e}`);

    return 'exception';
  }
}
