package t01;

/**
 * Created by arsenykogan on 11/03/14.
 */
public class ArrayQueue<E> implements Queue<E> {

    private int freeSpace;
    private int first;
    private int last;
    private final Object[] queue;
    private final int size;

    public ArrayQueue(final Integer size) {
        this.last = -1;
        this.first = 0;
        this.freeSpace = size;
        queue = new Object[size];
        this.size = size;
    }


    /* Simple "circularity" implemented using mod and freeSpace counter.
    * If there is some freSpace but last index equals to array size
    * than last index simply "jumps" to zero. */
    @Override
    public void add(final E elem) {
        if (freeSpace > 0) {
            last = ++last % size;
            queue[last] = elem;
            freeSpace--;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public E first() {
        if (last >= first) {
            return (E) queue[first];
        } else {
            return null;
        }
    }

    @Override
    public boolean contains(final E elem) {
        for (int i = first; i <= last; i++) {
            if (queue[i].equals(elem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E poll() {
        final E res;
        if (last >= first) {
            res = (E) queue[first];
            first = ++first % size;
            freeSpace++;
            return res;
        } else {
            return null;
        }
    }
}
