package main;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import logic.Hash;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated constructor stub
		Hash hash = new Hash(1000);
		BufferedReader reader;
		int colisionCounter = 0, wordsCounter=0;
		String currentLine;
		String[] words;
		Scanner scanner;
		while(true) {
			try{
				scanner = new Scanner(System.in);
				System.out.println("Digite o local do arquivo de texto:");
				reader = new BufferedReader(new FileReader(scanner.next().replace("\"", "")));
				while ( reader.ready())
				{
					currentLine = reader.readLine();
					words = currentLine.split("\\s");
					for(int i = 0 ; i < words.length ; i++) {
						if(hash.storeInfo(words[i])==false){
							colisionCounter++;
						}
						wordsCounter++;
						//System.out.println("Total words: "+wordsCounter+" | Total collisions: " + colisionCounter);
					}
				}
				System.out.println("Total de palavras: "+wordsCounter+", porcentual de falhas: "+ Math.round((colisionCounter*100)/Double.valueOf(wordsCounter))+"%.");
				System.out.println("Você deseja visualizar a tabela hash?");
				switch(scanner.next().toLowerCase()){
					case"sim":
						hash.drawTable();
						break;
				}
				scanner.close();
				break;
			}
			catch(Exception exception) {
				System.out.println(exception.toString());
				break;
			}
		}
		
	}

}
//421718554