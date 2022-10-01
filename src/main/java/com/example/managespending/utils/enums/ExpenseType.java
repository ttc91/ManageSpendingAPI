package com.example.managespending.utils.enums;

public enum ExpenseType {

    D("DISBURSE"),
    I("INCOME");

    public final String value;

    ExpenseType(String value){
        this.value = value;
    }

}
