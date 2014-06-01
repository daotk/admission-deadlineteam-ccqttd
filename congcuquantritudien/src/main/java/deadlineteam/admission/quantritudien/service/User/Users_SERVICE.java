package deadlineteam.admission.quantritudien.service.User;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Users_SERVICE {
	
	
	/**
	 * Check Username
	 * @param username
	 * @return
	 */
	public boolean checkUsername(String  username);
	
	/**
	 * Add User to Database
	 * @param user {@link Users}
	 */
	public void addUser( Users user);
	
	/**
	 * Update User
	 * @param User {@link Users}
	 */
	public void updateUser(Users user);
	
	/**
	 * Get User By ID
	 * @param ID {@link Integer}
	 * @return {@link Users}
	 */
	public Users getUserByUserID(int ID);
	
	/**
	 * Delete User By ID
	 * @param ID {@link Integer}
	 */
	public void deleteUserByUserID(int ID);
	
	/**
	 * Get All User
	 * @return {@link List} All User
	 */
	public List<Users> getAllUsers();
	
	/**
	 * Check Username and Password and Status when login
	 * @param username {@link String}
	 * @param password {@link String}
	 * @return
	 */
	public String checkLogin(String username, String password);
	
	/**
	 * 
	 * @param username {@link String}
	 * @return
	 */
	public int getIdbyUsername (String username);
	
	/**
	 * Change Password By ID
	 * @param Id {@link Integer}
	 * @param newpassword {@link String}
	 * @return {@link Integer}
	 */
	public int changePasswordByUserID(int Id,String newpassword);
	
	/**
	 * Check Is Administrator
	 * @param Id {@link Integer}
	 * @return
	 */
	public boolean checkIsAdmin(int Id);
	
	/**
	 * Get Fullname By ID
	 * @param Id {@link String}
	 * @return
	 */
	public String getFullnameByID(int Id);
	
	/**
	 * Add Setting for User
	 * @param setting {@link Setting}
	 */
	public void addSettingUser (Setting setting);
	
	/**
	 * Get Setting By ID
	 * @param Id {@link Integer}
	 * @return {@link Setting}
	 */
	public Setting getSetting(int Id);
	
	/**
	 * Update Setting
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSetting(int UserId, int Record, int Pagin);
	
	/**
	 * Update Setting Save List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSettingSaved(int UserId, int Record, int Pagin);
	
	/**
	 * Update Setting Replied List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSettingReplied(int UserId, int Record, int Pagin);
	
	/**
	 * Update Setting Delete List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSettingDelete(int UserId, int Record, int Pagin);
	
	/**
	 * Update User Authorization
	 * @param UserId {@link Integer}
	 * @param Authorization {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSettingDictionary(int UserId, int Authorization,String Fullname,int Status);
	
	/**
	 * Update Setting Dictionary List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSettingDictionary(int UserId, int Record, int Pagin);
}
