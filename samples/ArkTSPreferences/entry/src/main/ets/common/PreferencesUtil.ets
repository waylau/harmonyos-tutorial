// 导入preferences模块。
import data_preferences from '@ohos.data.preferences';

class PreferencesUtil {
  private preferences;

  public addData() {
    let putPromise = this.preferences.put('startup', 'auto');
    putPromise.then(() => {
      console.info("Succeeded in putting the value of 'startup'.");
    }).catch((err) => {
      console.info("Failed to put the value of 'startup'. Cause: " + err);
    })

    // 数据持久化
    this.preferences.flush();
  }

  public queryData() {
    let getPromise = this.preferences.get('startup', 'default');
    getPromise.then((value) => {
      console.info("The value of 'startup' is " + value);
    }).catch((err) => {
      console.info("Failed to get the value of 'startup'. Cause: " + err);
    })
  }

  public deleteData() {
    // todo
  }

  public updateData() {
    // todo
  }

  public initStore(context) {
    let promise = data_preferences.getPreferences(context, 'mystore');

    promise.then((pref) => {
      this.preferences = pref;
    }).catch((err) => {
      console.info("Failed to get preferences.");
    })
  }
}


export default new PreferencesUtil();