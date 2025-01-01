import AccountTable from '../common/database/tables/AccountTable';
import AccountData from '../viewmodel/AccountData';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'
  private accountTable = new AccountTable();

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
            let newAccount: AccountData = { id: 1, accountType: 0, typeText: '苹果', amount: 0 };
            this.accountTable.insertData(newAccount, () => {
            })
          })

        // 查询
        Button(('查询'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            this.accountTable.query(0, (result: AccountData[]) => {
              this.message = JSON.stringify(result);
            }, true);
          })

        // 修改
        Button(('修改'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            let newAccount: AccountData = { id: 1, accountType: 1, typeText: '栗子', amount: 1 };
            this.accountTable.updateData(newAccount, () => {
            })
          })

        // 删除
        Button(('删除'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            let newAccount: AccountData = { id: 1, accountType: 1, typeText: '栗子', amount: 1 };
            this.accountTable.deleteData(newAccount, () => {
            })
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}