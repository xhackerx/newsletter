package com.nikhil;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.DB.Querys;
import com.classes.User;
import com.google.gson.Gson;

@Path("nikhil")
public class Nikhil {
	
	public Querys querys = new Querys();
	public StringBuilder sb= new StringBuilder();

	@GET @Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayhello() {
		return "hello Nikhil";
	}
	
	@POST @Path("/newSubscriptor")
	@Produces(MediaType.APPLICATION_JSON)
	public String newSubscriptor(String data) {
		User user = new Gson().fromJson(data, User.class);
		return querys.addUser(user);
	}
	
	@GET @Path("/selectAllUsersSubscribedToday")
	@Produces(MediaType.TEXT_PLAIN)
	public String selectAllUsersSubscribedToday() {
		sb.setLength(0);
		sb.append("New users subscribed today: ").append(querys.selectAllUsersSubscribedToday());
		return sb.toString();
	}
	
	@POST @Path("/userShouldReceiveNewLetter")
	@Produces(MediaType.TEXT_PLAIN)
	public String userShouldReceiveNewLetter(String userEmail) {
		sb.setLength(0);
		sb.append("the user ").append(userEmail).append(" should receive newsletter: ").append(querys.userShouldReceiveNewLetter(userEmail));
		return sb.toString();
	}
	
	@POST @Path("/selectAllUsersSubscribedByDate")
	@Produces(MediaType.TEXT_PLAIN)
	public String selectAllUsersSubscribedByDate(String date) {
		sb.setLength(0);
		sb.append("Users subscribed: ").append(querys.selectAllUsersSubscribedByDate(date));
		return sb.toString();
	}
}