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
	public Dictionary availablequestion(int Id);
	public List<Dictionary> recentlist(int page, int UserID);
	public Dictionary recentquestion(int Id);
	public List<Dictionary> deletelist(int page, int UserID);
	public Dictionary question(int Id);
	public List<Dictionary> searchIdex(String keyword,String Status, int UserID);
	public List<Dictionary> removelist(int page, int UserID);
	public Dictionary removequestion(int Id);
	public Dictionary getinformation(int ID);
	public Dictionary loadquestion(int Id);
	public int upload(int Id);
	public int updateby(int Id,int UserID);
	public int remove(int Id);
	public int restore(int Id);
	public int delete(int Id);
	public int update(int Id,String Anwser, String Question);
	public void addDictionaryAnswer(String title, String question, String answer);
	//public void addDictionaryAnswer2(String title, String question,int createby, String answer, int answerby, Date CreateDate, int status, int deletestatus);
	public int busystatusupdate(int Id);
	public int updateCreateby(int Id, int UserID);
	public int busystatus(int Id);
	public void updateRemove(int Id, int userID);
	public void updatedelete(int Id, int userID);
	public int updaterestore(int Id);
	public Users getusername(int ID);
	public List<Dictionary> getAllDictionary();
	public List<Dictionary> getAllDictionaryAvailable();
	public List<Dictionary> getAllDictionaryDeleted();
	public List<Dictionary> getAllDictionaryUp() ;
	public List<Dictionary> getAllDictionaryDown();
	
	public void addDictionaryAnswer2(String title, String question,int createby, String answer, int answerby,Date CreateDate, int status, int deletestatus,int busystatus);
}
