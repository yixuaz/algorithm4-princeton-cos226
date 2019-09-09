package part1.week4.symboltable.extracredit;

import org.junit.Assert;
import org.junit.Test;
import part1.week4.symboltable.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PreorderWithConstantSpaceTest {
    @Test
    public void basicTest() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(-2);
        root.right = new TreeNode(-3);
        List<TreeNode> res = PreorderWithConstantSpace.solve(root);
        Assert.assertEquals(3, res.size());
        Assert.assertEquals(root, res.get(0));
        Assert.assertEquals(root.left, res.get(1));
        Assert.assertEquals(root.right, res.get(2));
    }

    @Test
    public void basicTest2() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(-2);
        root.left.right = new TreeNode(-3);
        List<TreeNode> res = PreorderWithConstantSpace.solve(root);
        Assert.assertEquals(3, res.size());
        Assert.assertEquals(root, res.get(0));
        Assert.assertEquals(root.left, res.get(1));
        Assert.assertEquals(root.left.right, res.get(2));
    }

    @Test
    public void basicTest3() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(-2);
        root.left.left = new TreeNode(-3);
        List<TreeNode> res = PreorderWithConstantSpace.solve(root);
        Assert.assertEquals(3, res.size());
        Assert.assertEquals(root.left.left, res.get(2));
        Assert.assertEquals(root.left, res.get(1));
        Assert.assertEquals(root, res.get(0));
    }

    @Test
    public void randomTest() {
        int N = 100000;
        Random r = new Random();
        for (int i = 10; i < N; i += r.nextInt(i)) {
            TreeNode root = TreeNode.buildBT(i);
            List<TreeNode> res = PreorderWithConstantSpace.solve(root);
            List<TreeNode> expect = expect(root);
            Assert.assertArrayEquals(expect.toArray(new TreeNode[0]),
                    res.toArray(new TreeNode[0]));
        }
    }

    private List<TreeNode> expect(TreeNode root) {
        List<TreeNode> res = new ArrayList<>();
        preOrder(root, res);
        return res;
    }

    private void preOrder(TreeNode cur, List<TreeNode> res) {
        if (cur == null) return;
        res.add(cur);
        preOrder(cur.left, res);
        preOrder(cur.right, res);
    }


}