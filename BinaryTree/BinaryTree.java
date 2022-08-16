package com.cmatthar.algorithms.trees.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class BinaryTree {

    Node root;

    public static void main(String[] args) {

        /*
        BinaryTree bt = createBinaryTree();

        System.out.println("\nTraverse In Order: ");
        bt.traverseInOrder(bt.root);

        System.out.println("\nTraverse Pre Order: ");
        bt.traversePreOrder(bt.root);

        System.out.println("\nTraverse Post Order: ");
        bt.traversePostOrder(bt.root);

        System.out.println("\nTraverse Level Order: ");
        bt.traverseLevelOrder();

        System.out.println("\nTraverse In Order after Swap Node: ");
        bt.swapEveryKLevel(bt.root, 2);
        bt.traverseInOrder(bt.root);

        */


        //////////////////////////////////////////////////////////////////////

        List<List<Integer>> indexes = new ArrayList<>();
        List<Integer> nodesPair;

        nodesPair = new ArrayList<>();
        nodesPair.add(2);
        nodesPair.add(3);
        indexes.add(nodesPair);

        nodesPair = new ArrayList<>();
        nodesPair.add(-1);
        nodesPair.add(-1);
        indexes.add(nodesPair);
        indexes.add(nodesPair);

        BinaryTree binaryTree = createBinaryTreeSwap(indexes);

        System.out.println("\nTraverse In Order: ");
        binaryTree.traverseInOrder(binaryTree.root);

        System.out.println("\nTraverse In Order after Swap Node: ");
        binaryTree.swapEveryKLevel(binaryTree.root, 1);
        binaryTree.traverseInOrder(binaryTree.root);

        binaryTree.swapEveryKLevel(binaryTree.root, 1);
        binaryTree.traverseInOrder(binaryTree.root);
    }

    private static BinaryTree createBinaryTreeSwap(List<List<Integer>> indexes) {

        BinaryTree bt = new BinaryTree();

        bt.add(1);

        Node currentLeft = bt.root;
        Node currentRight = bt.root;

        for (int i = 0; i < indexes.size(); i++) {
            int left = indexes.get(i).get(0);
            int right = indexes.get(i).get(1);
            if (left > 0) {
                currentLeft = bt.addLeft(currentLeft, left);
            }
            if (right > 0) {
                currentRight = bt.addRight(currentRight, right);
            }
        }
        return bt;
    }

    private static BinaryTree createBinaryTree() {

        BinaryTree bt = new BinaryTree();

        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);

        return bt;
    }

    public static int height(Node root) {
        // Write your code here.
        int lvLeft = root.left != null ? 1 : 0;
        int lvRight = root.right != null ? 1 : 0;

        if (root.left != null) {
            lvLeft += height(root.left);
        }

        if (root.right != null) {
            lvRight += height(root.right);
        }

        return Math.max(lvLeft, lvRight);
    }

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        } else {
            // value already exists
            return current;
        }

        return current;
    }

    private Node addLeft(Node current, int value) {
        current.left = new Node(value);
        return current;
    }

    private Node addRight(Node current, int value) {
        current.right = new Node(value);
        return current;
    }

    public void add(int value) {
        root = addRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }
        if (value == current.value) {
            return true;
        }
        return value < current.value
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    public boolean containsNode(int value) {
        return containsNodeRecursive(root, value);
    }

    private Node deleteRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }

        if (value == current.value) {
            // Node to delete found
            // ... code to delete the node will go here
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }
            int smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
            return current;
        }
        if (value < current.value) {
            current.left = deleteRecursive(current.left, value);
            return current;
        }
        current.right = deleteRecursive(current.right, value);
        return current;
    }

    private int findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(" " + node.value);
            traverseInOrder(node.right);
        }
    }

    public void traversePreOrder(Node node) {
        if (node != null) {
            System.out.print(" " + node.value);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    public void traversePostOrder(Node node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.print(" " + node.value);
        }
    }

    public void traverseLevelOrder() {
        if (root == null) {
            return;
        }

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {

            Node node = nodes.remove();

            System.out.print(" " + node.value);

            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.right != null) {
                nodes.add(node.right);
            }
        }
    }

    private static void swapEveryKLevelUtil(Node root, int level, int k) {
        // base case
        if (root == null || (root.left == null && root.right == null))
            return;

        //if current level + 1 is present in swap vector
        //then we swap left & right node
        if ((level + 1) % k == 0) {
            Node temp = root.left;
            root.left = root.right;
            root.right = temp;
        }

        // Recur for left and right subtrees
        swapEveryKLevelUtil(root.left, level + 1, k);
        swapEveryKLevelUtil(root.right, level + 1, k);
    }

    static void swapEveryKLevel(Node root, int k)
    {
        // call swapEveryKLevelUtil function with
        // initial level as 1.
        swapEveryKLevelUtil(root, 1, k);
    }

    static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

}
