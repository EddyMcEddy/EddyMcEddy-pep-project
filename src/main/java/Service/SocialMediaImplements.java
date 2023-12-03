package Service;

import java.util.List;

import DAO.SocialMediaDAO;
import DAO.SocialMediaImplementsDAO;
import Model.Account;
import Model.Message;

public class SocialMediaImplements implements SocialMediaService {


    private SocialMediaDAO socialMediaDAO;
    public String username;
    public String password;
    public int accountID;

    public SocialMediaImplements()
    {

        this.socialMediaDAO = new SocialMediaImplementsDAO();
    }

    @Override
    public Account addAccount(Account account)
    {

        return this.socialMediaDAO.addAccount(account);
    }
    @Override
    public Boolean doesUserNameExist(String username)
    {

        return socialMediaDAO.doesUserNameExist(username);
    }
    public Account verifyLogin(Account account){

        return socialMediaDAO.verifyLogin(account.getUsername(), account.getPassword());
    }
    @Override
    public Message createMessage(Message message)
    {

        if (message.getMessage_text() == "" || message.getMessage_text().length()> 254) 
        
        {
            return null;
        }else
        {
            return socialMediaDAO.createMessage(message);
        }


    }
    @Override
   public List<Message> getAllMessages()
   {
    return socialMediaDAO.getAllMessages();
   }

   @Override
   public Message getMessageById(int message_id) {
       Message message = socialMediaDAO.getMessageById(message_id);
   
       if (message != null) {
           return message;
       } else {
           // Create a dummy message with an empty body
           return new Message();
       }
   
}
@Override
public Message deleteMessageById(int message_id) {
    Message message = socialMediaDAO.getMessageById(message_id);

    if (message != null) {
        socialMediaDAO.deleteMessageById(message_id);
    }

    return message; // Return the deleted message or null if it doesn't exist
}

    @Override
    public Message updateMessage(Message message, int messageByID) {
       Message messages = socialMediaDAO.getMessageById(messageByID);

       if ( messages != null && messages.message_text.length() <256) {
           return socialMediaDAO.getMessageById(messageByID);
                    
        } else {
            return null;             
        }
       // return messages;
    }

    @Override
    public List<Message> getMessageByAccountId(int account_id) {
        return socialMediaDAO.getMessageByAccountId(account_id);
    }

    
}
