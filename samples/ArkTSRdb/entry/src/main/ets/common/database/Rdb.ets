import { relationalStore } from '@kit.ArkData';
import CommonConstants from '../constants/CommonConstants';

export default class Rdb {
  private rdbStore: relationalStore.RdbStore | null = null;
  private tableName: string;
  private sqlCreateTable: string;
  private columns: Array<string>;

  constructor(tableName: string, sqlCreateTable: string, columns: Array<string>) {
    this.tableName = tableName;
    this.sqlCreateTable = sqlCreateTable;
    this.columns = columns;
  }

  getRdbStore(callback: Function = () => {
  }) {
    if (!callback || typeof callback === 'undefined' || callback === undefined) {
      console.info('getRdbStore() has no callback!');
      return;
    }
    if (this.rdbStore !== null) {
      console.info('The rdbStore exists.');
      callback();
      return
    }
    let context: Context = getContext(this) as Context;
    relationalStore.getRdbStore(context, CommonConstants.STORE_CONFIG, (err, rdb) => {
      if (err) {
        console.error(`gerRdbStore() failed, err: ${err}`);
        return;
      }
      this.rdbStore = rdb;
      this.rdbStore.executeSql(this.sqlCreateTable);
      console.info('getRdbStore() finished.');
      callback();
    });
  }

  insertData(data: relationalStore.ValuesBucket, callback: Function = () => {
  }) {
    if (!callback || typeof callback === 'undefined' || callback === undefined) {
      console.info('insertData() has no callback!');
      return;
    }
    let resFlag: boolean = false;
    const valueBucket: relationalStore.ValuesBucket = data;
    if (this.rdbStore) {
      this.rdbStore.insert(this.tableName, valueBucket, (err, ret) => {
        if (err) {
          console.error(`insertData() failed, err: ${err}`);
          callback(resFlag);
          return;
        }
        console.info(`insertData() finished: ${ret}`);
        callback(ret);
      });
    }
  }

  deleteData(predicates: relationalStore.RdbPredicates, callback: Function = () => {
  }) {
    if (!callback || typeof callback === 'undefined' || callback === undefined) {
      console.info('deleteData() has no callback!');
      return;
    }
    let resFlag: boolean = false;
    if (this.rdbStore) {
      this.rdbStore.delete(predicates, (err, ret) => {
        if (err) {
          console.error(`deleteData() failed, err: ${err}`);
          callback(resFlag);
          return;
        }
        console.info(`deleteData() finished: ${ret}`);
        callback(!resFlag);
      });
    }
  }

  updateData(predicates: relationalStore.RdbPredicates, data: relationalStore.ValuesBucket, callback: Function = () => {
  }) {
    if (!callback || typeof callback === 'undefined' || callback === undefined) {
      console.info('updateDate() has no callback!');
      return;
    }
    let resFlag: boolean = false;
    const valueBucket: relationalStore.ValuesBucket = data;
    if (this.rdbStore) {
      this.rdbStore.update(valueBucket, predicates, (err, ret) => {
        if (err) {
          console.error(`updateData() failed, err: ${err}`);
          callback(resFlag);
          return;
        }
        console.info(`updateData() finished: ${ret}`);
        callback(!resFlag);
      });
    }
  }

  query(predicates: relationalStore.RdbPredicates, callback: Function = () => {
  }) {
    if (!callback || typeof callback === 'undefined' || callback === undefined) {
      console.info('query() has no callback!');
      return;
    }
    if (this.rdbStore) {
      this.rdbStore.query(predicates, this.columns, (err, resultSet) => {
        if (err) {
          console.error(`query() failed, err:  ${err}`);
          return;
        }
        console.info('query() finished.');
        callback(resultSet);
        resultSet.close();
      });
    }
  }
}