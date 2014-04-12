package deadlineteam.admission.quantritudien.dao.QuestionManagement;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getListQuestionmanagement() {
		return getCurrentSession().createQuery(" from Questionmanagement where Status = 1").list();
	}
	@SuppressWarnings("unchecked")
	public Questionmanagement getQuestionmanagementbyID(int Id) {
		// TODO Auto-generated method stub	
		Questionmanagement question =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where Status = 1 and ID = "+Id ).uniqueResult();
		return question;
	}
	//get question copy to dictionary
	@SuppressWarnings("unchecked")
	public Questionmanagement getQuestionmanagementbyIDToCopy(int Id) {
		// TODO Auto-generated method stub	
		Questionmanagement question =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where Status = 3 and ID = "+Id ).uniqueResult();
		return question;
	}
	
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> findpage1(String keyword) {
		// TODO Auto-generated method stub	
		return getCurrentSession().createQuery(" from Questionmanagement where Status = 3").list();
	}
	
	//private static final int limitResultsPerPage = 5;

	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getQuestionmanagementbyPage(int page , int UserID) {
	        Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where Status = 1 AND DeleteStatus = 0");
	         Setting settings = getSetting(UserID);
	         q.setFirstResult(page * settings.getRecordNotRep()); 
	         q.setMaxResults(settings.getRecordNotRep());
	         return (List<Questionmanagement>) q.list();
	}
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getQuestionmanagementbyPage_setting(int page, int record){
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
                "from Questionmanagement where Status = 1 AND DeleteStatus = 0");
         
         q.setFirstResult(page * record); 
         q.setMaxResults(record);
         return (List<Questionmanagement>) q.list();
	}
	//Luu tam cau tra loi
	public int SaveTemporaryAnswerbyId(int Id,String Answer){
		String sqlstring = "update Questionmanagement set Answer = :answer,  Status = '2' where ID = :Id AND DeleteStatus = 0";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("answer", Answer);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;
	}
	
	public int updateAnswerbyId(int Id,String Answer){
		String sqlstring = "update Questionmanagement set Answer = :answer,  Status = '3' where ID = :Id AND DeleteStatus = 0";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("answer", Answer);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;
	}
	/*Author:Phu Ta
	 * Delete question that is selected
	 */
	public int delete(int Id){
		String sqlstring = "update Questionmanagement set DeleteStatus = '1' where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;
		
	}
	//--------------------------Delete Page
	/*Author: Phu Ta
	 * Load delete question-list
	 */
	public Questionmanagement deletequestion(int Id){
		Questionmanagement deletestatus =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where DeleteStatus = 1 and ID = "+Id ).uniqueResult();
		return deletestatus;
	}
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> deleteList(int page,int UserID){
		   Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where DeleteStatus = 1 and DeleteBy ="+UserID);
		   Setting settings = getSetting(UserID);
	         q.setFirstResult(page * settings.getRecordDelete()); 
	         q.setMaxResults(settings.getRecordDelete());
	         return (List<Questionmanagement>) q.list();
	}

	/*Author: Phu Ta
	 * Restore question that is selected
	 */
	public int restore(int Id){
		String sqlstring = "update Questionmanagement set DeleteStatus = '0' where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;
		
	}
	/*
	 * Author: Phu Ta
	 * Load save-question list
	 */
	
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> savelist(int page,int UserID){
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where Status = 2 AND DeleteStatus = 0 and AnswerBy = "+UserID);
		 Setting settings = getSetting(UserID);
	         q.setFirstResult(page * settings.getRecordTemp()); 
	         q.setMaxResults(settings.getRecordTemp());
	         return (List<Questionmanagement>) q.list();	
	}
	public Questionmanagement savequestion(int Id){
		Questionmanagement savequestion =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where Status = 2 and ID = "+Id +" and DeleteStatus = 0" ).uniqueResult();
		return savequestion;
	}
	/*
	 * Author: Phu Ta
	 * delete save-question 
	 */
	public int deletesavequestion(int Id){
		String sqlstring = "update Questionmanagement set DeleteStatus = '1' where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;
	}
	/*
	 * Author: Phu Ta
	 * save save-question 
	 */
	public int SaveAnwser(int Id,String Answer){
		String sqlstring = "update Questionmanagement set Answer = :answer,  Status = '2' where ID = :Id AND DeleteStatus = 0";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("answer", Answer);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;		
	}
	/*
	 * Author: Phu Ta
	 * send save-question 
	 */
	public int SendAnwser(int Id,String Answer){
		String sqlstring = "update Questionmanagement set Answer = :answer,  Status = '3' where ID = :Id AND DeleteStatus = 0";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("answer", Answer);
		 q.setParameter("Id", Id);
		 int result= q.executeUpdate();
		return result;
		
	}
	
	/*
	 * Author: Chau Le
	 * Delete question-list page
	 */
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> repliedList(int page, int UserID){
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(
	                "from Questionmanagement where Status = 3 AND DeleteStatus = 0 and AnswerBy ="+UserID);
		 Setting settings = getSetting(UserID);
	         q.setFirstResult(page * settings.getRecordRepied()); 
	         q.setMaxResults(settings.getRecordRepied());
	         return (List<Questionmanagement>) q.list();	
	}
	
	/*
	 * Author: Chau Le
	 * load replied question-list
	 */
	public Questionmanagement repliedquestion(int ID){
		Questionmanagement savequestion =  (Questionmanagement)getCurrentSession().createQuery(" from Questionmanagement where Status = 3 and ID = "+ID +" and DeleteStatus = 0" ).uniqueResult();
		return savequestion;
	}
	
	/*
	 * Author: Chau Le
	 * delete replied question that is selected 
	 */
	public int deleterepliedquestion(int ID){
		String sqlstring = "update Questionmanagement set DeleteStatus = '1' where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", ID);
		 int result= q.executeUpdate();
		return result;
	}
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getListQuestionmanagementbyStatus(int status){
		return getCurrentSession().createQuery(" from Questionmanagement where Status = "+status+" AND DeleteStatus =0").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Questionmanagement> getListQuestionmanagementbyDeleteStatus(int status){
		return getCurrentSession().createQuery(" from Questionmanagement where DeleteStatus = "+status).list();
	}
	
	public void createIndex(){
		
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		try {
			fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Questionmanagement> searchIdex(String keyword,String Status){
		
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		
		QueryBuilder qb = fullTextSession.getSearchFactory()
			    .buildQueryBuilder().forEntity(Questionmanagement.class).get();
		if( Status.equals("1")){
			org.apache.lucene.search.Query luceneQuery = qb	
					.bool()
					
					.should(qb.phrase().onField("Title").andField("Answer").andField("Question").sentence(keyword).createQuery())
					.must( qb.keyword().onField("DeleteStatus").matching("1").createQuery()).not()
					.must( qb.keyword().onField("Status").matching("2").createQuery() ).not()
					.must( qb.keyword().onField("Status").matching("3").createQuery() ).not()
					
					.createQuery();
			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Questionmanagement.class);
			List<Questionmanagement> result = hibQuery.list();
			return result;
		}else{
			if( Status.equals("2")){
				org.apache.lucene.search.Query luceneQuery = qb	
						.bool()
						.should(qb.phrase().onField("Title").andField("Answer").andField("Question").sentence(keyword).createQuery())
						.must( qb.keyword().onField("DeleteStatus").matching("1").createQuery()).not()
						.must( qb.keyword().onField("Status").matching("3").createQuery() ).not()
						.must( qb.keyword().onField("Status").matching("1").createQuery() ).not()
						.must( qb.keyword().onField("DeleteStatus").matching("1").createQuery() ).not()
						.createQuery();
				org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Questionmanagement.class);
				List<Questionmanagement> result = hibQuery.list();
				return result;
			}else{
					org.apache.lucene.search.Query luceneQuery = qb	
							.bool()
							.should(qb.phrase().onField("Title").andField("Answer").andField("Question").sentence(keyword).createQuery())
							.must( qb.keyword().onField("DeleteStatus").matching("0").createQuery()).not()
							.createQuery();
					org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(luceneQuery, Questionmanagement.class);
					List<Questionmanagement> result = hibQuery.list();
					return result;
			}
		}

	}
	//-----------------------RESTful web service
	public void addquestion(Questionmanagement question){
		getCurrentSession().save(question);		
	}
	public void TransferToDictionary(int Id, int userid){
		String sqlstring = "update Questionmanagement set Status = '4', UpdateBy =:userid, UpdateDate =:now where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", userid);
		 Date now = new Date();
		 q.setParameter("now", now);
		 q.executeUpdate();
	}
	public void UpdateDelete(int Id, int userid){
		String sqlstring = "update Questionmanagement set DeleteBy =:userid, DeleteDate =:now where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", userid);
		 Date now = new Date();
		 q.setParameter("now", now);
		 q.executeUpdate();
	}
	public void UpdateAnwserBy(int Id, int userid){
		String sqlstring = "update Questionmanagement set AnswerBy =:userid, AnwserDate =:now where ID = :Id";
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("Id", Id);
		 q.setParameter("userid", userid);
		 Date now = new Date();
		 q.setParameter("now", now);
		 q.executeUpdate();
	}
	public Users getusername(int username){
		Users question =  (Users)getCurrentSession().createQuery(" from Users where ID = "+username ).uniqueResult();
		return question;
	}
	public Setting getSetting(int UserID){
		Setting temp =  (Setting)getCurrentSession().createQuery("from Setting where UserID = "+UserID ).uniqueResult();
		return temp;
	}

}
