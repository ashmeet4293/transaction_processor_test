package com.demo.induction.transactionprocessor.model;

public class Violation {
    private int order;
    private String property;
    private String description;

    public Violation() {
    }

    public Violation(int order, String property, String description) {
        this.order = order;
        this.property = property;
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "order=" + order +
                ", property='" + property + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
