package com.luminesway.concursoadminstrator.modules.pathfinders.services;

import org.springframework.stereotype.Service;

@Service
public interface TreasuryService {
    public void manageMoney(String account, Double amount);
    public void showExpenses(String fromAccount, String toAccount, Double amount);

    public void showIncomes(String fromAccount, String toAccount, Double amount);

    public void showBalance(String account);
}
