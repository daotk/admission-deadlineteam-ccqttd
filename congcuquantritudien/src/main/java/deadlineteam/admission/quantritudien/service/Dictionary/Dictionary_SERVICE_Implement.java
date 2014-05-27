package deadlineteam.admission.quantritudien.service.Dictionary;

import java.util.ArrayList;
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
	private int numAndroid = 10;
	@Autowired
	private Dictionary_DAO DictionaryDAO ;
	@Autowired
	private Users_SERVICE UserSERVICE ;
	@Autowired
	private Questionmanagement_DAO QuestionmanagementDAO ;
	@Autowired
	private Users_SERVICE userservice;
	
	
	
	/**
	 * Add Question to Dictionary
	 * @param Id
	 * @param UserId
	 * @return
	 */
	public int addDictionaryFormQuestion (int Id, int UserId){
		Questionmanagement questionmanagement = QuestionmanagementDAO.getQuestionByIDToCopy(Id);
		Dictionary dictionary = new Dictionary();
		dictionary.setQuestion(questionmanagement.getQuestion());
		dictionary.setCreateBy(UserId);
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
	
	/**
	 * Update Create When Edit
	 * @param Id
	 * @param UserID
	 * @return
	 */
	public int updateCreateByWhenEdit(int Id, int UserID){
		return DictionaryDAO.updateCreatebyWhenEdit(Id, UserID);
	}
	
	/**
	 * Add Dictionary
	 * @param dictionary
	 */
	public void AddDictionary(Dictionary dictionary){
		 DictionaryDAO.AddDictionary(dictionary);
	}
	
	/**
	 * Get List Available Dictionary By Page For User 
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> getListAvailableDictionaryByPageForUser(int page, int UserID) {
		// TODO Auto-generated method stub
		List<Dictionary> list=  DictionaryDAO.getAvailableListDictionaryForUser(UserID);
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
	
	
	public List<Dictionary> getListAvailableDictionaryForAdmin(int page, int UserID) {
		// TODO Auto-generated method stub
		List<Dictionary> list=  DictionaryDAO.getAvailableListForAdministrator();
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

	/**
	 * Restore multiple Dictionary
	 * @param checkbox
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> restoreMultipleDictionary(String checkbox, int UserID){
		List<Dictionary> returnlist = new ArrayList<Dictionary>();
		String[] liststring = checkbox.split(",");
		for(int i=0;i<liststring.length;i++){
			int deleteid = Integer.parseInt(liststring[i].toString());
			Dictionary question = DictionaryDAO.getDictionaryByID(deleteid);
			DictionaryDAO.updateDeleteByAndDeleteDateWhenDelete(deleteid, UserID);
			DictionaryDAO.updateDictionaryWhenRestore(deleteid);
			DictionaryDAO.updateDeleteByAndDeleteDateWhenRestore(deleteid);
			returnlist.add(question);
		}
		return returnlist;
	}
	
	/**
	 * Delete multiple Dictionary
	 * @param checkbox
	 * @param UserId
	 * @return
	 */
	public List<Dictionary> deleteMultipleDictionary(String checkbox, int UserId){
		List<Dictionary> returnlist = new ArrayList<Dictionary>();
		String[] liststring = checkbox.split(",");
		for(int i=0;i<liststring.length;i++){
			int deleteid = Integer.parseInt(liststring[i].toString());
			Dictionary question = DictionaryDAO.getDictionaryByID(deleteid);
			DictionaryDAO.updateDeleteByAndDeleteDateWhenDelete(deleteid, UserId);
			DictionaryDAO.updateDictionaryWhenDelete(deleteid);
			
			returnlist.add(question);
		}
		return returnlist;
		
	}
	
	/**
	 * Get List Recent Dictionary By Page
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> getAllListRecentDictionary(int page, int UserID) {		
		List<Dictionary> list=  DictionaryDAO.getAllDictionaryRecent();
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
	
	/**
	 * Get Recent Dictionary By ID
	 * @param Id
	 * @return
	 */
	public Dictionary getRecentDictionaryByID(int Id) {
		// TODO Auto-generated method stub
		return DictionaryDAO.getRecentDictionaryByID(Id);
	}
	
	
	public int updateDeleteByAndDeleteDateWhenRestore(int Id){
		return DictionaryDAO.updateDeleteByAndDeleteDateWhenRestore(Id);
	}
	
	/**
	 * Get All List Delete Dictionary 
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> getAllListDeleteDictionary(int page, int UserID) {
		// TODO Auto-generated method stub
		
		List<Dictionary> list=  DictionaryDAO.getAllDictionaryDeleted();
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

	/**
	 * Get Dictionary By ID
	 * @param ID
	 * @return
	 */
	public Dictionary getDictionaryByID(int ID){
		return DictionaryDAO.getDictionaryByID(ID);
	}
	
	/**
	 * Update Dictionary When Upload
	 * @param Id
	 * @return
	 */
	public int updateDictionaryWhenUpload(int Id){
		return DictionaryDAO.updateDictionaryWhenUpload(Id);
	}
	
	/**
	 * Update UpdateDate When Upload
	 * @param Id
	 * @param UserID
	 * @return
	 */
	public int updateUpdateByWhenUpload(int Id, int UserID){
		// TODO Auto-generated method stub
		return DictionaryDAO.updateUpdateByWhenUpload(Id, UserID);
	}
	
	/**
	 * Update Dictionary When Down
	 * @param Id
	 * @return
	 */
	public int updateDictionaryWhenDown(int Id){
		// TODO Auto-generated method stub
		return DictionaryDAO.updateDictionaryWhenDown(Id);
	}
	
	/**
	 *  Update Dictionary When Restore
	 * @param Id
	 * @return
	 */
	public int updateDictionaryWhenRestore(int Id){
		// TODO Auto-generated method stub
		return DictionaryDAO.updateDictionaryWhenRestore(Id);
	}
	
	/**
	 * Update Dictionary When Delete
	 * @param Id
	 * @return
	 */
	public int updateDictionaryWhenDelete(int Id){
		// TODO Auto-generated method stub
		return DictionaryDAO.updateDictionaryWhenDelete(Id);
	}

	/**
	 * Get All List Down Dictionary
	 * @param page
	 * @param UserID
	 * @return
	 */
	public List<Dictionary> getAllListDownDictionary(int page, int UserID){
		List<Dictionary> list=  DictionaryDAO.getAllDictionaryDown();
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
	
	
	public Dictionary getDownDictionaryByID(int Id){
		return DictionaryDAO.getDownDictionaryByID(Id);
	}
	public int updateQuesionAndAnwserDictionary(int Id,String Anwser,  String Question){
		return DictionaryDAO.updateQuesionAndAnwserDictionary(Id, Anwser, Question);
	}

	public Dictionary getDictionaryByIDNotDelete(int Id){
		return DictionaryDAO.getDictionaryByIDNotDelete(Id);
	}
	public void updateUpdateByAndUpdateDateWhenDown(int Id, int userID){
		DictionaryDAO.updateUpdateByAndUpdateDateWhenDown(Id, userID);
	}
	public void updateDeleteByAndDeleteDateWhenDelete(int Id, int userID){
		DictionaryDAO.updateDeleteByAndDeleteDateWhenDelete(Id, userID);
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
		Users users = userservice.getUserByUserID(UserID);
		if(users.getAuthorization() ==1){
			return list;
		}else{
			return newlist;
		}
		
	}
	
	public boolean checkDictionaryByUserId(int UserId,int Id) {
			Dictionary dictionary = DictionaryDAO.getDictionaryByID(Id);
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
			Dictionary dictionary = DictionaryDAO.getDictionaryByID(Id);
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
	
	public boolean checkIdDictionary(int Id) {
		
		List<Dictionary> listdictionary = DictionaryDAO.getAllDictionary();
		boolean result = false;
		for (int i = 0; i < listdictionary.size(); i++) {
			if(listdictionary.get(i).getID().equals(Id)){
				result =  true;
				break;
			}
		}
		return result;
	}
	
public boolean checkIdDictionaryAvaiable(int Id) {
		
		List<Dictionary> listdictionary = DictionaryDAO.getAllDictionaryAvailable();
		boolean result = false;
		for (int i = 0; i < listdictionary.size(); i++) {
			if(listdictionary.get(i).getID().equals(Id)){
				result =  true;
				break;
			}
		}
		return result;
	}

	public boolean checkIdDictionaryDeleted(int Id) {
		
		List<Dictionary> listdictionary = DictionaryDAO.getAllDictionaryDeleted();
		boolean result = false;
		for (int i = 0; i < listdictionary.size(); i++) {
			if(listdictionary.get(i).getID().equals(Id)){
				result =  true;
				break;
			}
		}
		return result;
	}
	
public boolean checkIdDictionaryUp(int Id) {
		
		List<Dictionary> listdictionary = DictionaryDAO.getAllDictionaryRecent();
		boolean result = false;
		for (int i = 0; i < listdictionary.size(); i++) {
			if(listdictionary.get(i).getID().equals(Id)){
				result =  true;
				break;
			}
		}
		return result;
	}

	public boolean checkIdDictionaryDown(int Id) {
		
		List<Dictionary> listdictionary = DictionaryDAO.getAllDictionaryDown();
		boolean result = false;
		for (int i = 0; i < listdictionary.size(); i++) {
			if(listdictionary.get(i).getID().equals(Id)){
				result =  true;
				break;
			}
		}
		return result;
	}
		
	// Khang android update 15/05
		public List<Dictionary> getDictionaryAvailableForAdmin(int page){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Dictionary> list = DictionaryDAO.getAvailableListForAdministrator();
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
			for(;list.size()>0;){
				Date max = list.get(0).getCreateDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getCreateDate().compareTo(max)>0){
						max = list.get(i).getCreateDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
		public List<Dictionary> getDictionaryAvailableForUser(int page, int UserID){
			
			List<Dictionary> list = DictionaryDAO.getAvailableListDictionaryForUser(UserID);
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
			for(;list.size()>0;){
				Date max = list.get(0).getCreateDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getCreateDate().compareTo(max)>0){
						max = list.get(i).getCreateDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
		public List<Dictionary> searchDictionaryAvailableForAdmin(int page, String keyword){
			List<Dictionary> list = DictionaryDAO.searchIdex(keyword, "1", 0); // 0 la khong co su dung so 0
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
			for(;list.size()>0;){
				Date max = list.get(0).getCreateDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getCreateDate().compareTo(max)>0){
						max = list.get(i).getCreateDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
		public List<Dictionary> searchDictionaryAvailableForUser(int page, String keyword, int UserID){
			
			List<Dictionary> question = DictionaryDAO.searchIdex(keyword, "1", UserID);
			
			List<Dictionary> newlistquestion= new ArrayList<Dictionary>();
			int L=0;
			for(int i=0;i<question.size();i++){
				if(question.get(i).getCreateBy().equals(UserID)){
					newlistquestion.add(L,question.get(i));
					L++;
				}
			}
			List<Dictionary> list = newlistquestion;
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
			for(;list.size()>0;){
				Date max = list.get(0).getCreateDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getCreateDate().compareTo(max)>0){
						max = list.get(i).getCreateDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
		public List<Dictionary> getDictionaryUpload(int page){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Dictionary> list = DictionaryDAO.getAllDictionaryRecent();
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
			for(;list.size()>0;){
				Date max = list.get(0).getUpdateDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getUpdateDate().compareTo(max)>0){
						max = list.get(i).getUpdateDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
		public List<Dictionary> searchDictionaryUpload(int page, String keyword){
			List<Dictionary> list = DictionaryDAO.searchIdex(keyword, "2", 0); // 0 la khong co su dung so 0
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
			for(;list.size()>0;){
				Date max = list.get(0).getUpdateDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getUpdateDate().compareTo(max)>0){
						max = list.get(i).getUpdateDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
		public List<Dictionary> getDictionaryDown(int page){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Dictionary> list = DictionaryDAO.getAllDictionaryDown();
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
			for(;list.size()>0;){
				Date max = list.get(0).getUpdateDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getUpdateDate().compareTo(max)>0){
						max = list.get(i).getUpdateDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
		public List<Dictionary> searchDictionaryDown(int page, String keyword){
			List<Dictionary> list = DictionaryDAO.searchIdex(keyword, "3", 0); // 0 la khong co su dung so 0
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
			for(;list.size()>0;){
				Date max = list.get(0).getUpdateDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getUpdateDate().compareTo(max)>0){
						max = list.get(i).getUpdateDate();
						rememberint = i;
					}
				}
				sortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
		public List<Dictionary> getDictionaryDelete(int page){
			//return QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
			
			List<Dictionary> list = DictionaryDAO.getAllDictionaryDeleted();
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
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
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
		public List<Dictionary> searchDictionaryDelete(int page, String keyword){
			List<Dictionary> list = DictionaryDAO.searchIdex(keyword, "4", 0); // 0 la khong co su dung so 0
			List<Dictionary> sortlist = new ArrayList<Dictionary>();
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
			
			List<Dictionary> shortlist = new ArrayList<Dictionary>();
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
