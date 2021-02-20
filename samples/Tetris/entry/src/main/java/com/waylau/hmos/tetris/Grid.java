package com.waylau.hmos.tetris;

public class Grid {
    private static final int[][] RedGrids1 =
            {{0, 3}, {0, 4}, {1, 4}, {1, 5}};//红色方块形态1
    private static final int[][] RedGrids2 =
            {{0, 5}, {1, 5}, {1, 4}, {2, 4}};//红色方块形态2
    private static final int[][] GreenGrids1 =
            {{0, 5}, {0, 4}, {1, 4}, {1, 3}};//绿色方块形态1
    private static final int[][] GreenGrids2 =
            {{0, 4}, {1, 4}, {1, 5}, {2, 5}};//绿色方块形态2
    private static final int[][] CyanGrids1 =
            {{0, 4}, {1, 4}, {2, 4}, {3, 4}};//蓝绿色方块形态1
    private static final int[][] CyanGrids2 =
            {{0, 3}, {0, 4}, {0, 5}, {0, 6}};//蓝绿色方块形态2
    private static final int[][] MagentaGrids1 =
            {{0, 4}, {1, 3}, {1, 4}, {1, 5}};//品红色方块形态1
    private static final int[][] MagentaGrids2 =
            {{0, 4}, {1, 4}, {1, 5}, {2, 4}};//品红色方块形态2
    private static final int[][] MagentaGrids3 =
            {{0, 3}, {0, 4}, {0, 5}, {1, 4}};//品红色方块形态3
    private static final int[][] MagentaGrids4 =
            {{0, 5}, {1, 5}, {1, 4}, {2, 5}};//品红色方块形态4
    private static final int[][] BlueGrids1 =
            {{0, 3}, {1, 3}, {1, 4}, {1, 5}};//蓝色方块形态1
    private static final int[][] BlueGrids2 =
            {{0, 5}, {0, 4}, {1, 4}, {2, 4}};//蓝色方块形态2
    private static final int[][] BlueGrids3 =
            {{0, 3}, {0, 4}, {0, 5}, {1, 5}};//蓝色方块形态3
    private static final int[][] BlueGrids4 =
            {{0, 5}, {1, 5}, {2, 5}, {2, 4}};//蓝色方块形态4
    private static final int[][] WhiteGrids1 =
            {{0, 5}, {1, 5}, {1, 4}, {1, 3}};//白色方块形态1
    private static final int[][] WhiteGrids2 =
            {{0, 4}, {1, 4}, {2, 4}, {2, 5}};//白色方块形态2
    private static final int[][] WhiteGrids3 =
            {{0, 5}, {0, 4}, {0, 3}, {1, 3}};//白色方块形态3
    private static final int[][] WhiteGrids4 =
            {{0, 4}, {0, 5}, {1, 5}, {2, 5}};//白色方块形态4
    private static final int[][] YellowGrids =
            {{0, 4}, {0, 5}, {1, 5}, {1, 4}};//黄色方块形态1

    private int[][] currentGrids;//当前方块的形态
    private int rowNumber;//方块的总行数
    private int columnNumber;//方块的总列数
    private int currentGridColor;//当前方格的颜色
    private int columnStart;//方块的第一个方格所在二维数组的列数

    private Grid(int[][] currentGrids, int rowNumber, int columnNumber,
                 int currentGridColor, int columnStart) {
        this.currentGrids = currentGrids;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.currentGridColor = currentGridColor;
        this.columnStart = columnStart;
    }

    public static Grid generateRandomGrid() {
        //随机生成一个颜色方块
        double random = Math.random();
        if (random >= 0 && random < 0.2) {
            if (random >= 0 && random < 0.1)
                return createRedGrids1();
            else
                return createRedGrids2();
        } else if (random >= 0.2 && random < 0.4) {
            if (random >= 0.2 && random < 0.3)
                return createGreenGrids1();
            else
                return createGreenGrids2();
        } else if (random >= 0.4 && random < 0.45) {
            if (random >= 0.4 && random < 0.43)
                return createCyanGrids1();
            else
                return createCyanGrids2();
        } else if (random >= 0.45 && random < 0.6) {
            if (random >= 0.45 && random < 0.48)
                return createMagentaGrids1();
            else if (random >= 0.48 && random < 0.52)
                return createMagentaGrids2();
            else if (random >= 0.52 && random < 0.56)
                return createMagentaGrids3();
            else
                return createMagentaGrids4();
        } else if (random >= 0.6 && random < 0.75) {
            if (random >= 0.6 && random < 0.63)
                return createBlueGrids1();
            else if (random >= 0.63 && random < 0.67)
                return createBlueGrids2();
            else if (random >= 0.67 && random < 0.71)
                return createBlueGrids3();
            else
                return createBlueGrids4();
        } else if (random >= 0.75 && random < 0.9) {
            if (random >= 0.75 && random < 0.78)
                return createWhiteGrids1();
            else if (random >= 0.78 && random < 0.82)
                return createWhiteGrids2();
            else if (random >= 0.82 && random < 0.86)
                return createWhiteGrids3();
            else
                return createWhiteGrids4();
        } else {
            return createYellowGrids();
        }
    }

