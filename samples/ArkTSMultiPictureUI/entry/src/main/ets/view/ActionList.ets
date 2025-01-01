/**
 * 操作栏
 */
@Component
export struct ActionList {

  build() {
    Column(){
        Text('操作栏')
          .fontSize('24fp')
    }
    .height('52vp')
    .borderColor(Color.Black)
    .borderWidth('2vp')
  }
}