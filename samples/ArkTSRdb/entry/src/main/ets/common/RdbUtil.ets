// 导入rdb模块。
import data_rdb from '@ohos.data.rdb'

class RdbUtil {
  private rdbStore;

  public addData() {
    var u8 = new Uint8Array([1, 2, 3])
    const valueBucket = { "name": "Tom", "age": 18, "salary": 100.5, "blobType": u8 }
    let insertPromise = this.rdbStore.insert("test", valueBucket)
  }

  public queryData() {
    let predicates = new data_rdb.RdbPredicates("test");
    predicates.equalTo("name", "Tom")
    let promisequery = this.rdbStore.query(predicates)
    promisequery.then((resultSet) => {
      resultSet.goToFirstRow()
      const id = resultSet.getLong(resultSet.getColumnIndex("id"))
      const name = resultSet.getString(resultSet.getColumnIndex("name"))
      const age = resultSet.getLong(resultSet.getColumnIndex("age"))
      const salary = resultSet.getDouble(resultSet.getColumnIndex("salary"))
      const blobType = resultSet.getBlob(resultSet.getColumnIndex("blobType"))
      resultSet.close()
    })
  }

  public deleteData() {
    // todo
  }

  public updateData() {
    // todo
  }

  public initStore(context) {
    const CREATE_TABLE_TEST = "CREATE TABLE IF NOT EXISTS test (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT NOT NULL, " + "age INTEGER, " + "salary REAL, " + "blobType BLOB)";

    const STORE_CONFIG = { name: "rdbstore.db" }
    data_rdb.getRdbStore(context, STORE_CONFIG, 1, function (err, rdbStore) {
      rdbStore.executeSql(CREATE_TABLE_TEST)
      console.info('create table done.')

      this.rdbStore = rdbStore;
    })
  }
}


export default new RdbUtil();