package deadlineteam.admission.hienthitudien.webservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidUtil {
	// This fuctions restore all special characters
		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

		public static String restoreTags(String string) {
		    if (string == null || string.length() == 0) {
		        return string;
		    }
		    string = string.replace("%5C","\\");
		    string = string.replace("%7C","|");
		    string = string.replace("%5B","[");
		    string = string.replace("%5D","]");
		    string = string.replace("%22","\"");
		    string = string.replace("%2f","/");
		    string = string.replace("commas",",");
		    
		    Matcher m = REMOVE_TAGS.matcher(string);
		    return m.replaceAll("");
		}
}
