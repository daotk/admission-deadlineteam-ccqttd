package deadlineteam.admission.quantritudien.service.QuestionManagement;

import java.util.List;

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

public interface Questionmanagement_SERVICE{
	
	/**
	 * Get List Question Not Reply
	 * @return
	 */
	public List<Questionmanagement> getListQuestionNotReply();
	
	/**
	 * Get Question By ID
	 * @param Id
	 * @return
	 */
	public Questionmanagement getQuestionByID(int Id) ;
	
	/**
	 * Get Question Not Reply By Page For User
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getQuestionNotReplyByPageForUser(int page, int UserID);
	
	/**
	 * Get Question Not Reply By Page For Administrator
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getQuestionNotReplyByPageForAdmin(int page, int UserID);
	
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
	 * Get All Deleted Question By Page for User
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getListDeletedQuestionByPageForUser(int page, int UserID);
	
	/**
	 * Get All Deleted Question By Page for Administrator
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getListDeletedQuestionByPageForAdmin(int page, int UserID);
	
	/**
	 * Update DeleteBy and DeleteDate
	 * @param ID
	 * @return
	 */
	public int updateDeleteByAndDeleteDate(int ID);
	
	/**
	 * Restore Question
	 * @param Id
	 * @return
	 */
	public int restoreQuestion(int Id);
	
	/**
	 * Get Save Question
	 * @param Id
	 * @return
	 */
	public Questionmanagement getSaveQuestion(int Id);
	
	/**
	 * Get List Save Question By Page For User
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getSaveListByPageForUser(int page, int UserID);
	
	/**
	 * Get List Save Question By Page For Administrator
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getSaveListByPageForAdmin(int page,int UserID );
	
	/**
	 * Update Question When Send Answer
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int updateQuestionWhenSendAnwser(int Id,String Answer);
	
	/**
	 * Save Question
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int saveTemporaryQuestion(int Id,String Answer);
	
	/**
	 * Get List Replied Question By Page For User
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getRepliedListByPageForUser(int page, int UserID);
	/**
	 * Get List Replied Question By Page For Administrator
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getRepliedListByPageForAdmin(int page, int UserID);
	
	/**
	 * Get Replied Question By ID
	 * @param ID
	 * @return
	 */
	public Questionmanagement getRepliedQuestion(int ID);
	
	/**
	 * Get Total Page Question and Dictionary By Status and User
	 * @param status
	 * @param UserID
	 * @return
	 */
	public int totalPageQuestionAndDictionary(int status, int UserID);
	
	/**
	 * Get Total Page Question Delete
	 * @param status
	 * @return
	 */
	public int totalPageQuestionDeleted(int status);
	
	/**
	 * Create Index
	 */
	public void createIndex();
	
	/**
	 * Search Index For Administrator
	 * @param keyword
	 * @param Status
	 * @return
	 */
	public List<Questionmanagement> searchIndexForAdmin(String keyword,String Status, int UserID);
	
	/**
	 * Search Index For User
	 * @param keyword
	 * @param Status
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> searchIndexForUser(String keyword,String Status,int UserID);
	
	/**
	 * Search Index Delete Question For User
	 * @param keyword
	 * @param Status
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> searchIndexDeleteListForUser(String keyword,String Status,int UserID);
	
	/**
	 * Restore Multiple Question
	 * @param listdelete
	 * @param userid
	 * @return
	 */
	public List<Questionmanagement> restoreMultipleQuestion (String listdelete, int userid);
	
	/**
	 * Delete Multiple Question
	 * @param listdelete
	 * @param userid
	 * @return
	 */
	public List<Questionmanagement> deleteMultipleQuestion (String listdelete, int userid);

	
	//----------------RESTful web service
	/**
	 * Add Question
	 * @param question
	 */
	public void addQuestionRESTful(Questionmanagement question);
	
	/**
	 * Copy Question to Dictionary
	 * @param Id
	 * @param userid
	 */
	public void TransferToDictionary(int Id, int userid);
	
