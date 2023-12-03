package DAO;
import java.util.List;
import Model.Account;
import Model.Message;

public interface SocialMediaDAO {

    /////CRUD: Create - Read - Update - Delete

    Account addAccount (Account account);
    boolean doesUserNameExist (String username);
    Message createMessage (Message message );

    Account verifyLogin (String username, String password);
    List<Message> getAllMessages();
    Message getMessageById(int messageById);
    List<Message> getMessageByAccountId(int accountById);

    boolean updateMessage(Message message);

    void deleteMessageById(int messageById);

    
    








}


