package deadlineteam.admission.quantritudien.dao.User;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Users_DAO {
	
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
	 * Change Password By ID
	 * @param Id {@link Integer}
	 * @param newpassword {@link String}
	 * @return {@link Integer}
	 */
	public int changePasswordByUserID(int Id,String newpassword);
	
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
	public int UpdateSetting(int UserId, int record, int pagin);
	
	/**
	 * Update Setting Save List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int UpdateSettingSaved(int UserId, int Record, int Pagin);
	
	/**
	 * Update Setting Replied List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int UpdateSettingReplied(int UserId, int Record, int Pagin);
	
	/**
	 * Update Setting Delete List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int UpdateSettingDelete(int UserId, int Record, int Pagin);
	
	/**
	 * Update Setting Dictionary List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int UpdateSettingDictionary(int UserId, int Record, int Pagin);
	
	/**
	 * Update User Authorization
	 * @param UserId {@link Integer}
	 * @param Authorization {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateAuthorizationUser(int UserId, int Authorization);
}
