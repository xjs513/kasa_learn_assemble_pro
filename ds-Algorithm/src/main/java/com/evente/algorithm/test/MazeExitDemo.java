package com.evente.algorithm.test;

public class MazeExitDemo {
    public static void main(String[] args) {

    }


    private class Cell{
        int x = 0; // 单元格所在行
        int y = 0; // 单元格所在列
        boolean visited = false; // 是否访问过
        char c = ' ';            // 墙：'1', 通道： '0', 最终路径：'*'

        public Cell(int x, int y, char c, boolean visited) {
            this.x = x;
            this.y = y;
            this.c = c;
            this.visited = visited;
        }
    }
}

