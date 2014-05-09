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

import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.entities.QuestionmanagementEntity;
import deadlineteam.admission.quantritudien.entities.QuestionmanagementListEntity;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.util.AeSimpleMD5;


@Controller
@RequestMapping("android")
public class QuestionWS {
	
	@Autowired
	private Users_SERVICE userService;
	
	@Autowired
	private UserWS uWS;
	
	@Autowired
	private Questionmanagement_SERVICE quesSer;
	private QuestionmanagementListEntity quesList = new QuestionmanagementListEntity();
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	// This function return the list not reply to android
	@RequestMapping(value="question/{username},{password}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public QuestionmanagementListEntity getDictionList(
			@PathVariable("username")String username,
			@PathVariable("password")String password){
		
		quesList.setQuestionList(new ArrayList<QuestionmanagementEntity>());
		String result = uWS.checkLoginPresent(username, password);
		if(result.equals("success")){
			List<Questionmanagement> quesTemp= quesSer.getListQuestionmanagementbyStatus(1);

			for(int i = 0; i < quesTemp.size(); i++){
				
				QuestionmanagementEntity us = new QuestionmanagementEntity();
				us.ID = quesTemp.get(i).getID();;
				us.Question = quesTemp.get(i).getQuestion();
				us.QuestionBy = quesTemp.get(i).getQuestionBy();
				us.QuestionEmail = quesTemp.get(i).getQuestionEmail();
			
				us.Answer = quesTemp.get(i).getAnswer();
				us.AnswerBy = quesTemp.get(i).getAnswerBy();
				us.AnwserDate = quesTemp.get(i).getAnwserDate();
				us.UpdateBy = quesTemp.get(i).getUpdateBy();
				us.UpdateDate = quesTemp.get(i).getUpdateDate();
				us.Status = quesTemp.get(i).getStatus();
				us.DeleteStatus = quesTemp.get(i).getDeleteStatus();
				us.DeleteBy = quesTemp.get(i).getDeleteBy();
			
				
				quesList.getQuestionList().add(us);
			}
		}
		return quesList;
	}
	
	// Search for question page not reply
	@RequestMapping(value="question/search/{username},{password},{keyword}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public QuestionmanagementListEntity getSearchQuestionList(
			@PathVariable("username")String username,
			@PathVariable("password")String password,
			@PathVariable("keyword")String keyword){
		quesList.setQuestionList(new ArrayList<QuestionmanagementEntity>());
		String result = uWS.checkLoginPresent(username, password);
		if(result.equals("success")){
			List<Questionmanagement> quesTemp= quesSer.searchIdex(keyword, "1");
			for(int i = 0; i < quesTemp.size(); i++){
				
				QuestionmanagementEntity us = new QuestionmanagementEntity();
				us.ID = quesTemp.get(i).getID();;
				us.Question = quesTemp.get(i).getQuestion();
				us.QuestionBy = quesTemp.get(i).getQuestionBy();
				us.QuestionEmail = quesTemp.get(i).getQuestionEmail();
				us.Answer = quesTemp.get(i).getAnswer();
				us.AnswerBy = quesTemp.get(i).getAnswerBy();
				us.AnwserDate = quesTemp.get(i).getAnwserDate();
				us.UpdateBy = quesTemp.get(i).getUpdateBy();
				us.UpdateDate = quesTemp.get(i).getUpdateDate();
				us.Status = quesTemp.get(i).getStatus();
				us.DeleteStatus = quesTemp.get(i).getDeleteStatus();
				us.DeleteBy = quesTemp.get(i).getDeleteBy();
				quesList.getQuestionList().add(us);
			}
		}
		return quesList;
	}
	
	
	
