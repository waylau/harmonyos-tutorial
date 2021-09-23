import file from '@system.file'
import app from '@system.app'
import device from '@system.device'
import router from '@system.router'
import {Core, Constant, ExpectExtend, ReportExtend, InstrumentLog} from 'deccjsunit/index'

export default {
    data: {
        title: ""
    },
    onInit() {
        this.title = this.$t('strings.world');
    },
    onShow() {
        console.info('onShow finish')
        const core = Core.getInstance()
        const expectExtend = new ExpectExtend({
            'id': 'extend'
        })
        const reportExtend = new ReportExtend(file)
        const instrumentLog = new InstrumentLog({
            'id': 'report',
            'version': '1.0.3'
        })
        core.addService('expect', expectExtend)
        core.addService('report', reportExtend)
        core.addService('report', instrumentLog)
        core.init()
        core.subscribeEvent('spec', instrumentLog)
        core.subscribeEvent('suite', instrumentLog)
        core.subscribeEvent('task', instrumentLog)

        const configService = core.getDefaultService('config')
        configService.setConfig(this)

        require('../../../test/List.test')
        core.execute()
    },
    onReady() {
    },
}