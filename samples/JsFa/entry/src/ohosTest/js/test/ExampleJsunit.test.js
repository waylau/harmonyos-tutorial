import app from '@system.app'
import {describe, beforeAll, beforeEach, afterEach, afterAll, it, expect} from 'deccjsunit/index'

describe('appInfoTest', function () {
    it('app_info_test_001', 0, function () {
        var info = app.getInfo()
        expect(info.versionName).assertEqual('1.0')
        expect(info.versionCode).assertEqual('3')
    })
}) 