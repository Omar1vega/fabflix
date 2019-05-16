package edu.uci.ics.vegao1.service.api_gateway.threadpool;

public class ClientRequestQueue {
    private ListNode head;
    private ListNode tail;

    public ClientRequestQueue() {
        this.head = null;
        this.tail = null;
    }

    public synchronized void enqueue(ClientRequest clientRequest) {
        ListNode newTail = new ListNode(clientRequest, null);
        if (this.isEmpty()) {
            this.head = newTail;
        } else {
            tail.setNext(newTail);
        }
        this.tail = newTail;
    }

    public synchronized ClientRequest dequeue() {
        if (this.isEmpty()) {
            return null;
        }

        ListNode currentHead = this.head;
        this.head = currentHead.getNext();

        return currentHead.getClientRequest();
    }

    boolean isEmpty() {
        return head == null || tail == null;
    }
}
