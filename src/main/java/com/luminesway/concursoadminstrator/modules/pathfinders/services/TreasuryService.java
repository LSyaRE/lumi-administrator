package com.luminesway.concursoadminstrator.modules.pathfinders.services;

public interface TreasuryService {
    public void manageMoney(String account, Double amount);
    public void showExpenses(String fromAccount, String toAccount, Double amount);

    public void showIncomes(String fromAccount, String toAccount, Double amount);

    public void showBalance(String account);
}
