package part1.week4.symboltable;

import commonutil.Shuffler;

import java.util.Random;

public class TreeNode {
    private static final Random r = new Random();
    protected TreeNode left;
    protected TreeNode right;
    protected final int val;
    public TreeNode(int val) {
        this.val = val;
    }
    public static TreeNode buildBT(int n) {
        int[] cur = new int[n];
        for (int i = 0; i < n; i++) cur[i] = i;
        Shuffler.shuffle(cur);
        return buildBT(cur,0,n - 1);
    }
    private static TreeNode buildBT(int[] cur, int from, int to) {
        if (from > to) return null;
        int split = from + r.nextInt(to - from + 1);
        TreeNode root = new TreeNode(cur[split]);
        root.left = buildBT(cur,from,split - 1);
        root.right = buildBT(cur,split + 1, to);
        return root;
    }

    public static TreeNode buildBST(int n) {
        int[] cur = new int[n];
        for (int i = 0; i < n; i++) cur[i] = i;
        return buildBT(cur,0,n - 1);
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        generateBSTString(this, 0, res);
        return res.toString();
    }

    private void generateBSTString(TreeNode node, int depth, StringBuilder res){

        if(node == null){
            res.append(generateDepthString(depth) + "null\n");
            return;
        }

        res.append(generateDepthString(depth) + node.val +"\n");
        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0 ; i < depth ; i ++)
            res.append("~~");
        return res.toString();
    }
}
