package deadlineteam.admission.hienthitudien.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import deadlineteam.admission.hienthitudien.dao.DictionaryDAO;
import deadlineteam.admission.hienthitudien.domain.Dictionary;
import deadlineteam.admission.hienthitudien.domain.Dictionary;


@Service
@Transactional
public class DictionaryService_Implement implements DictionaryService {
	@Autowired
	private DictionaryDAO DictionaryDAO ;

	int numAndroid = 10;
	
	@Override
	public List<Dictionary> getalldictionary(int page) {
		// TODO Auto-generated method stub		
		List<Dictionary> returnlist = DictionaryDAO.getalldictionary(page);		
		return returnlist;
	}
	public List<Dictionary> getall(int page){
		List<Dictionary> list = DictionaryDAO.getall();
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
	public List<Dictionary> getalldictionary(int page, int record){
		 List<Dictionary> list = DictionaryDAO.getalldictionary(page, record);
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
			List<Dictionary> returnlist = new ArrayList<Dictionary>();
			int begin = page * record;
			int end = begin + record;
			if (end > sortlist.size()) {
				end = sortlist.size();
			}
			int l = 0;
			for (int k = begin; k < end; k++) {
				returnlist.add(l, sortlist.get(k));
				l++;
			}
		return returnlist;
	}

	public List<Dictionary> searchIdex(String keyword){
		List<Dictionary> list = DictionaryDAO.searchIdex(keyword);
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
		return sortlist;
	}
	public List<Dictionary> searchIdexAndroid(int page, String keyword){
		
		List<Dictionary> list = DictionaryDAO.searchIdex(keyword);
		
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
         int setting = 1;
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
	public void updatequestion(Dictionary dictionary){
		 DictionaryDAO.updatequestion(dictionary);
	}
	public void deleteUser(Dictionary dictionary){
		 DictionaryDAO.deleteUser(dictionary);
	}
	
	public int totalPage(int record){
		List<Dictionary> dictionary = DictionaryDAO.getall();
		if(dictionary.size()==0){
			return 0;
		}else{
			if(dictionary.size() <= record){
				return 1;
			}else{
				if(dictionary.size()%record==0){
					return dictionary.size()/record;
				}else{
					return (dictionary.size()/record)+1;
				}
			}
		}
		
	}
}
