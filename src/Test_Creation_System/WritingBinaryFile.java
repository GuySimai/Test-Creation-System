package Test_Creation_System;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WritingBinaryFile {
	private ObjectOutputStream output;

	public void openFile(String fname) {
		try {
			output = new ObjectOutputStream(new FileOutputStream(fname));

		} catch (FileNotFoundException e) {
			System.out.println("Can't open file " + fname);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Can't open file " + fname);
			e.printStackTrace();
		}
	}

	public void closeFile() {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				System.out.println("Can't close file");
				e.printStackTrace();
			}
		}
	}

	public void writeData(Question[] questions) {
		if (output != null) {
			try {
				output.writeObject(questions);
			} catch (IOException e) {
				System.out.println("The object is not serializable");
				e.printStackTrace();
			}
		}
	}
}
