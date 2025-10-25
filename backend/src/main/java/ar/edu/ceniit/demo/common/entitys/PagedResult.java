package ar.edu.ceniit.demo.common.entitys;

import java.util.List;

public class PagedResult<T> {
    private final List<T> content;
    private final long totalElements;
    private final int totalPages;
    private final int number;
    private final int size;
    private final int numberOfElements;
    private final boolean first;
    private final boolean last;
    private final boolean empty;

    public PagedResult(List<T> content, long totalElements, int number, int size, int numberOfElements, boolean first, boolean last, boolean empty) {
        this.content = content;
        this.totalElements = totalElements;
        this.number = number;
        this.size = size;
        this.totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 0;
        this.numberOfElements = numberOfElements;
        this.first = first;
        this.last = last;
        this.empty = empty;
    }

    public List<T> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public boolean isEmpty() {
        return empty;
    }
}
