package deadlineteam.admission.hienthitudien.service;

import java.util.List;

import deadlineteam.admission.hienthitudien.domain.Dictionary;
public interface DictionaryService {
	public List<Dictionary> getalldictionary(int page);
	public List<Dictionary> getalldictionary(int page, int record);
	public List<Dictionary> getall(int page);
	public List<Dictionary> searchIdexAndroid(int page, String keyword);
	public List<Dictionary> searchIdex(String keyword);
	public void updatequestion(Dictionary dictionary);
	public void deleteUser(Dictionary dictionary);
	public int totalPage(int record);
}
