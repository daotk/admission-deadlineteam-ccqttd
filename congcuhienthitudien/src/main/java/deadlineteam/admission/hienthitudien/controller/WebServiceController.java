package deadlineteam.admission.hienthitudien.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import deadlineteam.admission.hienthitudien.service.DictionaryService;
import deadlineteam.admission.hienthitudien.domain.Dictionary;


@Controller
@RequestMapping("api")
public class WebServiceController {
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping(value="question", method=RequestMethod.POST)
	@ResponseBody
	public String savequestion(@RequestBody Dictionary dictionary) {
		String message ;
		if(checkinput(dictionary) == true){
			try{
				Dictionary newquestion = new Dictionary();
				newquestion.setID(dictionary.getID());
				newquestion.setQuestion(dictionary.getQuestion());
				newquestion.setAnwser(dictionary.getAnwser());
				Date now = new Date();
				newquestion.setCreateDate(now);
				dictionaryService.updatequestion(newquestion);
				message = "success";
			}catch(Exception e){
				message = "fail";
			}
			
		}else{
			message = "fail";	
		}
		return message;
	}
	
	@RequestMapping(value="question", method=RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> savequestion() {
		List<Dictionary> listDictionary = dictionaryService.getalldictionary(1, 10);
		return listDictionary;
	}
	
	private boolean checkinput(Dictionary dictionary){
		if(dictionary.getQuestion() != null 
				&& dictionary.getAnwser() != null
				&& dictionary.getID() != null){
			return true;
		}else{
			return false;
		}
		
	}
	
	@RequestMapping(value="romovequestion", method=RequestMethod.POST)
	@ResponseBody
	public String removequesstion(@RequestBody Dictionary dictionary) {
		String message ;
		if(checkinput(dictionary) == true){
			try{
				dictionaryService.deleteUser(dictionary);
				message = "success";
			}catch(Exception e){
				message = "fail";
			}
			
		}else{
			message = "fail";	
		}
		return message;
	}
}
