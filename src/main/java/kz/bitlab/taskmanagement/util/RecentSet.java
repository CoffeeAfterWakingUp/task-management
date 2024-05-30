package kz.bitlab.taskmanagement.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class RecentSet<T> implements Iterable<T> {

    private LinkedHashSet<T> set;
    private LinkedList<T> list;

    public RecentSet() {
        this.set = new LinkedHashSet<>();
        this.list = new LinkedList<>();
    }

    public void add(T element) {
        if (set.contains(element)) {
            list.remove(element);
        } else {
            set.add(element);
        }
        list.addFirst(element);
    }

    public boolean contains(T element) {
        return set.contains(element);
    }

    public void remove(T element) {
        if (set.contains(element)) {
            set.remove(element);
            list.remove(element);
        }
    }

    public int size() {
        return set.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
