package blockchain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.xml.crypto.Data;

public class FileDate {
	private static String file = "data.txt";

	public static void writeVote(String vote) {
		try {
			vote+="\n";
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
	public static int NLines() {
		try {
		    return Files.readAllLines(Paths.get("data.txt")).size()-1;
		}catch (IOException e) {
		}
		return 0;
	}
}
