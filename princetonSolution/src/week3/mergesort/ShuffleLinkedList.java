package week3.mergesort;

import java.util.Random;

public class ShuffleLinkedList {
    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
    public Node buildLinkedList(int n) {
        Node root = new Node(0);
        Node pre = root;
        for (int i = 1; i < n; i++, pre = pre.next) {
            pre.next = new Node(i);
        }
        return root;
    }

    public Node shuffle(Node list) {//TIME : O (N LOG N), SPACE : O (LOG N)
        if (list == null || list.next == null) return list;
        //split half
        Node slow = list, fast = list.next;
        int preL = 1;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            preL++;
            fast = fast.next.next;
        }
        int postL = preL;
        if (fast == null) {
            postL--;
        }
        Node post = slow.next, pre = list;
        slow.next = null;
        return merge(shuffle(pre),preL,shuffle(post),postL);
    }

    private Node merge(Node pre, int preL, Node post, int postL) {
        Random r = new Random();
        Node dummy = new Node(-1), p = dummy;
        for (;pre != null || post != null; p = p.next) {
            if (r.nextInt(preL + postL) < preL) {
                p.next = pre;
                pre = pre.next;
                preL--;
            } else {
                p.next = post;
                post = post.next;
                postL--;
            }
        }
        Node ret = dummy.next;
        dummy.next = null;
        return ret;
    }

//    public Node shuffle2(Node list) {//wrong answer in https://stackoverflow.com/questions/12167630/algorithm-for-shuffling-a-linked-list-in-n-log-n-time
//        if (list == null || list.next == null) return list;
//        //split half
//        Node slow = list, fast = list.next;
//        int preL = 1;
//        while (fast != null && fast.next != null) {
//            slow = slow.next;
//            fast = fast.next.next;
//            preL++;
//        }
//        Node post = slow.next, pre = list, dummy = new Node(-2);
//        slow.next = null;
//        pre = shuffle2(pre);
//        post = shuffle2(post);
//        if (fast == null) {
//            Random r = new Random();
//            int k = r.nextInt(preL);
//            Node curDummy = new Node(-1,post);
//            Node p = curDummy;
//            for (int i = 0; i < k; i++) {
//                p = p.next;
//            }
//            dummy.next = p.next;
//            p.next = dummy;
//            post = curDummy.next;
//        }
//
//        Node ret = merge2(pre, post);
//        Node head = new Node(-1, ret),p = head;
//        if (fast == null) {
//            while (p.next != dummy) {
//                p = p.next;
//            }
//            assert p.next == dummy;
//            p.next = dummy.next;
//            dummy.next = null;
//        }
//        ret = head.next;
//        head.next = null;
//       return ret;
//    }
//
//    private Node merge2(Node pre, Node post) {
//        Random r = new Random();
//        Node dummy = new Node(-1), p = dummy;
//        for (;pre != null || post != null; p = p.next) {
//            if (pre == null || (post != null && r.nextInt(2) < 1)) {
//                p.next = post;
//                post = post.next;
//            } else {
//                p.next = pre;
//                pre = pre.next;
//            }
//        }
//        Node ret = dummy.next;
//        dummy.next = null;
//        return ret;
//    }
}
