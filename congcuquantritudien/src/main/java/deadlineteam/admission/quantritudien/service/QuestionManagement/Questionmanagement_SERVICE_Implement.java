package deadlineteam.admission.quantritudien.service.QuestionManagement;
import deadlineteam.admission.quantritudien.dao.Dictionary.Dictionary_DAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import deadlineteam.admission.quantritudien.dao.QuestionManagement.Questionmanagement_DAO;
import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

@Service
@Transactional
public class Questionmanagement_SERVICE_Implement implements Questionmanagement_SERVICE{
	@Autowired
	private Questionmanagement_DAO QuestionmanagementDAO;
	@Autowired
	private Dictionary_DAO Dictionary_DAO;
	
	public List<Questionmanagement> getListQuestionmanagement() {
		return QuestionmanagementDAO.getListQuestionmanagement();
	}

	public Questionmanagement getQuestionmanagementbyID(int Id){
		return QuestionmanagementDAO.getQuestionmanagementbyID(Id);
	}
	@Override
	public List<Questionmanagement> findpage1(String keyword) {
		// TODO Auto-generated method stub
		List<Questionmanagement> list = QuestionmanagementDAO.getListQuestionmanagement();
		List<Questionmanagement> newlisst = new ArrayList<Questionmanagement>();
		
		return newlisst;
	}
	
