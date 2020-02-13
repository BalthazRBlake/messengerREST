package org.fhhf.dev.messenger.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.fhhf.dev.messenger.model.ErrorMessage;

@Provider		//Registers the class to JAX-RS and catches every exception
public class GenericExceptionMapper implements ExceptionMapper<WebApplicationException> {

	@Override 
	public Response toResponse(WebApplicationException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 666, "Tampco hay");
		return Response.status(Status.NOT_FOUND)
					   .entity(errorMessage)
					   .build();
	}

}