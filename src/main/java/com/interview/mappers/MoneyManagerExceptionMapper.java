package com.interview.mappers;

import com.interview.exception.MoneyManagerException;
import com.interview.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MoneyManagerExceptionMapper implements ExceptionMapper<MoneyManagerException> {

    public MoneyManagerExceptionMapper() {
        System.out.println("Mapper created money manager");
    }

    @Override
    public Response toResponse(MoneyManagerException e) {
        ErrorMessage errorMessage = new ErrorMessage(400,e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .build();
    }
}
