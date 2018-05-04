package blockchain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileDate {
	private static String file = "data.txt";

	public static void writeVote(String vote) {
		try {
		    Files.write(Paths.get(file), vote.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		}
	}

	public static String getData() {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));			
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void truncate() {
		try {
		    Files.write(Paths.get(file), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		}catch (IOException e) {
		}
	}
}
