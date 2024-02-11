// Fady Zaki SDEV200 2/10/24

import java.util.*;

public class TwoWayLinkedList<E> implements MyList<E> {
    private Node<E> head, tail;
    private int size = 0; // Number of elements in the list

    public TwoWayLinkedList() {
    }

    public TwoWayLinkedList(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }

    @Override
    public void add(int index, E e) {
        if (index == 0) {
            addFirst(e);
        } else if (index >= size) {
            addLast(e);
        } else {
            Node<E> current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<>(e);
            (current.next).next = temp;
            temp.previous = current.next;
            (current.next).previous = current;
            size++;
        }
    }

    @Override
    public void add(E e) {
        addLast(e);
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.next = head;
        head = newNode;
        size++;
        if (tail == null) {
            tail = head;
        }
    }

    @Override
    public void addLast(E e) {
        if (tail == null) {
            head = tail = new Node<>(e);
        } else {
            tail.next = new Node<>(e);
            (tail.next).previous = tail;
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            return null;
        else if (index == 0)
            return head.element;
        else if (index == size - 1)
            return tail.element;
        else {
            Node<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.element;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size)
            return null;
        else if (index == 0)
            return removeFirst();
        else if (index == size - 1)
            return removeLast();
        else {
            Node<E> previous = head;
            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }
            Node<E> current = previous.next;
            previous.next = current.next;
            size--;
            return current.element;
        }
    }

    @Override
    public E removeFirst() {
        if (size == 0) return null;
        else {
            Node<E> temp = head;
            head = head.next;
            size--;
            if (head == null) tail = null;
            return temp.element;
        }
    }

    @Override
    public E removeLast() {
        if (size == 0) return null;
        else if (size == 1) {
            Node<E> temp = head;
            head = tail = null;
            size = 0;
            return temp.element;
        } else {
            Node<E> current = head;
            for (int i = 0; i < size - 2; i++) {
                current = current.next;
            }
            Node<E> temp = tail;
            tail = current;
            tail.next = null;
            size--;
            return temp.element;
        }
    }

    @Override
    public java.util.ListIterator<E> listIterator() {
        return new LinkedListIterator();
    }

    @Override
    public java.util.ListIterator<E> listIterator(int index) {
        return new LinkedListIterator(index);
    }

    private class LinkedListIterator implements java.util.ListIterator<E> {
        private Node<E> current = head; // Current index
        private int index = 0;

        public LinkedListIterator() {
        }

        public LinkedListIterator(int index) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E element = current.element;
            current = current.next;
            index++;
            return element;
        }

        @Override
        public boolean hasPrevious() {
            return (current != null && current.previous != null);
        }

        @Override
        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            current = current.previous;
            E element = current.element;
            index--;
            return element;
        }

        @Override
        public int nextIndex() {
            if (hasNext()) return index;
            else return size;
        }

        @Override
        public int previousIndex() {
            if (hasPrevious()) return index - 1;
            else return -1;
        }

        @Override
        public void remove() {
            if (current == null) throw new IllegalStateException();
            Node<E> prevNode = current.previous;
            Node<E> nextNode = current.next;
            if (prevNode != null) prevNode.next = nextNode;
            else head = nextNode;
            if (nextNode != null) nextNode.previous = prevNode;
            else tail = prevNode;
            size--;
            index--;
        }

        @Override
        public void set(E e) {
            if (current == null) throw new IllegalStateException();
            current.element = e;
        }

        @Override
        public void add(E e) {
            if (current == null) {
                addLast(e);
                current = tail;
            } else {
                Node<E> newNode = new Node<>(e);
                newNode.next = current.next;
                newNode.previous = current;
                if (current.next != null) current.next.previous = newNode;
                else tail = newNode;
                current.next = newNode;
                current = newNode;
                size++;
                index++;
            }
        }
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E e) {
            element = e;
        }
    }
}
