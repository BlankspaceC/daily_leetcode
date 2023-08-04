package com.example.study.study;


import java.util.*;

/**
 * @Author: LongX
 * @Date: 2023/7/31 16:22
 * @Description: LCStudy
 * @Version: 1.0
 **/
public class LCStudy {


    public static void main(String[] args) {

    }

    //980. 不同路径 III
    public static int uniquePathsIII(int[][] grid) {
        List<String> totalPath = new ArrayList<>();
        int total = grid.length*grid[0].length;
        int xbg = 0,ybg = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int i1 = 0; i1 < grid[i].length; i1++) {
                int temp = grid[i][i1];
                if(temp == -1) total--;
                else if(temp == 1){
                    xbg = i;
                    ybg = i1;
                }
            }
        }
        Set<String> begin = new HashSet<>();
        begin.add(xbg+""+ybg);
        dg(begin,xbg,ybg,grid,total,totalPath);
        return totalPath.size();
    }
    //980. 不同路径 III
    public static void dg(Set<String> nowWay, int x, int y,int[][] grid,int total,List<String> totalPath){
        String tempPlace = x + "" + y;
        if(nowWay.size() > 1 && nowWay.contains(tempPlace)){
            return;
        }
        Set<String> temSet = new HashSet<>();
        nowWay.forEach(f->temSet.add(f));
        temSet.add(tempPlace);
        if(grid[x][y] == 2){
            if(temSet.size() == total){
                totalPath.add("a");
            }
            return;
        }
        //向上
        if(x-1 >= 0 && grid[x-1][y] != -1){
            dg(temSet,x-1,y,grid,total,totalPath);
        }
        //向下
        if(x+1 <= grid.length-1 && grid[x+1][y] != -1){
            dg(temSet,x+1,y,grid,total,totalPath);
        }
        //向左
        if(y-1 >= 0 && grid[x][y-1] != -1){
            dg(temSet,x,y-1,grid,total,totalPath);

        }
        //向右
        if(y+1 <= grid[0].length-1 && grid[x][y+1] != -1){
            dg(temSet,x,y+1,grid,total,totalPath);
        }
    }

}
