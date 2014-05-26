package deadlineteam.admission.hienthitudien.dao;

import java.util.List;

import deadlineteam.admission.hienthitudien.domain.Dictionary;

public interface DictionaryDAO {
	/**
	 * Get all question that contain dictionary but limit by number of record 
	 * @param page
	 * @param record
	 * @return
	 */
	public List<Dictionary> getalldictionary(int page, int record);
	/**
	 * Get all question that contain dictionary
	 * @return
	 */
	public List<Dictionary> getall();
	/**
	 * Search data using hibernate search
	 * @param keyword
	 * @return
	 */
	public List<Dictionary> searchIdex(String keyword);
	/**
	 * Save question to database internet
	 * Save question is saved after this question is sent from Dictionary Management system
	 * @param dictionary
	 */
	public void updatequestion(Dictionary dictionary);
	/**
	 * Delete question, question is deleted after Dictionary Management system send drop question request
	 * @param dictionary
	 */
	public void deleteUser(Dictionary dictionary);

}
