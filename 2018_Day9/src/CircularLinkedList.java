import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class CircularLinkedList<E> extends LinkedList<E>{

    public CircularLinkedList(){
        super();
    }

    @Override
    public ListIterator<E> listIterator(){

    }

    private class CLLIterator<E> implements ListIterator<E> {

        private int index =
        public CLLIterator<E>(LinkedList<E> list){

        }


        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public E next() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return true;
        }

        @Override
        public E previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {

        }
    }
}
