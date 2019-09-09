package part1.week4.symboltable;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class IsBstTest {
    @Test
    public void basicTest() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(-2);
        root.right = new TreeNode(-3);
        Assert.assertEquals(IsBst.isBst(root), false);
    }

    @Test
    public void basicTest2() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(-2);
        root.left.right = new TreeNode(-3);
        Assert.assertEquals(IsBst.isBst(root), false);
    }

    @Test
    public void basicTest3() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(-2);
        root.left.left = new TreeNode(-3);
        Assert.assertEquals(IsBst.isBst(root), true);
    }

    @Test
    public void conerCaseTest4() {
        TreeNode root = null;
        Assert.assertEquals(IsBst.isBst(root), true);
        root = new TreeNode(Integer.MIN_VALUE);
        Assert.assertEquals(IsBst.isBst(root), true);
        root = new TreeNode(Integer.MAX_VALUE);
        Assert.assertEquals(IsBst.isBst(root), true);
        root.left = new TreeNode(Integer.MIN_VALUE);
        Assert.assertEquals(IsBst.isBst(root), true);
        root.left = new TreeNode(Integer.MAX_VALUE);
        Assert.assertEquals(IsBst.isBst(root), false);
    }

    @Test
    public void randomTest() {
        int N = 100000;
        Random r = new Random();
        for (int i = 10; i < N; i += r.nextInt(i)) {
            TreeNode bt = TreeNode.buildBT(i);
            InorderWithConstantSpace.solve(bt);
            TreeNode bst = TreeNode.buildBST(i);
            Assert.assertEquals(false, IsBst.isBst(bt));
            Assert.assertEquals(true, IsBst.isBst(bst));
        }
    }

}