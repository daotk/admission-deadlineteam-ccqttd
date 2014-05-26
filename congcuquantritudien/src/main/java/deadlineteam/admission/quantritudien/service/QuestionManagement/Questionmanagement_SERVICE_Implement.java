package deadlineteam.admission.quantritudien.service.QuestionManagement;

import deadlineteam.admission.quantritudien.dao.Dictionary.Dictionary_DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import deadlineteam.admission.quantritudien.dao.QuestionManagement.Questionmanagement_DAO;
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

	private int check = 47;
	private int get = 44;

	public List<Questionmanagement> getListQuestionmanagement() {
		return QuestionmanagementDAO.getListQuestionNotReply();
	}

	public Questionmanagement getQuestionmanagementbyID(int Id) {
		return QuestionmanagementDAO.getQuestionByID(Id);
	}

	@Override
	public List<Questionmanagement> findpage1(String keyword) {
		// TODO Auto-generated method stub
		List<Questionmanagement> list = QuestionmanagementDAO
				.getListQuestionNotReply();
		List<Questionmanagement> newlisst = new ArrayList<Questionmanagement>();

		return newlisst;
	}

	public List<Questionmanagement> getQuestionmanagementbyPage(int page,
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

	// for admin
	public List<Questionmanagement> getQuestionmanagementbyPageForAdmin(
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

	public int updateAnswerbyId(int Id, String Answer) {
		return QuestionmanagementDAO.updateAnswer(Id, Answer);
	}


	/*
	 * Author: Phu Ta delete question that is selected
	 */
	public void ResetUpdateAnwserBy(int Id, int userid) {
		QuestionmanagementDAO.ResetUpdateAnwserBy(Id, userid);
	}

	public int delete(int Id) {
		return QuestionmanagementDAO.deleteQuestion(Id);
	}

	// ------------------------Delete Page
	/*
	 * Author: Phu Ta Load delete question-list
	 */
	public Questionmanagement deletequestion(int Id) {
		return QuestionmanagementDAO.getDeletedQuestion(Id);
	}

	public List<Questionmanagement> deleteList(int page, int UserID) {
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

	public int updatedelete(int ID) {
		return QuestionmanagementDAO.updateDeleteByAndDeleteDate(ID);
	}

	public List<Questionmanagement> getDeleteListForAdmin(int page, int UserID) {
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

	/*
	 * Author: Phu Ta delete question that is selected
	 */
	public int restore(int Id) {
		return QuestionmanagementDAO.restoreQuestion(Id);
	}

	// ----------------------- Save Page
	/*
	 * Author: Phu Ta Load save-question-list
	 */
	public Questionmanagement savequestion(int Id) {
		return QuestionmanagementDAO.getSaveQuestion(Id);
	}

	public List<Questionmanagement> savelist(int page, int UserID) {
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

	public List<Questionmanagement> getSaveListForAdmin(int page, int UserID) {
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


	/*
	 * Author: Phu Ta save save-question that is selected
	 */
	public int SendAnwser(int Id, String Answer) {
		return QuestionmanagementDAO.updateQuestionWhenSendAnwser(Id, Answer);
	}

	// SAve Temporary
	public int SaveTemporaryAnswerbyId(int Id, String Answer) {
		return QuestionmanagementDAO.saveTemporaryQuestion(Id, Answer);
	}

	// ------------------------------- replied page
	/*
	 * Author: Chau Le replied question-list page
	 */

	public int deleterepliedquestion(int ID) {
		return QuestionmanagementDAO.deleteQuestion(ID);
	}

	@Override
	public List<Questionmanagement> repliedList(int page, int UserID) {
		// TODO Auto-generated method stub
		List<Questionmanagement> list = QuestionmanagementDAO.repliedList(UserID);
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

	// get reply list for admin
	public List<Questionmanagement> getRepliedListForAdmin(int page, int UserID) {
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

	@Override
	public Questionmanagement repliedquestion(int ID) {
		// TODO Auto-generated method stub
		return QuestionmanagementDAO.getRepliedQuestion(ID);
	}

	@Override
	public int totalPageQuestiomanagement(int status, int UserID) {
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
							List<Dictionary> list = QuestionmanagementDAO
									.getListDictionarybyStatus(1);
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
								List<Dictionary> list = QuestionmanagementDAO
										.getListDictionarybyStatus(2);
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
									List<Dictionary> list = QuestionmanagementDAO
											.getListDictionarybyStatus(4);
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

										List<Dictionary> list = QuestionmanagementDAO
												.getListDictionaryDelete(4);
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

	public List<Dictionary> getListDictionaryDelete(int status) {
		return QuestionmanagementDAO.getListDictionaryDelete(status);
	}

	@Override
	public int totalPageQuestiomanagementDelete(int status) {
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

	public void createIndex() {
		QuestionmanagementDAO.createIndex();
	}

	public List<Questionmanagement> searchIdex(String keyword, String Status) {
		return QuestionmanagementDAO.searchIdex(keyword, Status);
	}

	public List<Questionmanagement> searchIdexForAdmin(String keyword,
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

	public List<Questionmanagement> searchIdexDeleteListForAdmin(
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

	public List<Questionmanagement> restoreall(String listdelete, int userid) {
		List<Questionmanagement> returnlist = new ArrayList<Questionmanagement>();

		String[] liststring = listdelete.split(",");
		for (int i = 0; i < liststring.length; i++) {
			int deleteid = Integer.parseInt(liststring[i].toString());
			// xu ly luu cau tra loi va gui mail
			Questionmanagement question = getQuestionmanagementbyID(deleteid);
			if (question.getDeleteBy() != null) {
				// Xu ly thao tac song song
				Users information = UserDAO.getUserByUserID(userid);
				int author = information.getAuthorization();
				if (userid == question.getDeleteBy()) {
					QuestionmanagementDAO.UpdateDelete(deleteid, userid);
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
									.UpdateDelete(deleteid, userid);
							QuestionmanagementDAO.restoreQuestion(deleteid);
							returnlist.add(question);
						}
					}
				}

			} else {
				QuestionmanagementDAO.UpdateDelete(deleteid, userid);
				QuestionmanagementDAO.restoreQuestion(deleteid);
				returnlist.add(question);
			}
		}// end for
		return returnlist;
	}

	public List<Questionmanagement> deleteall(String listdelete, int userid) {

		List<Questionmanagement> returnlist = new ArrayList<Questionmanagement>();
		String[] liststring = listdelete.split(",");
		for (int i = 0; i < liststring.length; i++) {
			int deleteid = Integer.parseInt(liststring[i].toString());
			// xu ly luu cau tra loi va gui mail
			Questionmanagement question = getQuestionmanagementbyID(deleteid);
			if (question.getDeleteBy() != null) {
				// Xu ly thao tac song song
				Users information = UserDAO.getUserByUserID(userid);
				int author = information.getAuthorization();
				if (userid == question.getDeleteBy()) {
					QuestionmanagementDAO.UpdateDelete(deleteid, userid);
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
									.UpdateDelete(deleteid, userid);
							QuestionmanagementDAO.deleteQuestion(deleteid);
							returnlist.add(question);
						}
					}
				}

			} else {
				QuestionmanagementDAO.UpdateDelete(deleteid, userid);
				QuestionmanagementDAO.deleteQuestion(deleteid);
				returnlist.add(question);
			}
		}// end for
		return returnlist;
	}

	// --------------------RESTful web service-----
	public void addQuestionRESTful(Questionmanagement question) {
		QuestionmanagementDAO.addQuestion(question);
	}

	public void TransferToDictionary(int Id, int userid) {
		QuestionmanagementDAO.TransferToDictionary(Id, userid);
	}

	public void UpdateDelete(int Id, int userid) {
		QuestionmanagementDAO.UpdateDelete(Id, userid);
	}

	public void UpdateAnwserBy(int Id, int userid) {
		QuestionmanagementDAO.UpdateAnwserBy(Id, userid);
	}

	public Users getusername(int username) {
		return QuestionmanagementDAO.getusername(username);
	}

	public Setting getSetting(int UserId) {
		return QuestionmanagementDAO.getSetting(UserId);
	}

	public List<Questionmanagement> getListQuestionmanagementbyStatus(int status) {
		return QuestionmanagementDAO.getListQuestionbyStatus(status);
	}

	public void updateBusyStatus(int Id, int UserId) {
		QuestionmanagementDAO.updateBusyStatus(Id, UserId);
	}

	public void updateBusyStatusAfter(int Id, int UserId) {
		QuestionmanagementDAO.updateBusyStatusAfter(Id, UserId);
	}

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
	public int geUserIDByIdQuestion(int Id) {
		Questionmanagement question = QuestionmanagementDAO.getQuestionByID(Id);
		int result = question.getBusyStatus();
		return result;
	}
	public boolean checkSavaListByUserId(int UserId,int Id) {
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
			
			List<Questionmanagement> list = QuestionmanagementDAO.repliedList(UserID); // so 0 la do ham co san chu khong co tac dung gi =.=' 
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
