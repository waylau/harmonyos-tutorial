// 导入preferences模块
import { preferences } from '@kit.ArkData';

import { BusinessError } from '@kit.BasicServicesKit';
import { common } from '@kit.AbilityKit';


let context = getContext(this) as common.UIAbilityContext;
let options: preferences.Options = { name: 'myStore' };

export default class PreferencesUtil {
  private dataPreferences: preferences.Preferences | null = null;

  // 调用getPreferences方法读取指定首选项持久化文件，
  // 将数据加载到Preferences实例，用于数据操作
  async getPreferencesFromStorage() {
    await preferences.getPreferences(context, options).then((data) => {
      this.dataPreferences = data;
      console.info(`Succeeded in getting preferences`);
    }).catch((err: BusinessError) => {
      console.error(`Failed to get preferences, Cause:` + err);
    });
  }

  // 将用户输入的数据，保存到缓存的Preference实例中
  async putPreference(key: string, data: string) {
    if (this.dataPreferences === null) {
      await this.getPreferencesFromStorage();
    }
	
	await this.dataPreferences.put(key, data).then(() => {
	  console.info(`Succeeded in putting value`);
	}).catch((err: BusinessError) => {
	  console.error(`Failed to get preferences, Cause:` + err);
	});

	// 将Preference实例存储到首选项持久化文件中
	await this.dataPreferences.flush();
  }

  // 使用Preferences的get方法读取数据
  async getPreference(key: string) {
    let result: string= '';
    if (this.dataPreferences === null) {
      await this.getPreferencesFromStorage();
    } else {
      await this.dataPreferences.get(key, '').then((data) => {
        result = data.toString();
        console.info(`Succeeded in getting value`);
      }).catch((err: BusinessError) => {
        console.error(`Failed to get preferences, Cause:` + err);
      });
    }

    return result;
  }

  // 从内存中移除指定文件对应的Preferences单实例。
  // 移除Preferences单实例时，应用不允许再使用该实例进行数据操作，否则会出现数据一致性问题。
  async deletePreferences() {

    preferences.deletePreferences(context, options, (err: BusinessError) => {
      if (err) {
        console.error(`Failed to delete preferences. Code:${err.code}, message:${err.message}`);
        return;
      }

      this.dataPreferences = null;
      console.info('Succeeded in deleting preferences.');
    })
  }
}
