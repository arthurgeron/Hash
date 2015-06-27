package logic;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


public class Hash  {
	
	String[] hashTable;
	private List<Integer> keys = new ArrayList<Integer>();
	
	
	public Hash() {
	}
	
	public boolean storeInfo(String info)//Generate list position from hashCode and store it
	{
		int indexLocation;
		
		
		
		try{
			indexLocation = hashCode(info);
			if(hashTable[indexLocation]!=null){
				if(hashTable[hashCode(String.valueOf(indexLocation))]==null)
				{
					hashTable[indexLocation] = info;
				}
				return false;
			}
			else{
				hashTable[indexLocation] = info;
				return true;
			}
		}
		catch(Exception exception) {
			return false;
		}
	}
	
	
	public boolean storeInfoUsinAdler32(String info)//Generate list position from hashCode and store it
	{
		int indexLocation;

		try{
			indexLocation = hashCodeAdler32(info);
			if(keys.contains(indexLocation)){
				return false;
			}
			else{
				keys.add(indexLocation);
				return true;
			}
		}
		catch(Exception exception) {
			return false;
		}
	}
	
	public void drawTable(){
	
		int counter=0;
		for(int i = 0 ; i < hashTable.length ; i ++) {
		    if(hashTable[i]!=null){
		    	System.out.println("Position: "+ i +" Value: "+hashTable[i]);
		    	counter++;
		    }
		}
		System.out.println("Number of valid elements: "+counter);
	}
	
	public boolean containInfo(String info)//Find info from list using hashCode
	{
			int indexLocation = hashCode(info);
			if(keys.contains(indexLocation)){
				return true;
			}
			else {
				return false;
			}
	}
	
	private int hashCode(String info) {//Alterar para utilizar bits
		  int result = 1,auxiliar = 0;
		  int base = 1759;
		  for(int i =0; i < info.length() ; i++) {
			 
			 // base = base  + i; // Low numbers method
			  
			  base += base  + i; // High numbers method
			  
			  result += (info.charAt(i) & 0xFF) + info.length() + (i+1);
			  result += (info.charAt(i) & 0xFF) + info.length() + (i+1);
			  result *= (info.charAt(i) & 0xFF) * (info.length()/4) ;
			  result -= ( (info.charAt(i) & 0xFF) - info.length() - (i+2) );
			  
			  if(i+1<=info.length()-1){
				  result += ((info.charAt(i) & 0xFF) - (int)info.charAt(i+1));
			  }
			  else if(i-1 > 0){
				  result += ((info.charAt(i) & 0xFF) + (int)info.charAt(i-1));
			  }
			  result = (result + (info.charAt(i) & 0xFF) ) % base;
			  auxiliar = (result + auxiliar) % base;
		  }
		  result = result < 0 ? Math.abs(result) : result;
		  result += auxiliar;
		  result = result < 0 ? Math.abs(result) : result;
		  result = reduceKey(result);
		  
		  return result;
	}
	
	public void readAndTestFile(String fileLocation) throws FileNotFoundException{
		
		BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
		String currentLine;
		String[] words;
		int collisionCounter=0,wordsCounter=0;
		Scanner scanner = new Scanner(System.in);
		int length = (int) new BufferedReader(new FileReader(fileLocation)).lines().count();
		hashTable = new String[length];
		
		try {
			while ( reader.ready())
			{
				currentLine = reader.readLine();
				words = currentLine.split("\\s");
				for(int i = 0 ; i < words.length ; i++) {
					if(storeInfo(words[i])==false){
						collisionCounter++;
					}
					wordsCounter++;
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Total de palavras utilizando o novo método: "+wordsCounter+", porcentual de falhas: "+ ((collisionCounter*100)/Double.valueOf(wordsCounter))+"%.");
		
		System.out.println("Você deseja visualizar a tabela hash?");
		switch(scanner.next().toLowerCase()){
			case"sim":
				drawTable();
				break;
		}
		scanner.close();
	}
	
	public void readAndTestFileUsingAdler32(String fileLocation) throws FileNotFoundException{
		BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
		String currentLine;
		Hash hash = this;
		String[] words;
		int collisionCounter=0,wordsCounter=0;
		Scanner scanner = new Scanner(System.in);
		
		try {
			while ( reader.ready())
			{
				currentLine = reader.readLine();
				words = currentLine.split("\\s");
				for(int i = 0 ; i < words.length ; i++) {
					if(hash.storeInfoUsinAdler32(words[i])==false){
						collisionCounter++;
					}
					wordsCounter++;
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Total de palavras utilizando Adler32: "+wordsCounter+", porcentual de falhas: "+ ((collisionCounter*100)/Double.valueOf(wordsCounter))+"%.");
		
		scanner.close();
	}
	
	private int hashCodeAdler32(String info) {//Adler32
		  int base,result = 0, a=0,b;
		  a = 1;
		  b = 0;
		  base = 65521;
		  for(int i =0; i < info.length() ; i++) {
			  a = (a + (info.charAt(i) & 0xFF)) % base;
			  b = (a + b) % base;
		  }
		  result = (b << 16) | a;
		  return result;
	}
	
	private int reduceKey(int key){
		while(key > hashTable.length){
			//key =  Integer.parseInt(Integer.toString(key).substring(1));
			key = key > 10 ? ((int) key / 10 ) : key;
		}

		return key;
	}
	
	
}