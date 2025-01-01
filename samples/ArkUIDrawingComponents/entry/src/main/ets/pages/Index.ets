@Entry
@Component
struct Index {
  @State message: string = 'Hello World'

  build() {
    Row() {
      Column() {
        /*** 以下为 Circle和Ellipse 示例***/
        /*// 绘制一个直径为150的圆
        Circle({ width: 150, height: 150 })

        // 绘制一个直径为150、线条为红色虚线的圆环（宽高设置不一致时以短边为直径）
        Circle()
          .width(150)
          .height(200)
          .fillOpacity(0) // 设置填充区域透明度
          .strokeWidth(3) //设置边框宽度
          .stroke(Color.Red) //设置边框颜色
          .strokeDashArray([1, 2]) //设置边框间隙

        // 绘制一个 150 * 80 的椭圆
        Ellipse({ width: 150, height: 50 })

        // 绘制一个 150 * 100 、线条为蓝色的椭圆环
        Ellipse()
          .width(150)
          .height(50)
          .fillOpacity(0) // 设置填充区域透明度
          .strokeWidth(3) //设置边框宽度
          .stroke(Color.Red) //设置边框颜色
          .strokeDashArray([1, 2]) //设置边框间隙*/

        /*** 以下为 Line 示例***/
        // 线条绘制的起止点坐标均是相对于Line组件本身绘制区域的坐标
        /*Line()
          .startPoint([0, 0])
          .endPoint([50, 100])
          .backgroundColor('#F5F5F5')
        Line()
          .width(200)
          .height(200)
          .startPoint([50, 50])
          .endPoint([150, 150])
          .strokeWidth(5)
          .stroke(Color.Orange)
          .strokeOpacity(0.5)
          .backgroundColor('#F5F5F5')

        // 当坐标点设置的值超出Line组件的宽高范围时，线条会画出组件绘制区域
        Line({ width: 50, height: 50 })
          .startPoint([0, 0])
          .endPoint([100, 100])
          .strokeWidth(3)
          .strokeDashArray([10, 3])
          .backgroundColor('#F5F5F5')

        // strokeDashOffset用于定义关联虚线strokeDashArray数组渲染时的偏移
        Line({ width: 50, height: 50 })
          .startPoint([0, 0])
          .endPoint([100, 100])
          .strokeWidth(3)
          .strokeDashArray([10, 3])
          .strokeDashOffset(5)
          .backgroundColor('#F5F5F5')*/

        /*** 以下为 Polyline 示例***/
        // 在 100 * 100 的矩形框中绘制一段折线，起点(0, 0)，经过(20,60)，到达终点(100, 100)
        /*Polyline({ width: 100, height: 100 })
          .points([[0, 0], [20, 60], [100, 100]])
          .fillOpacity(0)
          .stroke(Color.Blue)
          .strokeWidth(3)

        // 在 100 * 100 的矩形框中绘制一段折线，起点(20, 0)，经过(0,100)，到达终点(100, 90)
        Polyline()
          .width(100)
          .height(100)
          .fillOpacity(0)
          .stroke(Color.Red)
          .strokeWidth(8)
          .points([[20, 0], [0, 100], [100, 90]])
            // 设置折线拐角处为圆弧
          .strokeLineJoin(LineJoinStyle.Round)
            // 设置折线两端为半圆
          .strokeLineCap(LineCapStyle.Round)*/

        /*** 以下为 Polygon 示例***/
        // 在 100 * 100 的矩形框中绘制一个三角形，起点(0, 0)，经过(50, 100)，终点(100, 0)
        /*Polygon({ width: 100, height: 100 })
          .points([[0, 0], [50, 100], [100, 0]])
          .fill(Color.Green)
          .stroke(Color.Transparent)

        // 在 100 * 100 的矩形框中绘制一个四边形，起点(0, 0)，经过(0, 100)和(100, 100)，终点(100, 0)
        Polygon()
          .width(100)
          .height(100)
          .points([[0, 0], [0, 100], [100, 100], [100, 0]])
          .fillOpacity(0)
          .strokeWidth(5)
          .stroke(Color.Blue)

        // 在 100 * 100 的矩形框中绘制一个五边形，起点(50, 0)，依次经过(0, 50)、(20, 100)和(80, 100)，终点(100, 50)
        Polygon()
          .width(100)
          .height(100)
          .points([[50, 0], [0, 50], [20, 100], [80, 100], [100, 50]])
          .fill(Color.Red)
          .fillOpacity(0.6)
          .stroke(Color.Transparent)*/

        /*** 以下为 Path 示例***/
        // 绘制一条长900px，宽3vp的直线
        /*Path()
          .height(10)
          .commands('M0 0 L600 0')
          .stroke(Color.Black)
          .strokeWidth(3)

        // 绘制直线图形
        Path()
          .commands('M100 0 L200 240 L0 240 Z')
          .fillOpacity(0)
          .stroke(Color.Black)
          .strokeWidth(3)
        Path()
          .commands('M0 0 H200 V200 H0 Z')
          .fillOpacity(0)
          .stroke(Color.Black)
          .strokeWidth(3)
        Path()
          .commands('M100 0 L0 100 L50 200 L150 200 L200 100 Z')
          .fillOpacity(0)
          .stroke(Color.Black)
          .strokeWidth(3)


        // 绘制弧线图形
        Path()
          .commands("M0 300 S100 0 240 300 Z")
          .fillOpacity(0)
          .stroke(Color.Black)
          .strokeWidth(3)
        Path()
          .commands('M0 150 C0 100 140 0 200 150 L100 300 Z')
          .fillOpacity(0)
          .stroke(Color.Black)
          .strokeWidth(3)
        Path()
          .commands('M0 100 A30 20 20 0 0 200 100 Z')
          .fillOpacity(0)
          .stroke(Color.Black)
          .strokeWidth(3)*/


        // 绘制90% * 50矩形
        /*Rect({ width: '90%', height: 50 })
          .fill(Color.Pink)
          .stroke(Color.Transparent)

        // 绘制90% * 50的矩形框
        Rect()
          .width('90%')
          .height(50)
          .fillOpacity(0)
          .stroke(Color.Red)
          .strokeWidth(3)

        // 绘制90% * 80的矩形, 圆角宽高分别为40、20
        Rect({ width: '90%', height: 80 })
          .radiusHeight(20)
          .radiusWidth(40)
          .fill(Color.Pink)
          .stroke(Color.Transparent)

        // 绘制90% * 80的矩形, 圆角宽高为20
        Rect({ width: '90%', height: 80 })
          .radius(20)
          .fill(Color.Pink)
          .stroke(Color.Transparent)

        // 绘制90% * 50矩形, 左上圆角宽高40,右上圆角宽高20,右下圆角宽高40,左下圆角宽高20
        Rect({ width: '90%', height: 80 })
          .radius([[40, 40], [20, 20], [40, 40], [20, 20]])
          .fill(Color.Pink)
          .stroke(Color.Transparent)*/

        // 在Shape的(-2, 118)点绘制一个 300 * 10 直线路径,颜色0x317AF7,边框颜色黑色,宽度4,间隙20,向左偏移10,线条两端样式为半圆,拐角样式圆角,抗锯齿(默认开启)
        /*Shape() {
          Rect().width(300).height(50)
          Ellipse().width(300).height(50).offset({ x: 0, y: 60 })
          Path().width(300).height(10).commands('M0 0 L900 0').offset({ x: 0, y: 120 })
        }
        .viewPort({ x: -2, y: -2, width: 304, height: 130 })
        .fill(0x317AF7)
        .stroke(Color.Black)
        .strokeWidth(4)
        .strokeDashArray([20])
        .strokeDashOffset(10)
        .strokeLineCap(LineCapStyle.Round)
        .strokeLineJoin(LineJoinStyle.Round)
        .antiAlias(true)

        // 分别在Shape的(0, 0)、(-5, -5)点绘制一个 300 * 50 带边框的矩形,可以看出之所以将视口的起始位置坐标设为负值是因为绘制的起点默认为线宽的中点位置，因此要让边框完全显示则需要让视口偏移半个线宽
        Shape() {
          Rect().width(300).height(50)
        }
        .viewPort({ x: 0, y: 0, width: 320, height: 70 })
        .fill(0x317AF7)
        .stroke(Color.Black)
        .strokeWidth(10)

        // 在Shape的(0, -5)点绘制一条直线路径,颜色0xEE8443,线条宽度10,线条间隙20
        Shape() {
          Path().width(300).height(10).commands('M0 0 L900 0')
        }
        .viewPort({ x: 0, y: -5, width: 300, height: 20 })
        .stroke(0xEE8443)
        .strokeWidth(10)
        .strokeDashArray([20])

        // 在Shape的(0, -5)点绘制一条直线路径,颜色0xEE8443,线条宽度10,线条间隙20,向左偏移10
        Shape() {
          Path().width(300).height(10).commands('M0 0 L900 0')
        }
        .viewPort({ x: 0, y: -5, width: 300, height: 20 })
        .stroke(0xEE8443)
        .strokeWidth(10)
        .strokeDashArray([20])
        .strokeDashOffset(10)

        // 在Shape的(0, -5)点绘制一条直线路径,颜色0xEE8443,线条宽度10,透明度0.5
        Shape() {
          Path().width(300).height(10).commands('M0 0 L900 0')
        }
        .viewPort({ x: 0, y: -5, width: 300, height: 20 })
        .stroke(0xEE8443)
        .strokeWidth(10)
        .strokeOpacity(0.5)

        // 在Shape的(0, -5)点绘制一条直线路径,颜色0xEE8443,线条宽度10,线条间隙20,线条两端样式为半圆
        Shape() {
          Path().width(300).height(10).commands('M0 0 L900 0')
        }
        .viewPort({ x: 0, y: -5, width: 300, height: 20 })
        .stroke(0xEE8443)
        .strokeWidth(10)
        .strokeDashArray([20])
        .strokeLineCap(LineCapStyle.Round)

        // 在Shape的(-80, -5)点绘制一个封闭路径,颜色0x317AF7,线条宽度10,边框颜色0xEE8443,拐角样式锐角（默认值）
        Shape() {
          Path().width(200).height(60).commands('M0 0 L400 0 L400 150 Z')
        }
        .viewPort({ x: -80, y: -5, width: 310, height: 90 })
        .fill(0x317AF7)
        .stroke(0xEE8443)
        .strokeWidth(10)
        .strokeLineJoin(LineJoinStyle.Miter)
        .strokeMiterLimit(5)*/
      }
      .width('100%')
    }
    .height('100%')
  }
}