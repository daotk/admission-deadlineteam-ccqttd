package deadlineteam.admission.quantritudien.service.QuestionManagement;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Questionmanagement_SERVICE{
	public List<Questionmanagement> findpage1(String keyword);
	public List<Questionmanagement> getListQuestionmanagement();
	public Questionmanagement getQuestionmanagementbyID(int Id) ;
	public List<Questionmanagement> getQuestionmanagementbyPage(int page, int UserID);
	public int updateAnswerbyId(int Id,String Answer);
	public List<Questionmanagement> getQuestionmanagementbyPage_setting(int page, int record);
	//delete question that is selected
	public int delete(int Id);
	//--------------------- Delete Page
	public List<Dictionary> getListDictionaryDelete(int status);
	// Delete question-list page
	public List<Questionmanagement> deleteList(int page, int UserID);
	public List<Questionmanagement> getDeleteListForAdmin(int page, int UserID);
	//Load deletequestion-list
	public Questionmanagement deletequestion(int Id);
	//restore question that is selected
	public int restore(int Id);
	//--------------------- Save Page
	//Save-question list page
	public Questionmanagement savequestion(int Id);
	//Load save-question list for user
	public List<Questionmanagement> savelist(int page, int UserID);
	//Load save-question list for admin
	public List<Questionmanagement> getSaveListForAdmin(int page,int UserID );
	//delete save-question that is selected
	public int deletesavequestion(int Id);
	//save save-question
	public int SaveAnwser(int Id,String Answer);
	//send save-question
	public int SendAnwser(int Id,String Answer);
	// Save Temporaty
	public int SaveTemporaryAnswerbyId(int Id,String Answer);
	
	//-------------------------replied page
	//replied question-list page
	public List<Questionmanagement> repliedList(int page, int UserID);
	public List<Questionmanagement> getRepliedListForAdmin(int page, int UserID);
	
	//load replied question-list
	public Questionmanagement repliedquestion(int ID);
	
	//delete replied question that is selected
	public int deleterepliedquestion(int ID);
	
	public int totalPageQuestiomanagement(int status, int UserID);
	public int totalPageQuestiomanagementDelete(int status);
	public void createIndex();
	public List<Questionmanagement> searchIdex(String keyword,String Status);
	public List<Questionmanagement> searchIdexForAdmin(String keyword,String Status,int UserID);
	public List<Questionmanagement> searchIdexDeleteListForAdmin(String keyword,String Status,int UserID);
	public void deleteall (String listdelete, int userid);
	
	//----------------RESTful web service
	public void addQuestionRESTful(Questionmanagement question);
	public void TransferToDictionary(int Id, int userid);
	public void UpdateDelete(int Id, int userid);
	public void UpdateAnwserBy(int Id, int userid);
	public Users getusername(int username);
	public Setting getSetting(int UserId);

}
