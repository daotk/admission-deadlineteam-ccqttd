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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import deadlineteam.admission.quantritudien.controller.DictionaryController;
import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.entities.QuestionmanagementEntity;
import deadlineteam.admission.quantritudien.entities.QuestionmanagementListEntity;
import deadlineteam.admission.quantritudien.service.Dictionary.Dictionary_SERVICE;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.util.AeSimpleMD5;
import deadlineteam.admission.quantritudien.validator.DictionaryValidator;


@Controller
@RequestMapping("android")
public class CreateQuestionWS {
	
	@Autowired
	private Users_SERVICE userService;
	
	@Autowired
	private UserWS uWS;
	
	@Autowired
	private Dictionary_SERVICE DictionaryService;
	
	@Autowired
	private Questionmanagement_SERVICE quesSer;
	private QuestionmanagementListEntity quesList = new QuestionmanagementListEntity();
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
	
		// This function recieve save request from android application
		@RequestMapping(value="dictionary/create/{username},{password},{question},{anwser}", method=RequestMethod.GET)
		@Produces("application/xml")
		@ResponseBody
		public String saveQuestion(
				@PathVariable("username")String username,
				@PathVariable("password")String password,
				@PathVariable("question")String question,
				@PathVariable("anwser")String anwser){
			
			String result = "fail";
			String login = uWS.checkLoginPresent(username, password);
			
			if(login.equals("success")){
				int idUser = userService.getIdbyUsername(username);
	
					Dictionary dictionary = new Dictionary();
				
					dictionary.setQuestion(question);
					dictionary.setAnwser(anwser);
					dictionary.setCreateBy(idUser);
					dictionary.setAnwserBy(idUser);
					Date now = new Date();
					dictionary.setCreateDate(now);
					dictionary.setStatus(1);
					dictionary.setDeleteStatus(0);
					dictionary.setBusyStatus(0);
				
					DictionaryService.AddDictionary(dictionary);
					Users users = userService.getUserByUserID(idUser);
				
					String newquestion =  dictionary.getQuestion();
					
					logger.info("Tài khoản " + users.getUserName() + " đã tạo câu hỏi " +newquestion);
					result = "success";
			}
			return result;
		}
		
		
}