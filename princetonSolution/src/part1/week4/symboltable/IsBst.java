package part1.week4.symboltable;

/**
 * two solution, first we can use inorder to check every element is in sorted order.
 * another is to pass a range into recursive function,
 * in every function, we need to check the node is in the range,
 * and dfs its left subtree with new range and same with right subtree.
 */
public class IsBst {

    public static boolean isBst(TreeNode root) {
        return help(root, null, null);
    }

    private static boolean help(TreeNode cur, Integer min, Integer max) {
        if (cur == null) return true;
        if (min != null && cur.val <= min) return false;
        if (max != null && cur.val >= max) return false;
        return help(cur.left, min, cur.val) && help(cur.right, cur.val, max);
    }
}
