// 导入Calculator
import { Calculator } from '../Calculator';
// 导入CalculatorButtonInfo
import { CalculatorButtonInfo } from '../CalculatorButtonInfo';

@Entry
@Component
struct Index {
  private BTN_INFO_ARRAY: CalculatorButtonInfo[] = [ // 所有按钮样式信息
    new CalculatorButtonInfo('C', Color.Blue),
    new CalculatorButtonInfo('÷', Color.Blue),
    new CalculatorButtonInfo('×', Color.Blue),
    new CalculatorButtonInfo('←', Color.Blue),
    new CalculatorButtonInfo('7'),
    new CalculatorButtonInfo('8'),
    new CalculatorButtonInfo('9'),
    new CalculatorButtonInfo('-', Color.Blue),
    new CalculatorButtonInfo('4'),
    new CalculatorButtonInfo('5'),
    new CalculatorButtonInfo('6'),
    new CalculatorButtonInfo('+', Color.Blue),
    new CalculatorButtonInfo('1'),
    new CalculatorButtonInfo('2'),
    new CalculatorButtonInfo('3'),
    new CalculatorButtonInfo('=', Color.White, Color.Blue),
    new CalculatorButtonInfo('%'),
    new CalculatorButtonInfo('0'),
    new CalculatorButtonInfo('.')
  ]

  @State input: string = '' // 输入内容

  // 点击计算器按钮
  onClickBtn = (text: string) => {
    switch (text) {
      case 'C': // 清空所有输入
        this.input = ''
        break
      case '←': // 删除输入的最后一个字符
        if (this.input.length > 0) {
          this.input = this.input.substring(0, this.input.length - 1)
        }
        break
      case '=': // 计算结果
        this.input = Calculator.calculate(this.input)
        break
      default: // 输入内容
        this.input += text
        break
    }
  }

  // 构造计算器按钮
  @Builder CalculatorButton(btnInfo: CalculatorButtonInfo) { // 计算器按钮组件
    GridItem() {
      Text(btnInfo.text) // 文本
        .fontSize(50)
        .fontWeight(FontWeight.Bold)
        .width('100%')
        .height('100%')
        .textAlign(TextAlign.Center)
        .borderRadius(100) // 圆角
        .fontColor(btnInfo.textColor) // 字体颜色
        .backgroundColor(btnInfo.bgColor) // 背景颜色
    }
    .onClick(() => this.onClickBtn(btnInfo.text))
    .rowStart(btnInfo.text == '=' ? 4 : null)
    .rowEnd(btnInfo.text == '=' ? 5 : null) // 等于按钮占两格，其他按钮默认
  }

  build() {
    Stack({ alignContent: Alignment.Bottom }) {
      Column() {
        Text(this.input.length == 0 ? '0' : this.input) // 输入内容，若没有内容显示0
          .width('100%')
          .padding(10)
          .textAlign(TextAlign.End)
          .fontSize(46)
        Grid() {
          // 遍历生成按钮
          ForEach(this.BTN_INFO_ARRAY, (btnInfo:CalculatorButtonInfo) => this.CalculatorButton(btnInfo))
        }
        .columnsTemplate('1fr 1fr 1fr 1fr') // 按钮比重分配
        .rowsTemplate('1fr 1fr 1fr 1fr 1fr')
        .columnsGap(2) // 按钮间隙
        .rowsGap(2)
        .width('100%')
        .aspectRatio(1) // 长宽比
      }
    }.width('100%').height('100%').backgroundColor(Color.Gray)
  }
}
