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
        this.notify();
    }

    public synchronized ClientRequest dequeue() throws InterruptedException {
        while (true) {
            if (!this.isEmpty()) {
                ListNode currentHead = this.head;
                this.head = currentHead.getNext();

                return currentHead.getClientRequest();
            }
            this.wait();
        }
    }

    synchronized boolean isEmpty() {
        return head == null || tail == null;
    }
}
