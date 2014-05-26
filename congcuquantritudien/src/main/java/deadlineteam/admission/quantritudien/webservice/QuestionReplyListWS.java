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

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.entities.QuestionmanagementEntity;
import deadlineteam.admission.quantritudien.entities.QuestionmanagementListEntity;
import deadlineteam.admission.quantritudien.service.Dictionary.Dictionary_SERVICE;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.util.AeSimpleMD5;


@Controller
@RequestMapping("android")
public class QuestionReplyListWS {
	
	@Autowired
	private Users_SERVICE userService;
	
	@Autowired
	private UserWS uWS;
	
	@Autowired
	private Dictionary_SERVICE DictionaryService;
	
	@Autowired
	private Questionmanagement_SERVICE quesSer;
	private QuestionmanagementListEntity quesList = new QuestionmanagementListEntity();
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	// This function return the list not reply to android
	@RequestMapping(value="questionreply/{username},{password},{page}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public QuestionmanagementListEntity getDictionList(
			@PathVariable("username")String username,
			@PathVariable("password")String password,
			@PathVariable("page")String page){
		
		quesList.setQuestionList(new ArrayList<QuestionmanagementEntity>());
		String result = uWS.checkLoginPresent(username, password);
		if(result.equals("success")){
			int npage = Integer.parseInt(page);
			int idUser = userService.getIdbyUsername(username);
			List<Questionmanagement> quesTemp;

			if(userService.checkIsAdmin(idUser)==true){
				quesTemp= quesSer.getReplyListQuestionmanagementAndroid(npage);
			}else{
				quesTemp= quesSer.getReplyListForUserAndroid(npage, idUser);
			}	
			
			for(int i = 0; i < quesTemp.size(); i++){
				
				QuestionmanagementEntity us = new QuestionmanagementEntity();
				us.ID = quesTemp.get(i).getID();;
				us.Question = quesTemp.get(i).getQuestion();
				us.QuestionBy = quesTemp.get(i).getQuestionBy();
				us.QuestionEmail = quesTemp.get(i).getQuestionEmail();
				us.Answer = quesTemp.get(i).getAnswer();
				quesList.getQuestionList().add(us);
			}
		}
		return quesList;
	}
	
	// Search for question page not reply
	@RequestMapping(value="questionreply/search/{username},{password},{page},{keyword}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public QuestionmanagementListEntity getSearchQuestionList(
			@PathVariable("username")String username,
			@PathVariable("password")String password,
			@PathVariable("keyword")String keyword,
			@PathVariable("page")String page){
		quesList.setQuestionList(new ArrayList<QuestionmanagementEntity>());
		String result = uWS.checkLoginPresent(username, password);
		if(result.equals("success")){
			int npage = Integer.parseInt(page);
			int idUser = userService.getIdbyUsername(username);
			List<Questionmanagement> quesTemp;
			if(userService.checkIsAdmin(idUser)==true){
				quesTemp= quesSer.searchIdexAndroidReplyListAndroid(npage, keyword);
			}else{
				quesTemp= quesSer.searchIdexAndroidReplyListForUserAndroid(npage,keyword,idUser);
			}	
			
			for(int i = 0; i < quesTemp.size(); i++){
				
				QuestionmanagementEntity us = new QuestionmanagementEntity();
				us.ID = quesTemp.get(i).getID();;
				us.Question = quesTemp.get(i).getQuestion();
				us.QuestionBy = quesTemp.get(i).getQuestionBy();
				us.QuestionEmail = quesTemp.get(i).getQuestionEmail();
				us.Answer = quesTemp.get(i).getAnswer();
				quesList.getQuestionList().add(us);
			}
		}
		return quesList;
	}
	

