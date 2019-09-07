package part1.week3.mergesort;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Shuffling a linked list. Given a singly-linked list containing nn items,
 * rearrange the items uniformly at random. Your algorithm should consume a logarithmic (or constant) amount of
 * extra memory and run in time proportional to nlogn in the worst case.
 */
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
    //TIME : O (N LOG N), SPACE : O (LOG N)
    public Node shuffle(Node list) {
        //TODO: ADD YOUR CODE HERE
        return null;
    }


}
