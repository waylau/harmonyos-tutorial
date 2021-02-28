// index.js:
import router from '@system.router'
// 导入app模块
import app from '@system.app'

export default {
    data: {
        title: 'World'
    },
    clickAction(){
        router.replace({
            uri: 'pages/details/details'
        });
    },
    touchMove(e){  // swipe处理事件
        // 滑动网格
        console.info("event.direction:" + e.direction);
    },
    appExit(){  // 退出app
        app.terminate();
    }
}