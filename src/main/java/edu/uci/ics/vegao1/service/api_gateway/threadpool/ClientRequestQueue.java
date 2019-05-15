package edu.uci.ics.vegao1.service.api_gateway.threadpool;

public class ClientRequestQueue {
    private ListNode head;
    private ListNode tail;

    public ClientRequestQueue() {

    }

    public synchronized void enqueue(ClientRequest clientRequest) {

    }

    public synchronized ClientRequest dequeue() {
        return null;
    }

    boolean isEmpty() {
        return false;
    }
}
