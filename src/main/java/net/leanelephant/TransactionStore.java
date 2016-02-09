package net.leanelephant;

import net.leanelephant.exception.ParentTransactionDoesNotExistException;
import net.leanelephant.exception.TransactionExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionStore {

    private final Map<Long, Transaction> transactionIdMap = new HashMap<Long, Transaction>();

    private final Map<String, List<Transaction>> transactionTypeMap = new HashMap<String, List<Transaction>>();

    public void addTransaction(Transaction transaction) throws TransactionExistsException, ParentTransactionDoesNotExistException {
        Transaction existingTransaction = transactionIdMap.get(transaction.getId());
        if (existingTransaction != null) {
            throw new TransactionExistsException(transaction);
        }

        if (transaction.getParentId() != null) {
            Transaction parentTransaction = transactionIdMap.get(transaction.getParentId());
            if (parentTransaction == null) {
                throw new ParentTransactionDoesNotExistException();
            }
            transaction.setParent(transaction);
        }

        transactionIdMap.put(transaction.getId(), transaction);

        List<Transaction> typeList = transactionTypeMap.get(transaction.getType());
        if (typeList == null) {
            typeList = new ArrayList<Transaction>();
            transactionTypeMap.put(transaction.getType(), typeList);

        }
        typeList.add(transaction);
    }

    public List<Long> getTransactionsByType(String type) {
        List<Transaction> typeList = transactionTypeMap.get(type);
        if (typeList == null) {
            typeList = new ArrayList<Transaction>();
        }
        List<Long> result = new ArrayList<Long>();
        for (Transaction transaction : typeList) {
            result.add(transaction.getId());
        }
        return result;
    }

    public double getTransactionSum(Long transactionId) {
        Transaction transaction = transactionIdMap.get(transactionId);
        if (transaction != null) {
            return transaction.totalSum();
        }
        return 0.0;
    }
}
