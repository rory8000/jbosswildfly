package com.rory.demo.app.providers.exception;

import com.rory.demo.app.dtos.ErrorResponseConverterDTO;
import com.rory.demo.app.exceptions.MyCustomException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DemoExceptionMapper implements ExceptionMapper<MyCustomException> {

    @Context
    private HttpHeaders headers;

    @Override
    public Response toResponse(MyCustomException e) {
        return Response.status(e.getStatus()).
                entity(new ErrorResponseConverterDTO(e.getMessage(), e.getReason(), e.getErrorCode())).
                type(headers.getMediaType()).
                build();
    }
}
