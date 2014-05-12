package deadlineteam.admission.quantritudien.service.Dictionary;

import java.util.Date;
import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Dictionary_SERVICE {
	public int AddDictionarybyID (int Id,int UserId);
	public List<Dictionary> availablelist(int page, int UserID);
	public List<Dictionary> availablelistadmin(int page , int UserID);
	public Dictionary availablequestion(int Id);
	public List<Dictionary> recentlist(int page, int UserID);
	public Dictionary recentquestion(int Id);
	public List<Dictionary> deletelist(int page, int UserID);
	public Dictionary question(int Id);
	public List<Dictionary> removelist(int page, int UserID);
	public Dictionary removequestion(int Id);
	public Dictionary getinformation(int ID);
	public int upload(int Id);
	public int updateCreateby(int Id, int UserID);
	public int updateby(int Id, int UserID);
	public List<Dictionary> searchIdex(String keyword,String Status, int UserID);
	public Dictionary loadquestion(int Id);
	public int remove(int Id);
	public int restore(int Id);
	public int delete(int Id);
	public void addDictionaryAnswer(String title, String question, String answer);
	public void addDictionaryAnswer2(String title, String question,int createby, String answer, int answerby,Date CreateDate, int status, int deletestatus,int busystatus);
	public int update(int Id,String Anwser, String Question);
	public int busystatusupdate(int Id);
	public int busystatus(int Id);
	public List<Dictionary> restorealldictionary(String checkbox, int login);
	public List<Dictionary> deletealldictionary(String checkbox, int login);
	public void AddDictionary(Dictionary dictionary);
	public void updateRemove(int Id, int userID);
	public void updatedelete(int Id, int userID);
	public Users getusername(int ID);
	public Setting getSetting(int UserId);
}
