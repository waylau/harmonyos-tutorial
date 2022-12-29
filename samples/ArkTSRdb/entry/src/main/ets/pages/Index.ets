// 导入AccountData
import AccountData from '../database/AccountData';
// 导入AccountTable
import AccountTable from '../database/AccountTable';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'
  private accountTable = new AccountTable();

  aboutToAppear() {

    // 初始化数据库
    this.accountTable.getRdbStore(() => {
      this.accountTable.query(0, (result) => {
        this.message = result;
      }, true);
    });
  }

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
            let newAccount: AccountData = { id: 0, accountType: 0, typeText: '苹果', amount: 0 };
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
            this.accountTable.query(0, (result) => {
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
            let newAccount: AccountData = { id: 2, accountType: 1, typeText: '栗子', amount: 1 };
            this.accountTable.deleteData(newAccount, () => {
            })
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}