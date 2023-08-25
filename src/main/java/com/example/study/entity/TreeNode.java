package com.example.study.entity;

/**
 * @Author: LongX
 * @Date: 2023/8/25 10:35
 * @Description: TreeNode TODO
 * @Version: 1.0
 **/
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
