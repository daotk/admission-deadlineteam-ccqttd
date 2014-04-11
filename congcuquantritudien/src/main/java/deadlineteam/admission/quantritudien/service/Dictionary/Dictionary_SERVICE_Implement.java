package deadlineteam.admission.quantritudien.service.Dictionary;

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
import deadlineteam.admission.quantritudien.domain.Users;

@Service
@Transactional
public class Dictionary_SERVICE_Implement  implements Dictionary_SERVICE{
	@Autowired
	private Dictionary_DAO DictionaryDAO ;
	@Autowired
	private Questionmanagement_DAO QuestionmanagementDAO ;
	
	public int AddDictionarybyID (int Id, int UserId){
		Date date = new Date();
		Questionmanagement questionmanagement = QuestionmanagementDAO.getQuestionmanagementbyIDToCopy(Id);
		Dictionary dictionary = new Dictionary();
		dictionary.setTitle(questionmanagement.getTitle());
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

	public void AddDictionary(Dictionary dictionary){
		 DictionaryDAO.AddDictionary(dictionary);
	}
	public List<Dictionary> availablelist(int page) {
		// TODO Auto-generated method stub
		return DictionaryDAO.availablelist(page);
	}	
	public Dictionary availablequestion(int Id) {
		// TODO Auto-generated method stub
		return DictionaryDAO.availablequestion(Id);
	}
	public List<Dictionary> recentlist(int page) {
		// TODO Auto-generated method stub
		return DictionaryDAO.recentlist(page);
	}
	public Dictionary recentquestion(int Id) {
		// TODO Auto-generated method stub
		return DictionaryDAO.recentquestion(Id);
	}
	public List<Dictionary> deletelist(int page) {
		// TODO Auto-generated method stub
		return DictionaryDAO.deletelist(page);
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
	public List<Dictionary> removelist(int page){
		return DictionaryDAO.removelist(page);
	}
	public Dictionary removequestion(int Id){
		return DictionaryDAO.removequestion(Id);
	}
	public int update(int Id,String Anwser, String Title, String Question){
		return DictionaryDAO.update(Id, Anwser, Title, Question);
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
}
