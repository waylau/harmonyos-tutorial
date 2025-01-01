// 导入模块
import { distributedKVStore } from '@kit.ArkData';
import { BusinessError } from '@kit.BasicServicesKit';
import AccountData from '../database/AccountData';

class DistributedDataUtil {

  private KEY: string = 'fruit';
  private kvManager: distributedKVStore.KVManager | undefined = undefined;
  private kvStore: distributedKVStore.SingleKVStore | undefined = undefined;

  public initKVManager(){
    try {
      let context: Context = getContext(this) as Context;

      const kvManagerConfig: distributedKVStore.KVManagerConfig = {
        context: context,
        bundleName: 'com.waylau.hmos.arktsdistributeddata'
      };

      this.kvManager = distributedKVStore.createKVManager(kvManagerConfig);
    } catch (e) {
      console.log("An unexpected error occurred. Error:" + JSON.stringify(e));
    }
  }

  public initKVStore() {
    try {
      const options: distributedKVStore.Options = {
        createIfMissing: true,
        encrypt: false,
        backup: false,
        autoSync: false,
        // kvStoreType不填时，默认创建多设备协同数据库
        kvStoreType: distributedKVStore.KVStoreType.SINGLE_VERSION,
        // 多设备协同数据库：kvStoreType: distributedKVStore.KVStoreType.DEVICE_COLLABORATION,
        securityLevel: distributedKVStore.SecurityLevel.S1
      };


      if (this.kvManager !== undefined) {
        this.kvManager.getKVStore<distributedKVStore.SingleKVStore>('storeId', options,
          (err, store: distributedKVStore.SingleKVStore) => {
            if (err) {
              console.error(`Failed to get KVStore: Code:${err.code},message:${err.message}`);
              return;
            }
            console.info('Succeeded in getting KVStore.');
            this.kvStore = store;
            // 请确保获取到键值数据库实例后，再进行相关数据操作
          });
      }

    } catch (e) {
      let error = e as BusinessError;
      console.error(`An unexpected error occurred. Code:${error.code},message:${error.message}`);
    }
  }

  public addData() {
    try {
      if (this.kvStore !== undefined) {

        let newAccount: AccountData = { id: 1, accountType: 0, typeText: '苹果', amount: 0 };
        this.kvStore.put(this.KEY, JSON.stringify(newAccount), (err) => {
          if (err !== undefined) {
            console.error(`Failed to put data. Code:${err.code},message:${err.message}`);
            return;
          }
          console.info('Succeeded in putting data.');
        });
      }
    } catch (e) {
      let error = e as BusinessError;
      console.error(`An unexpected error occurred. Code:${error.code},message:${error.message}`);
    }
  }

  async queryData(){
    let result: string= '';

    if (this.kvStore !== undefined) {
      await this.kvStore.get(this.KEY).then((data) => {
        result = data.toString();
        console.info(`Succeeded in getting value`);
      }).catch((err: BusinessError) => {
        console.error(`Failed to get preferences, Cause:` + err);
      });
    }

    return result;
  }

  public deleteData() {
    try {
      if (this.kvStore !== undefined) {
        this.kvStore.delete(this.KEY, (err:BusinessError) => {
          if (err !== undefined) {
            console.error(`Failed to delete data. Code:${err.code},message:${err.message}`);
            return;
          }
          console.info('Succeeded in deleting data.');
        });
      }

    } catch (e) {
      console.log("An unexpected error occurred. Error:" + JSON.stringify(e));
    }
  }

  public updateData() {
    try {
      if (this.kvStore !== undefined) {
        let newAccount: AccountData = { id: 1, accountType: 1, typeText: '栗子', amount: 1 };
        this.kvStore.put(this.KEY, JSON.stringify(newAccount), (err:BusinessError) => {
          if (err !== undefined) {
            console.error(`Failed to put data. Code:${err.code},message:${err.message}`);
            return;
          }
          console.info('Succeeded in putting data.');
        });
      }
    } catch (e) {
      let error = e as BusinessError;
      console.error(`An unexpected error occurred. Code:${error.code},message:${error.message}`);
    }
  }


}

export default new DistributedDataUtil();