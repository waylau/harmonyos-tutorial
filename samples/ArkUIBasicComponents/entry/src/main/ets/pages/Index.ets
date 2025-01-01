// HTTP请求网络图片需要导入的包
import http from '@ohos.net.http';

import image from '@ohos.multimedia.image';

// Web组件控制器需要导入的包
import web_webview from '@ohos.web.webview'

@Entry
@Component
struct Index {
  private dataPanelValues: number[] = [11, 3, 10, 2, 36, 4, 7, 22, 5]

  // 先创建一个PixelMap状态变量用于接收网络图片
  @State imagePixelMap: image.PixelMap = null!;

  // 初始动画状态
  @State animationStatus: AnimationStatus = AnimationStatus.Initial

  // 可滚动组件的控制器
  private scroller: Scroller = new Scroller()

  private dataScroller: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

  private webviewController: web_webview.WebviewController = new web_webview.WebviewController()

  // TextTimer组件的控制器
  private textTimerController: TextTimerController = new TextTimerController()

  private tooTmpOne: ToolbarItem = {'value': "首页",
    'icon': $r('app.media.house'),
    'action': ()=> {}
  }
  private tooTmpTwo: ToolbarItem = {'value': "好友",
    'icon': $r('app.media.person_2'),
    'action': ()=> {}
  }
  private tooTmpThree: ToolbarItem = {'value': "我的",
    'icon': $r('app.media.gearshape'),
    'action': ()=> {}
  }
  private toolbarConfig: ToolbarItem[] = [this.tooTmpOne, this.tooTmpTwo, this.tooTmpThree];

