package part1.week4.symboltable;

import java.util.ArrayList;
import java.util.List;

/**
 * there is an algorithm named morris.
 * the key thing is when we need iterate with left path, we need to add a link to its parent for backtracking.
 * when we at a node, we need to make it left child with rightmost child' right link to the parent.
 * why because in inorder iteration, when we finished iterate this node, we need to go back to this place.
 * then we need to remove link and go right.
 * if we succefully add the link we need to go left. and do same thing.
 * so the basic plan is:
 * 1. check this node's left child, if null, visit it, then go right
 * 2.  if not null, try add left child right most node right link to this node
 * 3. if add success, go left
 * 4. if add fail mean remove success(mean all the left subtree done), visit this node, then go right.
 * <p>
 * basic plan for AddOrRemoveLinkToParent
 * 1. find the left's right most child
 * 2. check it's right link ==  parent.
 * 3. if  == parent, remove link then return add is false.
 * 4. if != parent, add link then return add is true
 */
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
            boolean add = addOrRemoveLinkToParent(p, p.left);
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
