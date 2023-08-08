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
        maxAbsoluteSum(new int[]{1,-3,2,3,-4});
    }


    //1749. 任意子数组和的绝对值的最大值
    public static int maxAbsoluteSum(int[] nums) {
        int max=Math.abs(nums[0]);
        int dp1=nums[0];
        int dp2=nums[0];
        for(int i=1;i<nums.length;i++){
            dp1=Math.max(dp1+nums[i],nums[i]);
            dp2=Math.min(dp2+nums[i],nums[i]);
            max=Math.max(max,Math.max(dp1,Math.abs(dp2)));
        }
        return max;
    }
//    public int maxAbsoluteSum(int[] nums) {
//        int[][] result = new int[nums.length][nums.length];
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = 0; j < nums.length; j++) {
//                if(j == i){
//                    result[i][i] = nums[i];
//                }else if(j < i){
//                    result[i][j] = result[j][i];
//                }else {
//                    result[i][j] = result[i][j-1]+nums[j];
//                }
//            }
//        }
//        int res = Integer.MIN_VALUE;
//        for (int i = 0; i < result.length; i++) {
//            for (int i1 = 0; i1 < result[i].length; i1++) {
//                if(Math.abs(result[i][i1]) > res){
//                    res = Math.abs(result[i][i1]);
//                }
//            }
//        }
//        return res;
//    }

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
