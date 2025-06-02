@Entry
@Component
struct Index {
  @State indicatorIndex: number = 0;

  // Indicator组件的控制器，可以将此对象绑定至Indicator组件来控制翻页。
  private indicatorController: IndicatorComponentController = new IndicatorComponentController();

  build() {
    Column() {
      Text(this.indicatorIndex + '')
        .fontSize($r('app.float.page_text_font_size'))
        .fontWeight(FontWeight.Bold)

      IndicatorComponent(this.indicatorController)
        .initialIndex(0) // 设置首次显示时当前导航点的索引值。
        .style( // 可选导航点指示器样式。
          new DotIndicator()
            .itemWidth(25)
            .itemHeight(25)
            .selectedItemWidth(25)
            .selectedItemHeight(25)
            .color(Color.Gray)
            .selectedColor(Color.Blue))
        .loop(true) // 设置是否开启循环
        .count(6) // 设置导航点总数量
        .vertical(false) // 设置是否为纵向滑动
        // 当前显示的选中导航点索引变化时触发该事件，可通过回调函数获取当前选中导航点的索引值。
        .onChange((index: number) => {
          console.log("current index: " + index );

          // 索引赋值给变量indicatorIndex
          this.indicatorIndex = index;
        })
    }
    .height('100%')
    .width('100%')
  }
}