package kz.kuzovatov.pavel.daos;

import kz.kuzovatov.pavel.models.Robot;
import org.apache.log4j.Logger;
import org.boon.Boon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RobotDao extends AbstractDao implements GenericDao<Robot> {
    private final static Logger log = Logger.getLogger(RobotDao.class);

    @Override
    public Robot findById(int id) {
        Robot robot = new Robot();
        Connection connection = super.getFactory().getConnection();
        String sql = "Select * from robomanager.robots where robots.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                robot.setId(rs.getInt("id"));
                robot.setName(rs.getString("name"));
                robot.setType(rs.getString("type"));
                robot.setYear(rs.getInt("year"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            log.error(e);
        }finally {
            if(connection!=null) {
                super.getFactory().freeConnection(connection);
            }
        }
        return robot;
    }

    @Override
    public Robot findByName(String name) {
        Robot robot = new Robot();
        Connection connection = super.getFactory().getConnection();
        String sql = "Select * from robomanager.robots where robots.name = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                robot.setId(rs.getInt("id"));
                robot.setName(rs.getString("name"));
                robot.setType(rs.getString("type"));
                robot.setYear(rs.getInt("year"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            log.error(e);
        }finally {
            if(connection!=null) {
                super.getFactory().freeConnection(connection);
            }
        }
        return robot;
    }

    @Override
    public List<Robot> findAll() {
        List<Robot> robotList = new ArrayList<>();
        Connection connection = super.getFactory().getConnection();
        String sql = "Select * from robomanager.robots;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Robot robot = new Robot();
                log.info(rs.getInt("id"));
                robot.setId(rs.getInt("id"));
                log.info(rs.getString("name"));
                robot.setName(rs.getString("name"));
                log.info(rs.getString("type"));
                robot.setType(rs.getString("type"));
                log.info(rs.getInt("year"));
                robot.setYear(rs.getInt("year"));
                robotList.add(robot);
            }
        }catch (SQLException e){
            e.printStackTrace();
            log.error(e);
        } finally {
            if(connection!=null) {
                super.getFactory().freeConnection(connection);
            }
        }
        log.info(Boon.toPrettyJson(robotList));
        return robotList;
    }

    @Override
    public void update(Robot robot) {
        Connection connection = super.getFactory().getConnection();
        String sql = "update robomanager.robots set robots.name = ?, robots.type = ?, robots.year = ? where robots.id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, robot.getName());
            ps.setString(2, robot.getType());
            ps.setInt(3, robot.getYear());
            ps.setInt(4, robot.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null) {
                super.getFactory().freeConnection(connection);
            }
        }
    }

    @Override
    public void save(Robot robot) {
        Connection connection = super.getFactory().getConnection();
        String sql = "insert into robomanager.robots (name,type,year) values (?,?,?);";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, robot.getName());
            ps.setString(2, robot.getType());
            ps.setInt(3, robot.getYear());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null) {
                super.getFactory().freeConnection(connection);
            }
        }
    }

    @Override
    public boolean removeById(int id) {
        boolean status = false;
        Connection connection = super.getFactory().getConnection();
        String sql = "delete from robomanager.robots where robots.id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            status = ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null) {
                super.getFactory().freeConnection(connection);
            }
        }
        return status;
    }
}
