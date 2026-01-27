@Entry
@Component
struct Index {
  @State isActive: boolean = true;

  @State triggerValueReplace: number = 0;

  build() {
    Column() {
      // SymbolGlyph通过$r引用Resource资源来创建，目前仅支持系统预置的Symbol资源名。
      // 通过fontSize属性设置SymbolGlyph的大小。
      // 通过fontColor属性设置SymbolGlyph的颜色。
      Row() {
        SymbolGlyph($r('sys.symbol.ohos_folder_badge_plus'))
          .fontSize(48)
          .fontColor([Color.Black])

        SymbolGlyph($r('sys.symbol.ohos_folder_badge_plus'))
          .fontSize(72)
          .fontColor([Color.Green])

        SymbolGlyph($r('sys.symbol.ohos_folder_badge_plus'))
          .fontSize(96)
          .fontColor([Color.Pink])
      }

      // SymbolSpan可作为Text的子组件用于显示图标小符号。
      // 可以在一个Text组件内添加多个SymbolSpan，从而展示一串连续的图标。
      // 通过fontSize属性设置SymbolSpan的大小。
      // 通过fontColor属性设置SymbolSpan的颜色。
      Row() {
        Text() {
          SymbolSpan($r('sys.symbol.ohos_folder_badge_plus'))
            .fontSize(48)
            .fontColor([Color.Black])

          SymbolSpan($r('sys.symbol.ohos_folder_badge_plus'))
            .fontSize(72)
            .fontColor([Color.Green])

          SymbolSpan($r('sys.symbol.ohos_folder_badge_plus'))
            .fontSize(96)
            .fontColor([Color.Pink])
        }
      }

      // 通过fontWeight属性设置SymbolSpan组件的粗细。
      Row() {
        Column() {
          Text("Lighter")
          Text() {
            SymbolSpan($r('sys.symbol.ohos_trash'))
              .fontWeight(FontWeight.Lighter)
              .fontSize(96)
          }
        }

        Column() {
          Text("Normal")
          Text() {
            SymbolSpan($r('sys.symbol.ohos_trash'))
              .fontWeight(FontWeight.Normal)
              .fontSize(96)
          }
        }

        Column() {
          Text("Bold")
          Text() {
            SymbolSpan($r('sys.symbol.ohos_trash'))
              .fontWeight(FontWeight.Bold)
              .fontSize(96)
          }
        }
      }

      // 通过renderingStrategy属性设置SymbolSpan的渲染策略。
      Row() {
        Column() {
          Text("单色")
          Text() {
            SymbolSpan($r('sys.symbol.ohos_folder_badge_plus'))
              .fontSize(96)
              .renderingStrategy(SymbolRenderingStrategy.SINGLE)
              .fontColor([Color.Black, Color.Green, Color.White])
          }
        }

        Column() {
          Text("多色")
          Text() {
            SymbolSpan($r('sys.symbol.ohos_folder_badge_plus'))
              .fontSize(96)
              .renderingStrategy(SymbolRenderingStrategy.MULTIPLE_COLOR)
              .fontColor([Color.Black, Color.Green, Color.White])
          }
        }

        Column() {
          Text("分层")
          Text() {
            SymbolSpan($r('sys.symbol.ohos_folder_badge_plus'))
              .fontSize(96)
              .renderingStrategy(SymbolRenderingStrategy.MULTIPLE_OPACITY)
              .fontColor([Color.Black, Color.Green, Color.White])
          }
        }
      }

      // 通过effectStrategy属性设置SymbolSpan的动效策略。
      Row() {
        Column() {
          Text("无动效")
          Text() {
            SymbolSpan($r('sys.symbol.ohos_wifi'))
              .fontSize(96)
              .effectStrategy(SymbolEffectStrategy.NONE)
          }
        }

        Column() {
          Text("整体缩放动效")
          Text() {
            SymbolSpan($r('sys.symbol.ohos_wifi'))
              .fontSize(96)
              .effectStrategy(SymbolEffectStrategy.SCALE)
          }
        }

        Column() {
          Text("层级动效")
          Text() {
            SymbolSpan($r('sys.symbol.ohos_wifi'))
              .fontSize(96)
              .effectStrategy(SymbolEffectStrategy.HIERARCHICAL)
          }
        }
      }

      // 自定义图标动效
      Row() {
        // 通过设置symbolEffect属性，可以同时配置SymbolGlyph的动效策略及其播放状态。
        Column() {
          Text("可变颜色动效")

          SymbolGlyph($r('sys.symbol.ohos_wifi'))
            .fontSize(96)
            .symbolEffect(new HierarchicalSymbolEffect(EffectFillStyle.ITERATIVE), this.isActive)

          Button(this.isActive ? '关闭' : '播放').onClick(() => {
            this.isActive = !this.isActive;
          })
        }

        // 通过设置symbolEffect属性，可以同时指定SymbolGlyph的动画效果策略及其播放触发条件。
        Column() {
          Text("弹跳动效")
          SymbolGlyph($r('sys.symbol.ellipsis_message_1'))
            .fontSize(96)
            .fontColor([Color.Gray])
            .symbolEffect(new BounceSymbolEffect(EffectScope.WHOLE, EffectDirection.UP), this.triggerValueReplace)

          Button('trigger').onClick(() => {
            this.triggerValueReplace = this.triggerValueReplace + 1;
          })
        }
      }
    }
    .height('100%')
    .width('100%')
  }
}