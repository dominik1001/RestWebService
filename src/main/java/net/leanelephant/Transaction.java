package net.leanelephant;

import com.owlike.genson.annotation.JsonIgnore;
import com.owlike.genson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private Long id;

    private double amount;

    private String type;

    @JsonIgnore
    private Transaction parent;

    @JsonProperty("parent_id")
    private Long parentId;

    @JsonIgnore
    private final List<Transaction> childTransactions = new ArrayList<Transaction>();

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Transaction() {

    }

    public Transaction(Long id, double amount, String type, Long parentId) {
        this();

        this.id = id;
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    public Transaction(double amount, String type) {
        this(null, amount, type, null);
    }

    public Transaction(long id, double amount, String type) {
        this(id, amount, type, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Transaction getParent() {
        return parent;
    }

    public void setParent(Transaction parent) {
        this.parent = parent;
    }

    public void addChildTransaction(Transaction transaction) {
        childTransactions.add(transaction);
    }

    public double totalSum() {
        double sum = amount;
        for (Transaction transaction : childTransactions) {
            sum += transaction.totalSum();
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", parent=" + parent +
                ", parentId=" + parentId +
                ", childTransactions=" + childTransactions +
                '}';
    }
}
