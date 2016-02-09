package net.leanelephant.exception;

import net.leanelephant.Transaction;

public class TransactionExistsException extends Exception {

    private final Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionExistsException(Transaction transaction) {
        this.transaction = transaction;
    }
}
