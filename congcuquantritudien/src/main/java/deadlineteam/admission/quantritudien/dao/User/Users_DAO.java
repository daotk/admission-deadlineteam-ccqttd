package deadlineteam.admission.quantritudien.dao.User;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Users_DAO {
	public void addUser( Users user);
	public void updateUser(Users user);
	public Users getUser(int ID);
	public void deleteUser(int ID);
	public List<Users> getAllUsers();
	public int changePassword(int Id,String newpassword);
	public void addSettingUser (Setting setting);
	public Setting getSetting(int Id);
	public int UpdateSetting(int UserId, int record, int pagin);
	public int UpdateSettingSaved(int UserId, int Record, int Pagin);
	public int UpdateSettingReplied(int UserId, int Record, int Pagin);
	public int UpdateSettingDelete(int UserId, int Record, int Pagin);
}
