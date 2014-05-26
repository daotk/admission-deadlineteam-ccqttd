package deadlineteam.admission.hienthitudien.service;

import java.util.List;

import deadlineteam.admission.hienthitudien.domain.Dictionary;
public interface DictionaryService {
	/**
	 * Get all question that contain dictionary but limit by number of record 
	 * @param page
	 * @param record
	 * @return
	 */
	public List<Dictionary> getalldictionary(int page, int record);
	/**
	 * Get all question that contain dictionary
	 * @param page
	 * @return
	 */
	public List<Dictionary> getall(int page);
	/**
	 * Search data of android app using hibernate search
	 * @param page
	 * @param keyword
	 * @return
	 */
	public List<Dictionary> searchIdexAndroid(int page, String keyword);
	/**
	 * Search data using hibernate search
	 * @param keyword
	 * @return
	 */
	public List<Dictionary> searchIdex(String keyword);
	/**
	 * Save question to database internet
	 * @param dictionary
	 */
	public void updatequestion(Dictionary dictionary);
	/**
	 * Delete question, question is deleted after Dictionary Management system send drop question request
	 * @param dictionary
	 */
	public void deleteUser(Dictionary dictionary);
	/**
	 * get total page of list-question
	 * @param record
	 * @return
	 */
	public int totalPage(int record);
}
