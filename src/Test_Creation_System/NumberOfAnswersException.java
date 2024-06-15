package Test_Creation_System;

public class NumberOfAnswersException extends Exception {

	public NumberOfAnswersException() {
		super("It is not possible to select American questions with less than 3 answers");
	}
}