		// This function recieve save request from android application
		@RequestMapping(value="questionreply/dictionary/{username},{password},{IdQuestion}", method=RequestMethod.GET)
		@Produces("application/xml")
		@ResponseBody
		public String putDictionary(
				@PathVariable("username")String username,
				@PathVariable("password")String password,
				@PathVariable("IdQuestion")String IdQuestion){
			
			String result = "fail";
			String login = uWS.checkLoginPresent(username, password);
			
			if(login.equals("success")){
				int idUser = userService.getIdbyUsername(username);
				int idQues = Integer.parseInt(IdQuestion);

				Questionmanagement question = quesSer.getQuestionmanagementbyID(idQues);
				if(question.getUpdateBy() != null){
					// Xu ly thao tac song song
					Users information = userService.getUserByUserID(idUser);
					int author = information.getAuthorization();
					if(idUser == question.getUpdateBy()){

						Dictionary newdic = new Dictionary();
						Questionmanagement question1 = quesSer.repliedquestion(idQues);
					
							newdic.setAnwser(question1.getAnswer());
							newdic.setQuestion(question1.getQuestion());
							newdic.setCreateBy(idUser);
							Date now = new Date();
							newdic.setCreateDate(now);
							newdic.setAnwserBy(question1.getAnswerBy());
							newdic.setStatus(1);
							newdic.setDeleteStatus(0);
							DictionaryService.AddDictionary(newdic);
							quesSer.TransferToDictionary(idQues, idUser);
							
							result = "success";
					}else{
						if(author ==1){
							Users otheruser = userService.getUserByUserID(question.getUpdateBy());
							int otherauthor = otheruser.getAuthorization();
							if(otherauthor ==1){
								// null
								result = "confict,"+otheruser.getID();
							}else{
								//
							
								Dictionary newdic = new Dictionary();
								Questionmanagement question1 = quesSer.repliedquestion(idQues);
							
									newdic.setAnwser(question1.getAnswer());
									newdic.setQuestion(question1.getQuestion());
									newdic.setCreateBy(idUser);
									Date now = new Date();
									newdic.setCreateDate(now);
									newdic.setAnwserBy(question1.getAnswerBy());
									newdic.setStatus(1);
									newdic.setDeleteStatus(0);
									DictionaryService.AddDictionary(newdic);
									quesSer.TransferToDictionary(idQues, idUser);
								
									result = "success";
							}
						}else{
							// null
							Users otheruser = userService.getUserByUserID(question.getUpdateBy());
							result = "confict,"+otheruser.getID();
						}
					}
					
					// ket thuc xu ly thao tac song song
				}else{
					//
					Dictionary newdic = new Dictionary();
					Questionmanagement question1 = quesSer.repliedquestion(idQues);
				
						newdic.setAnwser(question1.getAnswer());
						newdic.setQuestion(question1.getQuestion());
						newdic.setCreateBy(idUser);
						Date now = new Date();
						newdic.setCreateDate(now);
						newdic.setAnwserBy(question1.getAnswerBy());
						newdic.setStatus(1);
						newdic.setDeleteStatus(0);
						DictionaryService.AddDictionary(newdic);
						quesSer.TransferToDictionary(idQues, idUser);
					
						result = "success";

				}
			}
			return result;
		}
		// This function recieve save request from android application
			@RequestMapping(value="questionreply/delete/{username},{password},{IdQuestion}", method=RequestMethod.GET)
			@Produces("application/xml")
			@ResponseBody
		public String deleteQuestion(
					@PathVariable("username")String username,
					@PathVariable("password")String password,
					@PathVariable("IdQuestion")String IdQuestion){
				
				String result = "fail";
				String login = uWS.checkLoginPresent(username, password);
				
				if(login.equals("success")){
					int idUser = userService.getIdbyUsername(username);
					int idQues = Integer.parseInt(IdQuestion);
				
					Questionmanagement question = quesSer.getQuestionmanagementbyID(idQues);
					if(question.getDeleteBy() != null){
						// Xu ly thao tac song song
						Users information = userService.getUserByUserID(idUser);
						int author = information.getAuthorization();
						if(idUser == question.getDeleteBy()){
							int execute = quesSer.deleterepliedquestion(idQues);
							if(execute>0){
								quesSer.UpdateDelete(idQues, idUser);
								result = "success";

							}
						}else{
							if(author ==1){
								Users otheruser = userService.getUserByUserID(question.getDeleteBy());
								int otherauthor = otheruser.getAuthorization();
								if(otherauthor ==1){
									// null
									result = "confict,"+otheruser.getID();
								}else{
									// Processing restore question
									int execute = quesSer.deleterepliedquestion(idQues);
									if(execute>0){
										quesSer.UpdateDelete(idQues, idUser);
										result = "success";
									}
								}
							}else{
								Users otheruser = userService.getUserByUserID(question.getDeleteBy());
								result = "confict,"+otheruser.getID();
							}
						}
						
					}else{
						// Processing restore question
						int execute = quesSer.deleterepliedquestion(idQues);
						if(execute>0){
							quesSer.UpdateDelete(idQues, idUser);
							result = "success";
						}
					}
					
				}
				return result;
			}
}