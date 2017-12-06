package com.xs.data;

public interface DuplicateRemover<ID> {

    boolean isDuplicate(ID id);

    void resetDuplicateCheck();

    int getTotalCount();

}
