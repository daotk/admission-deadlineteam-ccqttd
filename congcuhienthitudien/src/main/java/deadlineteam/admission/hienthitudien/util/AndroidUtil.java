package deadlineteam.admission.hienthitudien.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidUtil {
	// This fuctions restore all special characters
		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

		public static String restoreTags(String string) {
		    if (string == null || string.length() == 0) {
		        return string;
		    }

		    string = string.replace("commas",",");
		    string = string.replace("enter","<br/>");
		    
		    return string;
		}
}
