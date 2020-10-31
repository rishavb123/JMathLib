package io.bhagat.math.graphtheory;

import io.bhagat.math.linearalgebra.Matrix;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Graph<T> {

    private Node<T> head;

    /**
     * Create an empty graph
     */
    public Graph() {}

    /**
     * Create a graph with data at the head
     * @param data the data
     */
    public Graph(T data) {
        this(new Node<T>(data));
    }

    /**
     * Create a graph with a node at the head
     * @param head the head node
     */
    public Graph(Node<T> head) {
        this.head = head;
    }

    /**
     * Uses a depth first search for the data specified
     * @param data the data to search for
     * @return if the data is in the graph
     */
    public boolean contains(T data) {
        Set<Node<T>> set = new HashSet<>();
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(head);
        while (true) {
            Node<T> node = queue.poll();
            set.add(node);
            assert node != null;
            if (node.getData().equals(data)) {
                return true;
            }
            for (Node<T> child: node.getConnections()) {
                if (!set.contains(child)) {
                    queue.add(child);
                }
            }
            if (queue.isEmpty()) {
                return false;
            }
        }
    }

    /**
     * Uses a depth first search to check if the graph is a tree
     * @return if the data is in the graph
     */
    public boolean isTree() {
        Set<Node<T>> set = new HashSet<>();
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(head);
        while (true) {
            Node<T> node = queue.poll();
            if(set.contains(node)) {
                return false;
            }
            set.add(node);
            assert node != null;
            queue.addAll(node.getConnections());
            if (queue.isEmpty()) {
                return true;
            }
        }
    }

    // TODO: Adjacency Matrix

    /**
     * Sets the head of the node
     * @param head the head
     */
    public void setHead(Node<T> head) {
        this.head = head;
    }

    /**
     * Gets the head node
     * @return the head node
     */
    public Node<T> getHead() {
        return head;
    }
}
