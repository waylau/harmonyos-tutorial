@Entry
@Component
struct Index {
  @State s: string = '鸿蒙HarmonyOS应用开发入门';
  @State length: number = 0;
  build() {
    Column() {
      Text(this.s)
        .id('HelloWorld')
        .fontSize(30)
        .fontWeight(FontWeight.Bold)

      Button('计算')
        .onClick(() => {
          // 统计字符串的字符数
          this.length = this.s.length;
        })

      Text(this.length + '')
        .id('length')
        .fontSize(50)
        .fontWeight(FontWeight.Bold)

    }
    .height('100%')
    .width('100%')
  }
}