package deadlineteam.admission.quantritudien.service.Question;

import deadlineteam.admission.quantritudien.dao.Dictionary.Dictionary_DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import deadlineteam.admission.quantritudien.dao.Question.Questionmanagement_DAO;
import deadlineteam.admission.quantritudien.dao.User.Users_DAO;
import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;

@Service
@Transactional
public class Questionmanagement_SERVICE_Implement implements
		Questionmanagement_SERVICE {
	private int numAndroid = 10;
	@Autowired
	private Users_DAO UserDAO;
	
	@Autowired
	private Users_SERVICE UserSERVICE;
	@Autowired
	private Questionmanagement_DAO QuestionmanagementDAO;
	@Autowired
	private Dictionary_DAO Dictionary_DAO;
	
	/**
	 * Get List Question Not Reply
	 * @return
	 */
	public List<Questionmanagement> getListQuestionNotReply() {
		return QuestionmanagementDAO.getListQuestionNotReply();
	}
	
	/**
	 * Get Question By ID
	 * @param Id
	 * @return
	 */
	public Questionmanagement getQuestionByID(int Id) {
		return QuestionmanagementDAO.getQuestionByID(Id);
	}

	/**
	 * Get Question Not Reply By Page For User
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getQuestionNotReplyByPageForUser(int page,
			int UserID) {
		List<Questionmanagement> list = QuestionmanagementDAO
				.getQuestionNotReplyForUser(UserID);
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();

		for (int i = 0; i < list.size(); i++) {
			shortlist.add(i, list.get(list.size() - 1 - i));
		}

		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		Setting settings = getSetting(UserID);
		int begin = page * settings.getRecordNotRep();
		int end = begin + settings.getRecordNotRep();
		if (end > shortlist.size()) {
			end = shortlist.size();
		}
		int l = 0;
		for (int k = begin; k < end; k++) {
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;

	}

	/**
	 * Get Question Not Reply By Page For Administrator
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getQuestionNotReplyByPageForAdmin(
			int page, int UserID) {
		List<Questionmanagement> list = QuestionmanagementDAO
				.getQuestionNotReplyForAdmin();
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();

		for (int i = 0; i < list.size(); i++) {
			shortlist.add(i, list.get(list.size() - 1 - i));
		}

		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		Setting settings = getSetting(UserID);
		int begin = page * settings.getRecordNotRep();
		int end = begin + settings.getRecordNotRep();
		if (end > shortlist.size()) {
			end = shortlist.size();
		}
		int l = 0;
		for (int k = begin; k < end; k++) {
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;

	}

	/**
	 * Update Answer
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int updateAnswer(int Id, String Answer) {
		return QuestionmanagementDAO.updateAnswer(Id, Answer);
	}

	/**
	 * Delete Question
	 * @param Id
	 * @return
	 */
	public int deleteQuestion(int Id) {
		return QuestionmanagementDAO.deleteQuestion(Id);
	}


	public Questionmanagement getDeletedQuestion(int Id) {
		return QuestionmanagementDAO.getDeletedQuestion(Id);
	}
	
	/**
	 * Get All Deleted Question By Page for User
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getListDeletedQuestionByPageForUser(int page, int UserID) {
		List<Questionmanagement> list = QuestionmanagementDAO.getListDeletedForUser(UserID);
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
		for (; list.size() > 0;) {
			Date max = list.get(0).getDeleteDate();
			int rememberint = 0;
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i).getDeleteDate().compareTo(max) > 0) {
					max = list.get(i).getDeleteDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}

		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		Setting settings = getSetting(UserID);
		int begin = page * settings.getRecordDelete();
		int end = begin + settings.getRecordDelete();
		if (end > shortlist.size()) {
			end = shortlist.size();
		}
		int l = 0;
		for (int k = begin; k < end; k++) {
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;

	}

	/**
	 * Get All Deleted Question By Page for Administrator
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getListDeletedQuestionByPageForAdmin(int page, int UserID) {
		List<Questionmanagement> list = QuestionmanagementDAO
				.getListDeletedForAdmin();
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
		for (; list.size() > 0;) {
			Date max = list.get(0).getDeleteDate();
			int rememberint = 0;
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i).getDeleteDate().compareTo(max) > 0) {
					max = list.get(i).getDeleteDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}

		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		Setting settings = getSetting(UserID);
		int begin = page * settings.getRecordDelete();
		int end = begin + settings.getRecordDelete();
		if (end > shortlist.size()) {
			end = shortlist.size();
		}
		int l = 0;
		for (int k = begin; k < end; k++) {
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
	}

	/**
	 * Update DeleteBy and DeleteDate
	 * @param ID
	 * @return
	 */
	public int updateDeleteByAndDeleteDate(int ID) {
		return QuestionmanagementDAO.updateDeleteByAndDeleteDate(ID);
	}
	
	/**
	 * Restore Question
	 * @param Id
	 * @return
	 */
	public int restoreQuestion(int Id) {
		return QuestionmanagementDAO.restoreQuestion(Id);
	}

	/**
	 * Get Save Question
	 * @param Id
	 * @return
	 */
	public Questionmanagement getSaveQuestion(int Id) {
		return QuestionmanagementDAO.getSaveQuestion(Id);
	}

	/**
	 * Get List Save Question By Page For User
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getSaveListByPageForUser(int page, int UserID) {
		List<Questionmanagement> list = QuestionmanagementDAO
				.getSaveListForUser(UserID);
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
		for (; list.size() > 0;) {
			Date max = list.get(0).getAnwserDate();
			int rememberint = 0;
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i).getAnwserDate().compareTo(max) > 0) {
					max = list.get(i).getAnwserDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}

		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		Setting settings = getSetting(UserID);
		int begin = page * settings.getRecordTemp();
		int end = begin + settings.getRecordTemp();
		if (end > shortlist.size()) {
			end = shortlist.size();
		}
		int l = 0;
		for (int k = begin; k < end; k++) {
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
	}

	/**
	 * Get List Save Question By Page For Administrator
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getSaveListByPageForAdmin(int page, int UserID) {
		List<Questionmanagement> list = QuestionmanagementDAO
				.getSaveListForAdmin();
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
		for (; list.size() > 0;) {
			Date max = list.get(0).getAnwserDate();
			int rememberint = 0;
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i).getAnwserDate().compareTo(max) > 0) {
					max = list.get(i).getAnwserDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}

		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		Setting settings = getSetting(UserID);
		int begin = page * settings.getRecordTemp();
		int end = begin + settings.getRecordTemp();
		if (end > shortlist.size()) {
			end = shortlist.size();
		}
		int l = 0;
		for (int k = begin; k < end; k++) {
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
	}

	/**
	 * Update Question When Send Answer
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int updateQuestionWhenSendAnwser(int Id, String Answer) {
		return QuestionmanagementDAO.updateQuestionWhenSendAnwser(Id, Answer);
	}

	/**
	 * Save Question
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int saveTemporaryQuestion(int Id, String Answer) {
		return QuestionmanagementDAO.saveTemporaryQuestion(Id, Answer);
	}

	/**
	 * Get List Replied Question By Page For User
	 * @param page
	 * @param UserID
	 * @return
	 */
	@Override
	public List<Questionmanagement> getRepliedListByPageForUser(int page, int UserID) {
		// TODO Auto-generated method stub
		List<Questionmanagement> list = QuestionmanagementDAO.getRepliedListForUser(UserID);
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
		for (; list.size() > 0;) {
			Date max = list.get(0).getAnwserDate();
			int rememberint = 0;
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i).getAnwserDate().compareTo(max) > 0) {
					max = list.get(i).getAnwserDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}

		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		Setting settings = getSetting(UserID);
		int begin = page * settings.getRecordRepied();
		int end = begin + settings.getRecordRepied();
		if (end > shortlist.size()) {
			end = shortlist.size();
		}
		int l = 0;
		for (int k = begin; k < end; k++) {
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
	}

	/**
	 * Get List Replied Question By Page For Administrator
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> getRepliedListByPageForAdmin(int page, int UserID) {
		List<Questionmanagement> list = QuestionmanagementDAO
				.getRepliedListForAdmin();
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
		for (; list.size() > 0;) {
			Date max = list.get(0).getAnwserDate();
			int rememberint = 0;
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i).getAnwserDate().compareTo(max) > 0) {
					max = list.get(i).getAnwserDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}

		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		Setting settings = getSetting(UserID);
		int begin = page * settings.getRecordRepied();
		int end = begin + settings.getRecordRepied();
		if (end > shortlist.size()) {
			end = shortlist.size();
		}
		int l = 0;
		for (int k = begin; k < end; k++) {
			newlist.add(l, shortlist.get(k));
			l++;
		}
		return newlist;
	}

	/**
	 * Get Replied Question By ID
	 * @param ID
	 * @return
	 */
	@Override
	public Questionmanagement getRepliedQuestion(int ID) {
		return QuestionmanagementDAO.getRepliedQuestion(ID);
	}
	
	/**
	 * Get Total Page Question and Dictionary By Status For Admin
	 * @param status
	 * @param UserID
	 * @return
	 */
	@Override
	public int totalPageQuestionAndDictionary(int status, int UserID) {
		int result = 0;
		List<Questionmanagement> listquestion = QuestionmanagementDAO
				.getListQuestionbyStatus(status);

		int totalRecord = listquestion.size();
		Setting settings = getSetting(UserID);
		if (status == 1) {
			if (totalRecord % settings.getRecordNotRep() == 0) {
				result = totalRecord / settings.getRecordNotRep();
			} else {
				result = (totalRecord / settings.getRecordNotRep()) + 1;
			}
		} else {
			if (status == 2) {
				if (totalRecord % settings.getRecordTemp() == 0) {
					result = totalRecord / settings.getRecordTemp();
				} else {
					result = (totalRecord / settings.getRecordTemp()) + 1;
				}
			} else {
				if (status == 3) {
					if (totalRecord % settings.getRecordRepied() == 0) {
						result = totalRecord / settings.getRecordRepied();
					} else {
						result = (totalRecord / settings.getRecordRepied()) + 1;
					}
				} else {
					if (status == 4) {
						List<Questionmanagement> listdelete = QuestionmanagementDAO.getListQuestionByDeleteStatus(1);
						totalRecord = listdelete.size();
						if (totalRecord % settings.getRecordDelete() == 0) {
							
							result = totalRecord / settings.getRecordDelete();
						} else {
							result = (totalRecord / settings.getRecordDelete()) + 1;
						}
					} else {
						if (status == 5) {
							List<Dictionary> list = Dictionary_DAO.getAllDictionaryAvailable();
							int totaldictionary = list.size();

							if (totaldictionary
									% settings.getRecordDictionary() == 0) {
								result = totaldictionary
										/ settings.getRecordDictionary();
							} else {
								result = totaldictionary
										/ settings.getRecordDictionary() + 1;
							}
						} else {
							if (status == 6) {
								List<Dictionary> list = Dictionary_DAO.getAllDictionaryRecent();
								int totaldictionary = list.size();
								if (totaldictionary
										% settings.getRecordDictionary() == 0) {
									result = totaldictionary
											/ settings.getRecordDictionary();
								} else {
									result = totaldictionary
											/ settings.getRecordDictionary()
											+ 1;
								}

							} else {
								if (status == 7) {
									List<Dictionary> list =Dictionary_DAO.getAllDictionaryDown();
									int totaldictionary = list.size();
									if (totaldictionary
											% settings.getRecordDictionary() == 0) {
										result = totaldictionary
												/ settings
														.getRecordDictionary();
									} else {
										result = totaldictionary
												/ settings
														.getRecordDictionary()
												+ 1;
									}

								} else {
									if (status == 8) {

										List<Dictionary> list = Dictionary_DAO.getAllDictionaryDeleted();
										int totaldictionary = list.size();
										if (totaldictionary
												% settings
														.getRecordDictionary() == 0) {
											result = totaldictionary
													/ settings
															.getRecordDictionary();
										} else {
											result = totaldictionary
													/ settings
															.getRecordDictionary()
													+ 1;
										}

									} 
								}
							}
						}

					}
				}
			}
		}
		return result;
	}

	
	/**
	 * Get Total Page Question and Dictionary By Status For User
	 * @param status
	 * @param UserID
	 * @return
	 */
	@Override
	public int totalPageQuestionAndDictionaryForUser(int status, int UserID) {
		int result = 0;
		Setting settings = getSetting(UserID);
			if (status == 2) {
				List<Questionmanagement> listquestion = QuestionmanagementDAO.getSaveListForUser(UserID);
				int totalRecord = listquestion.size();
				if (totalRecord % settings.getRecordTemp() == 0) {
					result = totalRecord / settings.getRecordTemp();
				} else {
					result = (totalRecord / settings.getRecordTemp()) + 1;
				}
			} else {
				if (status == 3) {
					List<Questionmanagement> listquestion = QuestionmanagementDAO.getRepliedListForUser(UserID);
					int totalRecord = listquestion.size();
					if (totalRecord % settings.getRecordRepied() == 0) {
						result = totalRecord / settings.getRecordRepied();
					} else {
						result = (totalRecord / settings.getRecordRepied()) + 1;
					}
				} else {
					if (status == 4) {
						List<Questionmanagement> listdelete = QuestionmanagementDAO.getListDeletedForUser(UserID);
						int totalRecord = listdelete.size();
						if (totalRecord % settings.getRecordDelete() == 0) {
							result = totalRecord / settings.getRecordDelete();
						} else {
							result = (totalRecord / settings.getRecordDelete()) + 1;
						}
					}else{
						if (status == 5) {
							List<Dictionary> list = Dictionary_DAO.getAvailableListDictionaryForUser(UserID);
							int totaldictionary = list.size();

							if (totaldictionary	% settings.getRecordDictionary() == 0) {
								result = totaldictionary/ settings.getRecordDictionary();
							} else {
								result = totaldictionary/ settings.getRecordDictionary() + 1;
							}
						}
					}
				}
			}
		return result;
	}


	
	/**
	 * Get Total Page Question Delete
	 * @param status
	 * @return
	 */
	@Override
	public int totalPageQuestionDeleted(int status) {
		int result;
		List<Questionmanagement> listquestion = QuestionmanagementDAO
				.getListQuestionByDeleteStatus(status);
		int totalRecord = listquestion.size();
		if (totalRecord % 5 == 0) {
			result = totalRecord / 5;
		} else {
			result = (totalRecord / 5) + 1;
		}
		return result;
	}

	/**
	 * Create Index
	 */
	public void createIndex() {
		QuestionmanagementDAO.createIndex();
	}

	/**
	 * Search Index For Administrator
	 * @param keyword
	 * @param Status
	 * @return
	 */
	public List<Questionmanagement> searchIndexForAdmin(String keyword, String Status, int UserID) {
		 List<Questionmanagement> searchlist = QuestionmanagementDAO.searchIdex(keyword, Status);
		 List<Questionmanagement> settinglist = new ArrayList<Questionmanagement>();
		 Setting settings = getSetting(UserID);
		 int begin ;
		 int end ;
		 if(Status == "1"){
			 begin = 0;
			 end = settings.getRecordNotRep();
		 }else{
			 if(Status == "2"){
				 begin = 0;
				 end = settings.getRecordTemp();
			 }else{
				 if(Status == "3"){
					 begin = 0;
					 end = settings.getRecordRepied();
				 }else{
					 begin = 0;
					 end = settings.getRecordDelete();
				 }
			 }
		 }
		 if(end > searchlist.size()){
				end = searchlist.size();
			}
			int l = 0;
			for(int k = begin; k < end; k++){
				settinglist.add(l, searchlist.get(k));
				l++;
			}
		return settinglist;
	}

	/**
	 * Search Index For User
	 * @param keyword
	 * @param Status
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> searchIndexForUser(String keyword,
			String Status, int UserID) {
		List<Questionmanagement> question = QuestionmanagementDAO.searchIdex(
				keyword, Status);

		List<Questionmanagement> newlistquestion = new ArrayList<Questionmanagement>();
		int L = 0;
		for (int i = 0; i < question.size(); i++) {
			if (question.get(i).getAnswerBy().equals(UserID)) {
				newlistquestion.add(L, question.get(i));
				L++;
			}
		}
		return newlistquestion;
	}


	/**
	 * Search Index Delete Question For User
	 * @param keyword
	 * @param Status
	 * @param UserID
	 * @return
	 */
	public List<Questionmanagement> searchIndexDeleteListForUser(
			String keyword, String Status, int UserID) {
		List<Questionmanagement> question = QuestionmanagementDAO.searchIdex(
				keyword, Status);

		List<Questionmanagement> newlistquestion = new ArrayList<Questionmanagement>();
		int L = 0;
		for (int i = 0; i < question.size(); i++) {
			if (question.get(i).getDeleteBy().equals(UserID)) {
				newlistquestion.add(L, question.get(i));
				L++;
			}
		}
		return newlistquestion;
	}

	/**
	 * Restore multiple Question
	 * @param listdelete
	 * @param userid
	 * @return
	 */
	public List<Questionmanagement> restoreMultipleQuestion(String listdelete, int userid) {
		List<Questionmanagement> returnlist = new ArrayList<Questionmanagement>();

		String[] liststring = listdelete.split(",");
		for (int i = 0; i < liststring.length; i++) {
			int deleteid = Integer.parseInt(liststring[i].toString());
			// xu ly luu cau tra loi va gui mail
			Questionmanagement question = getQuestionByID(deleteid);
			if (question.getDeleteBy() != null) {
				// Xu ly thao tac song song
				Users information = UserDAO.getUserByUserID(userid);
				int author = information.getAuthorization();
				if (userid == question.getDeleteBy()) {
					QuestionmanagementDAO.updateDeleteByAndDeleteDate(deleteid, userid);
					QuestionmanagementDAO.restoreQuestion(deleteid);
					QuestionmanagementDAO.updateDeleteByAndDeleteDate(deleteid);
					returnlist.add(question);
				} else {
					if (author == 1) {
						Users otheruser = UserDAO.getUserByUserID(question
								.getDeleteBy());
						int otherauthor = otheruser.getAuthorization();
						if (otherauthor == 1) {
							// Null
						} else {
							QuestionmanagementDAO
									.updateDeleteByAndDeleteDate(deleteid, userid);
							QuestionmanagementDAO.restoreQuestion(deleteid);
							returnlist.add(question);
						}
					}
				}

			} else {
				QuestionmanagementDAO.updateDeleteByAndDeleteDate(deleteid, userid);
				QuestionmanagementDAO.restoreQuestion(deleteid);
				returnlist.add(question);
			}
		}// end for
		return returnlist;
	}

	/**
	 * Delete Multiple Question
	 * @param listdelete
	 * @param userid
	 * @return
	 */
	public List<Questionmanagement> deleteMultipleQuestion(String listdelete, int userid) {

		List<Questionmanagement> returnlist = new ArrayList<Questionmanagement>();
		String[] liststring = listdelete.split(",");
		for (int i = 0; i < liststring.length; i++) {
			int deleteid = Integer.parseInt(liststring[i].toString());
			// xu ly luu cau tra loi va gui mail
			Questionmanagement question = getQuestionByID(deleteid);
			if (question.getDeleteBy() != null) {
				// Xu ly thao tac song song
				Users information = UserDAO.getUserByUserID(userid);
				int author = information.getAuthorization();
				if (userid == question.getDeleteBy()) {
					QuestionmanagementDAO.updateDeleteByAndDeleteDate(deleteid, userid);
					QuestionmanagementDAO.deleteQuestion(deleteid);
					returnlist.add(question);
				} else {
					if (author == 1) {
						Users otheruser = UserDAO.getUserByUserID(question
								.getDeleteBy());
						int otherauthor = otheruser.getAuthorization();
						if (otherauthor == 1) {
							// Null
						} else {
							QuestionmanagementDAO
									.updateDeleteByAndDeleteDate(deleteid, userid);
							QuestionmanagementDAO.deleteQuestion(deleteid);
							returnlist.add(question);
						}
					}
				}

			} else {
				QuestionmanagementDAO.updateDeleteByAndDeleteDate(deleteid, userid);
				QuestionmanagementDAO.deleteQuestion(deleteid);
				returnlist.add(question);
			}
		}// end for
		return returnlist;
	}

	
	
	// --------------------RESTful web service-----
	
	
	/**
	 * Add Question
	 * @param question
	 */
	public void addQuestionRESTful(Questionmanagement question) {
		QuestionmanagementDAO.addQuestion(question);
	}

	/**
	 * Copy Question to Dictionary
	 * @param Id
	 * @param userid
	 */
	public void TransferToDictionary(int Id, int userid) {
		QuestionmanagementDAO.copyQuestionToDictionary(Id, userid);
	}

	/**
	 * Update DeleteBy And DeleteDate
	 * @param Id
	 * @param userid
	 */
	public void updateDeleteByAndDeleteDate(int Id, int userid) {
		QuestionmanagementDAO.updateDeleteByAndDeleteDate(Id, userid);
	}

	/**
	 * Update AnwserBy and AnwserDate
	 * @param Id
	 * @param userid
	 */
	public void updateAnwserByAndAnwserDate(int Id, int userid) {
		QuestionmanagementDAO.updateAnwserByAndAnwserDate(Id, userid);
	}
	
	/**
	 * Get Setting
	 * @param UserId
	 * @return
	 */
	public Setting getSetting(int UserId) {
		return QuestionmanagementDAO.getSetting(UserId);
	}

	/**
	 * Get List Question by Status
	 * @param status
	 * @return
	 */
	public List<Questionmanagement> getListQuestionbyStatus(int status) {
		return QuestionmanagementDAO.getListQuestionbyStatus(status);
	}

	/**
	 * Update BusyStatus Question When Click Question
	 * @param Id
	 * @param UserId
	 */
	public void updateBusyStatusQuestion(int Id, int UserId) {
		QuestionmanagementDAO.updateBusyStatusQuestion(Id, UserId);
	}

	/**
	 * Reset BusyStatus Question When Click Question
	 * @param Id
	 * @param UserId
	 */
	public void resetBusyStatusQuestion(int Id, int UserId) {
		QuestionmanagementDAO.resetBusyStatusQuestion(Id, UserId);
	}

	/**
	 * Check Question Is Busy
	 * @param Id
	 * @param UserId
	 * @return
	 */
	public boolean checkQuestionIsBusy(int Id, int UserId) {
		Questionmanagement question = QuestionmanagementDAO
				.getQuestionByID(Id);
		if (question.getBusyStatus().equals(0)
				|| question.getBusyStatus().equals(UserId)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Get UserID in Busy Status By ID Question
	 * @param Id
	 * @return
	 */
	public int geUserIDByIdQuestion(int Id) {
		Questionmanagement question = QuestionmanagementDAO.getQuestionByID(Id);
		int result = question.getBusyStatus();
		return result;
	}
	
	/**
	 * Check UserID can use ID Question in Save List
	 * @param UserId
	 * @param Id
	 * @return
	 */
	public boolean checkSaveListByUserId(int UserId,int Id) {
		try {
			Questionmanagement question = QuestionmanagementDAO.getQuestionByID(Id);
			if(UserSERVICE.checkIsAdmin(UserId)==true){
				return true;
			}else{
				if(question.getAnswerBy().equals(UserId)){
					return true;
				}else {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}
	
	/**
	 * Check UserID can use ID Question in Delete List
	 * @param UserId
	 * @param Id
	 * @return
	 */
	public boolean checkDeleteListByUserId(int UserId,int Id) {
		try {
			Questionmanagement question = QuestionmanagementDAO.getQuestionByID(Id);
			if(UserSERVICE.checkIsAdmin(UserId)==true){
				return true;
			}else{
				if(question.getDeleteBy().equals(UserId)){
					return true;
				}else {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}
	
	/**
	 * Check ID Question in Not Reply List
	 * @param Id
	 * @return
	 */
	public boolean checkIdQuestionNotReply(int Id) {
		List<Questionmanagement> listquestion = QuestionmanagementDAO.getListQuestionbyStatus(1);
		boolean result = false;
		for (int i = 0; i < listquestion.size(); i++) {
			if(listquestion.get(i).getID().equals(Id)){
				result = true;
				break;
			}
		}
		return result;
	}
	

	/**
	 * Check ID Question in Not Save List
	 * @param Id
	 * @return
	 */
	public boolean checkIdQuestionSave(int Id) {
		List<Questionmanagement> listquestion = QuestionmanagementDAO.getListQuestionbyStatus(2);
		boolean result = false;
		for (int i = 0; i < listquestion.size(); i++) {
			if(listquestion.get(i).getID().equals(Id)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Check ID Question in Not Reply List
	 * @param Id
	 * @return
	 */
	public boolean checkIdQuestionReplied(int Id) {
		List<Questionmanagement> listquestion = QuestionmanagementDAO.getListQuestionbyStatus(3);
		boolean result = false;
		for (int i = 0; i < listquestion.size(); i++) {
			if(listquestion.get(i).getID().equals(Id)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Check ID Question in Not Delete List
	 * @param Id
	 * @return
	 */
	public boolean checkIdQuestionDeleted(int Id) {
		List<Questionmanagement> listquestion = QuestionmanagementDAO.getListQuestionByDeleteStatus(1);
		boolean result = false;
		for (int i = 0; i < listquestion.size(); i++) {
			if(listquestion.get(i).getID().equals(Id)){
				result = true;
				break;
			}
		}
		return result;
	}
	

	
	// Khang android update 11/05
		public List<Questionmanagement> getListQuestionmanagementAndroid(int page){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Questionmanagement> list = QuestionmanagementDAO.getListQuestionbyStatus(1);
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getQuestionDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getQuestionDate().compareTo(max)>0){
						max = list.get(i).getQuestionDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
			
		}
		public List<Questionmanagement> searchIdexAndroid(int page, String keyword){	
			List<Questionmanagement> list = QuestionmanagementDAO.searchIdex(keyword, "1");
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getQuestionDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getQuestionDate().compareTo(max)>0){
						max = list.get(i).getQuestionDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
		public List<Questionmanagement> getSaveListQuestionmanagementAndroid(int page){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Questionmanagement> list = QuestionmanagementDAO.getListQuestionbyStatus(2);
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getAnwserDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getAnwserDate().compareTo(max)>0){
						max = list.get(i).getAnwserDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
			
		}
		public List<Questionmanagement> getSaveListForUserAndroid(int page, int UserID){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Questionmanagement> list = QuestionmanagementDAO.getSaveListForUser(UserID); // so 0 la do ham co san 
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getAnwserDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getAnwserDate().compareTo(max)>0){
						max = list.get(i).getAnwserDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
		public List<Questionmanagement> searchIdexAndroidSaveListAndroid(int page, String keyword){
			List<Questionmanagement> list = QuestionmanagementDAO.searchIdex(keyword, "2");
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getAnwserDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getAnwserDate().compareTo(max)>0){
						max = list.get(i).getAnwserDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
		public List<Questionmanagement> searchIdexAndroidSaveListForUserAndroid(int page, String keyword, int UserID){
			
			List<Questionmanagement> question =  QuestionmanagementDAO.searchIdex(keyword, "2");
			
			List<Questionmanagement> newlistquestion= new ArrayList<Questionmanagement>();
			int L=0;
			for(int i=0;i<question.size();i++){
				if(question.get(i).getAnswerBy().equals(UserID)){
					newlistquestion.add(L,question.get(i));
					L++;
				}
			}
			List<Questionmanagement> list = newlistquestion;
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getAnwserDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getAnwserDate().compareTo(max)>0){
						max = list.get(i).getAnwserDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
		public List<Questionmanagement> getReplyListQuestionmanagementAndroid(int page){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Questionmanagement> list = QuestionmanagementDAO.getRepliedListForAdmin(); // get ds cho admin 0,0 la khong co j
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getAnwserDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getAnwserDate().compareTo(max)>0){
						max = list.get(i).getAnwserDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
			
		}
		public List<Questionmanagement> getReplyListForUserAndroid(int page, int UserID){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Questionmanagement> list = QuestionmanagementDAO.getRepliedListForUser(UserID); // so 0 la do ham co san chu khong co tac dung gi =.=' 
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getAnwserDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getAnwserDate().compareTo(max)>0){
						max = list.get(i).getAnwserDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
		public List<Questionmanagement> searchIdexAndroidReplyListAndroid(int page, String keyword){
			List<Questionmanagement> list = QuestionmanagementDAO.searchIdex(keyword, "3");
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getAnwserDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getAnwserDate().compareTo(max)>0){
						max = list.get(i).getAnwserDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
		public List<Questionmanagement> searchIdexAndroidReplyListForUserAndroid(int page, String keyword, int UserID){
			
			List<Questionmanagement> question =  QuestionmanagementDAO.searchIdex(keyword, "3");
			
			List<Questionmanagement> newlistquestion= new ArrayList<Questionmanagement>();
			int L=0;
			for(int i=0;i<question.size();i++){
				if(question.get(i).getAnswerBy().equals(UserID)){
					newlistquestion.add(L,question.get(i));
					L++;
				}
			}
			List<Questionmanagement> list = newlistquestion;
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getAnwserDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getAnwserDate().compareTo(max)>0){
						max = list.get(i).getAnwserDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
		
		public List<Questionmanagement> getDeleteListQuestionmanagementAndroid(int page){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Questionmanagement> list = QuestionmanagementDAO.getListDeletedForAdmin(); // get ds cho admin 0,0 la khong co j
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getDeleteDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getDeleteDate().compareTo(max)>0){
						max = list.get(i).getDeleteDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
			
		}
		public List<Questionmanagement> getDeleteListForUserAndroid(int page, int UserID){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Questionmanagement> list = QuestionmanagementDAO.getListDeletedForUser(UserID); // so 0 la do ham co san chu khong co tac dung gi =.=' 
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getDeleteDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getDeleteDate().compareTo(max)>0){
						max = list.get(i).getDeleteDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
		public List<Questionmanagement> searchIdexAndroidDeleteListAndroid(int page, String keyword){
			List<Questionmanagement> list = QuestionmanagementDAO.searchIdex(keyword, "4");
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getDeleteDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getDeleteDate().compareTo(max)>0){
						max = list.get(i).getDeleteDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
		public List<Questionmanagement> searchIdexAndroidDeleteListForUserAndroid(int page, String keyword, int UserID){
			
			List<Questionmanagement> question =  QuestionmanagementDAO.searchIdex(keyword, "4");
			
			List<Questionmanagement> newlistquestion= new ArrayList<Questionmanagement>();
			int L=0;
			for(int i=0;i<question.size();i++){
				if(question.get(i).getDeleteBy().equals(UserID)){
					newlistquestion.add(L,question.get(i));
					L++;
				}
			}
			List<Questionmanagement> list = newlistquestion;
			List<Questionmanagement> sortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getDeleteDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getDeleteDate().compareTo(max)>0){
						max = list.get(i).getDeleteDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
	         int setting = numAndroid;
	         int begin = 0;
	         int end =  page*setting + setting;
	         if(end > sortlist.size()){
	         	end = sortlist.size();
	         }
	         int l = 0;
	         for(int k = begin; k< end;k++){
	         	shortlist.add(l, sortlist.get(k));
	         	l++;
	         }
	         
			return shortlist;
		}
	
}
