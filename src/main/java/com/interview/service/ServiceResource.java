package com.interview.service;


import com.interview.dao.ServiceDAO;
import com.interview.model.Account;
//import com.interview.model.TransactionResponse;
import com.interview.model.Transactions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * http://www.dropwizard.io/1.0.6/docs/manual/core.html#resources
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceResource {

    private ServiceDAO dao;

    private static Log LOG = LogFactory.getLog(ServiceResource.class);

    public ServiceResource(ServiceDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/ping")
    public String ping() {
        return "Pong";
    }


    @GET
    @Path("/accounts")
    public List<Account> getAllAccounts() {
        return dao.getAllAccounts();
    }

    @GET
    @Path("/accounts/{id}")
    public Account getAllAccounts(@PathParam("id") String id) {
        return dao.getAccount(id);
    }


    @POST
    @Path("/transfer")
    public Response updateAccount(Transactions transactions) throws Exception {

        return dao.transferAmount(transactions);

    }

}