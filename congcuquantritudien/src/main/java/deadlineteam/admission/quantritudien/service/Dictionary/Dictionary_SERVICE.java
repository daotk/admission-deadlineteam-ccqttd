package deadlineteam.admission.quantritudien.service.Dictionary;

import java.util.Date;
import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Dictionary_SERVICE {
	public int AddDictionarybyID (int Id,int UserId);
	public List<Dictionary> availablelist(int page) ;
	public Dictionary availablequestion(int Id);
	public List<Dictionary> recentlist(int page);
	public Dictionary recentquestion(int Id);
	public List<Dictionary> deletelist(int page);
	public Dictionary question(int Id);
	public List<Dictionary> removelist(int page);
	public Dictionary removequestion(int Id);
	public Dictionary getinformation(int ID);
	public int upload(int Id);
	public Dictionary loadquestion(int Id);
	public int remove(int Id);
	public int restore(int Id);
	public int delete(int Id);
	public void addDictionaryAnswer(String title, String question, String answer);
	public void addDictionaryAnswer2(String title, String question,int createby, String answer, int answerby,Date CreateDate, int status, int deletestatus,int busystatus);
	public int update(int Id,String Anwser, String Title, String Question);
	public int busystatusupdate(int Id);
	public int busystatus(int Id);
	public void AddDictionary(Dictionary dictionary);
	public void updateRemove(int Id, int userID);
	public void updatedelete(int Id, int userID);
	public Users getusername(int ID);
}
