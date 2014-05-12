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

	 public static String ftp() {
		 	String server = "10.11.27.12";
	        int port = 21;
	        String user = "anonymous";
	        String pass = "";
	        FTPClient ftpClient = new FTPClient();
	        try {
	            ftpClient.connect(server, port);
	            ftpClient.login(user, pass);
	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
	   
	            
	            String workingDir = "E:/Source%20code%20Capstone/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/congcuquantritudien/upload/Dictionary";
	          
	              
	          
	            File directory = new File(workingDir);  
	            //get all the files from a directory
	            File[] fList = directory.listFiles();
	            int leng = fList.length;
	            
	            boolean success = ftpClient.changeWorkingDirectory("/congcuhienthitudien/index/upload");
	            int icount =0;
	            if (success) {
	            	String mess;
	            	 String firstRemoteFile1 = null;
	            	 InputStream inputStream1 = null;
	            	  for (int i= 0 ;i<leng;i++) {
	            		  
		            	    File firstLocalFile = new File(fList[i].getPath());
		            	  
		      	            String firstRemoteFile = fList[i].getName();
		      	            InputStream inputStream = new FileInputStream(firstLocalFile);
		      	            if(!firstRemoteFile.equals("write.lock")){
		      	              boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			      	            inputStream.close();
			      	          if (done) {
				                     icount++;
				                  }
		      	            }else{
		      	            	firstRemoteFile1 = fList[i].getName();
		      	            	inputStream1 = new FileInputStream(firstLocalFile);
		      	            }
	            	  }
	            	  /*
	            	  if(firstRemoteFile1!=null){
	            		  boolean done = ftpClient.storeFile(firstRemoteFile1, inputStream1);
	            		  if(done){
	            			  icount++;
	            		  }
	            	  }*/
	            	  if(icount==leng-1){
	            		  mess= "susses";
	            	  }else {
	            		  mess= "NotEnoughFile";
	            	  }
	            	  return mess;
	            } else {
	                return "NotChangeWorkingDirectory";
	            }
	        } catch (IOException ex) { 
	        	System.out.println("Error: " + ex.getMessage());
	            ex.printStackTrace();
	            return "Error";
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
