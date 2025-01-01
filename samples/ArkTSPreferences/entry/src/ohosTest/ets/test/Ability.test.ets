import { describe, it, expect } from '@ohos/hypium';
import { abilityDelegatorRegistry, Driver, ON } from '@kit.TestKit';
import { UIAbility, Want } from '@kit.AbilityKit';
import AccountData from '../../../main/ets/database/AccountData';

const delegator: abilityDelegatorRegistry.AbilityDelegator = abilityDelegatorRegistry.getAbilityDelegator()
const bundleName = abilityDelegatorRegistry.getArguments().bundleName;

function sleep(time: number) {
  return new Promise<void>((resolve: Function) => setTimeout(resolve, time));
}

export default function abilityTest() {
  describe('ActsAbilityTest', () => {

    // 编写UI测试脚本
    it('testUi',0, async (done: Function) => {
      console.info("uitest: testUi begin");
      // 启动待测试的 ability
      const want: Want = {
        bundleName: bundleName,
        abilityName: 'EntryAbility'
      }
      await delegator.startAbility(want);
      await sleep(1000);

      // 检查顶层显示的 ability
      await delegator.getCurrentTopAbility().then((Ability: UIAbility)=>{
        console.info("get top ability");
        expect(Ability.context.abilityInfo.name).assertEqual('EntryAbility');
      })

      // UI 测试代码
      // 初始化driver
      let driver = Driver.create();
      await driver.delayMs(1000);

      // 查找'增加'按钮
      let buttonAdd = await driver.findComponent(ON.text('增加'));

      // 点击按钮
      await buttonAdd.click();
      await driver.delayMs(1000);

      // 查找'查询'按钮
      let buttonQuery = await driver.findComponent(ON.text('查询'));

      // 点击按钮
      await buttonQuery.click();
      await driver.delayMs(1000);

      // 查找 id 为'text_result'的 Text 组件
      let text = await driver.findComponent(ON.id('text_result'));

      // 检查文本内容
      await text.getText().then(result => {
        let newAccount: AccountData = { id: 1, accountType: 0, typeText: '苹果', amount: 0 };

        expect(result).assertEqual(JSON.stringify(newAccount))
      });

      done();
    })

  })
}