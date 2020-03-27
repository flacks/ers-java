package com.revature.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Reimbursement implements Serializable {
    private int reimbId;
    private double amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private String receipt;
    private User author;
    private User resolver;
    private int statusId;
    private int typeId;

    public Reimbursement() {
        super();
    }

    public Reimbursement(
            int reimbId,
            double amount,
            Timestamp submitted,
            Timestamp resolved,
            String description,
            String receipt,
            User author,
            User resolver,
            int statusId,
            int typeId
    ) {
        this.reimbId = reimbId;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.author = author;
        this.resolver = resolver;
        this.statusId = statusId;
        this.typeId = typeId;
    }

    public int getReimbId() {
        return reimbId;
    }

    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getResolver() {
        return resolver;
    }

    public void setResolver(User resolver) {
        this.resolver = resolver;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return reimbId == that.reimbId &&
                Double.compare(that.amount, amount) == 0 &&
                statusId == that.statusId &&
                typeId == that.typeId &&
                Objects.equals(submitted, that.submitted) &&
                Objects.equals(resolved, that.resolved) &&
                Objects.equals(description, that.description) &&
                Objects.equals(receipt, that.receipt) &&
                Objects.equals(author, that.author) &&
                Objects.equals(resolver, that.resolver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbId, amount, submitted, resolved, description, receipt, author, resolver, statusId, typeId);
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbId=" + reimbId +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt='" + receipt + '\'' +
                ", author=" + author +
                ", resolver=" + resolver +
                ", statusId=" + statusId +
                ", typeId=" + typeId +
                '}';
    }
}
