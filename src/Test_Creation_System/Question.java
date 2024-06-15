package Test_Creation_System;

import java.io.Serializable;

public abstract class Question implements Serializable {
	// Member variables
	protected enum level {
		Easy, Medium, Hard
	};

	protected static final int MAX_ANSWERS = 10;
	protected String question;
	private static int occurrences = 1;
	protected int id;
	protected level lev;

	// Constructors
	public Question(String Question, level lev) {
		this.question = Question;
		id = +occurrences;
		occurrences++;
		this.lev = lev;
	}

	// Q1
	public String toString() {
		return "The question: \"" + question + "\"" + " ID: " + id + ", level: " + lev;
	}

	public abstract Answer getAnswer();

	// Q8
	public String getString() {
		return question + ", level: " + lev;
	}

	// Function to create the exam files.
	public abstract String chooseAnswers(int num);

	public String showAnswers() {
		return question + ", level: " + lev;
	}

	public abstract boolean getStatus(int numTemp);

}
