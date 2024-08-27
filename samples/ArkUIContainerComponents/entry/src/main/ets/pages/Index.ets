
@Entry
@Component
struct Index {
  private numberArray: String[] = ['0', '1', '2', '3', '4'];
  private bgColors: Color[] = [Color.Red, Color.Orange, Color.Yellow, Color.Green, Color.Pink, Color.Grey, Color.Blue, Color.Brown];
  private timetableListItemGroup: any = [
    {
      title:'星期一',
      projects:['语文', '数学', '英语']
    },
    {
      title:'星期二',
      projects:['物理', '化学', '生物']
    },
    {
      title:'星期三',
      projects:['历史', '地理', '政治']
    },
    {
      title:'星期四',
      projects:['美术', '音乐', '体育']
    }
  ]

  build() {
    Column() {
      /*** 以下为 Column和Row 示例 ***//*
      Column() {
        // 设置子组件水平方向的间距为5
        Row({ space: 5 }) {
          Row().width('30%').height(50).backgroundColor(0xAFEEEE)
          Row().width('30%').height(50).backgroundColor(0x00FFFF)
        }.width('90%').height(107).border({ width: 1 })

        // 设置子元素垂直方向对齐方式
        Row() {
          Row().width('30%').height(50).backgroundColor(0xAFEEEE)
          Row().width('30%').height(50).backgroundColor(0x00FFFF)
        }.width('90%').alignItems(VerticalAlign.Bottom).height('15%').border({ width: 1 })

        Row() {
          Row().width('30%').height(50).backgroundColor(0xAFEEEE)
          Row().width('30%').height(50).backgroundColor(0x00FFFF)
        }.width('90%').alignItems(VerticalAlign.Center).height('15%').border({ width: 1 })

        // 设置子元素水平方向对齐方式
        Row() {
          Row().width('30%').height(50).backgroundColor(0xAFEEEE)
          Row().width('30%').height(50).backgroundColor(0x00FFFF)
        }.width('90%').border({ width: 1 }).justifyContent(FlexAlign.End)

        Row() {
          Row().width('30%').height(50).backgroundColor(0xAFEEEE)
          Row().width('30%').height(50).backgroundColor(0x00FFFF)
        }.width('90%').border({ width: 1 }).justifyContent(FlexAlign.Center)
      }*/

      /*** 以下为 ColumnSplit和RowSplit 示例 ***//*
      // 纵向的分割线
      RowSplit() {
        Text('1').width('10%').height(400).backgroundColor(0xF5DEB3).textAlign(TextAlign.Center)
        Text('2').width('10%').height(400).backgroundColor(0xD2B48C).textAlign(TextAlign.Center)
        Text('3').width('10%').height(400).backgroundColor(0xF5DEB3).textAlign(TextAlign.Center)
        Text('4').width('10%').height(400).backgroundColor(0xD2B48C).textAlign(TextAlign.Center)
        Text('5').width('10%').height(400).backgroundColor(0xF5DEB3).textAlign(TextAlign.Center)
      }
      .resizeable(true) // 可拖动
      .width('90%').height(400)

      // 横向的分割线
      ColumnSplit() {
        Text('1').width('100%').height(50).backgroundColor(0xF5DEB3).textAlign(TextAlign.Center)
        Text('2').width('100%').height(50).backgroundColor(0xD2B48C).textAlign(TextAlign.Center)
        Text('3').width('100%').height(50).backgroundColor(0xF5DEB3).textAlign(TextAlign.Center)
        Text('4').width('100%').height(50).backgroundColor(0xD2B48C).textAlign(TextAlign.Center)
        Text('5').width('100%').height(50).backgroundColor(0xF5DEB3).textAlign(TextAlign.Center)
      }
      .resizeable(true) // 可拖动
      .width('90%').height('60%')
      */

      /*** 以下为 Flex 示例 ***//*
      // 主轴方向为FlexDirection.Row
      Flex({ direction: FlexDirection.Row }) {
        Text('1').width('20%').height(50).backgroundColor(0xF5DEB3)
        Text('2').width('20%').height(50).backgroundColor(0xD2B48C)
        Text('3').width('20%').height(50).backgroundColor(0xF5DEB3)
        Text('4').width('20%').height(50).backgroundColor(0xD2B48C)
      }
      .height('40%')
      .width('90%')
      .padding(10)
      .backgroundColor(0xAFEEEE)

      // 主轴方向为FlexDirection.Column
      Flex({ direction: FlexDirection.Column }) {
        Text('1').width('20%').height(50).backgroundColor(0xF5DEB3)
        Text('2').width('20%').height(50).backgroundColor(0xD2B48C)
        Text('3').width('20%').height(50).backgroundColor(0xF5DEB3)
        Text('4').width('20%').height(50).backgroundColor(0xD2B48C)
      }
      .height('40%')
      .width('90%')
      .padding(10)
      .backgroundColor(0xAFEEEE)
      */

      /*** 以下为 Grid和GridItem 示例 ***//*
      // 主轴方向为FlexDirection.Row
      Grid() {
        ForEach(this.numberArray, (day: string) => {
          ForEach(this.numberArray, (day: string) => {
            GridItem() {
              Text(day)
                .fontSize(16)
                .backgroundColor(0xF9CF93)
                .width('100%')
                .height('100%')
                .textAlign(TextAlign.Center)
            }
          }, day => day)
        }, day => day)
      }
      .columnsTemplate('1fr 1fr 1fr 1fr 1fr') // 设置当前网格布局列的数量
      .rowsTemplate('1fr 1fr 1fr 1fr 1fr') // 设置当前网格布局行的数量
      .columnsGap(10) // 设置列与列的间距
      .rowsGap(10) // 设置行与行的间距
      .width('90%')
      .backgroundColor(0xFAEEE0)
      .height(300)
      */

      /*** 以下为 ridRow和GridCol 示例 ***//*
      GridRow({
        columns: 5, // 设置布局列数
        gutter: { x: 5, y: 20 }, // 栅格布局间距，x代表水平方向，y代表垂直方向
        breakpoints: { value: ["400vp", "600vp", "800vp"], // 断点发生变化时触发回调
          reference: BreakpointsReference.WindowSize },
        direction: GridRowDirection.Row // 	栅格布局排列方向
      }) {
        ForEach(this.bgColors, (color) => {
          GridCol({ span: { xs: 1, sm: 2, md: 3, lg: 4 } }) {
            Row().width("100%").height("80vp")
          }.borderColor(color).borderWidth(2)
        })
      }.width("100%").height("100%")
      */

      /*** 以下为 List、ListItem和ListItemGroup 示例 ***//*
      List({ space: 2 }) {
        ForEach(this.timetableListItemGroup, (item) => {
          ListItemGroup() {
            ForEach(item.projects, (project) => {
              ListItem() {
                Text(project)
                  .width("100%").height(30).fontSize(20)
                  .textAlign(TextAlign.Center)
              }
            }, item => item)
          }
          .borderRadius(20)
          .divider({ strokeWidth: 2, color: 0xDCDCDC }) // 每行之间的分界线
        })
      }
      .width('100%')
      */

      /*** 以下为 AlphabetIndexer 示例 ***//*
      Row() {
        List({ space: 10, initialIndex: 0 }) {
          ForEach(this.alphabetIndexerArrayA, (item) => {
            ListItem() {
              Text(item)
                .width('80%')
                .height('5%')
                .fontSize(20)
                .textAlign(TextAlign.Center)
            }
          }, item => item)

          ForEach(this.alphabetIndexerArrayB, (item) => {
            ListItem() {
              Text(item)
                .width('80%')
                .height('5%')
                .fontSize(20)
                .textAlign(TextAlign.Center)
            }
          }, item => item)

          ForEach(this.alphabetIndexerArrayC, (item) => {
            ListItem() {
              Text(item)
                .width('80%')
                .height('5%')
                .fontSize(20)
                .textAlign(TextAlign.Center)
            }
          }, item => item)

          ForEach(this.alphabetIndexerArrayL, (item) => {
            ListItem() {
              Text(item)
                .width('80%')
                .height('5%')
                .fontSize(20)
                .textAlign(TextAlign.Center)
            }
          }, item => item)
        }
        .width('50%')
        .height('100%')

        AlphabetIndexer({ arrayValue: this.alphabetIndexerArrayValue, selected: 0 })
          .selectedColor(0xFFFFFF) // 选中项文本颜色
          .popupColor(0xFFFAF0) // 弹出框文本颜色
          .selectedBackgroundColor(0xCCCCCC) // 选中项背景颜色
          .popupBackground(0xD2B48C) // 弹出框背景颜色
          .usingPopup(true) // 是否显示弹出框
          .selectedFont({ size: 16, weight: FontWeight.Bolder }) // 选中项字体样式
          .popupFont({ size: 30, weight: FontWeight.Bolder }) // 弹出框内容的字体样式
          .itemSize(28) // 每一项的尺寸大小
          .alignStyle(IndexerAlign.Left) // 弹出框在索引条右侧弹出
          .onRequestPopupData((index: number) => {
            if (this.alphabetIndexerArrayValue[index] == 'A') {
              return this.alphabetIndexerArrayA // 当选中A时，弹出框里面的提示文本列表显示A对应的列表arrayA，选中B、C、L时也同样
            } else if (this.alphabetIndexerArrayValue[index] == 'B') {
              return this.alphabetIndexerArrayB
            } else if (this.alphabetIndexerArrayValue[index] == 'C') {
              return this.alphabetIndexerArrayC
            } else if (this.alphabetIndexerArrayValue[index] == 'L') {
              return this.alphabetIndexerArrayL
            } else {
              return [] // 选中其余子母项时，提示文本列表为空
            }
          })
        }
      */


      /*** 以下为 Badge 示例 ***//*
      // 如果不设置position，默认是在右上显示红点
      Badge({
        value: '',
        style: { badgeSize: 16, badgeColor: '#FA2A2D' }
      }) {
        Image($r('app.media.ic_user_portrait'))
          .width(40)
          .height(40)
      }
      .width(40)
      .height(40)

      // 在右侧显示“New”
      Badge({
        value: 'New',
        position: BadgePosition.Right,
        style: { badgeSize: 16, badgeColor: '#FA2A2D' }
      }) {
        Text('我的消息').width(170).height(40).fontSize(40).fontColor('#182431')
      }.width(170).height(40)

      // 在右侧显示“数字
      Badge({
        value: '1',
        position: BadgePosition.Right,
        style: { badgeSize: 16, badgeColor: '#FA2A2D' }
      }) {
        Text('我的消息').width(170).height(40).fontSize(40).fontColor('#182431')
      }.width(170).height(40)
      */

      /*** 以下为 Counter 示例 ***//*
      Counter() {
        Text(this.counterValue.toString())
      }.margin(100)
      // 监听数值增加事件
      .onInc(() => {
        this.counterValue++
      })
      // 监听数值减少事件。
      .onDec(() => {
        this.counterValue--
      })
      */

      /*** 以下为 Panel 示例 ***//*
      Panel(true) { // 展示日程
        Column() {
          Text('今日历程').fontSize(20)

          Text('1、Java核心编程').fontSize(20)
          Text('2、轻量级Java EE企业应用开发实战').fontSize(20)
          Text('3、鸿蒙HarmonyOS手机应用开发实战').fontSize(20)
          Text('4、Node.js+Express+MongoDB+Vue.js全栈开发实战').fontSize(20)
        }
      }
      .type(PanelType.Foldable).mode(PanelMode.Half)
      .dragBar(true) // 默认开启
      .halfHeight(500) // 默认一半
      */

      /*** 以下为 Refresh 示例 ***//*
      Refresh({ refreshing: true, //当前组件是否正在刷新
        offset: 120, // 新组件静止时距离父组件顶部的距离
        friction: 100 }) { //下拉摩擦系数，取值范围为0到100。默认值是62
        Text('下拉刷新 ')
          .fontSize(30)
          .margin(10)
      }
      */

      /*** 以下为 RelativeContainer 示例 ***/
      /*
      RelativeContainer() {
        Row()
          .width(100)
          .height(100)
          .backgroundColor('#FF3333')
          .alignRules({
            top: { anchor: '__container__', align: VerticalAlign.Top },  //以父容器为锚点，竖直方向顶头对齐
            middle: { anchor: '__container__', align: HorizontalAlign.Center }  //以父容器为锚点，水平方向居中对齐
          })
          .id('row1')  //设置锚点为row1

        Row() {
          Image($r('app.media.icon'))
        }
        .height(100).width(100)
        .alignRules({
          top: { anchor: 'row1', align: VerticalAlign.Bottom },  //以row1组件为锚点，竖直方向底端对齐
          left: { anchor: 'row1', align: HorizontalAlign.Start }  //以row1组件为锚点，水平方向开头对齐
        })
        .id('row2')  //设置锚点为row2

        Row()
          .width(100)
          .height(100)
          .backgroundColor('#FFCC00')
          .alignRules({
            top: { anchor: 'row2', align: VerticalAlign.Top }
          })
          .id('row3')  //设置锚点为row3

        Row()
          .width(100)
          .height(100)
          .backgroundColor('#FF9966')
          .alignRules({
            top: { anchor: 'row2', align: VerticalAlign.Top },
            left: { anchor: 'row2', align: HorizontalAlign.End },
          })
          .id('row4')  //设置锚点为row4

        Row()
          .width(100)
          .height(100)
          .backgroundColor('#FF66FF')
          .alignRules({
            top: { anchor: 'row2', align: VerticalAlign.Bottom },
            middle: { anchor: 'row2', align: HorizontalAlign.Center }
          })
          .id('row5')  //设置锚点为row5
      }
      .width(300).height(300)
      .border({ width: 2, color: '#6699FF' })
      */

      /*** 以下为 Scroll 示例 ***/
      /*
      // 与Scroller绑定
      Scroll(new Scroller()) {
        Column() {
          ForEach(this.numberArray, (item) => {
            Text(item.toString())
              .width('90%')
              .height(250)
              .backgroundColor(0xFFFFFF)
              .borderRadius(15)
              .fontSize(26)
              .textAlign(TextAlign.Center)
              .margin({ top: 10 })
          }, item => item)
        }.width('100%')
      }
      .scrollable(ScrollDirection.Vertical)  // 滚动方向纵向
      .scrollBar(BarState.On)  // 滚动条常驻显示
      .scrollBarColor(Color.Gray)  // 滚动条颜色
      .scrollBarWidth(40) // 滚动条宽度
      .edgeEffect(EdgeEffect.None)
      .onScroll((xOffset: number, yOffset: number) => {
        console.info(xOffset + ' ' + yOffset)
      })
      .onScrollEdge((side: Edge) => {
        console.info('To the edge')
      })
      .onScrollEnd(() => {
        console.info('Scroll Stop')
      }).backgroundColor(0xDCDCDC)
      */

      /*** 以下为 SideBarContainer 示例 ***/
      /*
      SideBarContainer(SideBarContainerType.Embed) {
        Column() {
          Text('菜单1').fontSize(25)
          Text('菜单2').fontSize(25)
        }.width('100%')
        .justifyContent(FlexAlign.SpaceEvenly)
        .backgroundColor('#19000000')

        Column() {
          Text('内容1').fontSize(25)
          Text('内容2').fontSize(25)
        }
      }
      */

      /*** 以下为 Text 示例 ***/
      /*
      // 第一层组件
      Text('第一层')
        .width('90%')
        .height('100%')
        .backgroundColor(Color.Grey)
        .align(Alignment.Top)
        .fontSize(40)

      // 第二层组件
      Text('第二层')
        .width('70%')
        .height('60%')
        .backgroundColor(Color.Orange)
        .align(Alignment.Top)
        .fontSize(40)
      }.width('100%').height(400).margin({ top: 5 })
      */


      /*** 以下为 Swiper 示例 ***/
      /*
      Swiper() {
        Image($r('app.media.book01'))
          .width(280).height(380)
        Image($r('app.media.book02'))
          .width(280).height(380)
        Image($r('app.media.book03'))
          .width(280).height(380)
        Image($r('app.media.book04'))
          .width(280).height(380)
      }
      .cachedCount(2) // 设置预加载子组件个数。
      .index(1) //设置当前在容器中显示的子组件的索引值。
      .autoPlay(true) //子组件是否自动播放，自动播放状态下，导航点不可操作。
      .interval(4000)//使用自动播放时播放的时间间隔，单位为毫秒。
      .indicator(true) //是否启用导航点指示器。
      .loop(true)//是否开启循环。
      .duration(1000)//子组件切换的动画时长，单位为毫秒。
      .itemSpace(0) //设置子组件与子组件之间间隙。
      .curve(Curve.Linear) // 设置Swiper的动画曲线
      */

      /*** 以下为 Tabs和TabContent 示例 ***/
      Tabs({ barPosition: BarPosition.Start, //设置Tabs的页签位置。
        controller: new TabsController() //设置Tabs控制器。
      }) {
        TabContent() {
          Column().width('100%').height('100%').backgroundColor(Color.Orange)
        }.tabBar('首页')

        TabContent() {
          Column().width('100%').height('100%').backgroundColor(Color.Blue)
        }.tabBar('商城')

        TabContent() {
          Column().width('100%').height('100%').backgroundColor(Color.Red)
        }.tabBar('直播')
      }
      .vertical(false) //设置为false是为横向Tabs，设置为true时为纵向Tabs。
      .barMode(BarMode.Fixed) // TabBar布局模式
      .barWidth(360) // TabBar的宽度值
      .barHeight(56) // TabBar的高度值
      .animationDuration(400) //TabContent滑动动画时长
      .width(360)
      .height(296)
      .margin({ top: 52 })

    }
    .height('100%')
  }

}