import { hilog } from '@kit.PerformanceAnalysisKit';
import { describe, beforeAll, beforeEach, afterEach, afterAll, it, expect } from '@ohos/hypium';
// 导入测试依赖kit
import { abilityDelegatorRegistry, Driver, ON } from '@kit.TestKit';
import { UIAbility, Want } from '@kit.AbilityKit';

const delegator: abilityDelegatorRegistry.AbilityDelegator = abilityDelegatorRegistry.getAbilityDelegator()
const bundleName = abilityDelegatorRegistry.getArguments().bundleName;

function sleep(time: number) {
  return new Promise<void>((resolve: Function) => setTimeout(resolve, time));
}

export default function abilityTest() {
  describe('ActsAbilityTest', () => {
    // Defines a test suite. Two parameters are supported: test suite name and test suite function.
    beforeAll(() => {
      // Presets an action, which is performed only once before all test cases of the test suite start.
      // This API supports only one parameter: preset action function.
    })
    beforeEach(() => {
      // Presets an action, which is performed before each unit test case starts.
      // The number of execution times is the same as the number of test cases defined by **it**.
      // This API supports only one parameter: preset action function.
    })
    afterEach(() => {
      // Presets a clear action, which is performed after each unit test case ends.
      // The number of execution times is the same as the number of test cases defined by **it**.
      // This API supports only one parameter: clear action function.
    })
    afterAll(() => {
      // Presets a clear action, which is performed after all test cases of the test suite end.
      // This API supports only one parameter: clear action function.
    })
    it('assertContain', 0, () => {
      // Defines a test case. This API supports three parameters: test case name, filter parameter, and test case function.
      hilog.info(0x0000, 'testTag', '%{public}s', 'it begin');
      let a = 'abc';
      let b = 'b';
      // Defines a variety of assertion methods, which are used to declare expected boolean conditions.
      expect(a).assertContain(b);
      expect(a).assertEqual(a);
    })

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

      // 查找'计算'按钮
      let button = await driver.findComponent(ON.text('计算'));

      // 点击按钮
      await button.click();
      await driver.delayMs(1000);

      // 检查 id 为'length'的 Text 组件
      let text = await driver.findComponent(ON.id('length'));

      // 查查文本内容
      await text.getText().then(result => {
        expect(result).assertEqual('17')
      });
      done();
    })
  })
}