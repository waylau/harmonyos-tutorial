@Entry
@Component
struct Index {
  @State message: string = '画龙迎春，“码”上“鸿”福到';
  @State fillColor: Color = Color.Black;

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(26)
          .fontWeight(FontWeight.Bold)
        Image($r('app.media.chineseloong'))
          .height(390)
          .width(330)
          .fillColor(this.fillColor)
        Button('画龙')
          .onClick(()=>{
            // 点击变化颜色
            if (this.fillColor == Color.Black) {
              this.fillColor = Color.Red;
            } else if (this.fillColor == Color.Red) {
            this.fillColor = Color.Blue;
            }else if (this.fillColor == Color.Blue) {
              this.fillColor = Color.Orange;
            }else if (this.fillColor == Color.Orange) {
              this.fillColor = Color.Pink;
            }else if (this.fillColor == Color.Pink) {
              this.fillColor = Color.Black;
            }
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}


