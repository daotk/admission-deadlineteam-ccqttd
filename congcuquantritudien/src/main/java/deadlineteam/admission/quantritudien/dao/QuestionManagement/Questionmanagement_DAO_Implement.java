
package deadlineteam.admission.quantritudien.dao.QuestionManagement;

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

import deadlineteam.admission.quantritudien.domain.Dictionary;
import deadlineteam.admission.quantritudien.domain.Questionmanagement;
import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;



@Repository
public class Questionmanagement_DAO_Implement implements Questionmanagement_DAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Get All Question
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getAllQuestion() {
		return getCurrentSession().createQuery(" from Questionmanagement ").list();
	}
		
	/**
	 * Get All Question in Not Reply
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getListQuestionNotReply() {
		return getCurrentSession().createQuery(" from Questionmanagement where Status = 1").list();
	}
	
	/**
	 * Get Question by ID
	 * @param Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Questionmanagement getQuestionByID(int Id) {
		// TODO Auto-generated method stub	
		Questionmanagement question =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where ID = "+Id ).uniqueResult();
		return question;
	}
	

	
	/**
	 * Get Question By ID to Copy
	 * @param Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Questionmanagement getQuestionByIDToCopy(int Id) {
		// TODO Auto-generated method stub	
		Questionmanagement question =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where Status = 3 and ID = "+Id ).uniqueResult();
		return question;
	}

	/**
	 * Get Question Not Reply For User
	 * @param UserID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getQuestionNotReplyForUser(int UserID) {
	        Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	        		"from Questionmanagement where Status = 1 AND DeleteStatus = 0 AND (BusyStatus = 0 OR BusyStatus = "+UserID+")");        
	         return (List<Questionmanagement>) q.list();
	}
	
	/**
	 * Get Question Not Reply For Administrator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getQuestionNotReplyForAdmin() {
	        Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	        		"from Questionmanagement where Status = 1 AND DeleteStatus = 0 ");
	         return (List<Questionmanagement>) q.list();
	}
	
	/**
	 * Save Temporary
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int saveTemporaryQuestion(int Id,String Answer){
		String sqlstring = "update Questionmanagement set Answer = :answer,  Status = '2' where ID = :Id AND DeleteStatus = 0";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("answer", Answer);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
				 
		Questionmanagement question = getQuestionByID(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Questionmanagement.class,Id);
		fullTextSession.index(question);
		return result;
	}
	

	
	/**
	 * Update Answer
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int updateAnswer(int Id,String Answer){
		String sqlstring = "update Questionmanagement set Answer = :answer,  Status = '3' where ID = :Id AND DeleteStatus = 0";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("answer", Answer);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		 Questionmanagement question = getQuestionByID(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Questionmanagement.class,Id);
		fullTextSession.index(question);
			
		
		return result;
	}
	
	/**
	 * Delete Question
	 * @param Id
	 * @return
	 */
	public int deleteQuestion(int Id){
		String sqlstring = "update Questionmanagement set DeleteStatus = '1' where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		 Questionmanagement question = getQuestionByID(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Questionmanagement.class,Id);
		fullTextSession.index(question);
		return result;
		
	}
	
	/**
	 * Get Question Was Delete
	 * @param Id
	 * @return
	 */
	public Questionmanagement getDeletedQuestion(int Id){
		Questionmanagement deletestatus =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where DeleteStatus = 1 and ID = "+Id ).uniqueResult();
		return deletestatus;
	}
	
	/**
	 * Get List Question Deleted For User
	 * @param UserID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getListDeletedForUser(int UserID){
		   Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where DeleteStatus = 1 and DeleteBy ="+UserID);
		  
	         return (List<Questionmanagement>) q.list();
	}
	
	/**
	 * Get List Question Deleted For Administrator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getListDeletedForAdmin(){
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where DeleteStatus = 1");
		  
	         return (List<Questionmanagement>) q.list();
	}

	/**
	 * Restore Question
	 * @param Id
	 * @return
	 */
	public int restoreQuestion(int Id){
		String sqlstring = "update Questionmanagement set DeleteStatus = '0' where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		 
		 Questionmanagement question = getQuestionByID(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Questionmanagement.class,Id);
		fullTextSession.index(question);
		return result;
		
	}
	
	/**
	 * Get List Save Question For User
	 * @param UserID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getSaveListForUser(int UserID){
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where Status = 2 AND DeleteStatus = 0 and AnswerBy = "+UserID);
		
	         return (List<Questionmanagement>) q.list();	
	}

	/**
	 * Get List Save Question For Administrator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getSaveListForAdmin(){
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where Status = 2 AND DeleteStatus = 0");
		
	         return (List<Questionmanagement>) q.list();	
	}
	
	/**
	 * Get Save Question 
	 * @param Id
	 * @return
	 */
	public Questionmanagement getSaveQuestion(int Id){
		Questionmanagement savequestion =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where Status = 2 and ID = "+Id +" and DeleteStatus = 0" ).uniqueResult();
		return savequestion;
	}
	
	/**
	 * Update When Send Answer
	 * @param Id
	 * @param Answer
	 * @return
	 */
	public int updateQuestionWhenSendAnwser(int Id,String Answer){
		String sqlstring = "update Questionmanagement set Answer = :answer,  Status = '3' where ID = :Id AND DeleteStatus = 0";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("answer", Answer);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		 Questionmanagement question = getQuestionByID(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Questionmanagement.class,Id);
		fullTextSession.index(question);
		return result;
	}
	
	/**
	 * Update DeleteBy And DeleteDate
	 * @param ID
	 * @return
	 */
	public int updateDeleteByAndDeleteDate(int ID){
		String sqlstring = "update Questionmanagement set DeleteBy = :delete,  DeleteDate =:date where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("delete", null);
		 q.setParameter("date", null);
		 q.setParameter("Id", ID);
		 int result= q.executeUpdate();
		 
		return result;
	}
	
	/**
	 * Get List Replied Question For User
	 * @param UserID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getRepliedListForUser(int UserID){
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where Status = 3 AND DeleteStatus = 0 and AnswerBy ="+UserID);
		
	         return (List<Questionmanagement>) q.list();	
	}
	
	/**
	 * Get List Replied Question For Administrator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getRepliedListForAdmin(){
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where Status = 3 AND DeleteStatus = 0");
		
	         return (List<Questionmanagement>) q.list();	
	}
	
	
	/**
	 * Get Reply Question
	 * @param ID
	 * @return
	 */
	public Questionmanagement getRepliedQuestion(int ID){
		Questionmanagement savequestion =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where ID = "+ID +" and DeleteStatus = 0" ).uniqueResult();
		return savequestion;
	}

	/**
	 * Get List Question By Status
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getListQuestionbyStatus(int status){
		return getCurrentSession().createQuery(" from Questionmanagement where Status = "+status+" AND DeleteStatus =0").list();
	}
	
	/**
	 * Get List Question By Delete Status
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getListQuestionByDeleteStatus(int status){
		return getCurrentSession().createQuery(" from Questionmanagement where DeleteStatus = "+status).list();
	}
	
	/**
	 * Create Index
	 */
	public void createIndex(){
		
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		try {
			fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Search index
	 * @param keyword
	 * @param Status
	 * @return
	 */
	public List<Questionmanagement> searchIdex(String keyword,String Status){
		
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		
		QueryBuilder qb = fullTextSession.getSearchFactory()
			    .buildQueryBuilder().forEntity(Questionmanagement.class).get();
		if( Status.equals("1")){
			org.apache.lucene.search.Query luceneQuery = qb	
					.bool()
					
					.should(qb.phrase().onField("Answer").andField("Question").sentence(keyword).createQuery())
					.must( qb.keyword().onField("DeleteStatus").matching("1").createQuery()).not()
					.must( qb.keyword().onField("Status").matching("2").createQuery() ).not()
					.must( qb.keyword().onField("Status").matching("3").createQuery() ).not()
					.must( qb.keyword().onField("Status").matching("4").createQuery() ).not()
					.createQuery();
			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Questionmanagement.class);
			List<Questionmanagement> result = hibQuery.list();
			return result;
		}else{
			if( Status.equals("2")){
				org.apache.lucene.search.Query luceneQuery = qb	
						.bool()
						.should(qb.phrase().onField("Answer").andField("Question").sentence(keyword).createQuery())
						.must( qb.keyword().onField("DeleteStatus").matching("1").createQuery()).not()
						.must( qb.keyword().onField("Status").matching("3").createQuery() ).not()
						.must( qb.keyword().onField("Status").matching("1").createQuery() ).not()
						.must( qb.keyword().onField("Status").matching("4").createQuery() ).not()
						.createQuery();
				org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Questionmanagement.class);
				List<Questionmanagement> result = hibQuery.list();
				return result;
			}else{
				if( Status.equals("3")){
					org.apache.lucene.search.Query luceneQuery = qb	
							.bool()
							.should(qb.phrase().onField("Answer").andField("Question").sentence(keyword).createQuery())
							.must( qb.keyword().onField("DeleteStatus").matching("1").createQuery()).not()
							.must( qb.keyword().onField("Status").matching("1").createQuery() ).not()
							.must( qb.keyword().onField("Status").matching("2").createQuery() ).not()
							.must( qb.keyword().onField("Status").matching("4").createQuery() ).not()
							.createQuery();
					org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Questionmanagement.class);
					List<Questionmanagement> result = hibQuery.list();
					return result;
				}else{
					org.apache.lucene.search.Query luceneQuery = qb	
							.bool()
							.should(qb.phrase().onField("Answer").andField("Question").sentence(keyword).createQuery())
							.must( qb.keyword().onField("DeleteStatus").matching("0").createQuery()).not()
							.createQuery();
					org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Questionmanagement.class);
					List<Questionmanagement> result = hibQuery.list();
					return result;
				}
					
			}
		}

	}
	
	/**
	 * Add Question (RESTfull)
	 * @param question
	 */
	public void addQuestion(Questionmanagement question){
		getCurrentSession().save(question);	
		FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.index(question);
		
	}
	
	/**
	 * Transfer Question to Dictionary
	 * @param Id
	 * @param userid
	 */
	public void copyQuestionToDictionary(int Id, int userid){
		String sqlstring = "update Questionmanagement set Status = '4', UpdateBy =:userid, UpdateDate =:now where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", userid);
		 Date now = new Date();
		 q.setParameter("now", now);
		 q.executeUpdate();
		 
		 Questionmanagement question = getQuestionByID(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		 fullTextSession.purge( Questionmanagement.class,Id);
		 fullTextSession.index(question);
	}
	
	/**
	 * Update DeleteBy and DeleteDate
	 * @param Id
	 * @param userid
	 */
	public void updateDeleteByAndDeleteDate(int Id, int userid){
		String sqlstring = "update Questionmanagement set DeleteBy =:userid, DeleteDate =:now where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", userid);
		 Date now = new Date();
		 q.setParameter("now", now);
		 q.executeUpdate();
		 
		 Questionmanagement question = getQuestionByID(Id);
		 FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Questionmanagement.class,Id);
		fullTextSession.index(question);
	}
	
	/**
	 * Update AnswerBy And AnswerDate
	 * @param Id
	 * @param userid
	 */
	public void updateAnwserByAndAnwserDate(int Id, int userid){
		String sqlstring = "update Questionmanagement set AnswerBy =:userid, AnwserDate =:now where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", userid);
		 Date now = new Date();
		 q.setParameter("now", now);
		 q.executeUpdate();
	}
		
	/**
	 * Get Setting
	 * @param Id
	 * @return
	 */
	public Setting getSetting(int UserID){
		Setting temp =  (Setting)getCurrentSession().createQuery("from Setting where UserID = "+UserID ).uniqueResult();
		return temp;
	}
	
	/**
	 * Update Busy Status when click question
	 * @param Id
	 * @param UserId
	 */
	public void updateBusyStatusQuestion(int Id, int UserId){
		String sqlstring = "update Questionmanagement set BusyStatus = :userid where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", UserId);
		 q.executeUpdate();
	}
	
	/**
	 * Update Busy Status when lick another question
	 * @param Id
	 * @param UserId
	 */
	public void resetBusyStatusQuestion(int Id, int UserId){	
		String sqlstring = "update Questionmanagement set BusyStatus = 0 where BusyStatus = :userid";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("userid", UserId);
		 q.executeUpdate();
	}
	
}
