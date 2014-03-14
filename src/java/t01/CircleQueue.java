package t01;

/**
 * Created by arsenykogan on 11/03/14.
 */
public class CircleQueue<E> implements Queue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;


    public CircleQueue() {
        size = 0;
    }

    @Override
    public void add(final E elem) {
        if (size == 0) {
            final Node<E> node = new Node<E>(elem, null);
            node.setNext(node);
            head = node;
            tail = node;
            size++;
        } else {
            final Node<E> node = new Node<E>(elem, head);
            tail.setNext(node);
            tail = node;
            size++;
        }
    }

    @Override
    public E poll() {
        if (size > 0) {
            final E res = head.getElem();
            head = head.getNext();
            tail.setNext(head);
            size--;
            return res;
        } else {
            return null;
        }
    }

    @Override
    public E first() {
        if (size > 0) {
            return head.getElem();
        } else {
            return null;
        }
    }

    @Override
    public boolean contains(final E elem) {
        Node<E> node = head;
        for (int i = 0; i < size; i++) {
            if (node.getElem().equals(elem)) {
                return true;
            } else {
                node = node.getNext();
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String res = "";
        Node<E> node = head;
        for (int i = 0; i < size; i++) {
            final String in = (i == 0) ? "" : "->";
            res = res + in + node.getElem();
            node = node.getNext();
        }
        return res;
    }

    private class Node<E> {

        private final E elem;
        private Node next;

        private Node(final E elem, final Node next) {
            this.elem = elem;
            this.next = next;
        }

        public void setNext(final Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }

        public E getElem() {
            return elem;
        }
    }
}
