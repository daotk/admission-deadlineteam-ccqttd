package deadlineteam.admission.quantritudien.service.Dictionary;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Setting;

public interface Dictionary_SERVICE {
	
	/**
	 * Add Question to Dictionary
	 * @param Id
	 * @param UserId
	 * @return
	 */
	public int addDictionaryFormQuestion (int Id,int UserId);
	
	/**
	 * Update Create When Edit
	 * @param Id
	 * @param UserID
	 * @return
	 */
	public int updateCreateByWhenEdit(int Id, int UserID);
	
	/**
	 * Add Dictionary
	 * @param dictionary
	 */
	public void AddDictionary(Dictionary dictionary);
	
	/**
	 * Get List Available Dictionary By Page For User 
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> getListAvailableDictionaryByPageForUser(int page, int UserID);
	
	/**
	 * Get List Available Dictionary By Page For Administrator 
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> getListAvailableDictionaryForAdmin(int page , int UserID);
	
	/**
	 * Restore multiple Dictionary
	 * @param checkbox
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> restoreMultipleDictionary(String checkbox, int UserID);
	
	/**
	 * Delete multiple Dictionary
	 * @param checkbox
	 * @param UserId
	 * @return
	 */
	public List<Dictionary> deleteMultipleDictionary(String checkbox, int UserId);

	/**
	 * Get List Recent Dictionary By Page
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> getAllListRecentDictionary(int page, int UserID);
	
	/**
	 * Get Recent Dictionary By ID
	 * @param Id
	 * @return
	 */
	public Dictionary getRecentDictionaryByID(int Id);
	
	/**
	 * Update DeleteBy And DeleteDate When Restore
	 * @param Id
	 * @return
	 */
	public int updateDeleteByAndDeleteDateWhenRestore(int Id);
	
	/**
	 * Get All List Delete Dictionary 
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> getAllListDeleteDictionary(int page, int UserID);
	
	/**
	 * Get Dictionary By ID
	 * @param ID
	 * @return
	 */
	public Dictionary getDictionaryByID(int ID);
	
	/**
	 * Update Dictionary When Upload
	 * @param Id
	 * @return
	 */
	public int updateDictionaryWhenUpload(int Id);
	
	/**
	 * Update UpdateDate When Upload
	 * @param Id
	 * @param UserID
	 * @return
	 */
	public int updateUpdateByWhenUpload(int Id, int UserID);
	
	/**
	 * Update Dictionary When Down
	 * @param Id
	 * @return
	 */
	public int updateDictionaryWhenDown(int Id);
	
	/**
	 *  Update Dictionary When Restore
	 * @param Id
	 * @return
	 */
	public int updateDictionaryWhenRestore(int Id);
	
	/**
	 * Update Dictionary When Delete
	 * @param Id
	 * @return
	 */
	public int updateDictionaryWhenDelete(int Id);
	
	/**
	 * Get All List Down Dictionary
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> getAllListDownDictionary(int page, int UserID);
	
	/**
	 * Get Down Dictionary By ID
	 * @param Id
	 * @return
	 */
	public Dictionary getDownDictionaryByID(int Id);
	
	/**
	 * Update Question And Answer Dictionary
	 * @param Id
	 * @param Anwser
	 * @param Question
	 * @return
	 */
	public int updateQuesionAndAnwserDictionary(int Id,String Anwser, String Question);
	
	/**
	 * get Dictionary Not Delete By ID
	 * @param Id
	 * @return
	 */
	public Dictionary getDictionaryByIDNotDelete(int Id);
	
	/**
	 * Update UpdateBy And UpdateDate When Down Dictionary
	 * @param Id
	 * @param userID
	 */
	public void updateUpdateByAndUpdateDateWhenDown(int Id, int userID);
	
	/**
	 * Update DeleteBy And DeleteDate When Delete Dictionary
	 * @param Id
	 * @param userID
	 */
	public void updateDeleteByAndDeleteDateWhenDelete(int Id, int userID);
	
	/**
	 * Get Setting
	 * @param UserId
	 * @return
	 */
	public Setting getSetting(int UserId);
	
	/**
	 * Search Index
	 * @param keyword
	 * @param Status
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> searchIdex(String keyword,String Status, int UserID);
	
	/**
	 * Check Dictionary By UserID
	 * @param UserId
	 * @param Id
	 * @return
	 */
	public boolean checkDictionaryByUserId(int UserId,int Id);
	
	/**
	 * Check Dictionary Delete By UserID
	 * @param UserId
	 * @param Id
	 * @return
	 */
	public boolean checkDictionaryDeleteByUserId(int UserId,int Id);
	
	/**
	 * Check ID Dictionary Is Existed
	 * @param Id
	 * @return
	 */
	public boolean checkIdDictionary(int Id);
	
	/**
	 * Check Id Dictionary In Available List
	 * @param Id
	 * @return
	 */
	public boolean checkIdDictionaryAvaiable(int Id);
	
	/**
	 * Check ID Dictionary Delete
	 * @param Id
	 * @return
	 */
	public boolean checkIdDictionaryDeleted(int Id);
	
	/**
	 * Check Id Dictionary Upload
	 * @param Id
	 * @return
	 */
	public boolean checkIdDictionaryUp(int Id);
	
	/**
	 * Check ID Dictionary Down
	 * @param Id
	 * @return
	 */
	public boolean checkIdDictionaryDown(int Id) ;
	

	// khang 15/10
	
	/**
	 * Get List Available Dictionary For Administrator
	 * @param page
	 * @return
	 */
	public List<Dictionary> getDictionaryAvailableForAdmin(int page);
	
	/**
	 * Get List Available Dictionary For User
	 * @param page
	 * @return
	 */
	public List<Dictionary> getDictionaryAvailableForUser(int page, int UserID);
	
	/**
	 * Search Available Dictionary For Administrator
	 * @param page
	 * @param keyword
	 * @return
	 */
	public List<Dictionary> searchDictionaryAvailableForAdmin(int page, String keyword);
	
	/**
	 * Search Available Dictionary For User
	 * @param page
	 * @param keyword
	 * @return
	 */
	public List<Dictionary> searchDictionaryAvailableForUser(int page, String keyword, int UserID);
	
	/**
	 * Get Dictionary Upload
	 * @param page
	 * @return
	 */
	public List<Dictionary> getDictionaryUpload(int page);
	
	/**
	 * Search Dictionary Upload
	 * @param page
	 * @param keyword
	 * @return
	 */
	public List<Dictionary> searchDictionaryUpload(int page, String keyword);
	
	/**
	 * Get Dictionary Down
	 * @param page
	 * @return
	 */
	public List<Dictionary> getDictionaryDown(int page);
	
	/**
	 * Search Dictionary Down
	 * @param page
	 * @param keyword
	 * @return
	 */
	public List<Dictionary> searchDictionaryDown(int page, String keyword);
	
	/**
	 * Get Dictionary Delete
	 * @param page
	 * @return
	 */
	public List<Dictionary> getDictionaryDelete(int page);
	
	/**
	 * Search Dictionary Delete
	 * @param page
	 * @return
	 */
	public List<Dictionary> searchDictionaryDelete(int page, String keyword);
}
