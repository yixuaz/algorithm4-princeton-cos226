package week3.mergesort;

import edu.princeton.cs.algs4.StdRandom;

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

//    public Node shuffle(Node list) {//TIME : O (N LOG N), SPACE : O (LOG N)
//        //TODO: ADD YOUR CODE HERE
//        return null;
//    }
    private void merge(Node lh, Node rh) {
        Node left = lh;
        Node right = rh;

        if (StdRandom.uniform(1) > 0) {
            lh = right;
            right = right.next;
        }
        else {
            left = left.next;
        }

        Node runner = lh;

        while (right != null || left != null) {
            if (left == null) {
                runner.next = right;
                right =right.next;
            }
            else if (right == null) {
                runner.next = left;
                left = left.next;
            }
            else if (StdRandom.uniform(1) > 0) {
                runner.next = right;
                right = right.next;
            }
            else {
                runner.next = left;
                left = left.next;
            }
            runner = runner.next;
        }
    }

    public void shuffle(Node head, int N) {
        if (N == 1) return;

        int k = 1;
        Node mid = head;
        while (k < N / 2) {
            mid = mid.next;
            k++;
        }
        Node rh = mid.next;
        mid.next = null;
        shuffle(head, N / 2);
        shuffle(rh, N - N / 2);
        merge(head, rh);
    }



}
