package deadlineteam.admission.hienthitudien.webservice;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import deadlineteam.admission.hienthitudien.dao.DictionaryDAO;
import deadlineteam.admission.hienthitudien.domain.Dictionary;
import deadlineteam.admission.hienthitudien.domain.Questionmanagement;
import deadlineteam.admission.hienthitudien.entities.DictionaryEntity;
import deadlineteam.admission.hienthitudien.entities.DictionaryListEntity;
import deadlineteam.admission.hienthitudien.service.DictionaryService;



@Controller
@RequestMapping("android")
public class DictionaryWS {
	

	@Value("${congcuquantri.url}")
    private String url;
	
	@Autowired
	private DictionaryService dicSer;
	private DictionaryListEntity dicList = new DictionaryListEntity();

	@RequestMapping(value="dictionary/{page}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public DictionaryListEntity getDictionList(
			@PathVariable("page")String page){
	
		int numpage = Integer.parseInt(page);
		dicList.setDictionaryList(new ArrayList<DictionaryEntity>());
		List<Dictionary> dicTemp= dicSer.getall(numpage);


		for(int i = 0; i < dicTemp.size(); i++){
			
			DictionaryEntity us = new DictionaryEntity();
			us.ID = dicTemp.get(i).getID();;
			us.Question = dicTemp.get(i).getQuestion();
			us.Answer = dicTemp.get(i).getAnwser();;
			us.CreateDate = dicTemp.get(i).getCreateDate();
			dicList.getDictionaryList().add(us);
		}
		return dicList;
	}
	
	@RequestMapping(value="dictionary/search/{page},{keyword}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public DictionaryListEntity getDictionSearchList(
			@PathVariable("page")String page,
			@PathVariable("keyword")String keyword){
	
		
		dicList.setDictionaryList(new ArrayList<DictionaryEntity>());
		int pageInt = Integer.parseInt(page);
		List<Dictionary> dicTemp= dicSer.searchIdexAndroid(pageInt, keyword);
		for(int i = 0; i < dicTemp.size(); i++){
			
			DictionaryEntity us = new DictionaryEntity();
			us.ID = dicTemp.get(i).getID();;
			us.Question = dicTemp.get(i).getQuestion();
			us.Answer = dicTemp.get(i).getAnwser();;
			us.CreateDate = dicTemp.get(i).getCreateDate();
			dicList.getDictionaryList().add(us);
		}
		return dicList;
	}
	
	@RequestMapping(value="send/{name},{email},{question}", method=RequestMethod.GET)
	@ResponseBody
	public String createQuestion(@PathVariable("name")String name,
			@PathVariable("email")String email,
			@PathVariable("question")String question) {
		
		String message="";
		RestTemplate restTemplate = new RestTemplate();
		Questionmanagement questionmanagement = new Questionmanagement();

		questionmanagement.setQuestionBy(name);
		questionmanagement.setQuestionEmail(email);
		question = AndroidUtil.restoreTags(question);
		questionmanagement.setQuestion(question);
		
		
		String result = restTemplate.postForObject(url+"/api/question", questionmanagement, String.class);
		if(result.equals("Issuccess")){
			message = "success";
		}else{
			message = "fail";
		}
		return message;
	}
	
	
}
