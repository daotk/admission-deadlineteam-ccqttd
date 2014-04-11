package deadlineteam.admission.quantritudien.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPClientTransfer {
	 public static void ftp() {
		 String server = "31.170.165.128";
	        int port = 21;
	        String user = "u898831271.testfpt";
	        String pass = "lKeIDZXnO6";
	        FTPClient ftpClient = new FTPClient();
	        try {
	            ftpClient.connect(server, port);
	            ftpClient.login(user, pass);
	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	            
	            File directory = new File("E:/tmp/lucene/indexes/Questionmanagement/");
	            
	            //get all the files from a directory
	            File[] fList = directory.listFiles();
	            int leng = fList.length;
	            
	            
	            ftpClient.changeWorkingDirectory("/index");
	          // FTPFile[] ftpFiles = ftpClient.listFiles("E:/tmp/lucene/indexes/Questionmanagement/_a.fdt");
	           

	          //  if (ftpFiles != null && ftpFiles.length > 0) {
	            	  for (int i= 0 ;i<leng;i++) {
	            		  
		            	    File firstLocalFile = new File(fList[i].getPath());
		            	  
		      	            String firstRemoteFile = fList[i].getName();
		      	            InputStream inputStream = new FileInputStream(firstLocalFile);
		      	 
		      	            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
		      	            inputStream.close();
		      	            if (done) {
		      	                System.out.println("The "+fList[i].getName() +" is uploaded successfully.");
		      	            }
	            	
	            		  
	            	  }
	            	
	            	
	          //  }
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
