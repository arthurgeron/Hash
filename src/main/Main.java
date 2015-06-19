package main;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import logic.Hash;

public class Main {


	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated constructor stub
		
		
		BufferedReader reader;
		int hashSize;
		Hash hash;
		String fileLocation;
		Scanner scanner;
			try{
				scanner = new Scanner(System.in);
				System.out.println("Digite o local do arquivo de texto:");
				fileLocation = scanner.next().replace("\"", "");
				hashSize = (int) new BufferedReader(new FileReader(fileLocation)).lines().count() * 100;
				reader = new BufferedReader(new FileReader(fileLocation));
				
				hash = new Hash();
				
				hash.readAndTestFile(fileLocation);
				
				hash = new Hash();
				
				hash.readAndTestFileUsingAdler32(fileLocation);
				
			}
			catch(Exception exception) {
				System.out.println(exception.toString());
			}
		
	}

}
//421718554