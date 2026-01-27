import { taskpool } from '@kit.ArkTS';

@Entry
@Component
struct Index {
  @State message: string = '启动';

  build() {
    RelativeContainer() {
      Button(this.message)
        .fontSize($r('app.float.page_text_font_size'))
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
        .onClick(() => {
          ConcurrentFunc();
        })
    }
    .height('100%')
    .width('100%')
  }
}

// 支持使用@Concurrent装饰器声明并校验并发函数
@Concurrent
function add(num1: number, num2: number): number {
  return num1 + num2;
}

async function ConcurrentFunc(): Promise<void> {
  try {
    let task: taskpool.Task = new taskpool.Task(add, 1, 2);
    console.info("taskpool res is: " + await taskpool.execute(task));
  } catch (e) {
    console.error("taskpool execute error is: " + e);
  }
}