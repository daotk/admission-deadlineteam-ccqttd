package deadlineteam.admission.quantritudien.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class Users {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer ID;
	private String FullName;
	private String UserName;
	private String Password;
	private String Email;
	private Integer Authorization;
	
	public Integer getID() {
		return ID;
	}
	 
	public void setID(Integer ID) {
		this.ID = ID;
	}
	// Full Name
	public String getFullName() {
		return FullName;
	}
	 
	public void setFullName(String FullName) {
		this.FullName = FullName;
	}
	
	// User Name
	public String getUserName() {
		return UserName;
	}
	 
	public void setUserName(String UserName) {
		this.UserName = UserName;
	}
	// Password
	public String getPassword() {
		return Password;
	}
	 
	public void setPassword(String Password) {
		this.Password = Password;
	}
	// ID
	public Integer getAuthorization() {
		return Authorization;
	}
	 
	public void setAuthorization(Integer Authorization) {
		this.Authorization = Authorization;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
}
