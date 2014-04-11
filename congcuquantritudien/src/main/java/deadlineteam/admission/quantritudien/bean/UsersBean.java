package deadlineteam.admission.quantritudien.bean;

public class UsersBean {
	private Integer ID;
	private String FullName;
	private String UserName;
	private String Password;
	private String NewPassword;
	private String ConfirmPassword;
	private String Email; 
	private Integer Authorization;
	
	public Integer getID() {
		return ID;
	}
	 
	public void setID(Integer ID) {
		this.ID = ID;
	}
	public String getFullName() {
		return FullName;
	}
	 
	public void setFullName(String FullName) {
		this.FullName = FullName;
	}
	
	public String getUserName() {
		return UserName;
	}
	 
	public void setUserName(String UserName) {
		this.UserName = UserName;
	}
	public String getPassword() {
		return Password;
	}
	 
	public void setPassword(String Password) {
		this.Password = Password;
	}
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

	public String getNewPassword() {
		return NewPassword;
	}

	public void setNewPassword(String newPassword) {
		NewPassword = newPassword;
	}

	public String getConfirmPassword() {
		return ConfirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}
}
