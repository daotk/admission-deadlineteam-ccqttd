package deadlineteam.admission.hienthitudien.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import deadlineteam.admission.hienthitudien.domain.Dictionary;


@Repository
public class DictionaryDAO_Implement implements DictionaryDAO{
	@Autowired
	private SessionFactory sessionFactory;
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> getall() {
		// TODO Auto-generated method stub
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
                "from Dictionary ");
         return (List<Dictionary>) q.list();
	}
	@SuppressWarnings("unchecked")
	public List<Dictionary> getalldictionary(int page, int record){
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
                "from Dictionary");
         
         return (List<Dictionary>) q.list();
	}

	public List<Dictionary> searchIdex(String keyword){
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		
		QueryBuilder qb = fullTextSession.getSearchFactory()
			    .buildQueryBuilder().forEntity(Dictionary.class).get();
		
			org.apache.lucene.search.Query luceneQuery = qb
					.bool()
					.should(qb.phrase().onField("Question").andField("Answer").sentence(keyword).createQuery())
					
					.createQuery();
			

			// wrap Lucene query in a javax.persistence.Query
			org.hibernate.Query hibQuery = 
				    fullTextSession.createFullTextQuery(luceneQuery, Dictionary.class);
			// execute search
			List<Dictionary> result = hibQuery.list();
			return result;
	}
	public void updatequestion(Dictionary dictionary){
		getCurrentSession().save(dictionary);
		
		FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.index(dictionary);
	}
	public void deleteUser(Dictionary dictionary){
		if (dictionary != null)
			getCurrentSession().delete(dictionary);
		
		FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
		fullTextSession.purge( Dictionary.class,dictionary.getID());
	}
	
	
	
	
	
	
	
}
