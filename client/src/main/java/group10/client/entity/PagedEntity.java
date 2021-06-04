package group10.client.entity;

import java.util.List;

/**
 * Generic class of Paged Entity for parsing JSON responses from the server
 * Developed according to the Pageable interface of Spring.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class PagedEntity<T> {

    /**
     * Inner class to hold sort status of the entity
     *
     * @author Alperen Caykus, Mustafa Ozan Alpay
     */
    public static class PageSort {
        /**
         * Field to indicate if the page is sorted.
         */
        private boolean sorted;
        /**
         * Field to indicate if the page is unsorted.
         */
        private boolean unsorted;
        /**
         * Field to indicate if the page is empty.
         */
        private boolean empty;

        /**
         * Gets sorted
         *
         * @return sorted
         */
        public boolean isSorted() {
            return sorted;
        }

        /**
         * Sets sorted
         *
         * @param sorted new value of sorted
         */
        public void setSorted(boolean sorted) {
            this.sorted = sorted;
        }

        /**
         * Gets unsorted
         *
         * @return unsorted
         */
        public boolean isUnsorted() {
            return unsorted;
        }

        /**
         * Sets unsorted
         *
         * @param unsorted new value of unsorted
         */
        public void setUnsorted(boolean unsorted) {
            this.unsorted = unsorted;
        }

        /**
         * Gets empty
         *
         * @return empty
         */
        public boolean isEmpty() {
            return empty;
        }

        /**
         * Sets empty
         *
         * @param empty new value of empty
         */
        public void setEmpty(boolean empty) {
            this.empty = empty;
        }
    }

    /**
     * Inner class to hold page details of the entity
     *
     * @author Alperen Caykus, Mustafa Ozan Alpay
     */
    public static class PageDetails {
        /**
         * Field to store page sort details
         */
        private PageSort sort;
        /**
         * Field to store offset from the beginning of the whole data
         */
        private long offset;
        /**
         * Field to store page number
         */
        private long pageNumber;
        /**
         * Field to store page size
         */
        private long pageSize;
        /**
         * Field to indicate if the response is paged
         */
        private boolean paged;
        /**
         * Field to indicate if the response is unpaged
         */
        private boolean unpaged;

        /**
         * Gets page sort
         *
         * @return page sort
         */
        public PageSort getPageSort() {
            return sort;
        }

        /**
         * Sets page sort
         *
         * @param pageSort new page sort
         */
        public void setPageSort(PageSort pageSort) {
            this.sort = pageSort;
        }

        /**
         * Gets offset
         *
         * @return offset
         */
        public long getOffset() {
            return offset;
        }

        /**
         * Sets offset
         *
         * @param offset new offset
         */
        public void setOffset(long offset) {
            this.offset = offset;
        }

        /**
         * Gets page number
         *
         * @return page number
         */
        public long getPageNumber() {
            return pageNumber;
        }

        /**
         * Sets page number
         *
         * @param pageNumber new page number
         */
        public void setPageNumber(long pageNumber) {
            this.pageNumber = pageNumber;
        }

        /**
         * Gets page size
         *
         * @return page size
         */
        public long getPageSize() {
            return pageSize;
        }

        /**
         * Sets page size
         *
         * @param pageSize new page size
         */
        public void setPageSize(long pageSize) {
            this.pageSize = pageSize;
        }

        /**
         * Gets paged
         *
         * @return paged
         */
        public boolean isPaged() {
            return paged;
        }

        /**
         * Sets paged
         *
         * @param paged new value of paged
         */
        public void setPaged(boolean paged) {
            this.paged = paged;
        }

        /**
         * Gets unpaged
         *
         * @return unpaged
         */
        public boolean isUnpaged() {
            return unpaged;
        }

        /**
         * Sets unpaged
         *
         * @param unpaged new value of unpaged
         */
        public void setUnpaged(boolean unpaged) {
            this.unpaged = unpaged;
        }
    }

    /**
     * Field for list of the generic type
     */
    private List<T> content;
    /**
     * Field for page details
     */
    private PageDetails pageable;
    /**
     * Field for total number of elements
     */
    private long totalElements;
    /**
     * Field for total number of pages
     */
    private long totalPages;
    /**
     * Flag to indicate if this page is the last one
     */
    private boolean last;
    /**
     * Field for page size
     */
    private long size;
    /**
     * Field for page number
     */
    private long number;
    /**
     * Field for sort status
     */
    private PageSort sort;
    /**
     * Field for number of elements
     */
    private long numberOfElements;
    /**
     * Flag to indicate if the page is the first one
     */
    private boolean first;
    /**
     * Flag to indicate if the page is empty
     */
    private boolean empty;

    /**
     * Gets list of the generic type
     *
     * @return List of Generic Type
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Sets content
     *
     * @param content new value of content
     */
    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * Gets page details
     *
     * @return page details
     */
    public PageDetails getPageDetails() {
        return pageable;
    }

    /**
     * Sets page details
     *
     * @param pageDetails new page details
     */
    public void setPageDetails(PageDetails pageDetails) {
        this.pageable = pageDetails;
    }

    /**
     * Gets total elements
     *
     * @return total elements
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Sets total elements
     *
     * @param totalElements new total elements
     */
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Gets total pages
     *
     * @return total pages
     */
    public long getTotalPages() {
        return totalPages;
    }

    /**
     * Sets total pages
     *
     * @param totalPages new total pages
     */
    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Gets last
     *
     * @return last
     */
    public boolean isLast() {
        return last;
    }

    /**
     * Sets last
     *
     * @param last new value of last
     */
    public void setLast(boolean last) {
        this.last = last;
    }

    /**
     * Gets size
     *
     * @return size
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets size
     *
     * @param size new size
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Gets number
     *
     * @return number
     */
    public long getNumber() {
        return number;
    }

    /**
     * Sets number
     *
     * @param number new value of number
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * Gets page sort
     *
     * @return page sort
     */
    public PageSort getPageSort() {
        return sort;
    }

    /**
     * Sets page sort
     *
     * @param pageSort new page sort
     */
    public void setPageSort(PageSort pageSort) {
        this.sort = pageSort;
    }

    /**
     * Gets number of elements
     *
     * @return number of elements
     */
    public long getNumberOfElements() {
        return numberOfElements;
    }

    /**
     * Sets number of elements
     *
     * @param numberOfElements new number of elements
     */
    public void setNumberOfElements(long numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    /**
     * Gets first
     *
     * @return first
     */
    public boolean isFirst() {
        return first;
    }

    /**
     * Sets first
     *
     * @param first new value of first
     */
    public void setFirst(boolean first) {
        this.first = first;
    }

    /**
     * Gets empty
     *
     * @return empty
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Sets empty
     *
     * @param empty new value of empty
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
