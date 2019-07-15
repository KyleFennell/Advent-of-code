import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class CircularLinkedList<E> extends LinkedList<E>{

    public CircularLinkedList(){
        super();
    }

    @Override
    public ListIterator<E> listIterator(){
        return new CLLIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index){
        return new CLLIterator(index);
    }

    @Override
    public String toString(){
        Iterator<E> it = super.listIterator(0);
        String out = "";
        while(it.hasNext()){
            out += it.next() + " ";
        }
        return out;
    }

    private class CLLIterator implements ListIterator<E> {

        private ListIterator<E> superIt;

        private CLLIterator(){
            superIt = CircularLinkedList.super.listIterator(0);
        }

        private CLLIterator(int index){
            superIt = CircularLinkedList.super.listIterator(index);
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public E next() {
//            System.out.println("next");
            if (CircularLinkedList.super.size() == 0){
//                System.out.println("List is empty");
                return null;
            }
            if(superIt.hasNext()){
//                System.out.println("hasNext: " + superIt.nextIndex());
                return superIt.next();
            }
//            System.out.println("noNext");
            while(superIt.hasPrevious()){superIt.previous();}
            return superIt.next();
        }

        @Override
        public boolean hasPrevious() {
            return true;
        }

        @Override
        public E previous() {
//            System.out.println("previous");
            if (CircularLinkedList.super.size() == 0){
//                System.out.println("List is empty");
                return null;
            }
            if (superIt.hasPrevious()){
//                System.out.println("hasPrevious: " + superIt.previousIndex());
                return superIt.previous();
            }
//            System.out.println("noPrevious");
            while(superIt.hasNext()){superIt.next();}
            return superIt.previous();
        }

        @Override
        public int nextIndex() {
            if (superIt.nextIndex() == CircularLinkedList.super.size()){
                return 0;
            }
            return superIt.nextIndex();
        }

        @Override
        public int previousIndex() {
            int listSize = CircularLinkedList.super.size();
            if (superIt.previousIndex() == -1){
                return listSize-1;
            }
            return superIt.previousIndex();
        }

        @Override
        public void remove() {
            superIt.remove();
        }

        @Override
        public void set(E e) {
            superIt.set(e);
        }

        @Override
        public void add(E e) {
            superIt.add(e);
        }
    }
}
