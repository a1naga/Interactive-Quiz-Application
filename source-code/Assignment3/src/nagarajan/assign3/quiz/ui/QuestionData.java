package nagarajan.assign3.quiz.ui;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * This class reads the quiz questions from the quiz.xml file
 */
public class QuestionData {

	private Map<String, List<Entry>> topicMap = new HashMap<String, List<Entry>>();

	/**
	 * parses the quiz.xml file and loads the questions for each topic
	 */
	public void loadDataFromXml() {

		try {

			InputStream inputStream = QuestionData.class.getResourceAsStream("/quiz.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(QuestionBank.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			QuestionBank quiz = (QuestionBank) jaxbUnmarshaller.unmarshal(inputStream);
			List<Topic> topics = quiz.getTopic();
			for (Topic topic : topics) {
				topicMap.put(topic.getName(), topic.getEntry());
			}
			

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, List<Entry>> getTopicMap() {
		return topicMap;
	}
	public void setTopicMap(Map<String, List<Entry>> topicMap) {
		this.topicMap = topicMap;
	}

}
