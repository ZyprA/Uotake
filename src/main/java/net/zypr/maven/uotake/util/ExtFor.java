package net.zypr.maven.uotake.util;

import java.util.Iterator;

public class ExtFor {

    public static <T> Iterable<ExtFor.Elem<T>> loop(final Iterable<T> list) {
        return new Iterable<ExtFor.Elem<T>>() {
            @Override
            public Iterator<Elem<T>> iterator() {
                return ExtFor.iterator(list);
            }
        };
    }

    private static <T> Iterator<ExtFor.Elem<T>> iterator(final Iterable<T> list) {

        return new Iterator<ExtFor.Elem<T>>() {
            private int counter = 0;
            private Iterator<T> iterator = list.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Elem<T> next() {
                Elem<T> elem = new Elem<T>(counter++, iterator.next());
                return elem;
            }

            @Override
            public void remove() {
                throw new RuntimeException();
            }
        };
    }


    public static class Elem<T> {
        private final int index;
        private final T value;
        private Elem(int index, T value) {
            this.index = index;
            this.value = value;
        }
        public int index() {
            return this.index;
        }
        public T value() {
            return this.value;
        }
    }
}
