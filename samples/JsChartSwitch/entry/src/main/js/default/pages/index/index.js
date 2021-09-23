export default {
    data: {
        interval: null, // 定时器对象
        showText: true, // 是否显示文本
        textOn: '动态',
        textOff: '静态',
        allowScale: true, // 文本尺寸跟随系统设置字体缩放
        dataLength: 5, // 数据长度
        barGroup: 3, // 柱状图组数

        lineData: null, // 线形图数据
        lineOps: { // 线形图样式
            xAxis: {
                min: 0,
                max: 5,
                display: true
            },
            yAxis: {
                min: 0,
                max: 100,
                display: true
            },
            series: {
                lineStyle: {
                    width: '1px',
                    smooth: true
                },
                headPoint: {
                    shape: 'circle',
                    size: 10,
                    strokeWidth: 2,
                    fillColor: '#ffffff',
                    strokeColor: '#8477DF',
                    display: true
                },
                loop: {
                    margin: 2
                }
            }
        },
        percent: 100, // 量规图进度

        barData: [ // 柱状图数据
            {
                fillColor: '#CF0A2C',
                data: [2, 20, 99, 56, 23]
            },
            {
                fillColor: '#8477DF',
                data: [99, 88, 2, 67, 12]
            },
            {
                fillColor: '#17A98E',
                data: [56, 2, 77, 99, 78]
            }
        ],
        barOps: { // 柱状图样式
            xAxis: {
                min: 0,
                max: 20,
                display: true,
                axisTick: 5
            },
            yAxis: {
                min: 0,
                max: 100,
                display: true
            }
        }
    },
    // 初始化
    onInit() {
        this.changeLine();
    },
    change(e) {
        if (e.checked) {
            this.interval = setInterval(() => {
                this.changeLine(); // 更新线形图数据
                this.changeGauge(); // 更新量规图数据
                this.changeBar(); // 更新柱状图数据
            }, 1000)
        } else {
            clearInterval(this.interval);
        }
    },
    // 更新线形图数据
    changeLine() {
        var dataArray = [];
        for (var i = 0; i < this.dataLength; i++) {
            var nowValue = Math.floor(Math.random() * 99 + 1);
            var obj = {
                "value": nowValue, // Y坐标
                "description": nowValue + "", // 当前点的注释内容
                "textLocation": "top", // 注释内容位置
                "textColor": "#CDCACA", // 注释内容颜色
                "pointStyle": { // 节点形状
                    "shape": "circle", // 形状
                    "size": 5, // 形状大小
                    "fillColor": "#CF0A2C", // 填充颜色
                    "strokeColor": "#CF0A2C" // 边框颜色
                }
            };
            dataArray.push(obj);
        }
        this.lineData = [
            {
                strokeColor: '#17A98E', // 线的颜色
                data: dataArray,
                gradient: false,
            }
        ]
    },
    // 更新量规图数据
    changeGauge() {
        this.percent = parseInt(this.percent) >= 99 ? 0 : parseInt(this.percent) + 1;

    },
    // 更新柱状图数据
    changeBar() {
        for (var i = 0;i < this.barGroup; i++) {
            var dataArray = this.barData[i].data;
            for (var j = 0;j < this.dataLength; j++) {
                dataArray[j] = Math.floor(Math.random() * 99 + 1);
            }
        }
        this.barData = this.barData.splice(0, this.barGroup + 1);
    }
}
