package com.example.managespending.utils;

public interface ApiPaths {

    String ACCOUNT_DOMAIN = "/account";
    String WALLET_DOMAIN = "/wallet";
    String EXPENSE_DOMAIN = "/expense";
    String GOAL_DOMAIN = "/goal";
    String BUDGET_DOMAIN = "/budget";
    String EVENT_DOMAIN = "/event";
    String HISTORY_DOMAIN = "/history";
    String ROLE_DOMAIN = "/role";

    String MODEL_CREATE_DOMAIN = "/create";
    String MODEL_UPDATE_DOMAIN = "/update";
    String MODEL_DELETE_DOMAIN = "/delete";
    String MODEL_GET_ONE_DOMAIN = "/get";
    String MODEL_GET_LIST_DOMAIN = "/get_list";
    String MODEL_GET_LIST_BY_STATUS = "/get_list_by_status";


    String ACCOUNT_SIGN_IN_DOMAIN = "/sign_in";
    String ACCOUNT_CHANGE_PASSWORD = "/change_password";
    String ACCOUNT_UPDATE_ROLE = "/update_role";

    String GOAL_DEPOSIT_DOMAIN = "/goal_deposit";




    String HISTORY_GET_LIST_BY_WITHDRAW_PIE_CHART = "/get_list_by_withdraw_pie";
    String HISTORY_GET_LIST_BY_RECHARGE_PIE_CHART = "/get_list_by_recharge_pie";
    String HISTORY_GET_LIST_BY_WITHDRAW_BAR_CHART = "/get_list_by_withdraw_bar";
    String HISTORY_GET_LIST_BY_WEEK = "/get_list_by_week";
    String HISTORY_GET_LIST_BY_MONTH =  "/get_list_by_month";
    String HISTORY_GET_LIST_BY_DAY =  "/get_list_by_day";
}
