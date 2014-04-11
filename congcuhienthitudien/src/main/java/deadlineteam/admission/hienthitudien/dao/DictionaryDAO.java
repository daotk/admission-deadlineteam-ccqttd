package deadlineteam.admission.hienthitudien.dao;

import java.util.List;

import deadlineteam.admission.hienthitudien.domain.Dictionary;

public interface DictionaryDAO {
	public List<Dictionary> getalldictionary(int page);
	public List<Dictionary> getalldictionary(int page, int record);
	public List<Dictionary> getall();
	public void createIndex();
	public List<Dictionary> searchIdex(String keyword);
	public void updatequestion(Dictionary dictionary);
	public void deleteUser(Dictionary dictionary);
}
