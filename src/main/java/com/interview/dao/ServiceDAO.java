package com.interview.dao;

import com.interview.exception.MoneyManagerException;
import com.interview.mappers.AccountResultMapper;
import com.interview.mappers.TransactionResultMapper;
import com.interview.model.Account;
import com.interview.model.TransactionResponse;
import com.interview.model.Transactions;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.core.Response;
import java.util.List;

@RegisterMapper({AccountResultMapper.class,TransactionResultMapper.class})
public class ServiceDAO {

    private DBI dbi;

    public ServiceDAO() {
    }

    private static Log LOG = LogFactory.getLog(ServiceDAO.class);

    public ServiceDAO(DBI dbi) {
        this.dbi = dbi;
    }

    public List<Account> getAllAccounts() {
        Handle h  = dbi.open();

        try{
            return h.createQuery("SELECT * FROM accounts;")
                    .mapTo(Account.class)
                    .list();
        } finally {
            h.close();
        }
    }

    public Account getAccount(String id) {
        Handle h = dbi.open();

        try{
            return h.createQuery("SELECT * FROM accounts " +
                                " WHERE id=:id;")
                    .bind("id", id)
                    .mapTo(Account.class)
                    .first();
        } finally {
            h.close();
        }
    }

    public void updateAccount(String id,double balance){
        Handle h = dbi.open();

        try{
            h.execute("UPDATE accounts SET balance=? WHERE id=?", balance, id);
        }finally {
            h.close();
        }
    }

    public Transactions insertTransaction(Transactions transactions){
        Handle h = dbi.open();

        try{
            h.execute("insert into transactions (fromAccountId, toAccountId,amountToTransfer) values (?, ?, ?)",
                    transactions.getFromAccountId(), transactions.getToAccountId(), transactions.getAmountToTransfer());
            return h.createQuery("SELECT * from transactions order by id desc limit 1")
                    .mapTo(Transactions.class)
                    .first();
        }finally {
            h.close();
        }
    }

    @Transaction
    public Response transferAmount(Transactions transactions) throws Exception {

        String fromAccountId = transactions.getFromAccountId();
        String toAccountId = transactions.getToAccountId();

        if(fromAccountId.equals(toAccountId)){
            LOG.error("Trying to transfer between same accounts");
            throw new MoneyManagerException("Cannot proceed, Invalid account id entered");
        }
        Double amountToTransfer = transactions.getAmountToTransfer();

        Account fromAccount = getAccount(fromAccountId);
        Account toAccount = getAccount(toAccountId);

        Double fromAccountBalance = null;
        Double toAccountBalance = null;

        TransactionResponse transactionResponse;
        try {

            if (fromAccount == null) {
                LOG.error("Account with id: " + fromAccountId + " is Invalid");
                throw new MoneyManagerException("Account with id: " + fromAccountId + " is Invalid");
            }
            if (toAccount == null) {
                LOG.error("Account with id: " + toAccountId + " is Invalid");
                throw new MoneyManagerException("Account with id: " + toAccountId + " is Invalid");
            }

            fromAccountBalance = fromAccount.getBalance();
            toAccountBalance = toAccount.getBalance();

            if (amountToTransfer <= 0) {
                LOG.error("Cant proceed with transactions from account: " + fromAccountId + " to account: " + toAccountId + " as amount to transfer is 0");
                throw new MoneyManagerException("Amount being transferred is not applicable");
            }

            if (fromAccountBalance == 0) {
                LOG.error("Account with id: " + fromAccountId + " has Insufficient Balance");
                throw new MoneyManagerException("Insufficient Balance");
            }
            if ((fromAccountBalance - amountToTransfer) < 0) {
                LOG.error("Account with id: " + fromAccountId + " has Insufficient Balance");
                throw new MoneyManagerException("Account with id: " + fromAccountId + " has Insufficient Balance");
            }

            fromAccountBalance = fromAccountBalance - amountToTransfer;
            toAccountBalance = toAccountBalance + amountToTransfer;

            updateAccount(fromAccountId, fromAccountBalance);
            updateAccount(toAccountId, toAccountBalance);

            transactions = insertTransaction(transactions);
            transactionResponse = new TransactionResponse(transactions.getId(), fromAccountBalance);

            LOG.info("Transactions from account: " + fromAccountId + " to: " + toAccountId + " is successful");
        } catch (MoneyManagerException exception) {
            throw exception;
        }
        return Response.status(Response.Status.OK).entity(transactionResponse).build();
    }
}