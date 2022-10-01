package com.example.managespending.utils.enums;

public enum HistoryType {

    G("GOAL"),
    D("DISBURSE"),
    I("INCOME"),
    C("CREATE"),
    R("REMOVE");

    public String value;

    HistoryType(String value){
        this.value = value;
    }

}
