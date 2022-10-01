package com.example.managespending.utils.enums;

public enum HistoryAction {

    W("WITHDRAW"),
    R("RECHARGE");

    public String value;

    HistoryAction(String value){
        this.value = value;
    }

}
