import {describe, beforeAll, beforeEach, afterEach, afterAll, it, expect} from "deccjsunit/index"
import app from '@system.app'

export default function exampleJsunit() {
  describe('appInfoTest', function () {
    it('app_info_test_001', 0, function () {
      var info = app.getInfo()
      expect("1.0").assertEqual('1.0')
      expect(info.versionCode).assertEqual('3')
    })
  })
}