package t01;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Арсений Коган, группа 2742
 * Created by arsenykogan on 11/03/14.
 */
public class Main {

    private static int numberOfTests = 10000;

    public static void main(final String[] args) {
        Object queue = null;
        try {

            /* Trying to create class specified by user */
            final Class<?> queueClass = Class.forName("t01." + args[0]);

            /* If there are two arguments we get constructor with parameters to create ArrayQueue */
            if (args.length == 2) {
                final Constructor constructor = queueClass.getConstructor(Integer.class);
                queue = constructor.newInstance(Integer.parseInt(args[1])); // ArrayQueue created
            } else {
                queue = queueClass.newInstance(); // CircleQueue created
            }

        } catch (final ClassNotFoundException e) {
            System.out.println("Such an implementation is not found!\nTry [ArrayQueue <buffer size>] or [CircleQueue]");
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final NoSuchMethodException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }

        /* Define Methods for add(), first(), contains() and poll() */
        Method add = null;
        Method poll = null;
        Method first = null;
        Method contains = null;
        try {
            add = queue.getClass().getMethod("add", Object.class);
            poll = queue.getClass().getMethod("poll");
            first = queue.getClass().getMethod("first");
            contains = queue.getClass().getMethod("contains", Object.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        /* Invoke these methods many times and record time using Timer */
        final Timer timer = new Timer();
        int size = (args.length == 2) ? Math.min(10, Integer.parseInt(args[1])) : 10;
        try {
            timer.start();
            for (int i = 0; i < numberOfTests; i++) {
                for (int j = 0; j < size; j++) {

                    // Add some elements to the queue
                    add.invoke(queue, j);

                    // Check if the queue contains this element
                    if (contains.invoke(queue, j).toString().equals(false)) {
                        throw new Error();
                    }

                    // Check that first element is 0
                    if (!first.invoke(queue).toString().equals("0")) {
                        throw new Error();
                    }
                }

                // Poll all elements from queue
                for (int j = 0; j < size; j++) {
                    poll.invoke(queue);
                }
            }
            System.out.println(timer.stop()); // Print timer result
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private static class Timer {

        private double time;

        private void start() {
            time = System.currentTimeMillis();
        }

        private double stop() {
            return System.currentTimeMillis() - time;
        }
    }
}
