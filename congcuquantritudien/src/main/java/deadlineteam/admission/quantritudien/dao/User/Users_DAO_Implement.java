package deadlineteam.admission.quantritudien.dao.User;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import deadlineteam.admission.quantritudien.domain.Setting;
import deadlineteam.admission.quantritudien.domain.Users;

@Repository
public class Users_DAO_Implement implements Users_DAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Add User to Database
	 * @param user {@link Users}
	 */
	public void addUser(Users user) {
		getCurrentSession().save(user);
		
	}

	/**
	 * Update User
	 * @param User {@link Users}
	 */
	public void updateUser(Users User) {
		Users userToUpdate = getUserByUserID(User.getID());
		
		userToUpdate.setFullName(User.getFullName());
		userToUpdate.setUserName(User.getUserName());
		userToUpdate.setPassword(User.getPassword());
		userToUpdate.setEmail(User.getEmail());
		userToUpdate.setAuthorization(User.getAuthorization());
		getCurrentSession().update(userToUpdate);
		
	}

	/**
	 * Get User By ID
	 * @param ID {@link Integer}
	 * @return {@link Users}
	 */
	public Users getUserByUserID(int ID) {
		Users User = (Users) getCurrentSession().get(Users.class, ID);
		return User;
	}

	/**
	 * Delete User By ID
	 * @param ID {@link Integer}
	 */
	public void deleteUserByUserID(int ID) {
		Users User = getUserByUserID(ID);
		if (User != null)
			getCurrentSession().delete(User);
	}

	/**
	 * Get All User
	 * @return {@link List} All User
	 */
	@SuppressWarnings("unchecked")
	public List<Users> getAllUsers() {
		return getCurrentSession().createQuery(" from Users").list();
	}
	
	/**
	 * Change Password By ID
	 * @param Id {@link Integer}
	 * @param newpassword {@link String}
	 * @return {@link Integer}
	 */
	public int changePasswordByUserID(int Id,String newpassword){
		String sqlstring = "update Users set Password= :answer where ID = :Id ";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		q.setParameter("answer", newpassword);
		q.setParameter("Id", Id);
		int result= q.executeUpdate();
		return result;
	}
	
	/**
	 * Add Setting for User
	 * @param setting {@link Setting}
	 */
	public void addSettingUser (Setting setting){
		getCurrentSession().save(setting);
	}
	
	/**
	 * Get Setting By ID
	 * @param Id {@link Integer}
	 * @return {@link Setting}
	 */
	public Setting getSetting(int Id){
		Setting temp =  (Setting)getCurrentSession().createQuery("from Setting where UserID = "+Id ).uniqueResult();
		return temp;
	}
	
	/**
	 * Update Setting
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSetting(int UserId, int Record, int Pagin){
		String sqlstring = "update Setting set RecordNotRep = :record, PaginDisplayNotRep = :pagin where UserID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("record", Record);
		 q.setParameter("pagin", Pagin);
		 int result= q.executeUpdate();
		return result;
	}
	
	/**
	 * Update Setting Save List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSettingSaved(int UserId, int Record, int Pagin){
		String sqlstring = "update Setting set RecordTemp = :record, PaginDisplayTemp = :pagin where UserID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("record", Record);
		 q.setParameter("pagin", Pagin);
		 int result= q.executeUpdate();
		return result;
	}
	
	/**
	 * Update Setting Replied List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSettingReplied(int UserId, int Record, int Pagin){
		String sqlstring = "update Setting set RecordRepied = :record, PaginDisplayReplied = :pagin where UserID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("record", Record);
		 q.setParameter("pagin", Pagin);
		 int result= q.executeUpdate();
		return result;
	}
	
	/**
	 * Update Setting Delete List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSettingDelete(int UserId, int Record, int Pagin){
		String sqlstring = "update Setting set RecordDelete = :record, PaginDisplayDelete = :pagin where UserID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("record", Record);
		 q.setParameter("pagin", Pagin);
		 int result= q.executeUpdate();
		return result;
	}
	
	/**
	 * Update Setting Dictionary List
	 * @param UserId {@link Integer}
	 * @param Record {@link Integer}
	 * @param Pagin {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateSettingDictionary(int UserId, int Record, int Pagin){
		String sqlstring = "update Setting set RecordDictionary = :record, PaginDisplayDictionary = :pagin where UserID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("record", Record);
		 q.setParameter("pagin", Pagin);
		 int result= q.executeUpdate();
		return result;
	}
	
	/**
	 * Update User Authorization
	 * @param UserId {@link Integer}
	 * @param Authorization {@link Integer}
	 * @return {@link Integer}
	 */
	public int updateAuthorizationUser(int UserId, int Authorization, String Fullname,int Status){
		String sqlstring = "update Users set Authorization = :authorization , FullName = :fullname , Status =:status where ID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("authorization", Authorization);
		 q.setParameter("fullname", Fullname);
		 q.setParameter("status", Status);
		 int result= q.executeUpdate();
		return result;
	}
}


