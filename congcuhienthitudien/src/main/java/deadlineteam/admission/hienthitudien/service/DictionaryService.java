package deadlineteam.admission.hienthitudien.service;

import java.util.List;

import deadlineteam.admission.hienthitudien.domain.Dictionary;
public interface DictionaryService {
	public List<Dictionary> getalldictionary(int page);
	public List<Dictionary> getalldictionary(int page, int record);
	public List<Dictionary> getall();
	public void createIndex();
	public List<Dictionary> searchIdex(String keyword);
	public void updatequestion(Dictionary dictionary);
	public void deleteUser(Dictionary dictionary);
}
