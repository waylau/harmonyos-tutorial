import app from '@system.app'
import {describe, it, expect} from "deccjsunit/index"

export default function exampleJsunit(){
    describe('appInfoTest', function () {
        it('app_info_test_001', 0, function () {
            var info = app.getInfo()
            expect(info.versionName).assertEqual('1.0.0')
            expect(info.versionCode).assertEqual(1000000)
        })
    })
}