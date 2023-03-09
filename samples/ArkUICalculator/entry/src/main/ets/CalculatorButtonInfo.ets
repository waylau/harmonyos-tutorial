// 按钮样式信息
export class CalculatorButtonInfo {
  text: string // 按钮上的文字
  textColor: number // 文字的颜色
  bgColor: number // 按钮背景颜色

  constructor(text: string, textColor: number = Color.Black, bgColor: number = Color.White) {
    this.text = text
    this.textColor = textColor
    this.bgColor = bgColor
  }
}