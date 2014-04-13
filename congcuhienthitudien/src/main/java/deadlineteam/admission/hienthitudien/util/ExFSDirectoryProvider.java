package deadlineteam.admission.hienthitudien.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.lucene.store.FSDirectory;
import org.hibernate.search.SearchException;
import org.hibernate.search.indexes.impl.DirectoryBasedIndexManager;
import org.hibernate.search.spi.BuildContext;
import org.hibernate.search.store.DirectoryProvider;
import org.hibernate.search.store.impl.DirectoryProviderHelper;
import org.hibernate.search.store.impl.FSDirectoryProvider;

/**
 * #Hibernate Configurationé…�ç½®æ–‡ä»¶
 * hibernate.search.default.directory_provider=com.oa166.hibernate.search.store.impl.ExFSDirectoryProvider
 * #æ”¾äºŽåº”ç”¨/upload/indexåŒ…ä¸‹
 * hibernate.search.default.indexBase=classpath:/upload/index  
*/


public class ExFSDirectoryProvider implements DirectoryProvider<FSDirectory>{
    
    private static final String CLASSPATH = "classpath:";
    protected Logger log = Logger.getLogger(ExFSDirectoryProvider.class); 
    
    private FSDirectory directory;
    private String indexName;
    
    @Override
    public FSDirectory getDirectory() {
        return this.directory;
    }
    
    @Override
    public void start(DirectoryBasedIndexManager indexManager) {
        
    }

    @Override
    public void stop() {
        try {
            this.directory.close();
          }
          catch (Exception e) {
            log.warn("can not close directory", e);
          }
    }
    
    @Override
    public void initialize(String directoryProviderName, Properties properties, BuildContext context) {
        boolean manual = context.getIndexingStrategy().equals("manual");
        /**
         * ä¸»è¦�æ”¹å†™äº†è¿™ä¸ªæ–¹æ³• File indexDir = org.hibernate.search.store.impl.DirectoryProviderHelper.DirectoryProviderHelper.getVerifiedIndexDir(directoryProviderName, properties, !(manual));
         */
        File indexDir = getVerifiedIndexDir(directoryProviderName, properties, !(manual));
        try {
          //è¿”å›žæŠ½è±¡è·¯å¾„å��çš„è§„èŒƒè·¯å¾„å��å­—ç¬¦ä¸²ã€‚
          this.indexName = indexDir.getCanonicalPath();

          this.directory = DirectoryProviderHelper.createFSIndex(indexDir, properties);
        }
        catch (IOException e) {
          throw new SearchException("Unable to initialize index: " + directoryProviderName, e);
        }
    }
    
    private File getVerifiedIndexDir(String annotatedIndexName, Properties properties, boolean verifyIsWritable)
    {
      String indexBase = properties.getProperty("indexBase", ".");
      /**
       * å¾—åˆ°å­˜ç´¢å¼•çš„è·¯å¾„.è‡³äºŽæ”¾åœ¨å“ªä¸ªä½�ç½®å°±ç”±ä½ è‡ªå·±æ“�ä½œ
       */
      indexBase = getIndexBaseByIsRelative(indexBase);
      
      String indexName = properties.getProperty("indexName", annotatedIndexName);
      File baseIndexDir = new File(indexBase);
      makeSanityCheckedDirectory(baseIndexDir, indexName, verifyIsWritable);
      File indexDir = new File(baseIndexDir, indexName);
      makeSanityCheckedDirectory(indexDir, indexName, verifyIsWritable);
      return indexDir;
    }
    
    private String getIndexBaseByIsRelative(String indexBase){
        if( indexBase.contains(CLASSPATH) ){
            String path = ExFSDirectoryProvider.class.getResource("/").toString();
            path = path.substring( path.indexOf("/") + 1 , path.lastIndexOf("/WEB-INF"));
            return indexBase = path + indexBase.substring( indexBase.indexOf(":") + 1 );
        }else{
            return indexBase;
        }
    }
    
    //éªŒè¯�ç›®å½•å­˜åœ¨å¹¶ä¸”æ˜¯å�¯å†™ï¼Œæˆ–åˆ›å»ºå®ƒå¦‚æžœä¸�å­˜åœ¨.
    private void makeSanityCheckedDirectory(File directory, String indexName, boolean verifyIsWritable)
    {
      if (!(directory.exists())) {
        log.info("ç›®å½•ä¸�å­˜åœ¨,åˆ›å»ºæ–°ç›®å½•:" + directory.getAbsolutePath() );

        if (directory.mkdirs()) {
            return;   //break label116;
        }
        throw new SearchException(new StringBuilder().append("Unable to create index directory: ").append(directory.getAbsolutePath()).append(" for index ").append(indexName).toString());
      }

      if (!(directory.isDirectory())) {
        throw new SearchException(new StringBuilder().append("Unable to initialize index: ").append(indexName).append(": ").append(directory.getAbsolutePath()).append(" is a file.").toString());
      }

      if ((verifyIsWritable) && (!(directory.canWrite())))
        //label116: 
        throw new SearchException(new StringBuilder().append("Cannot write into index directory: ").append(directory.getAbsolutePath()).append(" for index ").append(indexName).toString());
    }

    public boolean equals(Object obj)
    {
      if (obj == this)
        return true;

      if ((obj == null) || (!(obj instanceof FSDirectoryProvider)))
        return false;

      return this.indexName.equals(((ExFSDirectoryProvider)obj).indexName);
    }

    public int hashCode()
    {
      int hash = 11;
      return (37 * hash + this.indexName.hashCode());
    }
}