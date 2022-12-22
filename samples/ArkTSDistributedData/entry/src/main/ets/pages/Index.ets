// 导入DistributedDataUtil
import DistributedDataUtil from '../common/DistributedDataUtil';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'

  build() {
    Row() {
      Column() {
        Text(this.message)
          .fontSize(50)
          .fontWeight(FontWeight.Bold)

        // 增加
        Button(('增加'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            DistributedDataUtil.addData()
          })

        // 查询
        Button(('查询'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            DistributedDataUtil.queryData()
          })

        // 修改
        Button(('修改'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            DistributedDataUtil.updateData()
          })

        // 删除
        Button(('删除'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            DistributedDataUtil.deleteData()
          })
      }
      .width('100%')
    }
    .height('100%')
  }

}