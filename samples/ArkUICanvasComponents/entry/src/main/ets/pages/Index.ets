@Entry
@Component
struct Index {
  private renderingContextSettings: RenderingContextSettings = new RenderingContextSettings(true)

  //使用RenderingContext在Canvas组件上进行绘制，绘制对象可以是矩形、文本、图片等。
  private canvasRenderingContext2D: CanvasRenderingContext2D = new CanvasRenderingContext2D(this.renderingContextSettings)

  build() {
    Row() {
      Column() {
        Canvas(this.canvasRenderingContext2D)
          .width('100%')
          .height('100%')
            // onReady是Canvas组件初始化完成时的事件回调，该事件之后Canvas组件宽高确定且可获取
          .onReady(() => {
            // 绘制矩形。
            this.canvasRenderingContext2D.fillRect(0, 30, 100, 100)

            // 绘制贝赛尔曲线。
            this.canvasRenderingContext2D.beginPath()
            this.canvasRenderingContext2D.moveTo(170, 10)
            this.canvasRenderingContext2D.bezierCurveTo(20, 100, 200, 100, 200, 20)
            this.canvasRenderingContext2D.stroke()

            // 绘制渐变对象。
            let grad = this.canvasRenderingContext2D.createLinearGradient(150, 0, 300, 100)
            grad.addColorStop(0.0, 'red')
            grad.addColorStop(0.5, 'white')
            grad.addColorStop(1.0, 'green')
            this.canvasRenderingContext2D.fillStyle = grad
            this.canvasRenderingContext2D.fillRect(200, 0, 100, 100)
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}