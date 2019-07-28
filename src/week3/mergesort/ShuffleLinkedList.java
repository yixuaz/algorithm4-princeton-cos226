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
        //TODO: ADD YOUR CODE HERE
        return null;
    }



}
