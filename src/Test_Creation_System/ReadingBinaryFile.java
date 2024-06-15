package Test_Creation_System;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadingBinaryFile {
	private ObjectInputStream input;

	public void openFile(String fname) {
		try {
			input = new ObjectInputStream(new FileInputStream(fname));
		} catch (FileNotFoundException e) {
			System.out.println("Can't find file " + fname);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Can't open file " + fname);
			e.printStackTrace();
		}
	}

	public void closeFile() {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				System.out.println("Can't close file");
				e.printStackTrace();
			} // FileInputStream will be closed automatically
		}
	}

	public Question[] readData() {
		Question[] questions = null;
		try {
			questions = (Question[]) input.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questions;

	}
}
