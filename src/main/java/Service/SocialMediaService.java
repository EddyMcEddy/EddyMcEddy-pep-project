package Service;


import java.util.List;
import Model.Account;
import Model.Message;

public interface SocialMediaService {
    

Account addAccount(Account account);
Boolean doesUserNameExist(String username);
Message createMessage(Message message);

Account verifyLogin(Account account);
List<Message> getAllMessages();
Message getMessageById(int messageById);
List<Message> getMessageByAccountId(int accountById);

Message updateMessage(Message message, int messageByID);
Message deleteMessageById(int messageById);




}
