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
import deadlineteam.admission.quantritudien.entities.UserEntity;
import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.util.AeSimpleMD5;


@Controller
@RequestMapping("android")
public class UserWS {
	
	@Autowired
	private Users_SERVICE userService;

	
	@Autowired
	private Questionmanagement_SERVICE quesSer;
	
	@Autowired
	private JavaMailSender mailSender;
	
	// This function check username and password from android
	@RequestMapping(value="login/{username},{password}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public String checkLogin(@PathVariable("username")String username,
			@PathVariable("password")String password){
		String passmd5 ="";
		try {
			passmd5 = AeSimpleMD5.MD5(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String checklogin = userService.checkLogin(username, passmd5);
		String result = "fail";
		if(checklogin.equals("Right")){
			
			int idUser = userService.getIdbyUsername(username);
			
			Users information = userService.getUser(idUser);
			int author = information.getAuthorization();
			if(author == 1){
				result = "success," + "1";
			}else{
				result = "success," + "0";
			}
		}else{
			if(checklogin.equals("WrongPass")){
				result = "wrongpass";	
			}else{
				result = "fail";
			}
		}
		return result;
	}
	
	

	// This function return user information by id
	@RequestMapping(value="getUser/{username},{password},{idUser}", method=RequestMethod.GET)
	@Produces("application/xml")
	@ResponseBody
	public UserEntity getUser(
			@PathVariable("username")String username,
			@PathVariable("password")String password,
			@PathVariable("idUser")String idUser){
		
		String result = checkLoginPresent(username, password);
		// declare new user entity
					UserEntity us = new UserEntity();
		if(result.equals("success")){
			
			// get id from username
			int id = Integer.parseInt(idUser);
			// get user entity from id
			List<Users> temp = userService.getUserDetail(id);
			us.ID = temp.get(0).getID();
			us.FullName = temp.get(0).getFullName();
			us.Authorization = temp.get(0).getAuthorization();
		}
		return us;
	}
	// This fuction return result user valid or invalid
	public String checkLoginPresent(String username, String password){
		String passmd5 ="";
		try {
			passmd5 = AeSimpleMD5.MD5(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String checklogin = userService.checkLogin(username, passmd5);
		
		String result = "fail";
		if(checklogin.equals("Right")){
			result = "success";
		}
		return result;
	}
	
}