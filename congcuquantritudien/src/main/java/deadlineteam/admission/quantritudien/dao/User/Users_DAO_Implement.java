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

	public void addUser(Users user) {
		getCurrentSession().save(user);
		
	}

	public void updateUser(Users User) {
		Users userToUpdate = getUser(User.getID());
		
		userToUpdate.setFullName(User.getFullName());
		userToUpdate.setUserName(User.getUserName());
		userToUpdate.setPassword(User.getPassword());
		userToUpdate.setEmail(User.getEmail());
		userToUpdate.setAuthorization(User.getAuthorization());
		getCurrentSession().update(userToUpdate);
		
	}

	public Users getUser(int ID) {
		Users User = (Users) getCurrentSession().get(Users.class, ID);
		return User;
	}

	public void deleteUser(int ID) {
		Users User = getUser(ID);
		if (User != null)
			getCurrentSession().delete(User);
	}

	@SuppressWarnings("unchecked")
	public List<Users> getAllUsers() {
		return getCurrentSession().createQuery(" from Users").list();
	}
	
	public int changePassword(int Id,String newpassword){
		String sqlstring = "update Users set Password= :answer where ID = :Id ";
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		q.setParameter("answer", newpassword);
		q.setParameter("Id", Id);
		int result= q.executeUpdate();
		return result;
	}
	public void addSettingUser (Setting setting){
		getCurrentSession().save(setting);
	}
	public Setting getSetting(int Id){
		Setting temp =  (Setting)getCurrentSession().createQuery("from Setting where UserID = "+Id ).uniqueResult();
		return temp;
	}
	
// Function updates a number of record in setting
	public int UpdateSetting(int UserId, int Record, int Pagin){
		String sqlstring = "update Setting set RecordNotRep = :record, PaginDisplayNotRep = :pagin where UserID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("record", Record);
		 q.setParameter("pagin", Pagin);
		 int result= q.executeUpdate();
		return result;
	}
	public int UpdateSettingSaved(int UserId, int Record, int Pagin){
		String sqlstring = "update Setting set RecordTemp = :record, PaginDisplayTemp = :pagin where UserID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("record", Record);
		 q.setParameter("pagin", Pagin);
		 int result= q.executeUpdate();
		return result;
	}
	public int UpdateSettingReplied(int UserId, int Record, int Pagin){
		String sqlstring = "update Setting set RecordRepied = :record, PaginDisplayReplied = :pagin where UserID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("record", Record);
		 q.setParameter("pagin", Pagin);
		 int result= q.executeUpdate();
		return result;
	}
	public int UpdateSettingDelete(int UserId, int Record, int Pagin){
		String sqlstring = "update Setting set RecordDelete = :record, PaginDisplayDelete = :pagin where UserID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("record", Record);
		 q.setParameter("pagin", Pagin);
		 int result= q.executeUpdate();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getUserDetail(int ID) {
		return getCurrentSession().createQuery(" from Users where ID ="+ID).list();
	}
	public int UpdateStatusUser(int UserId, int Authorization){
		String sqlstring = "update Users set Authorization = :authorization where ID = "+UserId;
		 Query q = (Query) sessionFactory.getCurrentSession().createQuery(sqlstring);
		 q.setParameter("authorization", Authorization);
		 int result= q.executeUpdate();
		return result;
	}
}


