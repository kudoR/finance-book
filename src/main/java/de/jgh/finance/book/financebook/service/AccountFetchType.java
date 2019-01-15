package de.jgh.finance.book.financebook.service;

public enum AccountFetchType {

    FULL("KUmsAll"), NEW("KUmsNew");

    private String stringValue;

    AccountFetchType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String value() {
        return this.stringValue;
    }
}
