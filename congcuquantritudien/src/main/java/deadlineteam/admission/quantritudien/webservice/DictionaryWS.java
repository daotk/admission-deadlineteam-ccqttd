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
public class DictionaryWS {
	
	AndroidUtil util;
	
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
	@RequestMapping(value="dictionary/{username},{password},{page}", method=RequestMethod.GET)
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

			if(userService.checkIsAdmin(idUser)==true){
				quesTemp= dicSer.getDictionaryAvailableForAdmin(npage);
			}else{
				quesTemp= dicSer.getDictionaryAvailableForUser(npage, idUser);
			}	
			
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
	@RequestMapping(value="dictionary/search/{username},{password},{page},{keyword}", method=RequestMethod.GET)
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
			if(userService.checkIsAdmin(idUser)==true){
				quesTemp= dicSer.searchDictionaryAvailableForAdmin(npage, keyword);
			}else{
				quesTemp= dicSer.searchDictionaryAvailableForUser(npage, keyword, idUser);
			}	
			
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
	
	
		@RequestMapping(value="dictionary/upload/{username},{password},{IdQuestion}", method=RequestMethod.GET)
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
				
				try{
					Dictionary newdictionary = DictionaryService.getDictionaryByID(idQues);
					DictionaryRestful dicrestful = new DictionaryRestful();
					dicrestful.setID(idQues);
					dicrestful.setAnwser(newdictionary.getAnwser());
					dicrestful.setQuestion(newdictionary.getQuestion());
					RestTemplate restTemplate = new RestTemplate();
					String execute1 = restTemplate.postForObject(congcuhienthi+"/api/question", dicrestful, String.class);
					if(execute1.equals("success")){
						result = "success";
						// Processing restore question
						int execute = DictionaryService.updateDictionaryWhenUpload(idQues);	
						int update = DictionaryService.updateUpdateByWhenUpload(idQues, idUser);
						if(execute > 0 && update >0){
							Users users = userService.getUserByUserID(idUser);
							Dictionary question = DictionaryService.getDictionaryByID(idQues);
							String newquestion = question.getQuestion();
							if(newquestion.length() > 50){
								newquestion.substring(0, 45);
								newquestion = newquestion + "...";
							}
							logger.info("Tài khoản " + users.getUserName() + " đã đăng câu hỏi "+newquestion);

						}
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
		
				@RequestMapping(value="dictionary/delete/{username},{password},{IdQuestion}", method=RequestMethod.GET)
				@Produces("application/xml")
				@ResponseBody
				public String deleteQuestion(
						@PathVariable("username")String username,
						@PathVariable("password")String password,
						@PathVariable("IdQuestion")String IdQuestion){
					String result = "fail";
					String login = uWS.checkLoginPresent(username, password);
					
					if(login.equals("success")){
						// get id from username
						int idUser = userService.getIdbyUsername(username);
						int idQues = Integer.parseInt(IdQuestion);
						
						int execute = DictionaryService.updateDictionaryWhenDelete(idQues);
						DictionaryService.updateDeleteByAndDeleteDateWhenDelete(idQues, idUser);
						
						Users users = userService.getUserByUserID(idUser);
						Dictionary question = DictionaryService.getDictionaryByID(idQues);
						String newquestion = question.getQuestion();
						if(newquestion.length() > 50){
							newquestion.substring(0, 45);
							newquestion = newquestion + "...";
						}
						logger.info("Tài khoản " + users.getUserName() + " đã xóa câu hỏi "+newquestion);
						result = "success";
					}
					return result;
				}
				
				@RequestMapping(value="dictionary/edit/{username},{password},{IdQuestion},{question},{answer}", method=RequestMethod.GET)
				@Produces("application/xml")
				@ResponseBody
				public String editQuestion(
						@PathVariable("username")String username,
						@PathVariable("password")String password,
						@PathVariable("IdQuestion")String IdQuestion,
						@PathVariable("question")String question,
						@PathVariable("answer")String answer){
					String result = "fail";
					String login = uWS.checkLoginPresent(username, password);
					
					if(login.equals("success")){
						// get id from username
						int idUser = userService.getIdbyUsername(username);
						int idQues = Integer.parseInt(IdQuestion);
						
						Dictionary ques = DictionaryService.getDictionaryByID(idQues);
						if(ques.getCreateBy() != null){
							// Xu ly thao tac song song
							Users information = userService.getUserByUserID(idUser);
							int author = information.getAuthorization();
							if(idUser == ques.getCreateBy()){
								
								answer = util.restoreTags(answer);
								question = util.restoreTags(question);
								
								int execute = DictionaryService.updateQuesionAndAnwserDictionary(idQues, answer, question);
								int updatecreateby = DictionaryService.updateCreatebyWhenEdit(idQues, idUser);
								//int restart = DictionaryService.busystatus(idQues);
								Users users = userService.getUserByUserID(idUser);
								//Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
								Dictionary userquestion = DictionaryService.getDictionaryByID(idQues);
								String newquestion = userquestion.getQuestion();
								if(newquestion.length() > 50){
									newquestion.substring(0, 45);
									newquestion = newquestion + "...";
								}
								logger.info("Tài khoản " + users.getUserName() + " đã chỉnh sửa câu hỏi "+newquestion);
								result = "success";
							}else{
								if(author ==1){
									Users otheruser = userService.getUserByUserID(ques.getCreateBy());
									int otherauthor = otheruser.getAuthorization();
									if(otherauthor ==1){
										// null
										result = "confict,"+otheruser.getID();
									}else{
										//
										int execute = DictionaryService.updateQuesionAndAnwserDictionary(idQues, answer, question);
										int updatecreateby = DictionaryService.updateCreatebyWhenEdit(idQues, idUser);
									//	int restart = DictionaryService.busystatus(idQues);
										Users users = userService.getUserByUserID(idUser);
									//	Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
										Dictionary userquestion = DictionaryService.getDictionaryByID(idQues);
										String newquestion = userquestion.getQuestion();
										if(newquestion.length() > 50){
											newquestion.substring(0, 45);
											newquestion = newquestion + "...";
										}
										logger.info("Tài khoản " + users.getUserName() + " đã chỉnh sửa câu hỏi "+newquestion);
										result = "success";
									}
								}else{
									// null
									Users otheruser = userService.getUserByUserID(ques.getCreateBy());
									result = "confict,"+otheruser.getID();
								}
							}
							
							// ket thuc xu ly thao tac song song
						}else{
							//
							int execute = DictionaryService.updateQuesionAndAnwserDictionary(idQues, answer, question);
							int updatecreateby = DictionaryService.updateCreatebyWhenEdit(idQues, idUser);
							//int restart = DictionaryService.busystatus(idQues);
							
							Users users = userService.getUserByUserID(idUser);
							//Questionmanagement userquestion = QuestionmanagementService.getQuestionmanagementbyID(Id);
							Dictionary userquestion = DictionaryService.getDictionaryByID(idQues);
							String newquestion = userquestion.getQuestion();
							if(newquestion.length() > 50){
								newquestion.substring(0, 45);
								newquestion = newquestion + "...";
							}
							logger.info("Tài khoản " + users.getUserName() + " đã chỉnh sửa câu hỏi "+newquestion);
							result = "success";
						}
						
					}
					return result;
				}
				
				
}