	// This function receive data from android and send mail to user
		@RequestMapping(value="question/send/{username},{password},{IdQuestion},{email},{body}", method=RequestMethod.GET)
		@Produces("application/xml")
		@ResponseBody
		public String sendEmail(
				@PathVariable("username")String username,
				@PathVariable("password")String password,
				@PathVariable("email")String email,
				@PathVariable("body")String body,
				@PathVariable("IdQuestion")String IdQuestion){
			String result = "fail";
			String login = uWS.checkLoginPresent(username, password);
			
			if(login.equals("success")){
				// get id from username
				int idUser = userService.getIdbyUsername(username);
				int idQues = Integer.parseInt(IdQuestion);
				String title = "Trả lời câu hỏi tuyển sinh";
				body = AndroidUtil.restoreTags(body);
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				try {
					
					 MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					 message.setTo(email);
					 message.setSubject(title);
					 
					 message.setText(body, true);
					// sends the e-mail
					mailSender.send(mimeMessage);

					result = "success";
					
					
					Questionmanagement question = quesSer.getQuestionmanagementbyID(idQues);
					if(question.getAnswerBy() != null){
						// Xu ly thao tac song song
						Users information = userService.getUser(idUser);
						int author = information.getAuthorization();
						if(author ==1){
							Users otheruser = userService.getUser(question.getAnswerBy());
							int otherauthor = otheruser.getAuthorization();
							if(otherauthor == 1){
								// null
								result = "confict";
								//model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
								
							}else{
								int execute = quesSer.updateAnswerbyId(idQues,body);
								if(execute>0){			
									quesSer.UpdateAnwserBy(idQues, idUser);														
								}
							}
						}else{
							// null
							result = "confict";
//							Users otheruser = userService.getUser(question.getAnswerBy());
//							model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
						}
						// ket thuc xu ly thao tac song song
					}else{
						int execute = quesSer.updateAnswerbyId(idQues,body);
						if(execute>0){

							quesSer.UpdateAnwserBy(idQues, idUser);	
							result = "success";
						}
					}															
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	
				}	
			}
			return result;
		}
		
		// This function recieve save request from android application
		@RequestMapping(value="question/save/{username},{password},{IdQuestion},{body}", method=RequestMethod.GET)
		@Produces("application/xml")
		@ResponseBody
		public String saveQuestion(
				@PathVariable("username")String username,
				@PathVariable("password")String password,
				@PathVariable("body")String body,
				@PathVariable("IdQuestion")String IdQuestion){
			
			String result = "fail";
			String login = uWS.checkLoginPresent(username, password);
			
			if(login.equals("success")){
				int idUser = userService.getIdbyUsername(username);
				int idQues = Integer.parseInt(IdQuestion);
				body = AndroidUtil.restoreTags(body);
				Questionmanagement question = quesSer.getQuestionmanagementbyID(idQues);
				if(question.getAnswerBy() != null){
					// Xu ly thao tac song song
					Users information = userService.getUser(idUser);
					int author = information.getAuthorization();
					if(author ==1){
						Users otheruser = userService.getUser(question.getAnswerBy());
						int otherauthor = otheruser.getAuthorization();
						if(otherauthor ==1){
							// null
							result = "confict";
							//model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
						}else{
							int execute = quesSer.SaveTemporaryAnswerbyId(idQues,body);
							if(execute>0){			
								
								quesSer.UpdateAnwserBy(idQues, idUser);
								result = "success";
							}
						}
					}else{
						result = "confict";
					}
				}else{
					
					int execute = quesSer.SaveTemporaryAnswerbyId(idQues,body);
					if(execute>0){
						quesSer.UpdateAnwserBy(idQues, idUser);
						result = "success";
					}
				}
			}
			return result;
		}
		// This function recieve save request from android application
				@RequestMapping(value="question/delete/{username},{password},{IdQuestion}", method=RequestMethod.GET)
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
							Users information = userService.getUser(idUser);
							int author = information.getAuthorization();
							if(author ==1){
								Users otheruser = userService.getUser(question.getDeleteBy());
								int otherauthor = otheruser.getAuthorization();
								if(otherauthor == 1){
									// null
									result = "confict,"+otheruser.getID();
									//model.addAttribute("error", "Câu hỏi đã được "+otheruser.getFullName()+" trả lời");
								}else{
									int execute = quesSer.delete(idQues);
									if(execute>0){			
										quesSer.UpdateDelete(idQues, idUser);
										result = "success";
									}
								}
							}else{
								Users otheruser = userService.getUser(question.getDeleteBy());
								result = "confict,"+otheruser.getID();
							}
						}else{
							
							int execute = quesSer.delete(idQues);
							if(execute>0){
								quesSer.UpdateDelete(idQues, idUser);
								result = "success";
							}
						}
					}
					return result;
				}
}