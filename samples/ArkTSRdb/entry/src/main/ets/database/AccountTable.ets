import data_rdb from '@ohos.data.rdb';
import RdbUtil from '../common/RdbUtil';
import AccountData from './AccountData';

const ACCOUNT_TABLE = {
  tableName: 'accountTable',
  sqlCreate: 'CREATE TABLE IF NOT EXISTS accountTable(' +
  'id INTEGER PRIMARY KEY AUTOINCREMENT, accountType INTEGER, ' +
  'typeText TEXT, amount INTEGER)',
  columns: ['id', 'accountType', 'typeText', 'amount']
};

export default class AccountTable {

  private accountTable = new RdbUtil(ACCOUNT_TABLE.tableName, ACCOUNT_TABLE.sqlCreate,
    ACCOUNT_TABLE.columns);

  constructor(callback: Function = () => {}) {
    this.accountTable.getRdbStore(callback);
  }

  getRdbStore(callback: Function = () => {}) {
    this.accountTable.getRdbStore(callback);
  }

  // 插入数据
  insertData(account: AccountData, callback) {
    // 根据输入数据创建待插入的数据行
    const valueBucket = generateBucket(account);
    this.accountTable.insertData(valueBucket, callback);
  }

  // 删除数据
  deleteData(account: AccountData, callback) {
    let predicates = new data_rdb.RdbPredicates(ACCOUNT_TABLE.tableName);

    // 根据id匹配待删除的数据行
    predicates.equalTo('id', account.id);
    this.accountTable.deleteData(predicates, callback);
  }

  // 修改数据
  updateData(account: AccountData, callback) {
    const valueBucket = generateBucket(account);
    let predicates = new data_rdb.RdbPredicates(ACCOUNT_TABLE.tableName);

    // 根据id匹配待修改的数据行
    predicates.equalTo('id', account.id);
    this.accountTable.updateData(predicates, valueBucket, callback);
  }

  // 查找数据
  query(amount: number, callback, isAll: boolean = true){
    let predicates = new data_rdb.RdbPredicates(ACCOUNT_TABLE.tableName);

    // 是否查找全部数据
    if (!isAll) {
      predicates.equalTo('amount', amount);  // 根据金额匹配要查找的数据行
    }
    this.accountTable.query(predicates, function(resultSet) {
      let count = resultSet.rowCount;

      // 查找结果为空则返回空数组，否则返回查找结果数组
      if (count === 0 || typeof count === 'string') {
        console.log('Query no results!');
        callback([]);
      } else {
        resultSet.goToFirstRow();
        const result = [];
        for (let i = 0; i < count; i++) {
          let tmp: AccountData = { id: 0, accountType: 0, typeText: '', amount: 0 };
          tmp.id = resultSet.getDouble(resultSet.getColumnIndex('id'));
          tmp.accountType = resultSet.getDouble(resultSet.getColumnIndex('accountType'));
          tmp.typeText = resultSet.getString(resultSet.getColumnIndex('typeText'));
          tmp.amount = resultSet.getDouble(resultSet.getColumnIndex('amount'));
          result[i] = tmp;
          resultSet.goToNextRow();
        }
        callback(result);
      }
    });
  }
}

function generateBucket(account: AccountData) {
  let obj = {};
  ACCOUNT_TABLE.columns.forEach((item) => {
    if (item != 'id') {
      obj[item] = account[item];
    }
  });
  return obj;
}