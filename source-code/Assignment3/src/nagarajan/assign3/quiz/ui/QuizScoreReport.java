package nagarajan.assign3.quiz.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class creates user interface for the final score report summary and graph.
 *
 */
public class QuizScoreReport {

	private JFrame scoreReportFrame;
	private JPanel scorePanel;
	private JPanel graphPanel;
	private JLabel lblTitle;
	private JLabel lblquizScore;
	private JLabel lblTotalQuestions;
	private JLabel lblCorrectAnswers;
	private Map<String, TopicWiseScore> topicWiseScoreMap;

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Dimension dim = toolkit.getScreenSize();

	public QuizScoreReport(Map<String, TopicWiseScore> topicWiseScoreMap, int totalQuestions, int correctAnswers, int quizScore) {

		this.topicWiseScoreMap = topicWiseScoreMap;

		scoreReportFrame = new JFrame("Quiz Score report");
		scorePanel = new JPanel();
		Box box = Box.createVerticalBox();
		
		lblTitle = new JLabel("Top Quiz Score Report");
		lblTitle.setFont(new Font("Comic Sans MS", Font.CENTER_BASELINE, 30));
		lblTotalQuestions = new JLabel("Total Questions   : " + totalQuestions);
		lblTotalQuestions.setFont(new Font("Sans MS", Font.PLAIN, 20));
		lblCorrectAnswers = new JLabel("Correctly Answered : " + Integer.toString(correctAnswers));
		lblCorrectAnswers.setFont(new Font("Sans MS", Font.PLAIN, 20));
		lblquizScore = new JLabel("Final Score    : " + Integer.toString(quizScore));
		lblquizScore.setFont(new Font("Sans MS", Font.PLAIN, 20));

		box.add(Box.createVerticalStrut(20));
		box.add(lblTitle);
		box.add(Box.createVerticalStrut(40));
		box.add(lblTotalQuestions);
		//box.add(Box.createVerticalStrut(30));
		box.add(lblCorrectAnswers);
		//box.add(Box.createVerticalStrut(30));
		box.add(lblquizScore);
		box.add(Box.createVerticalStrut(30));
		scorePanel.add(box);
		

		scoreReportFrame.add(scorePanel);
		drawGraph();
		scoreReportFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scoreReportFrame.getContentPane()
				.setLayout(new BoxLayout(scoreReportFrame.getContentPane(), BoxLayout.PAGE_AXIS));
		scoreReportFrame.setSize(dim.width, dim.height);
		scoreReportFrame.setVisible(true);
		
	}

	/**
	 * this method draws graph from the provided topic wise score map
	 */
	private void drawGraph() {
		graphPanel = new JPanel();
		HistogramGraph panel = new HistogramGraph();

		Color[] colors = new Color[] { Color.RED, Color.YELLOW, Color.CYAN, Color.BLUE, Color.BLACK, Color.WHITE };
		
		int i = 0;
		Set<String> topicSet = topicWiseScoreMap.keySet();
		for (String topic : topicSet) {
			TopicWiseScore scoreValues = topicWiseScoreMap.get(topic);
			double percentage = (new Double(scoreValues.numberOfCorrectAnswers) / new Double(scoreValues.numberOfAttemptedQuestions)) * 100.0f;
			panel.addHistogramColumn(topic, percentage, colors[i++]);
		}
		
		panel.setPreferredSize(new Dimension(410, 370));
		panel.layoutHistogram();
		
		graphPanel.add(panel);
		scoreReportFrame.add(graphPanel);
	}

}
