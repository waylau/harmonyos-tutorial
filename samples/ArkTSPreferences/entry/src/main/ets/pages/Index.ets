// 导入PreferencesUtil
import PreferencesUtil from '../common/PreferencesUtil';
// 导入AccountData
import AccountData from '../database/AccountData';

const PREFERENCES_KEY = 'fruit';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World'
  private preferencesUtil = new PreferencesUtil();

  async aboutToAppear() {
    // 初始化首选项
    await this.preferencesUtil.getPreferencesFromStorage();

    // 获取结果
    this.preferencesUtil.getPreference(PREFERENCES_KEY).then(resultData => {
      this.message = resultData;
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
            // 保存数据
            let newAccount: AccountData = { id: 0, accountType: 0, typeText: '苹果', amount: 0 };
            this.preferencesUtil.putPreference(PREFERENCES_KEY, JSON.stringify(newAccount));
          })

        // 查询
        Button(('查询'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            // 获取结果
            this.preferencesUtil.getPreference(PREFERENCES_KEY).then(resultData => {
              this.message = resultData;
            });
          })

        // 修改
        Button(('修改'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            // 修改数据
            let newAccount: AccountData = { id: 1, accountType: 1, typeText: '栗子', amount: 1 };
            this.preferencesUtil.putPreference(PREFERENCES_KEY, JSON.stringify(newAccount));
          })

        // 删除
        Button(('删除'), { type: ButtonType.Capsule })
          .width(140)
          .fontSize(40)
          .fontWeight(FontWeight.Medium)
          .margin({ top: 20, bottom: 20 })
          .onClick(() => {
            this.preferencesUtil.deletePreferences();
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}