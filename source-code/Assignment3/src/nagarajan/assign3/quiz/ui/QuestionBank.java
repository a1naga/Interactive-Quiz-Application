package nagarajan.assign3.quiz.ui;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * this class represents the question bank containing all questions grouped by topic
 */
@XmlRootElement
public class QuestionBank {
	
	private List<Topic> topic;

	public List<Topic> getTopic() {
		return topic;
	}

	@XmlElement
	public void setTopic(List<Topic> topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Quiz [topic=" + topic + "]";
	}

}
