/**
 * 计算器计算逻辑
 */
export class Calculator {

  /**
   * 计算
   * @param input  例：input = '1.1-10%+2×3÷4'
   */
  public static calculate(input: string): string {
    // 先将百分数转为小数,
    input = input.replace(RegExp(`(((\\d*\\.\\d*)|(\\d+))%)`, 'g'), s => String(Number(s.replace(/%/, '')) / 100)) // input = '1.1-0.1+2×3÷4'
    // 要将input分割为数与运算符，分割节点的索引储存在splitIndex
    var splitIndex = [0]
    for (var i = 1;i < input.length; i++) {
      if (input[i].match(RegExp('(\\+|-|×|÷)')) != null) {
        splitIndex.push(i)
        splitIndex.push(i + 1)
        i++
      }
    }
    splitIndex.push(input.length) // splitIndex = [0, 3, 4, 7, 8, 9, 10, 11, 12, 13]
    // 分割input为数与运算符,储存在split
    var split = []
    for (var j = 0;j < splitIndex.length - 1; j++) {
      split.push(input.substring(splitIndex[j], splitIndex[j+1]))
    }
    // split = ['1.1', '-', '0.1', '+', '2', '×', '3', '÷', '4']
    return Calculator.recursiveCompute(split)[0] // 递归计算直至完成
  }

  /**
   * 递归计算直至完成，一次计算一对数，从左往右，乘除法优先于加减法
   * @param split
   * 例：split = ['1.1', '-', '0.1', '+', '2', '×', '3', '÷', '4']
   * 第1次：split = ['1.1', '-', '0.1', '+', '6', '÷', '4']
   * 第2次：split = ['1.1', '-', '0.1', '+', '1.5']
   * 第3次：split = ['1', '+', '1.5']
   * 第4次：split = ['2.5']
   */
  private static recursiveCompute(split: string[]): string[] {
    var symbolIndex // 符号索引
    // 先寻找乘除符号
    for (var i = 0;i < split.length; i++) {
      if (split[i].match(RegExp('^(×|÷)$')) != null) {
        symbolIndex = i
        break
      }
    }
    // 若没找到乘除符号，则寻找加减符号
    if (symbolIndex == null) {
      for (var j = 0;j < split.length; j++) {
        if (split[j].match(RegExp('^(\\+|-)$')) != null) {
          symbolIndex = j
          break
        }
      }
    }
    if (symbolIndex == null) { // 若没找到运算符号，表明计算结束，返回结果
      return split
    } else { // 若找到运算符号，运算后继续寻找运算
      var num1 = +split[symbolIndex-1]
      var symbo1 = split[symbolIndex]
      var num2 = +split[symbolIndex+1]
      var result = 0
      switch (symbo1) {
        case '+':
          result = num1 + num2
          break
        case '-':
          result = num1 - num2
          break
        case '×':
          result = num1 * num2
          break
        case '÷':
          result = num1 / num2
          break
      }
      split = split.slice(0, symbolIndex - 1).concat(`${result}`).concat(split.slice(symbolIndex + 2))
      return Calculator.recursiveCompute(split)
    }
  }
}