package deadlineteam.admission.quantritudien.dao.Dictionary;

import java.util.Date;
import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Users;


public interface Dictionary_DAO {	
	/**
	* Add dictionary to database *
	* @param dictionary 
	*/
	public void  AddDictionary (Dictionary  dictionary);
	
	/**
	* Get List Available Dictionary for UserID *
	* @param UserID {@link Integer}
	* @return {@link List}  List Available Dictionary for UserID
	*/
	public List<Dictionary> getAvailableListDictionaryForUser(int UserID) ;
	
	/**
	 * Get Available List For Administrator *
	 * @return {@link List} List Available Dictionary for Administrator
	 */
	public List<Dictionary> getAvailableListForAdministrator();
	
	/**
	 * Get All Available List Dictionary
	 * @return {@link List} List Available
	 */
	public List<Dictionary> getAllDictionaryAvailable();
	
	/**
	 * Get All Deleted List Dictionary
	 * @return {@link List} List Deleted
	 */
	public List<Dictionary> getAllDictionaryDeleted();
	
	/**
	 * Get All Recent List Dictionary
	 * @return {@link List} List Recent
	 */
	public List<Dictionary> getAllDictionaryRecent() ;
	
	/**
	 * Get All Down List Dictionary
	 * @return {@link List} List Down
	 */
	public List<Dictionary> getAllDictionaryDown();
	
	/**
	 * Get All Dictionary
	 * @return List All Dictionary
	 */
	public List<Dictionary> getAllDictionary();
	
	/**
	 * Get Available Dictionary By ID
	 * @param Id {@link Integer}
	 * @return {@link Dictionary}
	 */
	public Dictionary getAvailableDictionaryByID(int Id);
	
	/**
	 * Get Recent Dictionary By ID
	 * @param Id {@link Integer}
	 * @return {@link Dictionary}
	 */
	public Dictionary getRecentDictionaryByID(int Id);

	/**
	 * Get Dictionary By ID
	 * @param Id {@link Integer}
	 * @return {@link Dictionary}
	 */
	public Dictionary getDictionaryByID(int Id);
	
	/**
	 * Update Dictionary when Upload Dictionary
	 * @param Id {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateDictionaryWhenUpload(int Id);
	
	/**
	 * Update Dictionary When Down Dictionary
	 * @param Id {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateDictionaryWhenDown(int Id);
	
	/**
	 * Update Dictionary When Restore
	 * @param Id {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateDictionaryWhenRestore(int Id);
	
	/**
	 * Update Dictionary When Delete
	 * @param Id {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateDictionaryWhenDelete(int Id);
	
	
	/**
	 * Get Down Dictionary By ID
	 * @param Id {@link Integer}
	 * @return {@link Dictionary}
	 */
	public Dictionary getDownDictionaryByID(int Id);
	
	/**
	 * Search Index
	 * @param keyword {@link String}
	 * @param Status {@link String}
	 * @param UserID {@link Integer}
	 * @return {@link List}
	 */
	public List<Dictionary> searchIdex(String keyword,String Status, int UserID);
	
	
	public Dictionary loadquestion(int Id);
	
	public int updateby(int Id,int UserID);
	
	
	
	public int update(int Id,String Anwser, String Question);
	public int busystatusupdate(int Id);
	public int updateCreateby(int Id, int UserID);
	public int busystatus(int Id);
	public void updateRemove(int Id, int userID);
	public void updatedelete(int Id, int userID);
	public int updaterestore(int Id);	


}