  build() {
    Column() {
      /*** 以下为 Blank示例 ***/
      // Blank父组件Row未设置宽度时，子组件间无空白填充
      /*
      Row() {
        Text('Left Space').fontSize(24)
        Blank()
        Text('Right Space').fontSize(24)
      }

      // Blank父组件Row设置了宽度时，子组件间以空白填充
      Row() {
        Text('Left Space').fontSize(24)
        Blank()
        Text('Right Space').fontSize(24)
      }.width('100%')

      // Blank组件设置了颜色
      Row() {
        Text('Left Space').fontSize(24)

        // 设置空白填充的填充颜色
        Blank().color(Color.Yellow)

        Text('Right Space').fontSize(24)
      }.width('100%')
      */

      /*** 以下为 Button示例 ***/
      /*
      Row() {
        // 一个基本的按钮，设置要显示的文字
        Button('01')

        // 设置边框半径、背景色、宽度
        Button('02').borderRadius(8).backgroundColor(0x317aff).width(90)
      }

      Row() {
        // 胶囊型按钮（圆角默认为高度的一半）
        Button('03', { type: ButtonType.Capsule }).width(90)

        // 圆形按钮。
        Button('04', { type: ButtonType.Circle}).width(90)

        // 普通按钮（默认不带圆角）。
        Button('05', { type: ButtonType.Normal}).width(90)
      }

      Row() {
        // 可以包含子组件。文字就用Text组件来显示
        Button({ type: ButtonType.Capsule, stateEffect: true }) {
          Row() {
            LoadingProgress().width(20).height(20).margin({ left: 12 }).color(0xFFFFFF)
            Text('06').fontSize(12).fontColor(0xffffff).margin({ left: 5, right: 12 })
          }.alignItems(VerticalAlign.Center).width(90).height(40)
        }.backgroundColor(0x317aff)
      }
      */


      /*** 以下为 Checkbox 示例 ***/
      /*
      Row() {
        Checkbox({ name: 'checkbox1', group: 'checkboxGroup' })
          .select(true) // 设置默认选中
          .selectedColor(0xed6f21) // 设置选中颜色
          .onChange((value: boolean) => { // 设置选中事件
            console.info('Checkbox1 change is ' + value)
          })

        Checkbox({ name: 'checkbox2', group: 'checkboxGroup' })
          .select(false)
          .selectedColor(0x39a2db)
          .onChange((value: boolean) => {
            console.info('Checkbox2 change is ' + value)
          })
      }
      */

      /*** 以下为 CheckboxGroup 示例 ***/
      /*
      Row() {
        CheckboxGroup({ group: 'checkboxGroup' })
        Text('全要').fontSize(20)
      }

      Row() {
        Checkbox({ name: 'checkbox1', group: 'checkboxGroup' })
        Text('可乐').fontSize(20)
      }

      Row() {
        Checkbox({ name: 'checkbox2', group: 'checkboxGroup' })
        Text('鸡翅').fontSize(20)
      }
      */

      /*** 以下为 DataPanel 示例 ***/
      // 环形数据面板
      /*
      DataPanel({ values: this.dataPanelValues, max: 100, type: DataPanelType.Circle })

        .width(350)
        .height(350)

      // 线型数据面板
      DataPanel({ values: this.dataPanelValues, max: 100, type: DataPanelType.Line })
        .width(350)
        .height(50)
       */

      /*** 以下为 DatePicker 示例 ***/
      /*// 不设置参数就取默认值
      DatePicker()

      // 三个构造参数
      DatePicker({
        start: new Date('1970-1-1'), //指定选择器的起始日期。 默认值：Date('1970-1-1')
        end: new Date('2100-1-1'), // 指定选择器的结束日期。 默认值：Date('2100-12-31')
        selected: new Date('2023-02-15'), // 设置选中项的日期。默认值：当前系统日期
      })

      // 设置农历和事件监听
      DatePicker({
        start: new Date('1970-1-1'), //指定选择器的起始日期。 默认值：Date('1970-1-1')
        end: new Date('2100-1-1'), // 指定选择器的结束日期。 默认值：Date('2100-12-31')
        selected: new Date('2023-02-15'), // 设置选中项的日期。默认值：当前系统日期
      }).lunar(true) // 设置农历
        .onDateChange((value: Date) => { //选择日期时触发该事件
          console.info('select current date is: ' + value)
        })*/

      /*** 以下为 Divider 示例 ***/

      /*Text('我是天').fontSize(29)
      Divider()
      Text('我是地').fontSize(29)*/


      /*Text('我是天').fontSize(29)
      // 设置垂直
      Divider().vertical(true).height(100)
      Text('我是地').fontSize(29)*/

      /*Text('我是天').fontSize(29)
      // 设置样式
      Divider()
        .strokeWidth(15)  // 宽度
        .color(0x2788D9) // 颜色
        .lineCap(LineCapStyle.Round) // 端点样式
      Text('我是地').fontSize(29)*/

      /*** 以下为 Gauge 示例 ***/

      // value值的设置，是使用默认的min和max为0-100，角度范围默认0-360
      // 参数中设置当前值为75
      /*Gauge({ value: 75 })
        .width(200).height(200)
        // 设置量规图的颜色，支持分段颜色设置。
        .colors([[0x317AF7, 1], [0x5BA854, 1], [0xE08C3A, 1], [0x9C554B, 1]])*/

      // 参数设置当前值为75，属性设置值为25，属性设置优先级高
      /*Gauge({ value: 75 })
        .value(25) //属性和参数都设置时以属性为准
        .width(200).height(200)
        .colors([[0x317AF7, 1], [0x5BA854, 1], [0xE08C3A, 1], [0x9C554B, 1]])*/

      // 210--150度环形图表
      /*Gauge({ value: 70 })
        .startAngle(210) // 起始角度
        .endAngle(150) // 终止角度
        .colors([[0x317AF7, 0.1], [0x5BA854, 0.2], [0xE08C3A, 0.3], [0x9C554B, 0.4]])
        .strokeWidth(20) // 环形厚度
        .width(200)
        .height(200)*/

      /*** 以下为 Image 示例 ***/
      // 使用本地图片的示例
      // 图片资源在base/media目录下
      /*Image($r('app.media.waylau_181_181'))
        .width(180).height(180)*/

      // 使用网络图片的示例
      /*Button("获取网络图片")
        .onClick(() => {
          // 请求网络资源
          this.httpRequest();
        })
      Image(this.imagePixelMap).width(207).height(281)*/

      /*** 以下为 ImageAnimator 示例 ***/
      /*// 按钮控制动画的播放和暂停
      Button('播放').width(100).padding(5).onClick(() => {
        this.animationStatus = AnimationStatus.Running
      }).margin(5)
      Button('暂停').width(100).padding(5).onClick(() => {
        this.animationStatus = AnimationStatus.Paused
      }).margin(5)

      // images设置设置图片帧信息集合。
      // 每一帧的帧信息(ImageFrameInfo)包含图片路径、图片大小、图片位置和图片播放时长信息，
      ImageAnimator()
        .images([
          {
            src: $r('app.media.book01'), //图片路径
            duration: 500, //播放时长
            width: 240, //图片大小
            height: 350,
            top: 0, //图片位置
            left: 0
          },
          {
            src: $r('app.media.book02'),
            duration: 500,
            width: 240,
            height: 350,
            top: 0,
            left: 170
          },
          {
            src: $r('app.media.book03'),
            duration: 500,
            width: 240,
            height: 350,
            top: 120,
            left: 170
          },
          {
            src: $r('app.media.book04'),
            duration: 500,
            width: 240,
            height: 350,
            top: 120,
            left: 0
          }
        ])
        .state(this.animationStatus)
        .reverse(false) //是否逆序播放
        .fixedSize(false) //是否固定大小
        .iterations(-1) //循环播放次数
        .width(240)
        .height(350)
        .margin({ top: 100 })*/

      /*** 以下为 LoadingProgress 示例 ***/
      /*// 显示加载动效
      LoadingProgress()
        .color(Color.Red) //设置为红色*/

      /*** 以下为 Marquee 示例 ***/

      /*// 文本内容宽度未超过跑马灯组件宽度，不滚动。
      Marquee({
        start: true, // 控制跑马灯是否进入播放状态。
        step: 12, // 滚动动画文本滚动步长。默认值：6，单位vp
        loop: -1, // 循环次数，-1为无限循环
        fromStart: true, // 设置文本从头开始滚动或反向滚动
        src: "HarmonyOS也称为鸿蒙系统"
      }).fontSize(20)

      // 文本内容宽度超过了跑马灯组件宽度，滚动。
      Marquee({
        start: true, // 控制跑马灯是否进入播放状态。
        step: 12, // 滚动动画文本滚动步长。默认值：6，单位vp
        loop: -1, // 循环次数，-1为无限循环
        fromStart: true, // 设置文本从头开始滚动或反向滚动
        src: "在传统的单设备系统能力基础上，HarmonyOS提出了基于同一套系统能力、适配多种终端形态的分布式理念。"
      }).fontSize(20)*/

      /*** 以下为 Navigation 示例 ***/
      /*Navigation() {
        Flex() {
        }
      }
      .toolbarConfiguration(this.toolbarConfig); // 使用自定义属性*/

      /*** 以下为 PatternLock 示例 ***/
      /*PatternLock()
        .sideLength(200)//设置组件的宽度和高度（宽高相同）
        .circleRadius(9)//设置宫格中圆点的半径。
        .pathStrokeWidth(18)//设置连线的宽度。设置为0或负数等非法值时连线不显示。
        .activeColor('#B0C4DE')//设置宫格圆点在“激活”状态的填充颜色
        .selectedColor('#228B22')//设置宫格圆点在“选中”状态的填充颜色。
        .pathColor('#90EE90')//设置连线的颜色。
        .backgroundColor('#F5F5F5')//背景颜色
        .autoReset(true)//设置在完成密码输入后再次在组件区域按下时是否重置组件状态。*/

      /*** 以下为 Progress 示例 ***/
      /*Progress({ value: 20, total: 100, type: ProgressType.Linear })
        .width(150).margin({ top: 10 })
      Progress({ value: 20, total: 100, type: ProgressType.Ring })
        .width(150).margin({ top: 10 })
      Progress({ value: 20, total: 100, type: ProgressType.Eclipse })
        .width(150).margin({ top: 10 })
      Progress({ value: 20, total: 100, type: ProgressType.ScaleRing })
        .width(150).margin({ top: 10 })
      Progress({ value: 20, total: 100, type: ProgressType.Capsule })
        .width(40).margin({ top: 10 })*/

      /*** 以下为 QRCode 示例 ***/
      /*QRCode("https://waylau.comn")
        .width(360).height(360) // 大小
        .backgroundColor(Color.Orange)// 颜色*/

      /*** 以下为 Radio 示例 ***/

      /*Radio({ value: 'Radio1', group: 'radioGroup' })
        .checked(false) //默认不选中
        .height(50)
        .width(50)
      Radio({ value: 'Radio2', group: 'radioGroup' })
        .checked(true) //默认选中
        .height(50)
        .width(50)
      Radio({ value: 'Radio2', group: 'radioGroup' })
        .checked(false) //默认不选中
        .height(50)
        .width(50)*/

      /*** 以下为 Rating 示例 ***/

      // 设置初始星数为1，可以操作
      /*Rating({ rating: 1, indicator: false })
        .stars(5) // 设置评星总数。默认值：5
        .stepSize(0.5) // 操作评级的步长。默认值：0.5
        .onChange((value: number) => {
          //...
        })*/

      /*** 以下为 RichText 示例 ***/

      /*RichText('<h1 style="text-align: center;">h1标题</h1>' +
      '<h1 style="text-align: center;"><i>h1斜体</i></h1>' +
      '<h1 style="text-align: center;"><u>h1下划线</u></h1>' +
      '<h2 style="text-align: center;">h2标题</h2>' +
      '<h3 style="text-align: center;">h3标题</h3>' +
      '<p style="text-align: center;">p常规</p><hr/>' +
      '<div style="width: 500px;height: 500px;border: 1px solid;margin: 0auto;">' +
      '<p style="font-size: 35px;text-align: center;font-weight: bold; color: rgb(24,78,228)">' +
        '字体大小35px,行高45px' +
        '</p>' +
      '<p style="background-color: #e5e5e5;line-height: 45px;font-size: 35px;text-indent: 2em;">' +
      '<p>这是一段文字这是一段文字这是一段文字这是一段文字这是一段文字这是一段文字这是一段文字这是一段文字这是一段文字</p>')
      */
      /*** 以下为 Stack 示例 ***/
      /*Stack({ alignContent: Alignment.End }) {
        // 定义了可滚动组件Scroll
        Scroll(this.scroller) {
          Flex({ direction: FlexDirection.Column }) {
            ForEach(this.dataScroller, (item: number) => {
              Row() {
                Text(item.toString())
                  .width('90%')
                  .height(100)
                  .backgroundColor('#3366CC')
                  .borderRadius(15)
                  .fontSize(16)
                  .textAlign(TextAlign.Center)
                  .margin({ top: 5 })
              }
            })
          }.margin({ left: 52 })
        }
        .scrollBar(BarState.Off)
        .scrollable(ScrollDirection.Vertical)

        // 定义了滚动条组件ScrollBar
        ScrollBar({ scroller: this.scroller,
          direction: ScrollBarDirection.Vertical,
          state: BarState.Auto }) {
          // 定义Text作为滚动条的样式
          Text()
            .width(30)
            .height(100)
            .borderRadius(10)
            .backgroundColor('#C0C0C0')
        }.width(30).backgroundColor('#ededed')
      }*/

      /*** 以下为 Search 示例 ***/
      /*Search({ placeholder: '输入内容...'})
        .searchButton('搜索') // 搜索按钮的文字
        .width(300)
        .height(80)
        .placeholderColor(Color.Grey) // 提示文本样式
        .placeholderFont({ size: 24, weight: 400 }) // 提示文本字体大小
        .textFont({ size: 24, weight: 400 }) // 搜索框文字字体大小*/


      /*** 以下为 Select 示例 ***/
      // 设置下来列表值和图标
      /*Select([{ value: 'Java核心编程', icon: $r('app.media.book01') },
        { value: '轻量级Java EE企业应用开发实战', icon: $r('app.media.book02') },
        { value: '鸿蒙HarmonyOS手机应用开发实战', icon: $r('app.media.book03') },
        { value: 'Node.js+Express+MongoDB+Vue.js全栈开发实战', icon: $r('app.media.book04') }])
        .selected(2) // 选择的下来列表索引
        .value('老卫作品集') // 下拉按钮本身的文本内容。
        .font({ size: 16, weight: 500 }) // 下拉按钮本身的文本样式。
        .fontColor('#182431') // 下拉按钮本身的文本颜色。
        .selectedOptionFont({ size: 16, weight: 400 }) // 下拉菜单选中项的文本样式。
        .optionFont({ size: 16, weight: 400 }) // 下拉菜单项的文本样式。*/

      /*** 以下为 Slider 示例 ***/
      /*// 设置垂直的Slider
      Slider({
        value: 40,
        step: 10,
        style: SliderStyle.InSet, // 滑块在滑轨上
        direction: Axis.Vertical // 方向
      })
        .showSteps(true) // 设置显示步长刻度值
        .height('50%')

      // 设置水平的Slider
      Slider({
        value: 40,
        min: 0,
        max: 100,
        style: SliderStyle.OutSet //滑块在滑轨内
      })
        .blockColor('#191970') // 设置滑块的颜色
        .trackColor('#ADD8E6') // 设置滑轨的背景颜色
        .selectedColor('#4169E1') // 设置滑轨的已滑动部分颜色
        .showTips(true) // 设置气泡提示
        .width('50%')*/

      /*** 以下为 Span 示例 ***/
      /*// 文本添加横线
      Text() {
        Span('文本添加横线').decoration({ type: TextDecorationType.Underline, color: Color.Red }).fontSize(24)
      }

      // 文本添加划掉线
      Text() {
        Span('文本添加划掉线')
          .decoration({ type: TextDecorationType.LineThrough, color: Color.Red })
          .fontSize(24)
      }

      // 文本添加上划线
      Text() {
        Span('文本添加上划线').decoration({ type: TextDecorationType.Overline, color: Color.Red }).fontSize(24)
      }

      // 文本字符间距
      Text() {
        Span('文本字符间距')
          .letterSpacing(10)
          .fontSize(24)
      }

      // 文本转化为小写LowerCase
      Text() {
        Span('文本转化为小写LowerCase').fontSize(24)
          .textCase(TextCase.LowerCase)
          .decoration({ type: TextDecorationType.None })
      }

      // 文本转化为大写UpperCase
      Text() {
        Span('文本转化为小写UpperCase').fontSize(24)
          .textCase(TextCase.UpperCase)
          .decoration({ type: TextDecorationType.None })
      }*/

      /*** 以下为 Stepper 示例 ***/
      /*Stepper({
        // 设置步骤导航器当前显示StepperItem的索引值。
        index: 0
      }) {
        // 第1页
        StepperItem() {
          Text('第1页').fontSize(34)
        }
        .nextLabel('下一页')

        // 第2页
        StepperItem() {
          Text('第2页').fontSize(34)
        }
        .nextLabel('下一页')
        .prevLabel('上一页')

        // 第3页
        StepperItem() {
          Text('第3页').fontSize(34)
        }
        .prevLabel('上一页')

      }*/

      /*** 以下为 Text 示例 ***/
      /*// 单行文本
      // 红色单行文本居中
      Text('红色单行文本居中').fontSize(24)
        .fontColor(Color.Red) // 红色
        .textAlign(TextAlign.Center) // 居中
        .width('100%')

      // 单行文本对齐左侧
      Text('单行文本对齐左侧').fontSize(24)
        .textAlign(TextAlign.Start) // 对齐左侧
        .width('100%')

      // 单行文本带边框对齐右侧
      Text('单行文本带边框对齐右侧')
        .fontSize(24)
        .textAlign(TextAlign.End) // 对齐右侧
        .border({ width: 1 }) // 边宽
        .padding(10)
        .width('100%')

      // 多行文本
      // 超出maxLines截断内容展示
      Text('寒雨连江夜入吴，平明送客楚山孤。洛阳亲友如相问，一片冰心在玉壶。')
        .textOverflow({ overflow: TextOverflow.None }) // 超出截断内容
        .maxLines(2) //　最多显示２行
        .fontSize(24)
        .border({ width: 1 })
        .padding(10)

      // 超出maxLines展示省略号
      Text('寒雨连江夜入吴，平明送客楚山孤。洛阳亲友如相问，一片冰心在玉壶。')
        .textOverflow({ overflow: TextOverflow.Ellipsis }) // 超出展示省略号
        .maxLines(2)
        .fontSize(24)
        .border({ width: 1 })
        .padding(10)

      // 设置行高
      Text('寒雨连江夜入吴，平明送客楚山孤。洛阳亲友如相问，一片冰心在玉壶。')
        .textOverflow({ overflow: TextOverflow.Ellipsis }) // 超出展示省略号
        .maxLines(2)
        .fontSize(24)
        .border({ width: 1 })
        .padding(10)
        .lineHeight(50) // 设置文本的文本行高*/

      /*** 以下为 TextArea 示例 ***/
      /*TextArea({
        // 设置无输入时的提示文本
        placeholder: '寒雨连江夜入吴，平明送客楚山孤。洛阳亲友如相问，一片冰心在玉壶。'
      })
        .placeholderFont({ size: 24, weight: 400 })  // 设置placeholder文本样式
        .width(336)
        .height(100)
        .margin(20)
        .fontSize(16)
        .fontColor('#182431')
        .backgroundColor('#FFFFFF')*/

      /*** 以下为 TextClock 示例 ***/
      // 普通的TextClock示例
      /*TextClock().margin(20).fontSize(30)

      // 带日期格式化的TextClock示例
      TextClock().margin(20).fontSize(30)
        .format('yyyyMMdd hh:mm:ss') // 日期格式化*/

      /*** 以下为 TextInput 示例 ***/
      /*// 文本输入框
      TextInput({ placeholder: '请输入...'}) // 设置无输入时的提示文本
        .placeholderColor(Color.Grey) // 设置placeholder文本颜色
        .placeholderFont({ size: 14, weight: 400 }) // 设置placeholder文本样式
        .caretColor(Color.Blue) // 设置输入框光标颜色
        .width(300)
        .height(40)
        .margin(20)
        .fontSize(24)
        .fontColor(Color.Black)

      // 密码输入框
      TextInput({ placeholder: '请输入密码...' })
        .width(300)
        .height(40)
        .margin(20)
        .fontSize(24)
        .type(InputType.Password) // 密码类型
        .maxLength(9) // 	设置文本的最大输入字符数
        .showPasswordIcon(true) // 输入框末尾的图标显示*/

      /*** 以下为 TextPicker 示例 ***/
      /*TextPicker({
        // 选择器的数据选择列表
        range: ['Java核心编程', '轻量级Java EE企业应用开发实战', '鸿蒙HarmonyOS手机应用开发实战', 'Node.js+Express+MongoDB+Vue.js全栈开发实战'],
        // 设置默认选中项在数组中的索引值。默认值是0
        selected: 1
      }).defaultPickerItemHeight(30)*/

      /*** 以下为 TextTimer 示例 ***/
      // 定义TextTimer组件
      /*TextTimer({ controller: this.textTimerController,
        isCountDown: true, // 是否倒计时。默认值false
        count: 30000 }) // 倒计时时间，单位为毫秒
        .format('mm:ss.SS') // 格式化
        .fontColor(Color.Black) // 字体颜色
        .fontSize(50) // 字体大小

      // 控制按钮
      Row() {
        Button("开始").onClick(() => {
          this.textTimerController.start()
        })
        Button("暂停").onClick(() => {
          this.textTimerController.pause()
        })
        Button("重置").onClick(() => {
          this.textTimerController.reset()
        })
      }*/

      /*** 以下为 TimePicker 示例 ***/
      /*TimePicker()
        .useMilitaryTime(true) // 设置为24小时制*/

      /*** 以下为 Toggle 示例 ***/
      /*// 关闭的Switch类型
      Toggle({ type: ToggleType.Switch, isOn: false })
        .size({ width: 40, height: 40 }) // 设置大小
        .selectedColor('#007DFF') // 设置组件打开状态的背景颜色
        .switchPointColor('#FFFFFF') // 设置Switch类型的圆形滑块颜色

      // 打开的Switch类型
      Toggle({ type: ToggleType.Switch, isOn: true })
        .size({ width: 40, height: 40 }) // 设置大小
        .selectedColor('#007DFF') // 设置组件打开状态的背景颜色
        .switchPointColor('#FFFFFF') // 设置Switch类型的圆形滑块颜色

      // 关闭的Checkbox类型
      Toggle({ type: ToggleType.Checkbox, isOn: false })
        .size({ width: 40, height: 40 }) // 设置大小
        .selectedColor('#007DFF') // 设置组件打开状态的背景颜色

      // 打开的Checkbox类型
      Toggle({ type: ToggleType.Checkbox, isOn: true })
        .size({ width: 40, height: 40 }) // 设置大小
        .selectedColor('#007DFF') // 设置组件打开状态的背景颜色

      // 关闭的Button类型
      Toggle({ type: ToggleType.Button, isOn: false })
        .size({ width: 40, height: 40 }) // 设置大小
        .selectedColor('#007DFF') // 设置组件打开状态的背景颜色

      // 打开的Button类型
      Toggle({ type: ToggleType.Button, isOn: true })
        .size({ width: 40, height: 40 }) // 设置大小
        .selectedColor('#007DFF') // 设置组件打开状态的背景颜色*/

      /*** 以下为 Web 示例 ***/
      //Web({ src: 'https://waylau.com', controller: this.webviewController })

      /*** 以下为 SymbolGlyph 示例 ***/
      Row() {
        Column() {
          Text("Light")
          SymbolGlyph($r('sys.symbol.ohos_trash'))
            .fontWeight(FontWeight.Lighter)
            .fontSize(96)
        }

        Column() {
          Text("Normal")
          SymbolGlyph($r('sys.symbol.ohos_trash'))
            .fontWeight(FontWeight.Normal)
            .fontSize(96)
        }

        Column() {
          Text("Bold")
          SymbolGlyph($r('sys.symbol.ohos_trash'))
            .fontWeight(FontWeight.Bold)
            .fontSize(96)
        }
      }

      Row() {
        Column() {
          Text("单色")
          SymbolGlyph($r('sys.symbol.ohos_folder_badge_plus'))
            .fontSize(96)
            .renderingStrategy(SymbolRenderingStrategy.SINGLE)
            .fontColor([Color.Black, Color.Green, Color.White])
        }

        Column() {
          Text("多色")
          SymbolGlyph($r('sys.symbol.ohos_folder_badge_plus'))
            .fontSize(96)
            .renderingStrategy(SymbolRenderingStrategy.MULTIPLE_COLOR)
            .fontColor([Color.Black, Color.Green, Color.White])
        }

        Column() {
          Text("分层")
          SymbolGlyph($r('sys.symbol.ohos_folder_badge_plus'))
            .fontSize(96)
            .renderingStrategy(SymbolRenderingStrategy.MULTIPLE_OPACITY)
            .fontColor([Color.Black, Color.Green, Color.White])
        }
      }

      Row() {
        Column() {
          Text("无动效")
          SymbolGlyph($r('sys.symbol.ohos_wifi'))
            .fontSize(96)
            .effectStrategy(SymbolEffectStrategy.NONE)
        }

        Column() {
          Text("整体缩放动效")
          SymbolGlyph($r('sys.symbol.ohos_wifi'))
            .fontSize(96)
            .effectStrategy(1)
        }

        Column() {
          Text("层级动效")
          SymbolGlyph($r('sys.symbol.ohos_wifi'))
            .fontSize(96)
            .effectStrategy(2)
        }
      }
    }
    .height('100%')
  }

  // 自定义一个Toolbar组件
  @Builder NavigationToolbar() {
    Row() {
      Text("首页").fontSize(25).margin({ left: 70 })
      Text("+").fontSize(25).margin({ left: 70 })
      Text("我").fontSize(25).margin({ left: 70 })
    }
  }

  // 网络图片请求方法
  private httpRequest() {
    let httpRequest = http.createHttp();

    httpRequest.request(
      "https://waylau.com/images/showmethemoney-sm.jpg", // 网络图片地址
      (error, data) => {
        if (error) {
          console.log("error code: " + error.code + ", msg: " + error.message)
        } else {
          let code = data.responseCode
          if (http.ResponseCode.OK == code) {
            let resultArrayBuffer = data.result as ArrayBuffer

            let imageSource = image.createImageSource(resultArrayBuffer);
            let options: image.InitializationOptions =
              { editable: true,
                pixelFormat: 3,
                size:
                { height: 281,
                  width: 207
                }
              }
            imageSource.createPixelMap(options).then((pixelMap) => {
              this.imagePixelMap = pixelMap
            })
          } else {
            console.log("response code: " + code);
          }
        }
      }
    )
  }
}