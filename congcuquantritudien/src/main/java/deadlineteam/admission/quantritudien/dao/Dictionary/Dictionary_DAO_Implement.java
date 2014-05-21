package deadlineteam.admission.quantritudien.dao.Dictionary;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import deadlineteam.admission.quantritudien.dao.User.Users_DAO;
import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;
import deadlineteam.admission.quantritudien.service.User.Users_SERVICE;

@Repository
public class Dictionary_DAO_Implement implements Dictionary_DAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private Users_DAO userdao;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	
	public void AddDictionary(Dictionary dictionary) {
		getCurrentSession().save(dictionary);
	}
	private static final int limitResultsPerPage = 5;
	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> availablelist(int page , int UserID) {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
                "from Dictionary where Status = 1 and DeleteStatus = 0 and CreateBy ="+ UserID);

         return (List<Dictionary>) q.list();
	}
	@SuppressWarnings("unchecked")
	public List<Dictionary> availablelistadmin(int page , int UserID) {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
                "from Dictionary where Status = 1 and DeleteStatus = 0");

         return (List<Dictionary>) q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionaryAvailable() {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(" from Dictionary where Status = 1  and DeleteStatus = 0");
         return (List<Dictionary>) q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionaryDeleted() {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(" from Dictionary where DeleteStatus = 1");
         return (List<Dictionary>) q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionaryUp() {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(" from Dictionary where Status = 2  and DeleteStatus = 0");
         return (List<Dictionary>) q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionaryDown() {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(" from Dictionary where DeleteStatus = 4  and DeleteStatus = 0");
         return (List<Dictionary>) q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionary() {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(" from Dictionary ");
         return (List<Dictionary>) q.list();
	}

	@Override
	public Dictionary availablequestion(int Id) {
		// TODO Auto-generated method stub
		Dictionary avaiablequestion =  (Dictionary)getCurrentSession().createQuery(" from Dictionary where Status = 1 and ID = "+Id+" and DeleteStatus = 0"  ).uniqueResult();
		return avaiablequestion;
	}
	
	public Dictionary loadquestion(int Id) {
		// TODO Auto-generated method stub
		Dictionary avaiablequestion =  (Dictionary)getCurrentSession().createQuery(" from Dictionary where ID = "+Id+" and DeleteStatus = 0"  ).uniqueResult();
		return avaiablequestion;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> recentlist(int page, int UserID) {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
                "from Dictionary where Status = 2 and DeleteStatus = 0 ");
        
         return (List<Dictionary>) q.list();
	}

	@Override
	public Dictionary recentquestion(int Id) {
		// TODO Auto-generated method stub
		Dictionary recentquestion =  (Dictionary)getCurrentSession().createQuery(" from Dictionary where Status = 2 and ID = "+Id+" and DeleteStatus = 0"  ).uniqueResult();
		return recentquestion;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> deletelist(int page, int UserID) {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
                "from Dictionary where DeleteStatus = 1 ");
       
         return (List<Dictionary>) q.list();
	}

	@Override
	public Dictionary question(int Id) {
		// TODO Auto-generated method stub
		Dictionary recentquestion =  (Dictionary)getCurrentSession().createQuery(" from Dictionary where ID = "+Id  ).uniqueResult();
		return recentquestion;
	}
	@Override
	public Dictionary getinformation(int ID){
		Dictionary information = (Dictionary)getCurrentSession().createQuery(" from Dictionary where ID = "+ID  ).uniqueResult();
		return information;
	}
	@Override
	public int updateCreateby(int Id, int UserID){
		String sqlstring = "update Dictionary set CreateBy =:userid , CreateDate =:mow where ID = :Id";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		q.setParameter("Id", Id);
		q.setParameter("userid", UserID);
		Date now = new Date();
		q.setParameter("mow", now);
		int result = q.executeUpdate();
		
		return result;
	}
	
	@Override
	public int upload(int Id){
		String sqlstring = "update Dictionary set Status = '2' where ID = :Id AND DeleteStatus = 0";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		q.setParameter("Id", Id);
		int result = q.executeUpdate();
		
		 Dictionary question = getinformation(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Dictionary.class,Id);
		fullTextSession.index(question);
		return result;
	}
	@Override
	public int updateby(int Id, int UserID){
		String sqlstring = "update Dictionary set UpdateBy =:userid, UpdateDate =:now where ID = :Id AND DeleteStatus = 0";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		q.setParameter("userid", UserID);
		q.setParameter("Id", Id);
		Date now = new Date();
		q.setParameter("now", now);
		int result = q.executeUpdate();
		return result;
	}
	@Override
	public int remove(int Id){
		String sqlstring = "update Dictionary set Status = '4' where ID = :Id AND DeleteStatus = 0";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		q.setParameter("Id", Id);
		int result = q.executeUpdate();
		Dictionary question = getinformation(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Dictionary.class,Id);
		fullTextSession.index(question);
		return result;
	}
	@Override
	public int  restore(int Id){
		String sqlstring = "update Dictionary set DeleteStatus = '0' where ID = :Id";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		q.setParameter("Id", Id);
		int result = q.executeUpdate();
		
		Dictionary question = getinformation(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Dictionary.class,Id);
		fullTextSession.index(question);
		return result;
	}
	@Override
	public int delete(int Id){
		String sqlstring = "update Dictionary set DeleteStatus = '1' where ID = :Id";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		q.setParameter("Id", Id);
		int result = q.executeUpdate();
		
		Dictionary question = getinformation(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Dictionary.class,Id);
		fullTextSession.index(question);
		return result;
	}
	@Override
	public void addDictionaryAnswer(String title, String question, String answer)
	{		
		//String sqlstring = "update Questionmanagement set Title = :title, Question = :question, Answer = :answer where ID = '5'";		
		String sqlstring = "INSERT INTO Questionmanagement (ID, Title, Question, Answer) VALUES (33,'"+title+"', '"+question+"','"+answer+"')";
		Query q = (Query) sessionFactory.getCurrentSession().createSQLQuery(sqlstring);
		q.executeUpdate();
	}
	
	@Override
	public void addDictionaryAnswer2(String title, String question,int createby, String answer, int answerby,Date CreateDate, int status, int deletestatus,int busystatus)
	{		
		String sqlstring1 = "INSERT INTO dictionary (Title, Question, CreateBy, Anwser, AnwserBy, CreateDate, Status, DeleteStatus) VALUES ('"+title+"', '"+question+"', "+createby+", '"+answer+"',"+answerby+", CURDATE(), "+status+", "+deletestatus+", "+busystatus+");";
		Query q = (Query) sessionFactory.getCurrentSession().createSQLQuery(sqlstring1);
		q.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> removelist(int page, int UserID){
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
                "from Dictionary where Status = 4 and DeleteStatus = 0");
         
        
         return (List<Dictionary>) q.list();
	}
	@Override
	public Dictionary removequestion(int Id){
		Dictionary removequestion =  (Dictionary)getCurrentSession().createQuery(" from Dictionary where Status = 4 and ID = "+Id+" and DeleteStatus = 0"  ).uniqueResult();
		return removequestion;
	}
	@Override
	public int update(int Id,String Anwser,  String Question){
		String sqlstring = "update Dictionary set Anwser = :anwser, Question =:question where ID = :Id ";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("anwser", Anwser);	
		 q.setParameter("question", Question);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;
	}
	public int busystatusupdate(int Id){
		String sqlstring = "update Dictionary set BusyStatus =:busy where ID = :Id ";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("busy", 1);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;
	}
	public int busystatus(int Id){
		String sqlstring = "update Dictionary set BusyStatus =:busy where ID = :Id ";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("busy", 0);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;
	}
	public void updateRemove(int Id, int userID){
		String sqlstring = "update Dictionary set UpdateBy =:userid, UpdateDate =:now where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", userID);
		 Date now = new Date();
		 q.setParameter("now", now);
		 q.executeUpdate();
	}
	public void updatedelete(int Id, int userID){
		String sqlstring = "update Dictionary set DeleteBy =:userid, DeleteDate =:now where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", userID);
		 Date now = new Date();
		 q.setParameter("now", now);
		 q.executeUpdate();
	}
	public int updaterestore(int Id){
		String sqlstring = "update Dictionary set DeleteBy =:userid, DeleteDate =:now where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", null);
		
		 q.setParameter("now", null);
		 int result = q.executeUpdate();
		 return result;
	}
	public Users getusername(int ID){
		Users username =  (Users)getCurrentSession().createQuery(" from Users where ID = "+ID  ).uniqueResult();
		return username;
	}
	
public List<Dictionary> searchIdex(String keyword,String Status, int UserID){
		
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		
		QueryBuilder qb = fullTextSession.getSearchFactory()
			    .buildQueryBuilder().forEntity(Dictionary.class).get();
		Users users = userdao.getUser(UserID);
		
			if( Status.equals("1")){
				org.apache.lucene.search.Query luceneQuery = qb	
						.bool()
						
						.should(qb.phrase().onField("Anwser").andField("Question").sentence(keyword).createQuery())
						.must( qb.keyword().onField("DeleteStatus").matching("1").createQuery()).not()
						.must( qb.keyword().onField("Status").matching("2").createQuery() ).not()
						.must( qb.keyword().onField("Status").matching("4").createQuery() ).not()
						//.must( qb.keyword().onField("AnwserBy").matching(""+UserID).createQuery())
						.createQuery();
				org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Dictionary.class);
				List<Dictionary> result = hibQuery.list();
				return result;
			}else{
				if( Status.equals("2")){
					org.apache.lucene.search.Query luceneQuery = qb	
							.bool()
							.should(qb.phrase().onField("Anwser").andField("Question").sentence(keyword).createQuery())
							.must( qb.keyword().onField("DeleteStatus").matching("1").createQuery()).not()
							.must( qb.keyword().onField("Status").matching("1").createQuery() ).not()
							.must( qb.keyword().onField("Status").matching("4").createQuery() ).not()
						//	.must( qb.keyword().onField("UpdateBy").matching(""+UserID).createQuery())
							.createQuery();
					org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Dictionary.class);
					List<Dictionary> result = hibQuery.list();
					return result;
				}else{
					if( Status.equals("3")){
						org.apache.lucene.search.Query luceneQuery = qb	
								.bool()
								.should(qb.phrase().onField("Anwser").andField("Question").sentence(keyword).createQuery())
								.must( qb.keyword().onField("DeleteStatus").matching("1").createQuery()).not()
								.must( qb.keyword().onField("Status").matching("1").createQuery() ).not()
								.must( qb.keyword().onField("Status").matching("2").createQuery() ).not()
							//	.must( qb.keyword().onField("UpdateBy").matching(""+UserID).createQuery())
								.createQuery();
						org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Dictionary.class);
						List<Dictionary> result = hibQuery.list();
						return result;
					}else{
						org.apache.lucene.search.Query luceneQuery = qb	
								.bool()
								.should(qb.phrase().onField("Anwser").andField("Question").sentence(keyword).createQuery())
								.must( qb.keyword().onField("DeleteStatus").matching("0").createQuery()).not()
							//	.must( qb.keyword().onField("DeleteBy").matching(""+UserID).createQuery())
								.createQuery();
						org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Dictionary.class);
						List<Dictionary> result = hibQuery.list();
						return result;
					}
						
				}
			}
		
		
		

	}
}
