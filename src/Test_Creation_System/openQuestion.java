package Test_Creation_System;

public class openQuestion extends Question {
	// Member variables
	private Answer answer;

	// Constructors
	public openQuestion(String QuestionString, String answerString, level lev) {
		super(QuestionString, lev);
		answer = new Answer(answerString, true);
	}

	// Q1
	@Override
	public String toString() {
		String str = super.toString() + "\n";
		str += answer + "\n";

		return str;
	}

	// Q3
	public void addAnswer(Answer answer) {
		this.answer = answer;
	}

	// Q6
	public void deleteAnswer() {
		answer = null;
		System.out.println("The answer has successfully deleted.\n");
	}

	// Function to create the exam file.
	public Answer getAnswer() {
		return answer;
	}

	public String chooseAnswers() {
		return "";
	}

	@Override
	public String chooseAnswers(int num) {
		return answer.getString();
	}

	@Override
	public boolean getStatus(int numTemp) {
		return true;
	}

}
