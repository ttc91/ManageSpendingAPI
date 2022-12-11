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
    String MODEL_GET_LIST_BY_EXPIRED = "/get_list_by_expired";


    String ACCOUNT_SIGN_IN_DOMAIN = "/sign_in";
    String ACCOUNT_CHANGE_PASSWORD = "/change_password";
    String ACCOUNT_UPDATE_ROLE = "/update_role";

    String GOAL_DEPOSIT_DOMAIN = "/goal_deposit";




    String HISTORY_GET_LIST_BY_WITHDRAW_PIE_CHART = "/get_list_by_withdraw_pie";
    String HISTORY_GET_LIST_BY_RECHARGE_PIE_CHART = "/get_list_by_recharge_pie";
    String HISTORY_GET_LIST_BY_WITHDRAW_BAR_CHART = "/get_list_by_withdraw_bar";
    String HISTORY_GET_LIST_BY_EVENT = "/get_list_by_event";
    String HISTORY_GET_LIST_DAY_BY_EVENT =  "/get_list_day_by_event";
    String HISTORY_GET_LIST_BY_DATE =  "/get_list_by_date";
    String HISTORY_GET_LIST_DAY_IN_MONTH =  "/get_list_day_in_month";
    String HISTORY_GET_TOTAL_COST_OF_WITHDRAW = "/get_total_cost_of_withdraw";
    String HISTORY_GET_TOTAL_COST_OF_RECHARGE = "/get_total_cost_of_recharge";
    String HISTORY_GET_TOTAL_COST_BY_EVENT = "/get_total_cost_by_event";
    String HISTORY_GET_TOTAL_COST_BETWEEN_DATE = "/get_total_cost_between_date";
}
