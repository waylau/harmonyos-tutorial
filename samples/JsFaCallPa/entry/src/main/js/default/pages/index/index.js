
const globalRef = Object.getPrototypeOf(global) || global
globalRef.regeneratorRuntime = require('@babel/runtime/regenerator')

const ABILITY_TYPE_EXTERNAL = 0;
const ACTION_SYNC = 0;
const ACTION_ASYNC = 1;
const ACTION_MESSAGE_CODE_PLUS = 1001;

export const playAbility = {
    sum: async function(that){
        var actionData = {};
        actionData.firstNum = 1024;
        actionData.secondNum = 2048;

        var action = {};
        action.bundleName = 'com.waylau.hmos.jsfacallpa';
        action.abilityName = 'com.waylau.hmos.jsfacallpa.PlayAbility';
        action.messageCode = ACTION_MESSAGE_CODE_PLUS;
        action.data = actionData;
        action.abilityType = ABILITY_TYPE_EXTERNAL;
        action.syncOption = ACTION_SYNC;

        var result = await FeatureAbility.callAbility(action);
        var ret = JSON.parse(result);
        if (ret.code == 0) {
            console.info('result is:' + JSON.stringify(ret.abilityResult));
            that.title = 'result is:' + JSON.stringify(ret.abilityResult);
        } else {
            console.error('plus error code:' + JSON.stringify(ret.code));
        }
    }
}

export default {
    data: {
        title: ""
    },
    onInit() {
        this.title = "1024+2048=";
    },
    play(){
        this.title = "doing...";
        playAbility.sum(this);
    }
}
