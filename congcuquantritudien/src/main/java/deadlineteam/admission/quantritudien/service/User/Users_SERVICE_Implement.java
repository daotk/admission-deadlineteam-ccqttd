package deadlineteam.admission.quantritudien.service.User;

import java.util.List;

import org.apache.lucene.analysis.ReusableAnalyzerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import deadlineteam.admission.quantritudien.dao.User.Users_DAO;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

@Service
@Transactional
public class Users_SERVICE_Implement implements Users_SERVICE {
	
	@Autowired
	private Users_DAO usersDAO;

	public boolean checkUsername(String  username) {
		boolean checkusername=false;
		List<Users> listuser = usersDAO.getAllUsers();
		for(int i=0; i<listuser.size();i++){
			if(username.equals(listuser.get(i).getUserName())){
				checkusername = true;
				break;
			}
		}
		return checkusername;
	}
	public void addUser( Users user){
		usersDAO.addUser(user);
	}

	public void updateUser(Users user) {
		usersDAO.updateUser(user);
	}

	public Users getUser(int ID) {
		return usersDAO.getUserByUserID(ID);
	}

	public void deleteUser(int ID) {
		usersDAO.deleteUserByUserID(ID);
	}

	public List<Users> getAllUsers() {
		return usersDAO.getAllUsers();
	}

	public String checkLogin(String username,String password) {
		List<Users> users = getAllUsers();
		int count=0;
		for(int i=0; i<users.size();i++){
			if(users.get(i).getStatus().equals(0)){
			if(users.get(i).getUserName().equals(username)){
				count= count+1;
				if(users.get(i).getPassword().equals(password)){
					count= count+1;
					break;
					}
				}
			}else{
				return "notallow";
			}
		}
		if(count==2){
		return "Right";
		}else{
			if(count==1){
				return "WrongPass";
			}else{
				return "Wrong";
			}
		}
	}
	
	public int getIdbyUsername (String username){
		int result= 0;
		List<Users> users = getAllUsers();
		for(int i=0; i<users.size();i++){
			if(users.get(i).getUserName().equals(username)){
				result = (users.get(i).getID());
				break;
			}
		}
		return result;
	}
	
	public int changePassword(int Id,String newpassword){
		return usersDAO.changePasswordByUserID(Id, newpassword);
	}
	public boolean checkIsAdmin(int Id){
		boolean result= false;
		Users users = getUser(Id);
		if(users.getAuthorization().equals(1)){
			result = true;
		}else{
			result= false;
		}
		return result;
	}
	
	public String getFullnameByID(int Id){
		Users users = getUser(Id);
		return users.getFullName();
		}
	public void addSettingUser (Setting setting){
		usersDAO.addSettingUser(setting);
	}
	public Setting getSetting(int Id){
		return usersDAO.getSetting(Id);
	}
	public int UpdateSetting(int UserId, int Record, int Pagin){
		return usersDAO.UpdateSetting(UserId, Record, Pagin);
	}
	public int UpdateSettingSaved(int UserId, int Record, int Pagin){
		return usersDAO.UpdateSettingSaved(UserId, Record, Pagin);
	}
	public int UpdateSettingReplied(int UserId, int Record, int Pagin){
		return usersDAO.UpdateSettingReplied(UserId, Record, Pagin);
	}
	public int UpdateSettingDelete(int UserId, int Record, int Pagin){
		return usersDAO.UpdateSettingDelete(UserId, Record, Pagin);
	}
	public int UpdateStatusUser(int UserId, int Authorization){
		return usersDAO.updateAuthorizationUser(UserId, Authorization);
	}
	public int UpdateSettingDictionary(int UserId, int Record, int Pagin){
		return usersDAO.UpdateSettingDictionary(UserId, Record, Pagin);
	}
}

	
