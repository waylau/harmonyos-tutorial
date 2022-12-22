// 导入distributedData模块。
import distributedData from '@ohos.data.distributedData';

class DistributedDataUtil {
  private KEY_TEST_STRING_ELEMENT: string = 'key_test_string';
  private VALUE_TEST_STRING_ELEMENT: string = 'value-test-string';

  private kvManager;
  private kvStore;

  public addData() {
    try {
      this.kvStore.put(this.KEY_TEST_STRING_ELEMENT, this.VALUE_TEST_STRING_ELEMENT, function (err, data) {
        if (err != undefined) {
          console.log('put err: ' + JSON.stringify(err));
          return;
        }
        console.log('put success');
      });
    } catch (e) {
      console.log("An unexpected error occurred. Error:" + JSON.stringify(e));
    }
  }

  public queryData() {
    this.kvStore.get(this.KEY_TEST_STRING_ELEMENT, function (err, data) {
      console.log('get success data:  ${data}');
      this.message = data;
    });
  }

  public deleteData() {
    try {
      this.kvStore.del(this.KEY_TEST_STRING_ELEMENT, function (err, data) {
        if (err != undefined) {
          console.log('delete err: ' + JSON.stringify(err));
          return;
        }
        console.log('delete success');
      });
    } catch (e) {
      console.log("An unexpected error occurred. Error:" + JSON.stringify(e));
    }
  }

  public updateData() {
    try {
      this.kvStore.put(this.KEY_TEST_STRING_ELEMENT, this.VALUE_TEST_STRING_ELEMENT+"-Up", function (err, data) {
        if (err != undefined) {
          console.log('put err: ' + JSON.stringify(err));
          return;
        }
        console.log('put success');
      });
    } catch (e) {
      console.log("An unexpected error occurred. Error:" + JSON.stringify(e));
    }
  }

  public initKVManager(){
    try {
      const kvManagerConfig = {
        bundleName : 'com.waylau.hmos.arktsdistributeddata',
        userInfo : {
          userId : '0',
          userType : distributedData.UserType.SAME_USER_ID
        }
      }
      distributedData.createKVManager(kvManagerConfig, function (err, manager) {
        if (err) {
          console.log("createKVManager err: "  + JSON.stringify(err));
          return;
        }
        console.log("createKVManager success");
        this.kvManager = manager;
      });
    } catch (e) {
      console.log("An unexpected error occurred. Error:" + JSON.stringify(e));
    }
  }

  public initKVStore() {
    try {
      const options = {
        createIfMissing: true,
        encrypt: false,
        backup: false,
        autoSync: false,
        kvStoreType: distributedData.KVStoreType.SINGLE_VERSION,
        securityLevel: distributedData.SecurityLevel.S0
      };
      this.kvManager.getKVStore('storeId', options, function (err, store) {
        if (err) {
          console.log('getKVStore err: ' + JSON.stringify(err));
          return;
        }
        console.log('getKVStore success');
        this.kvStore = store;
      });
    } catch (e) {
      console.log("An unexpected error occurred. Error:" + JSON.stringify(e));
    }
  }
}


export default new DistributedDataUtil();