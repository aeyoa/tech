package t01;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by arsenykogan on 12/03/14.
 */
public class ArrayQueueTest extends Assert {

    @Test
    public void emptyQueuePoll() {
        final Queue<Integer> queue = new ArrayQueue<Integer>(10);
        assertNull(queue.poll());
    }

    @Test
    public void emptyQueueFirstPeek() {
        final Queue<Integer> queue = new ArrayQueue<Integer>(10);
        assertNull(queue.first());
    }

    @Test
    public void regularQueue1() {
        final Queue<Integer> queue = new ArrayQueue<Integer>(10);
        queue.add(1);
        queue.add(2);
        queue.add(3);

        for (int i = 0; i < 10; i++) {
            assertEquals(1, (int) queue.first());
        }

        assertEquals(1, (int) queue.poll());
        assertEquals(2, (int) queue.poll());
        assertEquals(3, (int) queue.poll());
    }

    @Test
    public void regularQueue2() {
        final Queue<Integer> queue = new ArrayQueue<Integer>(10);
        queue.add(1);
        queue.add(2);
        queue.add(3);

        for (int i = 0; i < 10; i++) {
            assertEquals(1, (int) queue.first());
        }

        assertEquals(1, (int) queue.poll());
        assertEquals(2, (int) queue.poll());
        assertEquals(3, (int) queue.poll());

        queue.add(4);
        queue.add(5);
        queue.add(6);

        assertEquals(4, (int) queue.poll());
        assertEquals(5, (int) queue.poll());
        assertEquals(6, (int) queue.poll());


    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void queueHasNoSpace1() {
        final Queue<Integer> queue = new ArrayQueue<Integer>(2);
        queue.add(1);
        queue.add(2);
        queue.add(3);
    }

    @Test
    public void queueFreeSpace() {
        final Queue<Integer> queue = new ArrayQueue<Integer>(2);
        queue.add(1);
        queue.add(2);
        assertEquals(1, (int) queue.poll());
        assertEquals(2, (int) queue.poll());
        queue.add(3);

    }

    @Test
    public void containsTest() {
        final Queue<String> queue = new ArrayQueue<String>(2);
        queue.add("Helvetica");
        queue.add("Open Sans");
        assertTrue(queue.contains("Helvetica"));
        assertFalse(queue.contains("Arial"));
    }

    @Test
    public void circularity1() {
        final Queue<Integer> queue = new ArrayQueue<Integer>(2);
        queue.add(1);
        queue.add(2);
        assertEquals(1, (int) queue.poll());
        queue.add(3);

    }

    @Test
    public void circularity2() {
        final Queue<Integer> queue = new ArrayQueue<Integer>(3);
        for (int i = 0; i < 10; i++) {
            queue.add(1);
            queue.add(2);
            queue.add(3);
            assertEquals(1, (int) queue.poll());
            assertEquals(2, (int) queue.poll());
            assertEquals(3, (int) queue.poll());
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void zeroSizeQueue() {
        final Queue<Integer> queue = new ArrayQueue<Integer>(0);
        queue.add(0);
    }

    @Test
    public void notContainsDeleted() {
        final Queue<String> queue = new ArrayQueue<String>(10);
        queue.add("Epsilon");
        queue.add("Alpha");
        queue.add("Beta");
        assertTrue(queue.contains("Alpha"));
        assertTrue(queue.contains("Beta"));
        assertTrue(queue.contains("Epsilon"));
        assertEquals("Epsilon", queue.poll());
        assertFalse(queue.contains("Epsilon"));
        assertFalse(queue.contains("Omega"));
    }
}