    public static Grid shiftGrid(Grid grid) {
        int currentGridColor = grid.getCurrentGridColor();
        int[][] currentGrids = grid.getCurrentGrids();

        //根据Grids的颜色值调用改变方块形状的"chang+Color+Grids"函数
        if (currentGridColor == 1) {
            return changRedGrids(currentGrids);
        } else if (currentGridColor == 2) {
            return changeGreenGrids(currentGrids);
        } else if (currentGridColor == 3) {
            return changeCyanGrids(currentGrids);
        } else if (currentGridColor == 4) {
            return changeMagentaGrids(currentGrids);
        } else if (currentGridColor == 5) {
            return changeBlueGrids(currentGrids);
        } else if (currentGridColor == 6) {
            return changeWhiteGrids(currentGrids);
        } else {
            return null;
        }
    }

    // 以下为为各种颜色各种形状的方块
    private static Grid createRedGrids1() {
        return new Grid(RedGrids1, 2, 3, 1, 3);
    }

    private static Grid createRedGrids2() {
        return new Grid(RedGrids2, 3, 2, 1, 4);
    }

    private static Grid createGreenGrids1() {
        return new Grid(GreenGrids1, 2, 3, 2, 3);
    }

    private static Grid createGreenGrids2() {
        return new Grid(GreenGrids2, 3, 2, 2, 4);
    }

    private static Grid createCyanGrids1() {
        return new Grid(CyanGrids1, 4, 1, 3, 4);
    }

    private static Grid createCyanGrids2() {
        return new Grid(CyanGrids2, 1, 4, 3, 3);
    }

    private static Grid createMagentaGrids1() {
        return new Grid(MagentaGrids1, 2, 3, 4, 3);
    }

    private static Grid createMagentaGrids2() {
        return new Grid(MagentaGrids2, 3, 2, 4, 4);
    }

    private static Grid createMagentaGrids3() {
        return new Grid(MagentaGrids3, 2, 3, 4, 3);
    }

    private static Grid createMagentaGrids4() {
        return new Grid(MagentaGrids4, 3, 2, 4, 4);
    }

    private static Grid createBlueGrids1() {
        return new Grid(BlueGrids1, 2, 3, 5, 3);
    }

    private static Grid createBlueGrids2() {
        return new Grid(BlueGrids2, 3, 2, 5, 4);
    }

    private static Grid createBlueGrids3() {
        return new Grid(BlueGrids3, 2, 3, 5, 3);
    }

    private static Grid createBlueGrids4() {
        return new Grid(BlueGrids4, 3, 2, 5, 4);
    }

    private static Grid createWhiteGrids1() {
        return new Grid(WhiteGrids1, 2, 3, 6, 3);
    }

    private static Grid createWhiteGrids2() {
        return new Grid(WhiteGrids2, 3, 2, 6, 4);
    }

    private static Grid createWhiteGrids3() {
        return new Grid(WhiteGrids3, 2, 3, 6, 3);
    }

    private static Grid createWhiteGrids4() {
        return new Grid(WhiteGrids4, 3, 2, 6, 4);
    }

    private static Grid createYellowGrids() {
        return new Grid(YellowGrids, 2, 2, 7, 4);
    }

    // 以下为变换方块形状时调用新方块的"create+Color+Grids"函数
    private static Grid changRedGrids(int[][] currentGrids) {
        if (currentGrids == RedGrids1) {
            return createRedGrids2();
        } else if (currentGrids == RedGrids2) {
            return createRedGrids1();
        } else {
            return null;
        }
    }

    private static Grid changeGreenGrids(int[][] currentGrids) {
        if (currentGrids == GreenGrids1) {
            return createGreenGrids2();
        } else if (currentGrids == GreenGrids2) {
            return createGreenGrids1();
        } else {
            return null;
        }
    }

    private static Grid changeCyanGrids(int[][] currentGrids) {
        if (currentGrids == CyanGrids1) {
            return createCyanGrids2();
        } else if (currentGrids == CyanGrids2) {
            return createCyanGrids1();
        } else {
            return null;
        }
    }

    private static Grid changeMagentaGrids(int[][] currentGrids) {
        if (currentGrids == MagentaGrids1) {
            return createMagentaGrids2();
        } else if (currentGrids == MagentaGrids2) {
            return createMagentaGrids3();
        } else if (currentGrids == MagentaGrids3) {
            return createMagentaGrids4();
        } else if (currentGrids == MagentaGrids4) {
            return createMagentaGrids1();
        } else {
            return null;
        }
    }

    private static Grid changeBlueGrids(int[][] currentGrids) {
        if (currentGrids == BlueGrids1) {
            return createBlueGrids2();
        } else if (currentGrids == BlueGrids2) {
            return createBlueGrids3();
        } else if (currentGrids == BlueGrids3) {
            return createBlueGrids4();
        } else if (currentGrids == BlueGrids4) {
            return createBlueGrids1();
        } else {
            return null;
        }
    }

    private static Grid changeWhiteGrids(int[][] currentGrids) {
        if (currentGrids == WhiteGrids1) {
            return createWhiteGrids2();
        } else if (currentGrids == WhiteGrids2) {
            return createWhiteGrids3();
        } else if (currentGrids == WhiteGrids3) {
            return createWhiteGrids4();
        } else if (currentGrids == WhiteGrids4) {
            return createWhiteGrids1();
        } else {
            return null;
        }
    }

    public int[][] getCurrentGrids() {
        return currentGrids;
    }

    public void setCurrentGrids(int[][] currentGrids) {
        this.currentGrids = currentGrids;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getCurrentGridColor() {
        return currentGridColor;
    }

    public void setCurrentGridColor(int currentGridColor) {
        this.currentGridColor = currentGridColor;
    }

    public int getColumnStart() {
        return columnStart;
    }

    public void setColumnStart(int columnStart) {
        this.columnStart = columnStart;
    }
}
