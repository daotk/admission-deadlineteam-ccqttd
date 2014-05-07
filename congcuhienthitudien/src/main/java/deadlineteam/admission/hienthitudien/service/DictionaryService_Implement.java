package deadlineteam.admission.hienthitudien.service;

import java.util.ArrayList;
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

	@Override
	public List<Dictionary> getalldictionary(int page) {
		// TODO Auto-generated method stub		
		List<Dictionary> returnlist = DictionaryDAO.getalldictionary(page);		
		return returnlist;
	}
	public List<Dictionary> getall(){
		List<Dictionary> list = DictionaryDAO.getall();
		List<Dictionary> shortlist = new ArrayList<Dictionary>();
		 int page = 0;
         int setting = 3;
         int begin = page*setting;
         int end = begin + setting;
         if(end > list.size()){
         	end = list.size();
         }
         int l = 0;
         for(int k = begin; k< end;k++){
         	shortlist.add(l, list.get(k));
         	l++;
         }
         
		return shortlist;		
	}
	public List<Dictionary> getalldictionary(int page, int record){
		return DictionaryDAO.getalldictionary(page, record);
	}

	public List<Dictionary> searchIdex(String keyword){
		
		return DictionaryDAO.searchIdex(keyword);
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
