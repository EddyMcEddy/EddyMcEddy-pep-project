package DAO;
import Model.*;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class SocialMediaDAO {
    private Connection connect = ConnectionUtil.getConnection();


    public Account accountSelect (Account account)
    {
       
       try 
       {

        String sql = "SELECT FROM account WHERE username = ? AND password =?";

        PreparedStatement preparedStatement = this.connect.prepareStatement(sql);

        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) 
        {
            Account returnedNewAccount = new Account(resultSet.getInt("account_Id")
            , resultSet.getString("username"),
            resultSet.getString("password"));

            return returnedNewAccount;
            
        }
        
       } 
       catch (SQLException e) 
       {
        // TODO: handle exception
        System.out.println(e.getMessage());
       }
       
        return null;    
    
    }

    public Account accountInsert(Account account)
    {
        
       try 
       {
        String sql = "INSERT INTO account (username,password) VALUES (?,?) ";

        PreparedStatement preparedStatement = this.connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());

        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) 
        {
            int account_id = (int) resultSet.getLong(1);
            account.setAccount_id(account_id);
            return account;
            
        }
       } 
       catch (SQLException e) 
       {
        // TODO: handle exception
        System.out.println(e.getMessage());
       } 
        
        return null;
    }

    public Account accountDelete (Account account)
    {

        try 
        {
            String sql = "DELETE FROM account WHERE account_id = ?";

            PreparedStatement preparedStatement = this.connect.prepareStatement(sql);

            preparedStatement.setInt(1, account.getAccount_id());

            int deleteAccount = preparedStatement.executeUpdate();

            if (deleteAccount > 0) 
            {
            
                System.out.println("Account Has Been Deleted");
                return account;
            }

            
            
        } 
        catch (SQLException e) 
        {
            // TODO: handle exception
            System.out.println(e.getMessage());

        }

        return null;
    }

    public Message messageInsert (Message message)
    {
       
      try {
        
      
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?) ";

       PreparedStatement preparedStatement = this.connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

       preparedStatement.setInt(1, message.getPosted_by());
       preparedStatement.setString(2, message.getMessage_text());
       preparedStatement.setLong(3, message.getTime_posted_epoch());

       int updatedMessage = preparedStatement.executeUpdate();

       if(updatedMessage > 0){
        
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(resultSet.next()){
            int message_id = (int) resultSet.getLong(1);
            message.setMessage_id(message_id);
            return message;
        }
    }
}
       catch (SQLException e) 
       {
        // TODO: handle exception
        System.out.println(e.getMessage());
      }
       return null;
    }

    public Message selectMessage(int message_id) {
        try {
            
            String sql = "SELECT * FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = this.connect.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Message mesage = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch"));
                    return mesage;
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
          return null;
    }
    public List<Message> selectAllMessages() {
        List<Message> messages = new ArrayList<>();
    
        try {
          
            String sql = "SELECT * FROM message";

            PreparedStatement preparedStatement = this.connect.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Message mesage = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch"));
                messages.add(mesage);
            }
            return messages;
        } catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> selectAllMessagesFromAccountId(int account_id) {
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";

            PreparedStatement preparedStatement = this.connect.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Message mesage = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch"));
                messages.add(mesage);
            }
            System.out.println(messages.toString());
            return messages;
        } catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message deleteMessage(int message_id){
        try {
            Message message = selectMessage(message_id);
            if(message == null) {
                return null;
            }

            String sql = "DELETE FROM message WHERE message_id = ?;";

            PreparedStatement preparedStatement = this.connect.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            int deletedMessage = preparedStatement.executeUpdate();

            if(deletedMessage > 0){
                System.out.println("Message Has Been Deleted");
                return message;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int message_id, String message_text){
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";

            PreparedStatement preparedStatement = this.connect.prepareStatement(sql);

            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);

            int updatedMessage = preparedStatement.executeUpdate();
            if(updatedMessage > 0){
                return selectMessage(message_id);
            }
        } catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return null;
    }












    
}
