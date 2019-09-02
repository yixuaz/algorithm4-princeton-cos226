package part1.week4.symboltable;

import java.util.ArrayList;
import java.util.List;

public class InorderWithConstantSpace {
    public static List<TreeNode> solve(TreeNode root) {
        List<TreeNode> res = new ArrayList<>();
        TreeNode p = root;
        while (p != null) {
            if (p.left == null) {
                res.add(p);
                p = p.right;
                continue;
            }
            boolean add = addOrRemoveLinkToParent(p,p.left);
            if (add) {
                p = p.left;
            } else {
                res.add(p);
                p = p.right;
            }
        }
        return res;
    }

    private static boolean addOrRemoveLinkToParent(TreeNode p, TreeNode left) {
        while (left.right != null && left.right != p) {
            left = left.right;
        }
        if (left.right == p) {
            left.right = null;
            return false;
        }
        left.right = p;
        return true;
    }
}
