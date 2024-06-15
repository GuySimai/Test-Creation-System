package Test_Creation_System;

public class americanQuestion extends Question {
	// Member variables
	private Answer[] answers;
	private int actualNumAnswers;

	// Constructors
	public americanQuestion(String Question, int actualNumAnswers, Answer[] OtherAnswers, level lev) {
		super(Question, lev);
		this.actualNumAnswers = actualNumAnswers <= MAX_ANSWERS ? actualNumAnswers : MAX_ANSWERS;
		this.answers = new Answer[MAX_ANSWERS];
		for (int i = 0; i < OtherAnswers.length && i < MAX_ANSWERS; i++) {
			answers[i] = OtherAnswers[i];
		}
	}

	// Q1
	@Override
	public String toString() {
		String str = super.toString() + " ,answers:\n";
		for (int i = 0, num = 1; i < actualNumAnswers; i++, num++)
			str += num + ") " + answers[i] + "\n";

		return str;
	}

	// Q3
	public void addAnswer(Answer answer) {
		if (actualNumAnswers >= 10) {
			System.out.println("Your answer has not been added, it is not possible to add more than 10 answers.\n");
		} else {
			answers[actualNumAnswers] = answer;
			actualNumAnswers++;
			System.out.println("You have successfully added the answer.\n");
		}
	}

	// Q6
	public boolean deleteAnswer(int num) {
		if (num > 0 && num <= actualNumAnswers) {
			for (int i = num - 1; i < 9; i++) {
				answers[i] = answers[i + 1];
			}
			answers[9] = null;
			actualNumAnswers--;

			return true;
		} else {
			return false;
		}
	}

	// Function to create the exam file.
	public String showAnswers() {
		String str = super.showAnswers() + "\n";
		for (int i = 0, num = 1; i < actualNumAnswers; i++, num++)
			str += num + ") " + answers[i].getString() + "\n";
		return str;
	}

	@Override
	public Answer getAnswer() {
		return null;
	}

	public String chooseAnswers(int num) {
		String str = "";
		str += answers[num - 1].getString() + "\n";
		return str;
	}

	public boolean getStatus(int num) {
		return answers[num - 1].getStatus();
	}

	public int getActualNumAnswers() {
		return actualNumAnswers;
	}

	public int getActualNumFalseAnswers() {
		int numOfFalse = 0;
		for (int i = 0; i < actualNumAnswers; i++) {
			if (answers[i].getStatus() == false) {
				numOfFalse++;
			}
		}
		return numOfFalse;
	}
}
