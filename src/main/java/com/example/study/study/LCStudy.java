package com.example.study.study;


import com.example.study.entity.TreeNode;
import org.apache.kafka.common.metrics.stats.Max;

import java.util.*;

/**
 * @Author: LongX
 * @Date: 2023/7/31 16:22
 * @Description: LCStudy
 * @Version: 1.0
 **/
public class LCStudy {


    public static void main(String[] args) {
        int[] a= new int[]{3,9,6,1,5,4,8,7,2};
        sort(a,0,8);
        for (int i : a) {
            System.out.println(i);
        }
    }


    //1448. 统计二叉树中好节点的数目
    public static int goodNodes(TreeNode root) {
        List<Character> list = new ArrayList<>();
        int nowNum = Integer.MIN_VALUE;
        goodNodesDg(root,list,nowNum);
        return list.size();
    }

    public static void goodNodesDg(TreeNode root,List<Character> list,int nowNum){
        if(root.val >= nowNum){
            list.add('0');
            nowNum = root.val;
        }
        if(root.left != null){
            goodNodesDg(root.left,list,nowNum);
        }
        if(root.right!=null){
            goodNodesDg(root.right,list,nowNum);
        }
    }

    //1782. 统计点对的数目
    public static int[] countPairs(int n, int[][] edges, int[] queries) {
        int[] point_num = new int[n];
        Map<String,Integer> repeat = new HashMap<>();
        for (int[] edge : edges) {
            String key1 = (edge[0]-1) + "|" + (edge[1]-1);
            repeat.put(key1,repeat.containsKey(key1) ? repeat.get(key1)+1 : 1);
            point_num[edge[0]-1] += 1;
            point_num[edge[1]-1] += 1;
        }
        Map<Integer,Integer> queryMap = new HashMap<>();
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                Integer result = point_num[i] + point_num[j] - (repeat.get(i + "|" + j) == null ? 0 : repeat.get(i + "|" + j)) - (repeat.get(j + "|" + i) == null ? 0 : repeat.get(j + "|" + i));
                queryMap.put(result,queryMap.containsKey(result) ? queryMap.get(result)+1:1);
            }
        }
        int[] result = new int[queries.length];
        Set<Map.Entry<Integer, Integer>> entries = queryMap.entrySet();
        for (int i = 0; i < queries.length; i++) {
            for (Map.Entry<Integer, Integer> entry : entries) {
                if(entry.getKey()>queries[i]) result[i] += entry.getValue();
            }
        }
        return result;
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

    //849. 到最近的人的最大距离
    public static int maxDistToClosest(int[] seats) {
        int begin = 0,end=0,max = 0,now = 0;
        for (int i = 0; seats[i] == 0; i++) {
            begin++;
        }
        for (int i = seats.length-1; seats[i] == 0; i--) {
            end++;
        }
        for (int i = begin; i < seats.length-end; i++) {
            if(seats[i] == 1){
                max = Math.max(now,max);
                now = 0;
                continue;
            }
            now++;
        }
        max = max%2 == 1?max/2+1 : max/2;
        max = Math.max(max,Math.max(begin,end));
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

    //447. 回旋镖的数量
    public int numberOfBoomerangs(int[][] points) {
        int ans = 0;
        for (int[] p : points) {
            Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
            for (int[] q : points) {
                int dis = (p[0] - q[0]) * (p[0] - q[0]) + (p[1] - q[1]) * (p[1] - q[1]);
                cnt.put(dis, cnt.getOrDefault(dis, 0) + 1);
            }
            for (Map.Entry<Integer, Integer> entry : cnt.entrySet()) {
                int m = entry.getValue();
                ans += m * (m - 1);
            }
        }
        return ans;
    }

    public static void sort(int arr[], int left, int right){
        if(left >= right){
            return ;
        }
        int index = quickSort(arr,left,right);
        quickSort(arr,left,index-1);
        quickSort(arr,index+1,right);
    }

    public static int quickSort(int arr[],int left,int right){
        int start = left;
        int check = arr[left];
        while (left < right){
            while (arr[right] >= check && left < right){
                right--;
            }
            while (arr[left] <= check && left < right){
                left++;
            }
            if(left<right){
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }
        int temp = arr[start];
        arr[start] = arr[left];
        arr[left] = temp;
        return left;
    }

    //179. 最大数
    public static String largestNumber(int[] nums) {
        if(nums.length == 1){
            return String.valueOf(nums[0]);
        }
        List<Integer> list = new ArrayList<>();
        boolean flag = false;
        for (int num : nums) {
            if(num > 0){
                flag = true;
            }
        }

        if(!flag){
            return "0";
        }

        Collections.sort(list, (a, b) ->
            (String.valueOf(a) + String.valueOf(b)).compareTo(String.valueOf(b) + String.valueOf(a))
        );

        StringBuilder sb = new StringBuilder("");

        for (Integer integer : list) {
            sb.append(integer);
        }

        return sb.toString();
    }


    //3159. 查询数组中元素的出现位置
    public static int[] occurrencesOfElement(int[] nums, int[] queries, int x) {
        List<Integer> indexRecord = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == x){
                indexRecord.add(i);
            }
        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            if(queries[i] > indexRecord.size()){
                res[i] = -1;
                continue;
            }
            res[i] = indexRecord.get(queries[i]-1);
        }
        return res;
    }





}
