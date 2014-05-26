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
import deadlineteam.admission.quantritudien.domain.Users;

@Repository
public class Dictionary_DAO_Implement implements Dictionary_DAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private Users_DAO userdao;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	* Add dictionary to database *
	* @param dictionary 
	*/
	public void AddDictionary(Dictionary dictionary) {
		getCurrentSession().save(dictionary);
	}
	
	/**
	* Get List Available Dictionary for UserID *
	* @param UserID {@link Integer}
	* @return {@link List}  List Available Dictionary for UserID
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> getAvailableListDictionaryForUser(int UserID) {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				"from Dictionary where Status = 1 and DeleteStatus = 0 and CreateBy ="+ UserID);
		return (List<Dictionary>) q.list();
	}

	/**
	 * Get Available List For Administrator *
	 * @return {@link List} List Available Dictionary for Administrator
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAvailableListForAdministrator() {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				"from Dictionary where Status = 1 and DeleteStatus = 0");

		return (List<Dictionary>) q.list();
	}

	/**
	 * Get All Available List Dictionary
	 * @return {@link List} List Available
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionaryAvailable() {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				" from Dictionary where Status = 1  and DeleteStatus = 0");
		return (List<Dictionary>) q.list();
	}

	/**
	 * Get All Deleted List Dictionary
	 * @return {@link List} List Deleted
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionaryDeleted() {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				" from Dictionary where DeleteStatus = 1");
		return (List<Dictionary>) q.list();
	}

	/**
	 * Get All Recent List Dictionary
	 * @return {@link List} List Recent
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionaryRecent() {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				" from Dictionary where Status = 2  and DeleteStatus = 0");
		return (List<Dictionary>) q.list();
	}

	/**
	 * Get All Down List Dictionary
	 * @return {@link List} List Down
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionaryDown() {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				" from Dictionary where Status = 4  and DeleteStatus = 0");
		return (List<Dictionary>) q.list();
	}

	/**
	 * Get All Dictionary
	 * @return List All Dictionary
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getAllDictionary() {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				" from Dictionary ");
		return (List<Dictionary>) q.list();
	}

	/**
	 * Get Available Dictionary By ID
	 * @param Id {@link Integer}
	 * @return {@link Dictionary}
	 */
	@Override
	public Dictionary getAvailableDictionaryByID(int Id) {
		Dictionary avaiablequestion = (Dictionary) getCurrentSession()
				.createQuery(
						" from Dictionary where Status = 1 and ID = " + Id
								+ " and DeleteStatus = 0").uniqueResult();
		return avaiablequestion;
	}
	
	/**
	 * Get Recent Dictionary By ID
	 * @param Id {@link Integer}
	 * @return {@link Dictionary}
	 */
	@Override
	public Dictionary getRecentDictionaryByID(int Id) {
		Dictionary recentquestion = (Dictionary) getCurrentSession()
				.createQuery(
						" from Dictionary where Status = 2 and ID = " + Id
								+ " and DeleteStatus = 0").uniqueResult();
		return recentquestion;
	}

	/**
	 * Get Dictionary By ID
	 * @param Id {@link Integer}
	 * @return {@link Dictionary}
	 */
	@Override
	public Dictionary getDictionaryByID(int Id) {
		Dictionary recentquestion = (Dictionary) getCurrentSession()
				.createQuery(" from Dictionary where ID = " + Id)
				.uniqueResult();
		return recentquestion;
	}

	/**
	 * Update Dictionary when Upload Dictionary
	 * @param Id {@link Integer}
	 * @return {@link Integer}
	 */
	@Override
	public int updateDictionaryWhenUpload(int Id) {
		String sqlstring = "update Dictionary set Status = '2' where ID = :Id AND DeleteStatus = 0";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("Id", Id);
		int result = q.executeUpdate();

		Dictionary question = getDictionaryByID(Id);
		FullTextSession fullTextSession = Search
				.getFullTextSession(getCurrentSession());
		fullTextSession.purge(Dictionary.class, Id);
		fullTextSession.index(question);
		return result;
	}

	/**
	 * Update Dictionary When Down Dictionary
	 * @param Id {@link Integer}
	 * @return {@link Integer}
	 */
	@Override
	public int updateDictionaryWhenDown(int Id) {
		String sqlstring = "update Dictionary set Status = '4' where ID = :Id AND DeleteStatus = 0";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("Id", Id);
		int result = q.executeUpdate();
		Dictionary question = getDictionaryByID(Id);
		FullTextSession fullTextSession = Search
				.getFullTextSession(getCurrentSession());
		fullTextSession.purge(Dictionary.class, Id);
		fullTextSession.index(question);
		return result;
	}

	/**
	 * Update Dictionary When Restore
	 * @param Id {@link Integer}
	 * @return {@link Integer}
	 */
	@Override
	public int updateDictionaryWhenRestore(int Id) {
		String sqlstring = "update Dictionary set DeleteStatus = '0' where ID = :Id";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("Id", Id);
		int result = q.executeUpdate();

		Dictionary question = getDictionaryByID(Id);
		FullTextSession fullTextSession = Search
				.getFullTextSession(getCurrentSession());
		fullTextSession.purge(Dictionary.class, Id);
		fullTextSession.index(question);
		return result;
	}

	/**
	 * Update Dictionary When Delete
	 * @param Id {@link Integer}
	 * @return {@link Integer}
	 */
	@Override
	public int updateDictionaryWhenDelete(int Id) {
		String sqlstring = "update Dictionary set DeleteStatus = '1' where ID = :Id";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("Id", Id);
		int result = q.executeUpdate();

		Dictionary question = getDictionaryByID(Id);
		FullTextSession fullTextSession = Search
				.getFullTextSession(getCurrentSession());
		fullTextSession.purge(Dictionary.class, Id);
		fullTextSession.index(question);
		return result;
	}

	/**
	 * Get Down Dictionary By ID
	 * @param Id {@link Integer}
	 * @return {@link Dictionary}
	 */
	@Override
	public Dictionary getDownDictionaryByID(int Id) {
		Dictionary removequestion = (Dictionary) getCurrentSession()
				.createQuery(
						" from Dictionary where Status = 4 and ID = " + Id
								+ " and DeleteStatus = 0").uniqueResult();
		return removequestion;
	}

	/**
	 * Search Index
	 * @param keyword {@link String}
	 * @param Status {@link String}
	 * @param UserID {@link Integer}
	 * @return {@link List}
	 */
	public List<Dictionary> searchIdex(String keyword, String Status, int UserID) {

		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());

		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Dictionary.class).get();
		if (Status.equals("1")) {
			org.apache.lucene.search.Query luceneQuery = qb
					.bool()

					.should(qb.phrase().onField("Anwser").andField("Question")
							.sentence(keyword).createQuery())
					.must(qb.keyword().onField("DeleteStatus").matching("1")
							.createQuery())
					.not()
					.must(qb.keyword().onField("Status").matching("2")
							.createQuery())
					.not()
					.must(qb.keyword().onField("Status").matching("4")
							.createQuery()).not()
					// .must(
					// qb.keyword().onField("AnwserBy").matching(""+UserID).createQuery())
					.createQuery();
			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
					luceneQuery, Dictionary.class);
			List<Dictionary> result = hibQuery.list();
			return result;
		} else {
			if (Status.equals("2")) {
				org.apache.lucene.search.Query luceneQuery = qb
						.bool()
						.should(qb.phrase().onField("Anwser")
								.andField("Question").sentence(keyword)
								.createQuery())
						.must(qb.keyword().onField("DeleteStatus")
								.matching("1").createQuery())
						.not()
						.must(qb.keyword().onField("Status").matching("1")
								.createQuery())
						.not()
						.must(qb.keyword().onField("Status").matching("4")
								.createQuery()).not()
						// .must(
						// qb.keyword().onField("UpdateBy").matching(""+UserID).createQuery())
						.createQuery();
				org.hibernate.Query hibQuery = fullTextSession
						.createFullTextQuery(luceneQuery, Dictionary.class);
				List<Dictionary> result = hibQuery.list();
				return result;
			} else {
				if (Status.equals("3")) {
					org.apache.lucene.search.Query luceneQuery = qb
							.bool()
							.should(qb.phrase().onField("Anwser")
									.andField("Question").sentence(keyword)
									.createQuery())
							.must(qb.keyword().onField("DeleteStatus")
									.matching("1").createQuery())
							.not()
							.must(qb.keyword().onField("Status").matching("1")
									.createQuery())
							.not()
							.must(qb.keyword().onField("Status").matching("2")
									.createQuery()).not()
							// .must(
							// qb.keyword().onField("UpdateBy").matching(""+UserID).createQuery())
							.createQuery();
					org.hibernate.Query hibQuery = fullTextSession
							.createFullTextQuery(luceneQuery, Dictionary.class);
					List<Dictionary> result = hibQuery.list();
					return result;
				} else {
					org.apache.lucene.search.Query luceneQuery = qb
							.bool()
							.should(qb.phrase().onField("Anwser")
									.andField("Question").sentence(keyword)
									.createQuery())
							.must(qb.keyword().onField("DeleteStatus")
									.matching("0").createQuery()).not()
							// .must(
							// qb.keyword().onField("DeleteBy").matching(""+UserID).createQuery())
							.createQuery();
					org.hibernate.Query hibQuery = fullTextSession
							.createFullTextQuery(luceneQuery, Dictionary.class);
					List<Dictionary> result = hibQuery.list();
					return result;
				}
			}
		}
	}
	
	
	/**
	 * Get Dictionary By Id Not Delete
	 * @param Id {@link Integer}
	 * @return
	 */
	public Dictionary getDictionaryByIDNotDelete(int Id) {
		// TODO Auto-generated method stub
		Dictionary avaiablequestion = (Dictionary) getCurrentSession()
				.createQuery(
						" from Dictionary where ID = " + Id
								+ " and DeleteStatus = 0").uniqueResult();
		return avaiablequestion;
	}
	
	/**
	 * Update CreateBy When edit dictionary
	 * @param Id
	 * @param UserID
	 * @return
	 */
	@Override
	public int updateCreatebyWhenEdit(int Id, int UserID) {
		String sqlstring = "update Dictionary set CreateBy =:userid , CreateDate =:mow where ID = :Id";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("Id", Id);
		q.setParameter("userid", UserID);
		Date now = new Date();
		q.setParameter("mow", now);
		int result = q.executeUpdate();

		return result;
	}
	
	/**
	 * Update UpdateBy dictionary When Upload Dictionary
	 * @param Id
	 * @param UserID
	 * @return
	 */
	@Override
	public int updateUpdateByWhenUpload(int Id, int UserID) {
		String sqlstring = "update Dictionary set UpdateBy =:userid, UpdateDate =:now where ID = :Id AND DeleteStatus = 0";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("userid", UserID);
		q.setParameter("Id", Id);
		Date now = new Date();
		q.setParameter("now", now);
		int result = q.executeUpdate();
		return result;
	}

	
	
	@Override
	public int updateQuesionAndAnwserDictionary(int Id, String Anwser, String Question) {
		String sqlstring = "update Dictionary set Anwser = :anwser, Question =:question where ID = :Id ";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("anwser", Anwser);
		q.setParameter("question", Question);
		q.setParameter("Id", Id);
		int result = q.executeUpdate();
		return result;
	}

	
	public void updateUpdateByAndUpdateDateWhenDown(int Id, int userID) {
		String sqlstring = "update Dictionary set UpdateBy =:userid, UpdateDate =:now where ID = :Id";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("Id", Id);
		q.setParameter("userid", userID);
		Date now = new Date();
		q.setParameter("now", now);
		q.executeUpdate();
	}
	
	/**
	 * Update DeleteBy and DeleteDate When Delete
	 * @param Id
	 * @param userID
	 */
	public void updateDeleteByAndDeleteDateWhenDelete(int Id, int userID) {
		String sqlstring = "update Dictionary set DeleteBy =:userid, DeleteDate =:now where ID = :Id";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("Id", Id);
		q.setParameter("userid", userID);
		Date now = new Date();
		q.setParameter("now", now);
		q.executeUpdate();
	}

	public int updateDeleteByAndDeleteDateWhenRestore(int Id) {
		String sqlstring = "update Dictionary set DeleteBy =:userid, DeleteDate =:now where ID = :Id";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				sqlstring);
		q.setParameter("Id", Id);
		q.setParameter("userid", null);

		q.setParameter("now", null);
		int result = q.executeUpdate();
		return result;
	}
}
