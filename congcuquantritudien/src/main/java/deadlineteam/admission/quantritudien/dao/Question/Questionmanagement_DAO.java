package deadlineteam.admission.quantritudien.dao.Question;

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
	
	/**
	 * Save Temporary
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int saveTemporaryQuestion(int Id,String Answer);
	
	/**
	 * Update Answer
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int updateAnswer(int Id,String Answer);
	
	/**
	 * Delete Question
	 * @param Id
	 * @return
	 */
	public int deleteQuestion(int Id);
	
	/**
	 * Get Question Was Delete
	 * @param Id
	 * @return
	 */
	public Questionmanagement getDeletedQuestion(int Id);
	
	/**
	 * Get List Question Deleted For User
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getListDeletedForUser(int UserID);
	
	/**
	 * Get List Question Deleted For Administrator
	 * @return
	 */
	public List<Questionmanagement> getListDeletedForAdmin();
	
	/**
	 * Restore Question
	 * @param Id
	 * @return
	 */
	public int restoreQuestion(int Id);
	
	/**
	 * Get List Save Question For User
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getSaveListForUser(int UserID);
	
	/**
	 * Get List Save Question For Administrator
	 * @return
	 */
	public List<Questionmanagement> getSaveListForAdmin();
	
	/**
	 * Get Save Question 
	 * @param Id
	 * @return
	 */
	public Questionmanagement getSaveQuestion(int Id);
	
	/**
	 * Update When Send Answer
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int updateQuestionWhenSendAnwser(int Id,String Answer);
	
	/**
	 * Update DeleteBy And DeleteDate
	 * @param ID
	 * @return
	 */
	public int updateDeleteByAndDeleteDate(int ID);
	
	/**
	 * Get List Replied Question For User
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getRepliedListForUser(int UserID);

	/**
	 * Get List Replied Question For Administrator
	 * @return
	 */
	public List<Questionmanagement> getRepliedListForAdmin();
	
	/**
	 * Get Reply Question
	 * @param ID
	 * @return
	 */
	public Questionmanagement getRepliedQuestion(int ID);
	
	/**
	 * Get List Question By Status
	 * @param status
	 * @return
	 */
	public List<Questionmanagement> getListQuestionbyStatus(int status);
	
	/**
	 * Get List Question By Delete Status
	 * @param status
	 * @return
	 */
	public List<Questionmanagement> getListQuestionByDeleteStatus(int status);
	
	/**
	 * Create Index
	 */
	public void createIndex();	
	
	/**
	 * Search index
	 * @param keyword
	 * @param Status
	 * @return
	 */
	public List<Questionmanagement> searchIdex(String keyword,String Status);
	
	/**
	 * Add Question (RESTfull)
	 * @param question
	 */
	public void addQuestion(Questionmanagement question);
	
	/**
	 * Transfer Question to Dictionary
	 * @param Id
	 * @param userid
	 */
	public void copyQuestionToDictionary(int Id, int userid);
	
	/**
	 * Update DeleteBy and DeleteDate
	 * @param Id
	 * @param userid
	 */
	public void updateDeleteByAndDeleteDate(int Id, int userid);

	/**
	 * Update AnswerBy And AnswerDate
	 * @param Id
	 * @param userid
	 */
	public void updateAnwserByAndAnwserDate(int Id, int userid);
	
	/**
	 * Get Setting
	 * @param Id
	 * @return
	 */
	public Setting getSetting(int Id);
	
	/**
	 * Update Busy Status when click question
	 * @param Id
	 * @param UserId
	 */
	public void updateBusyStatusQuestion(int Id,int UserId);
	
	/**
	 * Update Busy Status when lick another question
	 * @param Id
	 * @param UserId
	 */
	public void resetBusyStatusQuestion(int Id, int UserId);
}
