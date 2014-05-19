package deadlineteam.admission.quantritudien.service.Dictionary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import deadlineteam.admission.quantritudien.dao.Dictionary.Dictionary_DAO;
import deadlineteam.admission.quantritudien.dao.QuestionManagement.Questionmanagement_DAO;
import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;

@Service
@Transactional
public class Dictionary_SERVICE_Implement  implements Dictionary_SERVICE{
	@Autowired
	private Dictionary_DAO DictionaryDAO ;
	@Autowired
	private Users_SERVICE UserSERVICE ;
	@Autowired
	private Questionmanagement_DAO QuestionmanagementDAO ;
	@Autowired
	private Users_SERVICE userservice;
	public int AddDictionarybyID (int Id, int UserId){
		Date date = new Date();
		Questionmanagement questionmanagement = QuestionmanagementDAO.getQuestionmanagementbyIDToCopy(Id);
		Dictionary dictionary = new Dictionary();
		dictionary.setQuestion(questionmanagement.getQuestion());
		dictionary.setCreateBy(2);
		dictionary.setAnwser(questionmanagement.getAnswer());
		dictionary.setAnwserBy(questionmanagement.getAnswerBy());
		dictionary.setCreateDate(null);
		dictionary.setUpdateBy(null);
		dictionary.setUpdateDate(null);
		dictionary.setStatus(1);
		dictionary.setDeleteStatus(0);
		dictionary.setDeleteBy(null);
		dictionary.setDeleteDate(null);
		DictionaryDAO.AddDictionary(dictionary);
		return 1;
	}
	public int updateCreateby(int Id, int UserID){
		return DictionaryDAO.updateCreateby(Id, UserID);
	}
	public void AddDictionary(Dictionary dictionary){
		 DictionaryDAO.AddDictionary(dictionary);
	}
	public List<Dictionary> availablelist(int page, int UserID) {
		// TODO Auto-generated method stub
		List<Dictionary> list=  DictionaryDAO.availablelist(page, UserID);
		List<Dictionary> shortlist = new ArrayList<Dictionary>();
		for(;list.size()>0;){
			Date max = list.get(0).getCreateDate();
			int rememberint =0;
			for(int i=1;i<list.size();i++){
				if(list.get(i).getCreateDate().compareTo(max)>0){
					max = list.get(i).getCreateDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}	
		List<Dictionary> newlist = new ArrayList<Dictionary>();
		 Setting settings = getSetting(UserID);
		 int begin = page* settings.getRecordDictionary();
		 int end = begin + settings.getRecordDictionary();
		if(end > shortlist.size()){
			end = shortlist.size();
		}
		int l = 0;
		for(int k = begin; k < end; k++){
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
		
	}	
	public List<Dictionary> availablelistadmin(int page, int UserID) {
		// TODO Auto-generated method stub
		List<Dictionary> list=  DictionaryDAO.availablelistadmin(page, UserID);
		List<Dictionary> shortlist = new ArrayList<Dictionary>();
		for(;list.size()>0;){
			Date max = list.get(0).getCreateDate();
			int rememberint =0;
			for(int i=1;i<list.size();i++){
				if(list.get(i).getCreateDate().compareTo(max)>0){
					max = list.get(i).getCreateDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}	
		List<Dictionary> newlist = new ArrayList<Dictionary>();
		 Setting settings = getSetting(UserID);
		 int begin = page* settings.getRecordDictionary();
		 int end = begin + settings.getRecordDictionary();
		if(end > shortlist.size()){
			end = shortlist.size();
		}
		int l = 0;
		for(int k = begin; k < end; k++){
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
		
	}	
	
	public Dictionary availablequestion(int Id) {
		// TODO Auto-generated method stub
		return DictionaryDAO.availablequestion(Id);
		
	}
	public List<Dictionary> restorealldictionary(String checkbox, int login){
		List<Dictionary> returnlist = new ArrayList<Dictionary>();
		String[] liststring = checkbox.split(",");
		for(int i=0;i<liststring.length;i++){
			int deleteid = Integer.parseInt(liststring[i].toString());
			Dictionary question = DictionaryDAO.getinformation(deleteid);
			DictionaryDAO.updatedelete(deleteid, login);
			DictionaryDAO.restore(deleteid);
			DictionaryDAO.updaterestore(deleteid);
			returnlist.add(question);
		}
		return returnlist;
	}
	public List<Dictionary> deletealldictionary(String checkbox, int login){
		List<Dictionary> returnlist = new ArrayList<Dictionary>();
		String[] liststring = checkbox.split(",");
		for(int i=0;i<liststring.length;i++){
			int deleteid = Integer.parseInt(liststring[i].toString());
			Dictionary question = DictionaryDAO.getinformation(deleteid);
			DictionaryDAO.updatedelete(deleteid, login);
			DictionaryDAO.delete(deleteid);
			
			returnlist.add(question);
		}
		return returnlist;
		
	}
	public List<Dictionary> recentlist(int page, int UserID) {
		// TODO Auto-generated method stub
		
		List<Dictionary> list=  DictionaryDAO.recentlist(page, UserID);
		List<Dictionary> shortlist = new ArrayList<Dictionary>();
		for(;list.size()>0;){
			Date max = list.get(0).getUpdateDate();
			int rememberint =0;
			for(int i=1;i<list.size();i++){
				if(list.get(i).getUpdateDate().compareTo(max)>0){
					max = list.get(i).getUpdateDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}	
		List<Dictionary> newlist = new ArrayList<Dictionary>();
		 Setting settings = getSetting(UserID);
		 int begin = page* settings.getRecordDictionary();
		 int end = begin + settings.getRecordDictionary();
		if(end > shortlist.size()){
			end = shortlist.size();
		}
		int l = 0;
		for(int k = begin; k < end; k++){
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
	}
	public Dictionary recentquestion(int Id) {
		// TODO Auto-generated method stub
		return DictionaryDAO.recentquestion(Id);
	}
	public int updaterestore(int Id){
		return DictionaryDAO.updaterestore(Id);
	}
	public List<Dictionary> deletelist(int page, int UserID) {
		// TODO Auto-generated method stub
		
		List<Dictionary> list=  DictionaryDAO.deletelist(page, UserID);
		List<Dictionary> shortlist = new ArrayList<Dictionary>();
		for(;list.size()>0;){
			Date max = list.get(0).getDeleteDate();
			int rememberint =0;
			for(int i=1;i<list.size();i++){
				if(list.get(i).getDeleteDate().compareTo(max)>0){
					max = list.get(i).getDeleteDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}	
		List<Dictionary> newlist = new ArrayList<Dictionary>();
		 Setting settings = getSetting(UserID);
		 int begin = page* settings.getRecordDictionary();
		 int end = begin + settings.getRecordDictionary();
		if(end > shortlist.size()){
			end = shortlist.size();
		}
		int l = 0;
		for(int k = begin; k < end; k++){
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
	}
	public Dictionary question(int Id) {
		// TODO Auto-generated method stub
		return DictionaryDAO.question(Id);	
	}
	public Dictionary getinformation(int ID){
		return DictionaryDAO.getinformation(ID);
	}
	public int upload(int Id){
		// TODO Auto-generated method stub
		return DictionaryDAO.upload(Id);
	}
	public int updateby(int Id, int UserID){
		// TODO Auto-generated method stub
		return DictionaryDAO.updateby(Id, UserID);
	}
	public int remove(int Id){
		// TODO Auto-generated method stub
		return DictionaryDAO.remove(Id);
	}
	public int restore(int Id){
		// TODO Auto-generated method stub
		return DictionaryDAO.restore(Id);
	}
	public int delete(int Id){
		// TODO Auto-generated method stub
		return DictionaryDAO.delete(Id);
	}
	
	@Override
	public void addDictionaryAnswer(String title, String question, String answer)
	{
		DictionaryDAO.addDictionaryAnswer(title,question,answer);
	}
	public void addDictionaryAnswer2(String title, String question,int createby, String answer, int answerby,Date CreateDate, int status, int deletestatus,int busystatus){
		DictionaryDAO.addDictionaryAnswer2(title,question,createby,answer,answerby,CreateDate,status,deletestatus,busystatus);
	}
	public List<Dictionary> removelist(int page, int UserID){
		
		List<Dictionary> list=  DictionaryDAO.removelist(page, UserID);
		List<Dictionary> shortlist = new ArrayList<Dictionary>();
		for(;list.size()>0;){
			Date max = list.get(0).getUpdateDate();
			int rememberint =0;
			for(int i=1;i<list.size();i++){
				if(list.get(i).getUpdateDate().compareTo(max)>0){
					max = list.get(i).getUpdateDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}	
		List<Dictionary> newlist = new ArrayList<Dictionary>();
		 Setting settings = getSetting(UserID);
		 int begin = page* settings.getRecordDictionary();
		 int end = begin + settings.getRecordDictionary();
		if(end > shortlist.size()){
			end = shortlist.size();
		}
		int l = 0;
		for(int k = begin; k < end; k++){
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
	}
	public Dictionary removequestion(int Id){
		return DictionaryDAO.removequestion(Id);
	}
	public int update(int Id,String Anwser,  String Question){
		return DictionaryDAO.update(Id, Anwser, Question);
	}
	public int busystatusupdate(int Id){
		return DictionaryDAO.busystatusupdate(Id);
	}
	public int busystatus(int Id){
		return DictionaryDAO.busystatus(Id);
	}
	public Dictionary loadquestion(int Id){
		return DictionaryDAO.loadquestion(Id);
	}
	public void updateRemove(int Id, int userID){
		DictionaryDAO.updateRemove(Id, userID);
	}
	public void updatedelete(int Id, int userID){
		DictionaryDAO.updatedelete(Id, userID);
	}
	public Users getusername(int ID){
		return DictionaryDAO.getusername(ID);
	}
	public Setting getSetting(int UserId){
		return QuestionmanagementDAO.getSetting(UserId);
	}
	public List<Dictionary> searchIdex(String keyword,String Status, int UserID){
		List<Dictionary> list = DictionaryDAO.searchIdex(keyword, Status, UserID);
		List<Dictionary> newlist = new ArrayList<Dictionary>();
		for(int i = 0 ; i < list.size() ; i++){
			if(list.get(i).getCreateBy() == UserID){
				newlist.add(list.get(i));
			}
		}
		Users users = userservice.getUser(UserID);
		if(users.getAuthorization() ==1){
			return list;
		}else{
			return newlist;
		}
		
	}
	
	public boolean checkDictionaryByUserId(int UserId,int Id) {
		Dictionary dictionary = DictionaryDAO.getinformation(Id);
		if(UserSERVICE.checkIsAdmin(UserId)==true){
			return true;
		}else{
			if(dictionary.getUpdateBy()!=null){
				if(dictionary.getUpdateBy().equals(UserId)){
					return true;
				}else {
					return false;
				}
			}else{
				if(dictionary.getAnwserBy().equals(UserId)){
					return true;
				}else {
					return false;
				}
			}
		}
		
	}
	
	public boolean checkDictionaryDeleteByUserId(int UserId,int Id) {
		Dictionary dictionary = DictionaryDAO.getinformation(Id);
		if(UserSERVICE.checkIsAdmin(UserId)==true){
			return true;
		}else{
				if(dictionary.getDeleteBy().equals(UserId)){
					return true;
				}else {
					return false;
				}
		}
	}
	
}
