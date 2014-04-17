package deadlineteam.admission.quantritudien.dao.Dictionary;

import java.util.Date;
import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Users;


public interface Dictionary_DAO {	
	public void  AddDictionary (Dictionary  dictionary);
	public List<Dictionary> availablelist(int page , int UserID) ;
	public Dictionary availablequestion(int Id);
	public List<Dictionary> recentlist(int page);
	public Dictionary recentquestion(int Id);
	public List<Dictionary> deletelist(int page);
	public Dictionary question(int Id);
	public List<Dictionary> removelist(int page);
	public Dictionary removequestion(int Id);
	public Dictionary getinformation(int ID);
	public Dictionary loadquestion(int Id);
	public int upload(int Id);
	public int remove(int Id);
	public int restore(int Id);
	public int delete(int Id);
	public int update(int Id,String Anwser, String Question);
	public void addDictionaryAnswer(String title, String question, String answer);
	//public void addDictionaryAnswer2(String title, String question,int createby, String answer, int answerby, Date CreateDate, int status, int deletestatus);
	public int busystatusupdate(int Id);
	public int busystatus(int Id);
	public void updateRemove(int Id, int userID);
	public void updatedelete(int Id, int userID);
	public Users getusername(int ID);
	public void addDictionaryAnswer2(String title, String question,int createby, String answer, int answerby,Date CreateDate, int status, int deletestatus,int busystatus);
}
