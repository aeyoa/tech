package t01;

/**
 * Created by arsenykogan on 11/03/14.
 */
interface Queue<E> {

    void add(E elem);   // Добавляет элемент в очередь.

    E first();          // Выдает первый элемент из очереди, не удаляя его

    boolean contains(E elem); // Проверяет, есть ли элемент в очереди.

    E poll();           // Удаляет из очереди первый элемент и выдает его
    // в качестве результата.
}
