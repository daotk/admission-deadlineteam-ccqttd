package deadlineteam.admission.quantritudien.webservice;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import deadlineteam.admission.quantritudien.service.QuestionManagement.Questionmanagement_SERVICE;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;
import deadlineteam.admission.quantritudien.util.AeSimpleMD5;

public class AndroidUtil {
	// This fuctions restore all special characters
		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
		@Autowired
		private static Users_SERVICE userService;
		public static String restoreTags(String string) {
		    if (string == null || string.length() == 0) {
		        return string;
		    }
//		    string = string.replace("%5C","\\");
//		    string = string.replace("%7C","|");
//		    string = string.replace("%5B","[");
//		    string = string.replace("%5D","]");
//		    string = string.replace("%22","\"");
//		    string = string.replace("%2f","/");
		    string = string.replace("commas",",");
		    string = string.replace("enter","<br/>");
		    
//		    Matcher m = REMOVE_TAGS.matcher(string);
//		    return m.replaceAll("");
		    return string;
		}
		
		
}
