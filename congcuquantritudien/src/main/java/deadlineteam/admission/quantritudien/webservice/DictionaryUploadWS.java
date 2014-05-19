package deadlineteam.admission.quantritudien.webservice;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Produces;

import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import deadlineteam.admission.quantritudien.controller.DictionaryController;
import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.DictionaryRestful;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.entities.DictionaryEntity;
import deadlineteam.admission.quantritudien.entities.QuestionmanagementEntity;
import deadlineteam.admission.quantritudien.entities.DictionaryListEntity;
import deadlineteam.admission.quantritudien.service.Dictionary.Dictionary_SERVICE;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.util.AeSimpleMD5;


@Controller
@RequestMapping("android")
public class DictionaryUploadWS {
	
	@Autowired
	private Users_SERVICE userService;
	
	@Autowired
	private UserWS uWS;
	
	@Value("${congcuhienthi.url}")
    private String congcuhienthi;
	
	@Autowired
	private Dictionary_SERVICE DictionaryService;
	
	@Autowired
	private Dictionary_SERVICE dicSer;
	private DictionaryListEntity dicList = new DictionaryListEntity();
	
	@Autowired
	private JavaMailSender mailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
	
	// This function return the list not reply to android
	@RequestMapping(value="dictionaryupload/{username},{password},{page}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public DictionaryListEntity getDictionList(
			@PathVariable("username")String username,
			@PathVariable("password")String password,
			@PathVariable("page")String page){
		
		dicList.setDictionaryList(new ArrayList<DictionaryEntity>());
		String result = uWS.checkLoginPresent(username, password);
		if(result.equals("success")){
			int npage = Integer.parseInt(page);
			int idUser = userService.getIdbyUsername(username);
			List<Dictionary> quesTemp;

			quesTemp= dicSer.getDictionaryUpload(npage);
			
			for(int i = 0; i < quesTemp.size(); i++){
				
				DictionaryEntity us = new DictionaryEntity();
				us.ID = quesTemp.get(i).getID();;
				us.Question = quesTemp.get(i).getQuestion();
				us.Anwser = quesTemp.get(i).getAnwser();
				us.AnwserBy = quesTemp.get(i).getAnwserBy();
				us.CreateBy = quesTemp.get(i).getCreateBy();
				dicList.getDictionaryList().add(us);
			}
		}
		return dicList;
	}
	
	// Search for question page not reply
	@RequestMapping(value="dictionaryupload/search/{username},{password},{page},{keyword}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public DictionaryListEntity getSearchQuestionList(
			@PathVariable("username")String username,
			@PathVariable("password")String password,
			@PathVariable("keyword")String keyword,
			@PathVariable("page")String page){
		dicList.setDictionaryList(new ArrayList<DictionaryEntity>());
		String result = uWS.checkLoginPresent(username, password);
		if(result.equals("success")){
			int npage = Integer.parseInt(page);
			int idUser = userService.getIdbyUsername(username);
			List<Dictionary> quesTemp;
			quesTemp= dicSer.searchDictionaryUpload(npage, keyword);
			
			for(int i = 0; i < quesTemp.size(); i++){
				
				DictionaryEntity us = new DictionaryEntity();
				us.ID = quesTemp.get(i).getID();;
				us.Question = quesTemp.get(i).getQuestion();
				us.Anwser = quesTemp.get(i).getAnwser();
				us.AnwserBy = quesTemp.get(i).getAnwserBy();
				us.CreateBy = quesTemp.get(i).getCreateBy();
				dicList.getDictionaryList().add(us);
			}
		}
		return dicList;
	}
	
	
		@RequestMapping(value="dictionaryupload/down/{username},{password},{IdQuestion}", method=RequestMethod.GET)
		@Produces("application/xml")
		@ResponseBody
		public String uploadQuestion(
				@PathVariable("username")String username,
				@PathVariable("password")String password,
				@PathVariable("IdQuestion")String IdQuestion){
			String result = "fail";
			String login = uWS.checkLoginPresent(username, password);
			
			if(login.equals("success")){
				// get id from username
				int idUser = userService.getIdbyUsername(username);
				int idQues = Integer.parseInt(IdQuestion);
				
				Dictionary newdictionary = DictionaryService.getinformation(idQues);
				
				DictionaryRestful dicrestful = new DictionaryRestful();
				dicrestful.setID(idQues);
				dicrestful.setAnwser(newdictionary.getAnwser());
				dicrestful.setQuestion(newdictionary.getQuestion());
				try{
					RestTemplate restTemplate = new RestTemplate();
					String execute1 = restTemplate.postForObject(congcuhienthi+"/api/romovequestion", dicrestful, String.class);
					
					if(execute1.equals("success")){
						Users users = userService.getUser(idUser);
					//	Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
						Dictionary userquestion = DictionaryService.getinformation(idQues);
						String newquestion =  userquestion.getQuestion();
						if(newquestion.length() > 50){
							newquestion.subSequence(0, 45);
							newquestion = newquestion + "...";
						}
						logger.info("Tài khoản " + users.getUserName() + " đã hạ câu hỏi " +newquestion);
						// Processing restore question
						int down = DictionaryService.remove(idQues);
						int update = DictionaryService.updateby(idQues, idUser);
						result = "success";
					}else{
						if(execute1.equals("fail")){
							result = "fail";
						}
					}
				}catch(Exception e){
					result = "fail";
				}
			}
			return result;
		}
}