	public List<Questionmanagement> getQuestionmanagementbyPage(int page, int UserID) {
		List<Questionmanagement> list = QuestionmanagementDAO.getQuestionmanagementbyPage(page,UserID);
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
		
		for(int i = 0 ; i < list.size(); i++){
			shortlist.add(i, list.get(list.size() - 1-i) );
        }
		
		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		 Setting settings = getSetting(UserID);
		 int begin = page* settings.getRecordNotRep();
		 int end = begin + settings.getRecordNotRep();
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
	public int updateAnswerbyId(int Id,String Answer){	
		return QuestionmanagementDAO.updateAnswerbyId(Id,Answer);
	}
	public List<Questionmanagement> getQuestionmanagementbyPage_setting(int page, int record){
		return QuestionmanagementDAO.getQuestionmanagementbyPage_setting(page, record);
	}
	/*Author: Phu Ta
	 * delete question that is selected
	 */
	public int delete(int Id){
		return QuestionmanagementDAO.delete(Id);
	}
	//------------------------Delete Page
	/*Author: Phu Ta
	 * Load delete question-list
	 */
	public Questionmanagement deletequestion(int Id){
		return QuestionmanagementDAO.deletequestion(Id);
	}
	public List<Questionmanagement> deleteList(int page, int UserID){
		List<Questionmanagement> list= QuestionmanagementDAO.deleteList(page, UserID);
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
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
		
		
		
		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		 Setting settings = getSetting(UserID);
		 int begin = page* settings.getRecordDelete();
		 int end = begin + settings.getRecordDelete();
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

	/*Author: Phu Ta
	 * delete question that is selected
	 */
	public int restore(int Id){
		return QuestionmanagementDAO.restore(Id);
	}
	//----------------------- Save Page
	/*Author: Phu Ta
	 * Load save-question-list
	 */
		public Questionmanagement savequestion(int Id){
			return QuestionmanagementDAO.savequestion(Id);
		}
		public List<Questionmanagement> savelist(int page, int UserID){
			List<Questionmanagement> list= QuestionmanagementDAO.savelist(page, UserID);
			List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
			for(;list.size()>0;){
				Date max = list.get(0).getAnwserDate();
				int rememberint =0;
				for(int i=1;i<list.size();i++){
					if(list.get(i).getAnwserDate().compareTo(max)>0){
						max = list.get(i).getAnwserDate();
						rememberint = i;
					}
				}
				shortlist.add(list.get(rememberint));
				list.remove(rememberint);
			}
			
			
			
			List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
			 Setting settings = getSetting(UserID);
			 int begin = page* settings.getRecordTemp();
			 int end = begin + settings.getRecordTemp();
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
	/*
	 * 	Author: Phu Ta
	 * delete save-question that is selected
	 */
	public int deletesavequestion(int Id){
		return 	QuestionmanagementDAO.deletesavequestion(Id);
	}
	/*
	 * 	Author: Phu Ta
	 * save save-question that is selected
	 */
	public int SaveAnwser(int Id,String Answer){
		return QuestionmanagementDAO.SaveAnwser(Id, Answer);
	}
	/*
	 * 	Author: Phu Ta
	 * save save-question that is selected
	 */
	public int SendAnwser(int Id,String Answer){		
		return QuestionmanagementDAO.SendAnwser(Id, Answer);
	}
	//SAve Temporary
	public int SaveTemporaryAnswerbyId(int Id,String Answer){
		return QuestionmanagementDAO.SaveTemporaryAnswerbyId(Id, Answer);
	}
	
	//------------------------------- replied page
	/*Author: Chau Le
	 * replied question-list page
	 */	


	public int deleterepliedquestion(int ID){
		return 	QuestionmanagementDAO.deletesavequestion(ID);
	}

	@Override
	public List<Questionmanagement> repliedList(int page,int UserID ) {
		// TODO Auto-generated method stub
		List<Questionmanagement> list = QuestionmanagementDAO.repliedList(page, UserID);
		List<Questionmanagement> shortlist = new ArrayList<Questionmanagement>();
		for(;list.size()>0;){
			Date max = list.get(0).getAnwserDate();
			int rememberint =0;
			for(int i=1;i<list.size();i++){
				if(list.get(i).getAnwserDate().compareTo(max)>0){
					max = list.get(i).getAnwserDate();
					rememberint = i;
				}
			}
			shortlist.add(list.get(rememberint));
			list.remove(rememberint);
		}
	
		List<Questionmanagement> newlist = new ArrayList<Questionmanagement>();
		 Setting settings = getSetting(UserID);
		 int begin = page* settings.getRecordRepied();
		 int end = begin + settings.getRecordRepied();
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

	@Override
	public Questionmanagement repliedquestion(int ID) {
		// TODO Auto-generated method stub
		return QuestionmanagementDAO.repliedquestion(ID);
	}
	@Override
	public int totalPageQuestiomanagement(int status, int UserID){
		int result;
		List<Questionmanagement> listquestion = QuestionmanagementDAO.getListQuestionmanagementbyStatus(status);
		
		int totalRecord = listquestion.size();
		Setting settings = getSetting(UserID);
		if(status ==1){
			if( totalRecord % settings.getRecordNotRep() ==0){
				result= totalRecord/settings.getRecordNotRep();
			}else{
				result= (totalRecord/settings.getRecordNotRep())+1;
			}
		}else{
			if(status ==2){
				if( totalRecord % settings.getRecordTemp() ==0){
					result= totalRecord/settings.getRecordTemp();
				}else{
					result= (totalRecord/settings.getRecordTemp())+1;
				}
			}else{
				if(status ==3){
					if( totalRecord % settings.getRecordRepied() ==0){
						result= totalRecord/settings.getRecordRepied();
					}else{
						result= (totalRecord/settings.getRecordRepied())+1;
					}
				}else{
					if(status ==4){
						if( totalRecord % settings.getRecordDelete() ==0){
							result= totalRecord/settings.getRecordDelete();
						}else{
							result= (totalRecord/settings.getRecordDelete())+1;
						}
					}else{
						if(status ==5){
							List<Dictionary> list = QuestionmanagementDAO.getListDictionarybyStatus(1);
							int totaldictionary = list.size() ;
							if(totaldictionary <= settings.getRecordDictionary()){
								result =1;
							}else{
								if( totaldictionary % settings.getRecordDictionary() ==0){
									result= totaldictionary/settings.getRecordDictionary();
								}else{
									result= totaldictionary/settings.getRecordDictionary()+1;
								}
							}
							
							
						}else{
							if(status ==6){
								List<Dictionary> list = QuestionmanagementDAO.getListDictionarybyStatus(2);
								int totaldictionary = list.size() ;
								if(totaldictionary <= settings.getRecordDictionary()){
									result =1;
								}else{
									if( totaldictionary % settings.getRecordDictionary() ==0){
										result= totaldictionary/settings.getRecordDictionary();
									}else{
										result= totaldictionary/settings.getRecordDictionary()+1;
									}
								}
								
							}else{
								if(status ==7){
									List<Dictionary> list = QuestionmanagementDAO.getListDictionarybyStatus(4);
									int totaldictionary = list.size() ;
									if(totaldictionary <= settings.getRecordDictionary()){
										result =1;
									}else{
										if( totaldictionary % settings.getRecordDictionary() ==0){
											result= totaldictionary/settings.getRecordDictionary();
										}else{
											result= totaldictionary/settings.getRecordDictionary()+1;
										}
									}
									
								}else{
									if(status ==8){
										
										List<Dictionary> list = QuestionmanagementDAO.getListDictionaryDelete(4);
										int totaldictionary = list.size() ;
										if(totaldictionary <= settings.getRecordDictionary()){
											result =1;
										}else{
											if( totaldictionary % settings.getRecordDictionary() ==0){
												result= totaldictionary/settings.getRecordDictionary();
											}else{
												result= totaldictionary/settings.getRecordDictionary()+1;
											}
										}
										
									}else{
										result = 0;
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
	public List<Dictionary> getListDictionaryDelete(int status){
		return QuestionmanagementDAO.getListDictionaryDelete(status);
	}
	@Override
	public int totalPageQuestiomanagementDelete(int status){
		int result;
		List<Questionmanagement> listquestion = QuestionmanagementDAO.getListQuestionmanagementbyDeleteStatus(status);
		int totalRecord = listquestion.size();
		if( totalRecord % 5 ==0){
			result = totalRecord/5;
		}else{
			result = (totalRecord/5)+1;
		}
		return result;
	}
	
	public void createIndex(){
		QuestionmanagementDAO.createIndex();
	}
	public List<Questionmanagement> searchIdex(String keyword,String Status){
		return QuestionmanagementDAO.searchIdex(keyword, Status);
	}
	public void deleteall (String listdelete, int userid){
		String[] liststring = listdelete.split(",");
		for(int i=0;i<liststring.length;i++){
			int deleteid = Integer.parseInt(liststring[i].toString());
			QuestionmanagementDAO.UpdateDelete(deleteid, userid);
			QuestionmanagementDAO.delete(deleteid);
		}
	}
	//--------------------RESTful web service-----
	public void addquestion(Questionmanagement question){
		QuestionmanagementDAO.addquestion(question);	
	}
	public void TransferToDictionary(int Id, int userid){
		QuestionmanagementDAO.TransferToDictionary(Id, userid);
	}
	public void UpdateDelete(int Id, int userid){
		QuestionmanagementDAO.UpdateDelete(Id, userid);
	}
	public void UpdateAnwserBy(int Id, int userid){
		QuestionmanagementDAO.UpdateAnwserBy(Id, userid);
	}
	public Users getusername(int username){
		return QuestionmanagementDAO.getusername(username);
	}
	public Setting getSetting(int UserId){
		return QuestionmanagementDAO.getSetting(UserId);
	}

}

