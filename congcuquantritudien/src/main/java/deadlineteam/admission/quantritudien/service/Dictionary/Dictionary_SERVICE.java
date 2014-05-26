package deadlineteam.admission.quantritudien.service.Dictionary;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Setting;

public interface Dictionary_SERVICE {
	public int AddDictionarybyID (int Id,int UserId);
	public List<Dictionary> availablelist(int page, int UserID);
	public List<Dictionary> availablelistadmin(int page , int UserID);
	public List<Dictionary> recentlist(int page, int UserID);
	public Dictionary getRecentDictionaryByID(int Id);
	public List<Dictionary> deletelist(int page, int UserID);
	public List<Dictionary> removelist(int page, int UserID);
	public Dictionary getDownDictionaryByID(int Id);
	public Dictionary getDictionaryByID(int ID);
	public int updateDictionaryWhenUpload(int Id);
	public int updateCreatebyWhenEdit(int Id, int UserID);
	public int updateUpdateByWhenUpload(int Id, int UserID);
	public List<Dictionary> searchIdex(String keyword,String Status, int UserID);
	public Dictionary getDictionaryByIDNotDelete(int Id);
	public int updateDictionaryWhenDown(int Id);
	public int updateDictionaryWhenRestore(int Id);
	public int updateDictionaryWhenDelete(int Id);
	public int updateQuesionAndAnwserDictionary(int Id,String Anwser, String Question);
	public int updateDeleteByAndDeleteDateWhenRestore(int Id);
	public List<Dictionary> restorealldictionary(String checkbox, int login);
	public List<Dictionary> deletealldictionary(String checkbox, int login);
	public void AddDictionary(Dictionary dictionary);
	public void updateUpdateByAndUpdateDateWhenDown(int Id, int userID);
	public void updateDeleteByAndDeleteDateWhenDelete(int Id, int userID);
	public Setting getSetting(int UserId);
	public boolean checkDictionaryByUserId(int UserId,int Id);
	public boolean checkDictionaryDeleteByUserId(int UserId,int Id);
	public boolean checkIdDictionary(int Id);
	public boolean checkIdDictionaryAvaiable(int Id);
	public boolean checkIdDictionaryDeleted(int Id);
	public boolean checkIdDictionaryUp(int Id);
	public boolean checkIdDictionaryDown(int Id) ;
	
	// khang 15/10
			public List<Dictionary> getDictionaryAvailableForAdmin(int page);
			public List<Dictionary> getDictionaryAvailableForUser(int page, int UserID);
			public List<Dictionary> searchDictionaryAvailableForAdmin(int page, String keyword);
			public List<Dictionary> searchDictionaryAvailableForUser(int page, String keyword, int UserID);
			public List<Dictionary> getDictionaryUpload(int page);
			public List<Dictionary> searchDictionaryUpload(int page, String keyword);
			public List<Dictionary> getDictionaryDown(int page);
			public List<Dictionary> searchDictionaryDown(int page, String keyword);
			public List<Dictionary> getDictionaryDelete(int page);
			public List<Dictionary> searchDictionaryDelete(int page, String keyword);
}
