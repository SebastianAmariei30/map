package ro.ubbcluj.map.demogui.repository;

import ro.ubbcluj.map.demogui.domain.Message;
import ro.ubbcluj.map.demogui.domain.Prietenie;
import ro.ubbcluj.map.demogui.domain.Tuple;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.Validator;
import ro.ubbcluj.map.demogui.utils.controller.MessageAlert;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MessagesDPRepo implements Repository<Long, Message>{
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Message> validator;

    public MessagesDPRepo(String url, String username, String password, Validator<Message> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Message> findOne(Long aLong) {
        if (aLong==null)
            throw new IllegalArgumentException("id must be not null");
        List<Message> list;
        String insertQuery = "select * from message where id=?";
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement= connection.prepareStatement(insertQuery);
            statement.setInt(1, Math.toIntExact(aLong));
            ResultSet resultSet=statement.executeQuery();
            list=extractMesaj(resultSet);
            if(list.isEmpty())
                throw new IllegalArgumentException("id inexistent");
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.of(list.get(0));
    }
    private List<Message> extractMesaj(ResultSet resultSet) throws SQLException {
        List<Message> list= new ArrayList<>();
        while (resultSet.next()){
            int id=resultSet.getInt("id");
            int from=resultSet.getInt("from");
            Long[] to=(Long[])resultSet.getArray("to").getArray();
            List<Long> tof = new ArrayList<>(Arrays.asList(to));
            String mesaj=resultSet.getString("message");
            int reply=resultSet.getInt("reply");
            Timestamp timestamp =resultSet.getTimestamp("date");
            LocalDateTime date=timestamp.toLocalDateTime();
            Message message=new Message((long)from,tof,mesaj,date);
            message.setReply((long) reply);message.setId((long)id);
            list.add(message);
        }
        return list;
    }

    @Override
    public Iterable<Message> findAll() {
        List<Message> list;
        try(Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement= connection.prepareStatement("select * from message");
            ResultSet resultSet=statement.executeQuery();
        ){
            list=extractMesaj(resultSet);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return list;
    }

    @Override
    public Optional<Message> save(Message entity) {
        String insertQuery = "INSERT INTO message (\"from\",message,reply,date,\"to\") VALUES (?, ?, ?, ?, ?)";
        validator.validate(entity);
        this.findAll().forEach(x->{
            if(x.getReply().equals(entity.getReply())&& Objects.equals(x.getFrom(), entity.getFrom())&&entity.getReply()!=-1)
                throw new IllegalArgumentException("Ai raspuns deja la acest mesaj");
        });
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement= connection.prepareStatement(insertQuery);
            statement.setInt(1,entity.getFrom().intValue());
            statement.setString(2,entity.getMessage());
            if(entity.getReply()!=null)
                statement.setInt(3,entity.getReply().intValue());
            else
                statement.setInt(3,-1);
            statement.setTimestamp(4,Timestamp.valueOf(entity.getData()));
            Long[] arr=entity.getTo().toArray(new Long[0]);
            Array array=connection.createArrayOf("bigint",arr);
            statement.setArray(5,array);
            int rowCount=statement.executeUpdate();
            if(rowCount>0){
                return Optional.empty();
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Message> delete(Long aLong) {
        if(aLong==null)
            throw new IllegalArgumentException("Id invalid");
        Message message = null;
        if(this.findOne(aLong).isPresent())
            message= this.findOne(aLong).get();
        String deleteQuery="DELETE FROM message WHERE id=?";
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement= connection.prepareStatement(deleteQuery);
            statement.setInt(1,Math.toIntExact(aLong));
            int rowCount=statement.executeUpdate();
            if(rowCount>0){
                return Optional.empty();
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.ofNullable(message);
    }

    @Override
    public Optional<Message> update(Message entity) {
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);
        this.findOne(entity.getId());
        String updateQuery="UPDATE message SET message=?,reply=?,\"to\"=? WHERE id=?";
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement= connection.prepareStatement(updateQuery);
            statement.setString(1,entity.getMessage());
            statement.setInt(2,entity.getReply().intValue());
            Long[] arr=entity.getTo().toArray(new Long[0]);
            Array array=connection.createArrayOf("bigint",arr);
            statement.setArray(3,array);
            statement.setInt(4,Math.toIntExact(entity.getId()));
            int rowCount=statement.executeUpdate();
            if(rowCount>0){
                return Optional.empty();
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.of(entity);
    }
}
