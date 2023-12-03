package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class SocialMediaImplementsDAO implements SocialMediaDAO{

    @Override
    public Account addAccount(Account account)
    {

        try (Connection connection = ConnectionUtil.getConnection()) {

            String sql = "INSERT INTO account (username , password) VALUES (?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {

                int createdAccountId = (int)resultSet.getLong(1);
                return new Account(createdAccountId, account.getUsername(), account.getPassword());
                
            }
            
        } catch (SQLException e) {
            // TODO: handle exception

            e.printStackTrace();
        }
              
        return null; 
    
    }

    public Account verifyLogin(String username, String password)
    {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";

            try 
            { 
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    
                    Account account = new Account(resultSet.getInt("account_id"), resultSet.getString("username"),resultSet.getString("password"));

                    return account;
                    
                }
    
                } 
                catch (SQLException e) 
                {
                // TODO: handle exception
                e.printStackTrace();
                

                }



            return null;

    }

    @Override
    public Message createMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        //Doesnt need messageID or accountID since they Auto Increment 
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";

        try 
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) 
            {

                int createdMessageId = (int) resultSet.getLong(1);

                return new Message(createdMessageId,message.getPosted_by(), message.getMessage_text(),
                message.getTime_posted_epoch());
                

            }

        } catch (SQLException e) {
            // TODO: handle exception

            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages()
    {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try 
        {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) 
            {
                Message message = new Message(resultSet.getInt("message_id"),resultSet.getInt("posted_by"),resultSet.getString("message_text"),resultSet.getLong("time_posted_epoch"));

                messages.add(message);
                
            }

        } 
        catch (SQLException e) {
            // TODO: handle exception

            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message getMessageById(int messageID)
    {
        Connection connection = ConnectionUtil.getConnection();
        Message message = new Message();

        try 
        {

            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) 
            {

                message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));

                return message;
            }
            
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }



        return message;
    }

    public void deleteMessageById(int messageById)
    {

        Connection connection = ConnectionUtil.getConnection();
        
        try 
        {
            String sql = "DELETE FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageById);
            preparedStatement.executeUpdate();

        } 
        catch (SQLException e) 
        {
            // TODO: handle exception

            System.out.println(e.getMessage());
        }


    }

    public boolean updateMessageById(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "UPDATE message SET message_text = ? WHERE message_id=? ";

        try 
        {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);            
            preparedStatement.setString(1, message.message_text);
            preparedStatement.setInt(2,message.message_id);
            int i = preparedStatement.executeUpdate(); 
            if(i == 1)
          return true;
        } 
        catch (SQLException e) 
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        
        
        
        return false;
    }


    public List<Message> getAllMessagesByID(int accountID)
    {

       Connection connection = ConnectionUtil.getConnection();

       List<Message> messages = new ArrayList<>();
       try 
       {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM message WHERE posted_by = ?");
        preparedStatement.setInt(1, accountID);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) 
        {
            Message message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
           
            messages.add(message);
            
        }
       }
        catch (SQLException e) 
        {
        // TODO: handle exception
        e.printStackTrace();
       }
              
        return messages;
    }

    @Override
    public boolean doesUserNameExist(String username)
    {

        Connection connection = ConnectionUtil.getConnection();
        try 
        {
            String sql = "SELECT * FROM account WHERE username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
                
            }
            
        } 
        catch (SQLException e) 
        {
            // TODO: handle exception

            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Message> getMessageByAccountId(int accountID) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public boolean updateMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "UPDATE message SET message_text = ? WHERE message_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message.message_text);
            preparedStatement.setInt(2, message.message_id);
            int i = preparedStatement.executeUpdate();
            return i == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   
    


    
}
