package com.waylau.hmos.tetris.slice;

import com.waylau.hmos.tetris.Grid;
import com.waylau.hmos.tetris.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.RectFloat;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private static final int LENGTH = 100;//方格的边长
    private static final int MARGIN = 2;//方格的间距
    private static final int GRID_NUMBER = 4;//方块所占方格的数量，固定为4

    private DirectionalLayout layoutGame;//布局
    private Text textScoreValue;
    private Text textGameover;

    private int[][] grids;//描绘方格颜色的二维数组
    private int currentRow;//向下移动的行数
    private int currentColumn;//向左右移动的列数，减1表示左移，加1表示右移
    private String tipValue = "Start!";
    private TaskDispatcher taskDispatcher;
    private Grid currentGrid;
    private boolean isRunning;
    private int scoreValue = 0;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        // 初始化布局
        layoutGame =
                (DirectionalLayout) findComponentById(ResourceTable.Id_layout_game);

        // 初始化组件
        Button buttonLeft =
                (Button) findComponentById(ResourceTable.Id_button_left);
        buttonLeft.setClickedListener(listener -> goLeft(grids));

        Button buttonRight =
                (Button) findComponentById(ResourceTable.Id_button_right);
        buttonRight.setClickedListener(listener -> goRight(grids));

        Button buttonShift =
                (Button) findComponentById(ResourceTable.Id_button_shift);
        buttonShift.setClickedListener(listener -> shiftGrids(grids));

        Button buttonRestart =
                (Button) findComponentById(ResourceTable.Id_button_restart);
        buttonRestart.setClickedListener(listener -> {
            initialize();
            startGame();
        });

        textScoreValue =
                (Text) findComponentById(ResourceTable.Id_text_score_value);
        textGameover =
                (Text) findComponentById(ResourceTable.Id_text_gameover);

        // 初始化游戏
        initialize();

        // 启动游戏
        startGame();
    }

    // (重新)开始游戏
    private void initialize() {
        // 初始化变量
        scoreValue = 0;
        tipValue = "Start!";
        isRunning = false;
        taskDispatcher = null;

        // 显示提示
        showTip();

        // 初始化网格，
        // 15*10的二维数组，
        // 数组元素都是0
        grids = new int[15][10];
        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 10; column++) {
                grids[row][column] = 0;
            }
        }

        // 创建网格数据
        createGrids(grids);

        // 画出网格
        drawGrids(grids);
    }

    private void createGrids(int[][] grids) {
        currentColumn = 0;
        currentRow = 0;

        // 当有任一行全部填满颜色方块时，消去该行
        eliminateGrids(grids);

        // 判断游戏是否完成：
        // 完成时就停止定时器和并提示结束文本；
        // 未完成时就生成新的颜色方块
        if (isGameOver(grids)) {
            tipValue = "Game Over!";
            isRunning = false;
            taskDispatcher = null;
            showTip();
        } else {
            //随机生成一个颜色方块
            currentGrid = Grid.generateRandomGrid();
            int[][] currentGrids = currentGrid.getCurrentGrids();
            int currentGridColor = currentGrid.getCurrentGridColor();

            //将颜色方块对应的Grids添加到二维数组中
            for (int row = 0; row < GRID_NUMBER; row++) {
                grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] = currentGridColor;
            }
        }
    }

    //绘制背景图和方格
    private void drawGrids(int[][] grids) {
        Component.DrawTask task = new Component.DrawTask() {
            @Override
            public void onDraw(Component component, Canvas canvas) {
                Paint paint = new Paint();

                for (int row = 0; row < 15; row++) {
                    for (int column = 0; column < 10; column++) {
                        // grids的值表示不同的颜色
                        int grideValue = grids[row][column];
                        Color color = null;

                        switch (grideValue) {
                            case 0:
                                color = Color.GRAY;
                                break;
                            case 1:
                                color = Color.RED;
                                break;
                            case 2:
                                color = Color.GREEN;
                                break;
                            case 3:
                                color = Color.CYAN;
                                break;
                            case 4:
                                color = Color.MAGENTA;
                                break;
                            case 5:
                                color = Color.BLUE;
                                break;
                            case 6:
                                color = Color.WHITE;
                                break;
                            case 7:
                                color = Color.YELLOW;
                                break;
                            default:
                                break;
                        }

                        paint.setColor(color);

                        RectFloat rectFloat =
                                new RectFloat(30 + column * (LENGTH + MARGIN),
                                        40 + row * (LENGTH + MARGIN),
                                        30 + LENGTH + column * (LENGTH + MARGIN),
                                        40 + LENGTH + row * (LENGTH + MARGIN));

                        //绘制方格
                        canvas.drawRect(rectFloat, paint);
                    }
                }
            }
        };

        layoutGame.addDrawTask(task);
    }

    // 变换方块的形状
    private void shiftGrids(int[][] grids) {
        int[][] currentGrids = currentGrid.getCurrentGrids();
        int columnNumber = currentGrid.getColumnNumber();
        int columnStart = currentGrid.getColumnStart();

        //将原来的颜色方块清除
        for (int row = 0; row < GRID_NUMBER; row++) {
            grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] = 0;
        }

        if (columnNumber == 2 && currentColumn + columnStart == 0) {
            currentColumn++;
        }

        //根据Grids的颜色值调用改变方块形状的"chang+Color+Grids"函数
        currentGrid = Grid.shiftGrid(currentGrid);
        int currentGridColor = currentGrid.getCurrentGridColor();
        currentGrids = currentGrid.getCurrentGrids();

        //重新绘制颜色方块
        for (int row = 0; row < GRID_NUMBER; row++) {
            grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] = currentGridColor;
        }

        drawGrids(grids);
    }

    // 显示提示信息
    private void showTip() {
        textScoreValue.setText(scoreValue + "");
        textGameover.setText(tipValue);
    }

    // 开始游戏，方块随着时间逐渐下移
    private void startGame() {
        // 设置游戏状态
        isRunning = true;

        // 派发任务
        taskDispatcher = getUITaskDispatcher();
        taskDispatcher.asyncDispatch(new GameTask());
    }

    private class GameTask implements Runnable {

        @Override
        public void run() {
            HiLog.info(LABEL_LOG, "before GameTask run");

            int[][] currentGrids = currentGrid.getCurrentGrids();
            int currentGridColor = currentGrid.getCurrentGridColor();
            int rowNumber = currentGrid.getRowNumber();

            //如果方块能下移则下移，否则重新随机生成新的方块
            if (couldDown(currentGrids, rowNumber)) {
                //将原来的颜色方块清除
                for (int row = 0; row < GRID_NUMBER; row++) {
                    grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] = 0;
                }

                currentRow++;

                //重新绘制颜色方块
                for (int row = 0; row < GRID_NUMBER; row++) {
                    grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] = currentGridColor;
                }
            } else {
                createGrids(grids);
            }

            drawGrids(grids);

            if (isRunning) {
                taskDispatcher.delayDispatch(this, 750);
            }

            HiLog.info(LABEL_LOG, "end GameTask run");
        }
    }

    // 判断方块能否下移
    private boolean couldDown(int[][] currentGrids, int rowNumber) {
        boolean k;

        // 如果方块向下移动到下边界，则返回false
        if (currentRow + rowNumber == 15) {
            return false;
        }

        //当下边缘方块再下一格为空时则可以下移
        for (int row = 0; row < GRID_NUMBER; row++) {
            k = true;
            for (int i = 0; i < GRID_NUMBER; i++) {
                if (currentGrids[row][0] + 1 == currentGrids[i][0]
                        && currentGrids[row][1] == currentGrids[i][1]) {//找出非下边缘方块
                    k = false;
                }
            }

            //当任一下边缘方块再下一格不为空时返回false
            if (k) {
                if (grids[currentGrids[row][0] + currentRow + 1][currentGrids[row][1] + currentColumn] != 0)
                    return false;
            }
        }

        return true;
    }

    //方块向左移动一格
    private void goLeft(int[][] grids) {
        int currentGridColor = currentGrid.getCurrentGridColor();
        int[][] currentGrids = currentGrid.getCurrentGrids();
        int columnStart = currentGrid.getColumnStart();

        //当方块能向左移动时则左移
        if (couldLeft(currentGrids, columnStart)) {
            //将原来的颜色方块清除
            for (int row = 0; row < GRID_NUMBER; row++) {
                grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] = 0;
            }

            currentColumn--;

            //重新绘制颜色方块
            for (int row = 0; row < GRID_NUMBER; row++) {
                grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] = currentGridColor;
            }
        }

        drawGrids(grids);
    }

    //判断方块能否向左移动
    private boolean couldLeft(int[][] currentGrids, int columnStart) {
        boolean k;

        //如果方块向左移动到左边界，则返回false
        if (currentColumn + columnStart == 0) {
            return false;
        }

        //当左边缘方块再左一格为空时则可以左移
        for (int column = 0; column < GRID_NUMBER; column++) {
            k = true;
            for (int j = 0; j < GRID_NUMBER; j++) {
                //找出非左边缘方块
                if (currentGrids[column][0] == currentGrids[j][0]
                        && currentGrids[column][1] - 1 == currentGrids[j][1]) {
                    k = false;
                }
            }

            //当任一左边缘方块再左一格不为空时返回false
            if (k) {
                if (grids[currentGrids[column][0] + currentRow][currentGrids[column][1] + currentColumn - 1] != 0)
                    return false;
            }
        }

        return true;
    }

    //方块向右移动一格
    private void goRight(int[][] grids) {
        int currentGridColor = currentGrid.getCurrentGridColor();
        int[][] currentGrids = currentGrid.getCurrentGrids();
        int columnNumber = currentGrid.getColumnNumber();
        int columnStart = currentGrid.getColumnStart();

        //当方块能向右移动时则右移
        if (couldRight(currentGrids, columnNumber, columnStart)) {
            //将原来的颜色方块清除
            for (int row = 0; row < GRID_NUMBER; row++) {
                grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] = 0;
            }

            currentColumn++;

            //重新绘制颜色方块
            for (int row = 0; row < GRID_NUMBER; row++) {
                grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] = currentGridColor;
            }
        }

        drawGrids(grids);
    }

    //判断方块能否向右移动
    private boolean couldRight(int[][] currentGrids, int columnNumber, int columnStart) {
        boolean k;

        //如果方块向右移动到右边界，则返回false
        if (currentColumn + columnNumber + columnStart == 10) {
            return false;
        }

        //当右边缘方块再右一格为空时则可以右移
        for (int column = 0; column < GRID_NUMBER; column++) {
            k = true;
            for (int j = 0; j < GRID_NUMBER; j++) {
                //找出非右边缘方块
                if (currentGrids[column][0] == currentGrids[j][0]
                        && currentGrids[column][1] + 1 == currentGrids[j][1]) {
                    k = false;
                }
            }

            //当任一右边缘方块再右一格不为空时返回false
            if (k) {
                if (grids[currentGrids[column][0] + currentRow][currentGrids[column][1] + currentColumn + 1] != 0)
                    return false;
            }
        }

        return true;
    }

    //当有任一行全部填满颜色方块时，消去该行，且所有方格向下移动一格
    private void eliminateGrids(int[][] grids) {
        boolean k;
        for (int row = 14; row >= 0; row--) {
            k = true;

            //判断是否有任一行全部填满颜色方块
            for (int column = 0; column < 10; column++) {
                if (grids[row][column] == 0) {
                    k = false;
                }
            }

            //消去全部填满颜色方块的行
            if (k) {
                // 加分
                this.scoreValue++;
                this.showTip();

                // 且所有方格向下移动一格
                for (int i = row - 1; i >= 0; i--) {
                    for (int j = 0; j < 10; j++) {
                        grids[i + 1][j] = grids[i][j];
                    }
                }
                for (int n = 0; n < 10; n++) {
                    grids[0][n] = 0;
                }
            }
        }

        drawGrids(grids);
    }

    //判断游戏是否结束，游戏结束返回true
    private boolean isGameOver(int[][] grids) {
        //新生成的颜色方块覆盖原有的颜色方块则游戏结束
        if (currentGrid != null) {
            int[][] currentGrids = currentGrid.getCurrentGrids();

            for (int row = 0; row < GRID_NUMBER; row++) {
                if (grids[currentGrids[row][0] + currentRow][currentGrids[row][1] + currentColumn] != 0) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
