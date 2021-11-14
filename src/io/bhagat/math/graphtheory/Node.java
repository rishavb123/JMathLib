package io.bhagat.math.graphtheory;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in a graph.
 * @param <T> The type of the node's value.
 */
public class Node<T> {

    private static final int DEFAULT_INITIAL_CONNECTION_CAPACITY = 5;

    private T data;
    private List<Node<T>> connections;

    /**
     * Creates a node object with null data and default initial connection capacity
     */
    public Node() {
        this(null, DEFAULT_INITIAL_CONNECTION_CAPACITY);
    }

    /**
     * Creates a node object with data and default initial connection capacity
     * @param data the data in the node
     */
    public Node(T data) {
        this(data, DEFAULT_INITIAL_CONNECTION_CAPACITY);
    }

    /**
     * Creates a node object with data and initial connection capacity
     * @param data the data in the node
     * @param connectionsCapacity the initial connection capacity
     */
    public Node(T data, int connectionsCapacity) {
        this.data = data;
        connections = new ArrayList<>(connectionsCapacity);
    }

    /**
     * Creates a node for data and connects it
     * @param data the data
     */
    public void connect(T data) {
        connect(new Node<>(data));
    }

    /**
     * Connects this node to another node (in one direction)
     * @param node the node to connect this node to
     */
    public void connect(Node<T> node) {
        connections.add(node);
    }

    /**
     * Get a connection by index
     * @param index the index
     * @return the node this node is connected to
     */
    public Node<T> getConnection(int index) {
        return connections.get(index);
    }

    /**
     * Get a connection by index
     * @param index the index
     * @param node the node to connect this node to
     */
    public void setConnection(int index, Node<T> node) {
        connections.set(index, node);
    }

    /**
     * Get all the nodes this node is connected to
     * @return a list of the connected nodes
     */
    public List<Node<T>> getConnections() {
        return connections;
    }

    /**
     * Get the data in the node
     * @return the data to get
     */
    public T getData() {
        return data;
    }

    /**
     * Set the data in the node
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * toStrign method
     * @return the string representation of the node
     */
    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
