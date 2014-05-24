package deadlineteam.admission.quantritudien.service.User;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Users_SERVICE {
	public boolean checkUsername(String  username);
	public void addUser( Users user);
	public void updateUser(Users user);
	public Users getUser(int ID);
	public void deleteUser(int ID);
	public List<Users> getAllUsers();
	public String checkLogin(String username, String password);
	public int getIdbyUsername (String username);
	public int changePassword(int Id,String newpassword);
	public boolean checkIsAdmin(int Id);
	public String getFullnameByID(int Id);
	public void addSettingUser (Setting setting);
	public Setting getSetting(int Id);
	public int UpdateSetting(int UserId, int Record, int Pagin);
	public int UpdateSettingSaved(int UserId, int Record, int Pagin);
	public int UpdateSettingReplied(int UserId, int Record, int Pagin);
	public int UpdateSettingDelete(int UserId, int Record, int Pagin);
	public int UpdateStatusUser(int UserId, int Authorization);
	public int UpdateSettingDictionary(int UserId, int Record, int Pagin);
}
