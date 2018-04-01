package com.interview.mappers;


import com.interview.model.Transactions;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TransactionResultMapper implements ResultSetMapper<Transactions> {

    @Override
    public Transactions map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        return new Transactions(r.getInt("id"),
                r.getString("fromAccountId"),r.getString("toAccountId"),r.getDouble("amountToTransfer"));

    }
}

