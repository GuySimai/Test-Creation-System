package Test_Creation_System;

import java.io.Serializable;

public class Answer implements Serializable {
	// Member variables
	private String answer;
	private boolean status;

	// Constructors
	public Answer(String answer, boolean status) {
		this.answer = answer;
		this.status = status;
	}

	public Answer(Answer other) {
		answer = other.answer;
		status = other.status;
	}

	// Methods
	// Q1
	@Override
	public String toString() {
		return "The answer: \"ֿ" + answer + "\", is: " + status;
	}

	// Q8
	public String getString() {
		return answer;
	}

	public boolean getStatus() {
		return status;
	}

}