	/**
	 * Update DeleteBy And DeleteDate
	 * @param Id
	 * @param userid
	 */
	public void updateDeleteByAndDeleteDate(int Id, int userid);
	
	/**
	 * Update AnwserBy and AnwserDate
	 * @param Id
	 * @param userid
	 */
	public void updateAnwserByAndAnwserDate(int Id, int userid);
	
	/**
	 * Get Setting
	 * @param UserId
	 * @return
	 */
	public Setting getSetting(int UserId);
	
	/**
	 * Get List Question by Status
	 * @param status
	 * @return
	 */
	public List<Questionmanagement> getListQuestionbyStatus(int status);
	
	/**
	 * Update BusyStatus Question When Click Question
	 * @param Id
	 * @param UserId
	 */
	public void updateBusyStatusQuestion(int Id,int UserId);
	
	/**
	 * Reset BusyStatus Question When Click Question
	 * @param Id
	 * @param UserId
	 */
	public void resetBusyStatusQuestion(int Id,int UserId);

	/**
	 * Check Question Is Busy
	 * @param Id
	 * @param UserId
	 * @return
	 */
	public boolean checkQuestionIsBusy(int Id,int UserId) ;
	
	/**
	 * Get UserID in Busy Status By ID Question
	 * @param Id
	 * @return
	 */
	public int geUserIDByIdQuestion(int Id);
	
	/**
	 * Check UserID can use ID Question in Save List
	 * @param UserId
	 * @param Id
	 * @return
	 */
	public boolean checkSaveListByUserId(int UserId,int Id);
	
	/**
	 * Check UserID can use ID Question in Delete List
	 * @param UserId
	 * @param Id
	 * @return
	 */
	public boolean checkDeleteListByUserId(int UserId,int Id);
	
	/**
	 * Check ID Question in Not Reply List
	 * @param Id
	 * @return
	 */
	public boolean checkIdQuestionNotReply(int Id) ;
	
	/**
	 * Check ID Question in Not Save List
	 * @param Id
	 * @return
	 */
	public boolean checkIdQuestionSave(int Id) ;
	
	/**
	 * Check ID Question in Not Reply List
	 * @param Id
	 * @return
	 */
	public boolean checkIdQuestionReplied(int Id) ;
	
	/**
	 * Check ID Question in Not Delete List
	 * @param Id
	 * @return
	 */
	public boolean checkIdQuestionDeleted(int Id);

	// Khang android update 11/05
			public List<Questionmanagement> getListQuestionmanagementAndroid(int page);
			public List<Questionmanagement> searchIdexAndroid(int page, String keyword);
			
			public List<Questionmanagement> getSaveListQuestionmanagementAndroid(int page); // for admin
			public List<Questionmanagement> getSaveListForUserAndroid(int page, int UserID);
			public List<Questionmanagement> searchIdexAndroidSaveListAndroid(int page, String keyword); // for admin
			public List<Questionmanagement> searchIdexAndroidSaveListForUserAndroid(int page, String keyword, int UserID);
			
			public List<Questionmanagement> getReplyListQuestionmanagementAndroid(int page); // for admin
			public List<Questionmanagement> getReplyListForUserAndroid(int page, int UserID);
			public List<Questionmanagement> searchIdexAndroidReplyListAndroid(int page, String keyword); // for admin
			public List<Questionmanagement> searchIdexAndroidReplyListForUserAndroid(int page, String keyword, int UserID);
			
			public List<Questionmanagement> getDeleteListQuestionmanagementAndroid(int page); // for admin
			public List<Questionmanagement> getDeleteListForUserAndroid(int page, int UserID);
			public List<Questionmanagement> searchIdexAndroidDeleteListAndroid(int page, String keyword); // for admin
			public List<Questionmanagement> searchIdexAndroidDeleteListForUserAndroid(int page, String keyword, int UserID);
}
