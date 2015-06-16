package logic;

import java.awt.List;
import java.util.ArrayList;
import java.util.Random;


public class Hash {
	private String[] hashTable;
	boolean autoIncrement = false;
	ArrayList<Integer> storedElements = new ArrayList<Integer>();
	int counter;
	public Hash() {
		// TODO Auto-generated constructor stub
		autoIncrement = true;
	}
	
	public Hash(int arraySize) {
		hashTable = new String[arraySize];
	}
	
	
	private String getElementAtPosition(int position){
			return hashTable[position];
	}
	
	public boolean storeInfo(String info)//Generate list position from hashCode and store it
	{
		int indexLocation;
		counter = 0;
		try{
			indexLocation = hashCode(info);
			
			if(hashTable == null){
				hashTable = setNewSizeForStringArray(hashTable,indexLocation+1);
			}
			if(hashTable.length<	indexLocation+1){
				if(autoIncrement){
					hashTable = setNewSizeForStringArray(hashTable,indexLocation+1);
				}
				else {
					return false;
				}
			}
			
			while(hashTable[indexLocation] != null && counter != hashTable.length){
				counter++;
				indexLocation = hashCode(String.valueOf(indexLocation + String.valueOf(indexLocation).length() * indexLocation));
			}
			if(hashTable[indexLocation] != null) {
				return false;
			}
			hashTable[indexLocation] = info;
			return true;
		}
		catch(Exception exception) {
			return false;
		}
	}
	
	public void drawTable(){
		int nonNullElements = 0;
		for(int i = 0 ; i < hashTable.length ; i++) {
			if(hashTable[i]!=null) {
				nonNullElements++;
				System.out.println("Position: "+i+" Value: "+hashTable[i]);
			}
			
		}
		System.out.println("Number of valid elements: "+nonNullElements);
	}
	
	private String[] setNewSizeForStringArray(String[] firstArray, int newSize){
		String[] result = new String[newSize];
		try{
		for(int i = 0; i < firstArray.length; i++){
				result[i] = firstArray[i];
			}
			System.gc();//Garbage collector call
			return result;
		}
		catch(Exception exception) {
			System.gc();//Garbage collector call
			return null;
		}
	}
	
	public String findInfo(String info)//Find info from list using hashCode
	{
		try{
			int indexLocation = hashCode(info);
			return getElementAtPosition(indexLocation);
		}
		catch(Exception exception) {
			return null;
		}
	}
	
	private int hashCode(String info) {//Alterar para utilizar bits
		  int result = 0, counter=0;
		  for(int i =0; i < info.length() ; i++) {
			  if(counter==0){
			  result += (int)info.charAt(i) + info.length() + (i+1);
			  	counter++;
			  }
			  else if(counter==1) {
				  result *= (int)info.charAt(i) * (info.length()/4) ;
				  counter++;
			  }
			  else if(counter==2){
				  result -= (int)info.charAt(i) - info.length() - (i+2);
				  	counter++;  
			  }
			  else {
				  result /= (int)info.charAt(i) / info.length();
				  counter = 0;
			  }
//			  result = (int) Long.parseLong(String.valueOf(result+((int)info.charAt(i))) +info.length()) ;
			  if(i+1<=info.length()-1){
				  result += ((int)info.charAt(i) - (int)info.charAt(i+1));
			  }
			  else if(i-1 > 0){
				  result += ((int)info.charAt(i) + (int)info.charAt(i-1));
			  }
			  
		  }
		  result = result < 0 ? Math.abs(result) : result;
		  while(result>hashTable.length - 1 ) {
			  result =  (result/2) + (result%2);
			  result = result < 0 ? Math.abs(result) : result;
		  }
		  return result;
		}
	

}
