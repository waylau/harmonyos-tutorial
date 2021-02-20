var grids; // 网格
var context; // canvas上下文
var timer; // 定时器
const SIDELEN = 70; // 网格边长
const MARGIN = 5; // 网格间距
const ORIGINAL_GRIDS = [[1, 2, 3, 4],
                        [5, 6, 7, 8],
                        [9, 10, 11, 12],
                        [13, 14, 15, 0]];
const DIRECTIONS = ["left", "up", "right", "down"]; // 方向
const ORIGINAL_TIME = '0.0'; // 初始时间

export default {
    data: {
        timeSeconds: ORIGINAL_TIME,
        isShowTip: false
    },
    onInit() {
        grids =  JSON.parse(JSON.stringify(ORIGINAL_GRIDS)); // 深拷贝
        timer = null;
    },
    onReady() {
        context = this.$refs.canvas.getContext('2d');
    },
    onShow() {
        // 初始化网格
        this.initGrids();

        // 绘制网格
        this.drawGrids();

        // 启动定时器
        timer = setInterval(this.costTime, 100);
    },
    initGrids() {
        // 搞乱格子
        for (let i = 0; i < 100; i++) {
            let randomIndex = Math.floor(Math.random() * 4);
            let direction = DIRECTIONS[randomIndex];
            this.updateGrids(direction);
        }
    },
    updateGrids(direction) {
        let x;
        let y;
        for (let row = 0; row < 4; row++) {
            for (let column = 0; column < 4; column++) {
                if (grids[row][column] == 0) {
                    x = row;
                    y = column;
                    break;
                }
            }
        }
        let temp;
        if (this.isShowTip == false) {
            if (direction == 'left' && (y + 1) < 4) {
                temp = grids[x][y + 1];
                grids[x][y + 1] = grids[x][y];
                grids[x][y] = temp;
            } else if (direction == 'right' && (y - 1) > -1) {
                temp = grids[x][y - 1];
                grids[x][y - 1] = grids[x][y];
                grids[x][y] = temp;
            } else if (direction == 'up' && (x + 1) < 4) {
                temp = grids[x + 1][y];
                grids[x + 1][y] = grids[x][y];
                grids[x][y] = temp;
            } else if (direction == 'down' && (x - 1) > -1) {
                temp = grids[x - 1][y];
                grids[x - 1][y] = grids[x][y];
                grids[x][y] = temp;
            }
        }
    },
    drawGrids() {
        for (let row = 0; row < 4; row++) {
            for (let column = 0; column < 4; column++) {
                let gridStr = grids[row][column].toString();

                context.fillStyle = "#BBB509";

                let leftTopX = column * (MARGIN + SIDELEN) + MARGIN;
                let leftTopY = row * (MARGIN + SIDELEN) + MARGIN;

                context.fillRect(leftTopX, leftTopY, SIDELEN, SIDELEN);

                // 非0网格的特殊处理
                if (gridStr != "0") {
                    context.fillStyle = "#0000FF";
                    context.font = "30px";

                    let offsetX = (4 - gridStr.length) * (SIDELEN / 8);
                    let offsetY = (SIDELEN - 30) / 2;

                    context.fillText(gridStr, leftTopX + offsetX, leftTopY + offsetY);
                }
            }
        }
    },
    costTime() {
        this.timeSeconds = (Math.floor(parseFloat(this.timeSeconds) * 10 + 1) / 10);
        if (this.timeSeconds % 1 == 0) {
            this.timeSeconds = this.timeSeconds + ".0";
        }
    },
    swipeGrids(event) {
        // 按滑动的方向更新网格
        this.updateGrids(event.direction);

        // 绘制网格
        this.drawGrids();

        if (this.isSuccess()) {
            clearInterval(timer);
            this.isShowTip = true;
        }
    },
    isSuccess() {
        // 判断grids与ORIGINAL_GRIDS相等。相等则游戏完成
        for (let row = 0; row < 4; row++) {
            for (let column = 0; column < 4; column++) {
                if (grids[row][column] != ORIGINAL_GRIDS[row][column]) {
                    return false;
                }
            }
        }
        return true;
    },
    restart() {
        this.timeSeconds = ORIGINAL_TIME;
        this.isShowTip = false;

        this.onShow();
    }
}
