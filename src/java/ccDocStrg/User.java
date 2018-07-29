
package ccDocStrg;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Muhammad Wannous
 */
public class User implements Serializable{
  private String firstName;
  private String lastName;
  private String userName;
  private String passWord;
  private String lastDate;
  
  public User(String firstName, String lastName, String userName, String passWord, String lastDate) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.passWord = passWord;
    this.lastDate = lastDate;
    
  }

  public String getFirstName() {
    return firstName;
  }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }


  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }
  
  
}
