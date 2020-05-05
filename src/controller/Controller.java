package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import db.MessageRepositoryStub;
import domain.*;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PersonService model;
	private MessageService messageModel;
	private ControllerFactory controllerFactory;

	public Controller() {
		super();
	}

	public void init() throws ServletException {
		super.init();
		model = new PersonService();
		messageModel = new MessageService();
		controllerFactory = new ControllerFactory();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action == null){
			String destination = "index.jsp";
			RequestDispatcher view = request.getRequestDispatcher(destination);
			view.forward(request, response);
		}else {
			Person user =  (Person) request.getSession().getAttribute("user");
			switch (action) {
				case "ChangeStatus":
					String status = request.getParameter("status");
					if (status.trim().isEmpty()) {
						response.getWriter().write("ONLINE");
					} else {
						if (status.equalsIgnoreCase("AWAY") || status.equalsIgnoreCase("ONLINE") || status.equalsIgnoreCase("OFFLINE")) {
							UserStatus status1 = UserStatus.valueOf(status.toUpperCase());
							request.getSession().setAttribute("statusUser", status1);
						} else {
							request.getSession().setAttribute("statusUser", status.toUpperCase());
						}
						user.setStatus(status.toUpperCase());
						response.getWriter().write(status.toUpperCase());
					}
					break;
				case "GetFriends":
					JsonObject jsonObject = model.getFriends(user.getUserId());
					response.getWriter().write(jsonObject.toString());
					break;
				case "AddFriend":
					String newFriendFirstName = request.getParameter("firstName");
					if(newFriendFirstName != null && !newFriendFirstName.trim().isEmpty()) {
						Person newFriend = model.getPersonWithFirstName(request.getParameter("firstName"));
						if(newFriend != null) {
							user.addFriend(newFriend);
							newFriend.addFriend(user);
							JsonObject jsonObject1 = model.getFriends(user.getUserId());
							response.getWriter().write(jsonObject1.toString());
						}
					}
					break;
				case "Message":
					String message = request.getParameter("message");
					String recipientId = request.getParameter("recipient");
					Person recipient = model.getPerson(recipientId);
					messageModel.addMessage(user,recipient,message);
					JsonObject jsonObject2 = messageModel.getMessages(user);
					response.getWriter().write(jsonObject2.toString());
					break;
				case "GetMessages":
					JsonObject jsonObject1 = messageModel.getMessages(user);
					response.getWriter().write(jsonObject1.toString());
					break;
				case "Emoji":
					String identifier = request.getParameter("identifier");
					String emojiMessage = Emoji.valueOf(identifier).getHex();
					String recipientId2 = request.getParameter("recipient");
					Person recipient2 = model.getPerson(recipientId2);
					messageModel.addMessage(user,recipient2,emojiMessage);
					JsonObject jsonObject3 = messageModel.getMessages(user);
					response.getWriter().write(jsonObject3.toString());
					break;
				default:
					String destination = "index.jsp";
					if (action != null) {
						RequestHandler handler;
						try {
							handler = controllerFactory.getController(action, model);
							destination = handler.handleRequest(request, response);
						} catch (NotAuthorizedException exc) {
							List<String> errors = new ArrayList<String>();
							errors.add(exc.getMessage());
							request.setAttribute("errors", errors);
							destination = "index.jsp";
						}
					}
					RequestDispatcher view = request.getRequestDispatcher(destination);
					view.forward(request, response);
					break;
			}
		}
	}

}
