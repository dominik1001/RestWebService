package net.leanelephant;

public class TransactionExistsException extends Exception {

    private final Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionExistsException(Transaction transaction) {
        this.transaction = transaction;
    }
}
