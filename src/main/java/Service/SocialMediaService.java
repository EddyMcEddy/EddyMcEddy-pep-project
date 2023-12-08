package Service;
import Model.*;
import java.util.List;
import DAO.SocialMediaDAO;


public class SocialMediaService {
    SocialMediaDAO socialMediaDAO;

    public SocialMediaService(){
     this.socialMediaDAO = new SocialMediaDAO();
    }


    public Account register(Account account) {

        try {

            if(account.getUsername().equals("") || account.getPassword().length() < 4) {
                return null;
            }
            else {
                return this.socialMediaDAO.accountInsert(account);
            }
        } catch (Exception e) 
        {
            System.out.println(e);
        }
        return null;

    }

    public Account login(Account account) {
        try {
            Account loggedInAccount = this.socialMediaDAO.accountSelect(account);
    
            if (loggedInAccount == null) {
                return null;  
            } else {
                return loggedInAccount;  
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
  
    public Message createMessage(Message message) {

        try {
            if(message.getMessage_text().length() > 254 || message.getMessage_text().equals("")) {
                return null;
            }
            else {
                return this.socialMediaDAO.messageInsert(message);
            }
        } catch (Exception e) 
        {
            System.out.println(e);
        }
        return null;

    }

    public Message readMessage(int message_id){

        try {
            return this.socialMediaDAO.selectMessage(message_id);
        } catch (Exception e) 
        {
            System.out.println(e);
        }
        return null;
    }

    public List<Message> readAllMessages(){
        try {
            return this.socialMediaDAO.selectAllMessages();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Message> readAllMessagesById(int account_id){
        try {
            return this.socialMediaDAO.selectAllMessagesFromAccountId(account_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Message updateMessage(Message message){
        try {
            if(message.getMessage_text().length() > 254 || message.getMessage_text().equals("")) {
                return null;
            }
            else {
                return this.socialMediaDAO.updateMessage(message.getMessage_id(), message.getMessage_text());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Message deleteMessage(int message_id){
        try {
            return this.socialMediaDAO.deleteMessage(message_id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }




    
}
