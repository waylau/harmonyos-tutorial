// 导入rdb模块。
import data_rdb from '@ohos.data.rdb'
// 导入context模块。
import context from '@ohos.application.context';

type Context = context.Context;
const STORE_CONFIG = { name: "rdbstore.db" }

export default class RdbUtil {
  private rdbStore: any = null;
  private tableName: string;
  private sqlCreateTable: string;
  private columns: Array<string>;

  constructor(tableName: string, sqlCreateTable: string, columns: Array<string>) {
    this.tableName = tableName;
    this.sqlCreateTable = sqlCreateTable;
    this.columns = columns;
  }

  getRdbStore(callback) {
    // 如果已经获取到RdbStore则不做操作
    if (this.rdbStore != null) {
      console.info('The rdbStore exists.');
      callback();
      return;
    }

    // 应用上下文，本例子是使用API9 Stage模型的Context
    let context: Context = getContext(this) as Context;
    data_rdb.getRdbStore(context, STORE_CONFIG, 1, (err, rdb) => {
      if (err) {
        console.error('gerRdbStore() failed, err: ' + err);
        return;
      }
      this.rdbStore = rdb;

      // 获取到RdbStore后，需使用executeSql接口初始化数据库表结构和相关数据
      this.rdbStore.executeSql(this.sqlCreateTable);
      console.info('getRdbStore() finished.');
      callback();
    });
  }

  // 插入数据
  insertData(data, callback) {
    let resFlag: boolean = false;  // 用于记录插入是否成功的flag
    const valueBucket = data;  // 存储键值对的类型，表示要插入到表中的数据行
    this.rdbStore.insert(this.tableName, valueBucket, function (err, ret) {
      if (err) {
        console.error('Rdb', 'insertData() failed, err: ' + err);
        callback(resFlag);
        return;
      }
      callback(!resFlag);
    });
  }

  // 删除数据
  deleteData(predicates, callback) {
    let resFlag: boolean = false;

    // predicates表示待删除数据的操作条件
    this.rdbStore.delete(predicates, function (err, ret) {
      if (err) {
        console.error('Rdb', 'deleteData() failed, err: ' + err);
        callback(resFlag);
        return;
      }
      callback(!resFlag);
    });
  }

  // 更新数据
  updateData(predicates, data, callback) {
    let resFlag: boolean = false;
    const valueBucket = data;
    this.rdbStore.update(valueBucket, predicates, function (err, ret) {
      if (err) {
        console.error('Rdb', 'updateData() failed, err: ' + err);
        callback(resFlag);
        return;
      }
      callback(!resFlag);
    });
  }

  // 查找数据
  query(predicates, callback){
    // columns表示要查询的列，如果为空则表示查询所有列
    this.rdbStore.query(predicates, this.columns, function (err, resultSet) {
      if (err) {
        console.error('Rdb', 'query() failed, err: ' + err);
        return;
      }
      callback(resultSet);  // 如果查找成功则返回resultSet结果集
      resultSet.close();  // 操作完成后关闭结果集
    });
  }

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

  public initStore() {
    const CREATE_TABLE_TEST = "CREATE TABLE IF NOT EXISTS test (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT NOT NULL, " + "age INTEGER, " + "salary REAL, " + "blobType BLOB)";



    var context: Context = getContext(this) as Context;

    data_rdb.getRdbStore(context, STORE_CONFIG, 1, function (err, rdbStore) {
      if (err) {
        console.error('gerRdbStore() failed, err: ' + err);
        return;
      }
      this.rdbStore = rdbStore;

      this.rdbStore.executeSql(CREATE_TABLE_TEST)
      console.info('create table done.')

    })
  }


}
