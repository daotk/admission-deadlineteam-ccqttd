package deadlineteam.admission.quantritudien.dao.QuestionManagement;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Questionmanagement_DAO {
	public List<Questionmanagement> findpage1(String keyword);
	public List<Questionmanagement> getListQuestionmanagement();
	public Questionmanagement getQuestionmanagementbyID(int Id);
	public List<Questionmanagement> getQuestionmanagementbyPage(int page, int UserID);
	public List<Questionmanagement> getQuestionmanagementbyPage_setting(int page, int record);
	public int updateAnswerbyId(int Id,String Answer);
	public Questionmanagement getQuestionmanagementbyIDToCopy(int Id);
	public List<Dictionary> getListDictionarybyStatus(int status);
	//delete question that is selected
	public int delete(int Id);
	//---------------------- Delete Page
	// delete question-list page
	public List<Questionmanagement> deleteList(int page, int UserID);
	//Load deletequestion-list
	public Questionmanagement deletequestion(int Id);
	//restore question that is selected
	public int restore(int Id);
	
	//--------------------- Save Page
	// delete question-list page
	public List<Questionmanagement> savelist(int page, int UserID);
	//Load deletequestion-list
	public List<Dictionary> getListDictionaryDelete(int status);
	public Questionmanagement savequestion(int Id);
	//delete save-question that is selected
	public int deletesavequestion(int Id);
	//save save-question
	public int SaveAnwser(int Id,String Answer);
	//send save-question
	public int SendAnwser(int Id,String Answer);
	//Save Temporary Answer
	public int SaveTemporaryAnswerbyId(int Id,String Answer);
	
	//---------------------- Replied page
	// replied question-list page
	public List<Questionmanagement> repliedList(int page, int UserID);
	
	// load replied question-list
	public Questionmanagement repliedquestion(int ID);
	
	//delete replied question that is selected
	public int deleterepliedquestion (int ID);
	
	
	public List<Questionmanagement> getListQuestionmanagementbyStatus(int status);
	public List<Questionmanagement> getListQuestionmanagementbyDeleteStatus(int status);
	
	public void createIndex();	
	public List<Questionmanagement> searchIdex(String keyword,String Status);	
	//-------------------- RESTful web service----------
	public void addquestion(Questionmanagement question);
	public void TransferToDictionary(int Id, int userid);
	public void UpdateDelete(int Id, int userid);
	public void UpdateAnwserBy(int Id, int userid);
	public Users getusername(int username);
	
	public Setting getSetting(int Id);
	
}
