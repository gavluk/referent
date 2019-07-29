package ua.com.gavluk.commons;

import java.util.List;

public class PageableEntityList<T> {

    private final List<T> list;

    public PageableEntityList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        // todo: return immutable list
        return this.list;
    }
}
