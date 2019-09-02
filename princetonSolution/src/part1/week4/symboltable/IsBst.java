package part1.week4.symboltable;

public class IsBst {

    public static boolean isBst(TreeNode root) {
        return help(root,null,null);
    }

    private static boolean help(TreeNode cur, Integer min, Integer max) {
        if (cur == null) return true;
        if (min != null && cur.val <= min) return false;
        if (max != null && cur.val >= max) return false;
        return help(cur.left,min,cur.val) && help(cur.right,cur.val,max);
    }
}
