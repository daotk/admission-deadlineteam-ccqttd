package deadlineteam.admission.quantritudien.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.omg.CORBA.Current;


public class FTPClientTransfer {

	 public static void ftp() {
		 String server = "10.11.27.12";
	        int port = 21;
	        String user = "anonymous";
	        String pass = "";
	        FTPClient ftpClient = new FTPClient();
	        try {
	            ftpClient.connect(server, port);
	            ftpClient.login(user, pass);
	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	   
	            
	            String workingDir = "E:/Source%20code%20Capstone/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/congcuquantritudien/upload/Questionmanagement";
	          
	              
	          
	            File directory = new File(workingDir);  
	            //get all the files from a directory
	            File[] fList = directory.listFiles();
	            int leng = fList.length;
	            
	            boolean success = ftpClient.changeWorkingDirectory("/congcuhienthitudien/index");
	          
	            if (success) {
	            	  for (int i= 0 ;i<leng;i++) {
	            		  
		            	    File firstLocalFile = new File(fList[i].getPath());
		            	  
		      	            String firstRemoteFile = fList[i].getName();
		      	            InputStream inputStream = new FileInputStream(firstLocalFile);
		      	 
		      	            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
		      	            inputStream.close();           	
	            	  }
	            } else {
	                System.out.println("Failed to change working directory. See server's reply.");
	            }
	        } catch (IOException ex) {
	            System.out.println("Error: " + ex.getMessage());
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	 
}
