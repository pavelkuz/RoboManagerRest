package kz.kuzovatov.pavel.services;

import kz.kuzovatov.pavel.daos.JdbcDaoFactory;
import kz.kuzovatov.pavel.daos.RobotDao;
import kz.kuzovatov.pavel.models.Robot;
import kz.kuzovatov.pavel.servlets.Controller;
import org.apache.log4j.Logger;
import org.boon.Boon;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/robots")
public class RobotRestService {
    private static final Logger log = Logger.getLogger(RobotRestService.class);
    JdbcDaoFactory jdbcDaoFactory = Controller.getJdbcDaoFactory();
    RobotDao robotDao = (RobotDao) jdbcDaoFactory.getDao(RobotDao.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRobots() {
        List<Robot> robotList = robotDao.findAll();
        String json = Boon.toJson(robotList);
        String response = "{\"robots\":" +json+"}";
        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/search/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRobotByName(@PathParam("name") String name) {
        Robot robot = robotDao.findByName(name);
        String output = Boon.toJson(robot);
        String response = "{\"robot\":" +output+"}";
        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRobotById(@PathParam("id") int id) {
        Robot robot = robotDao.findById(id);
        String output = Boon.toJson(robot);
        String response = "{\"robot\":" +output+"}";
        return Response.status(200).entity(response).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRobotByJson(Robot robot){
        String result = Boon.toPrettyJson(robot);
        log.info(result);
        robotDao.save(robot);
        return Response.status(201).entity(result).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateRobotByJson(@PathParam("id") int id, Robot robot){
        String result = Boon.toPrettyJson(robot);
        log.info(result);
        Robot foundedRobot = robotDao.findById(id);
        if(foundedRobot!=null) {
            if(foundedRobot.getId()==id) {
                log.info("founded robot: " + Boon.toJson(foundedRobot));
                foundedRobot.setName(robot.getName());
                foundedRobot.setType(robot.getType());
                foundedRobot.setYear(robot.getYear());
                robotDao.update(foundedRobot);
                return Response.status(200).entity(result).build();
            }
        }
        return Response.status(204).entity("No Content").build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteRobotById(@PathParam("id") int id) {
        Robot robot = robotDao.findById(id);
        if (robot!=null) {
            if(robot.getId()==id) {
                String output = Boon.toJson(robot);
                robotDao.removeById(id);
                String response = "{\"robot\":" + output + "}";
                return Response.status(200).entity(response).build();
            }
        }
        return Response.status(204).entity("No Content").build();
    }
}