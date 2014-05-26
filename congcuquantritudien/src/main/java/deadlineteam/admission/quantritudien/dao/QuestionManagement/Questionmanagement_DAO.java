package deadlineteam.admission.quantritudien.dao.QuestionManagement;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Questionmanagement_DAO {
	
	/**
	 * Get All Question
	 * @return
	 */
	public List<Questionmanagement> getAllQuestion();
	
	/**
	 * Get All Question in Not Reply
	 * @return
	 */
	public List<Questionmanagement> getListQuestionNotReply();
	
	/**
	 * Get Question by ID
	 * @param Id
	 * @return
	 */
	public Questionmanagement getQuestionByID(int Id);
	
	/**
	 * Get Question By ID to Copy
	 * @param Id
	 * @return
	 */
	public Questionmanagement getQuestionByIDToCopy(int Id);
	
	/**
	 * Get Question Not Reply For User
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getQuestionNotReplyForUser(int UserID);
	
	/**
	 * Get Question Not Reply For Administrator
	 * @return
	 */
	public List<Questionmanagement> getQuestionNotReplyForAdmin();
	
	
	public List<Questionmanagement> getQuestionmanagementbyPage_setting(int page, int record);
	public int updateAnswerbyId(int Id,String Answer);
	
	public List<Dictionary> getListDictionarybyStatus(int status);
	//delete question that is selected
	public int delete(int Id);
	//---------------------- Delete Page
	// delete question-list page
	public List<Questionmanagement> deleteList(int page, int UserID);
	public List<Questionmanagement> getDeleteListForAdmin(int page, int UserID);
	//Load deletequestion-list
	public Questionmanagement deletequestion(int Id);
	public void ResetUpdateAnwserBy(int Id, int userid);
	//restore question that is selected
	public int restore(int Id);
	
	//--------------------- Save Page
	// delete question-list page
	public List<Questionmanagement> getSaveListForUser(int page, int UserID);
	public List<Questionmanagement> getSaveListForAdmin(int page);
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
	// replied question-list page
	public List<Questionmanagement> getRepliedListForAdmin(int page, int UserID);
	
	// load replied question-list
	public Questionmanagement repliedquestion(int ID);
	
	//delete replied question that is selected
	public int deleterepliedquestion (int ID);
	public int updatedelete(int ID);
	
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
	public void updateBusyStatus(int Id,int UserId);
	public void updateBusyStatusAfter(int Id, int UserId);
}
