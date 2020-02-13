package org.fhhf.dev.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.fhhf.dev.messenger.exception.DataNotFoundException;
import org.fhhf.dev.messenger.model.Message;
import org.fhhf.dev.messenger.resources.filterbean.MessageFilterBean;
import org.fhhf.dev.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessagesResource {

	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() >= 0 && filterBean.getSize() >= 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam ("messageId") long id) {
		
		Message message = messageService.getMessage(id);
		if(message == null) {
			throw new DataNotFoundException("messageId: " + id + ", not found");
		}
		return message;
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		
		return Response.created(uri)
					   .entity(newMessage)
					   .build();
	}
	/*public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}*/
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam ("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam ("messageId") long id){
		messageService.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentsResource getCommentResource() {
		return new CommentsResource();
	}
}
