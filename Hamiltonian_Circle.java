package hamiltonian_circle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * // Hamiltonian Circles and Paths //
 * @author Savvas Theofilou
 */
public class Hamiltonian_Circle {
    /**
     * A method that prints the matrix
     * @param myMatrix the matrix
     * @param rows total rows of the matrix
     */
    public static void printMatrix(ArrayList<ArrayList<Integer>> myMatrix, int rows){
        for (int i=0;i<rows;i++){
            for (int j=0;j<rows;j++){
                System.out.print(myMatrix.get(i).get(j)+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * A method that prints the matrix
     * @param myMatrix the matrix
     * @param rows total rows of the matrix
     */
    public static void printCharMatrix(ArrayList<ArrayList<StringBuilder>> myMatrix, int rows){
        for (int i=0;i<rows;i++){
            for (int j=0;j<rows;j++){
                System.out.print(myMatrix.get(i).get(j).toString()+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * A method that "names" the nodes accordingly to the number of nodes, 
     * @param rows total rows of the matrix
     * @return 
     */
    public static ArrayList<Character> findCharForArray(int rows){
        ArrayList<Character> temp=new ArrayList<>();
        int asciiOfChar=(int) 'A';
        
        for (int i=0;i<rows;i++){
            temp.add((char)asciiOfChar);
            asciiOfChar++;
        }
        
        return temp;
    }
    
    /**
     * A method that creates the first matrix(M)
     * @param myMatrix adjacency matrix
     * @param rows total rows of the matrix
     * @param charOfRows an ArrayList with the names of the nodes
     * @return the first matrix(M)
     */
    public static ArrayList<ArrayList<StringBuilder>> createFirstCharMatrix(ArrayList<ArrayList<Integer>> myMatrix, int rows, ArrayList<Character> charOfRows){
        ArrayList<ArrayList<StringBuilder>> temp=new ArrayList<>();
        
        for (int i=0;i<rows;i++){
            ArrayList<StringBuilder> tempArrayList=new ArrayList<>();
            for (int j=0;j<rows;j++){
                StringBuilder tempString=new StringBuilder();
                if (i!=j){ //non diagonal
                    if (myMatrix.get(i).get(j)==1){
                        tempString.append(charOfRows.get(j));
                    }
                    else{
                        tempString.append("0");
                    }
                }
                else{ //diagonal is full of zeros
                    tempString.append("0");
                }
                tempArrayList.add(tempString);
            }
            temp.add(tempArrayList);
        }
        
        return temp;
    }
    
    /**
     * A method that creates the second matrix(M1)
     * @param myMatrix adjacency matrix
     * @param rows total rows of the matrix
     * @param charOfRows an ArrayList with the names of the nodes
     * @return the second matrix(M1)
     */
    public static ArrayList<ArrayList<StringBuilder>> createSecondCharMatrix(ArrayList<ArrayList<Integer>> myMatrix, int rows, ArrayList<Character> charOfRows){
        ArrayList<ArrayList<StringBuilder>> temp=new ArrayList<>();
        
        for (int i=0;i<rows;i++){
            ArrayList<StringBuilder> tempArrayList=new ArrayList<>();
            for (int j=0;j<rows;j++){
                StringBuilder tempString=new StringBuilder();
                if (i!=j){ //non diagonal
                    if (myMatrix.get(i).get(j)==1){
                        tempString.append(charOfRows.get(i));
                        tempString.append(charOfRows.get(j));
                    }
                    else{
                        tempString.append("0");
                    }
                }
                else{ //diagonal is full of zeros
                    tempString.append("0");
                }
                tempArrayList.add(tempString);
            }
            temp.add(tempArrayList);
        }
        
        return temp;
    }
    
    /**
     * A method that multiplies the two matrices and returns the result (the new Matrix)
     * @param previousMatrix Matrix Mj-1
     * @param firstMatrix Matrix M
     * @param rows total rows of the matrix
     * @return the new Matrix Mj
     */
    public static ArrayList<ArrayList<StringBuilder>> multiplyMatrices(ArrayList<ArrayList<StringBuilder>> previousMatrix, ArrayList<ArrayList<StringBuilder>> firstMatrix, int rows){
        ArrayList<ArrayList<StringBuilder>> temp=new ArrayList<>();
        int index=0;
        
        for (int i=0;i<rows;i++){
            ArrayList<StringBuilder> tempArrayList=new ArrayList<>();
            for (int j=0;j<rows;j++){
                tempArrayList.add(new StringBuilder("#")); //WITH THE # CHAR WE KNOW THAT STRING IS "EMPTY"
            }
            temp.add(tempArrayList);
        }
        
        for (int i=0;i<rows;i++){
            for (int j=0;j<rows;j++){
                for (int k=0;k<rows;k++){
                    if (previousMatrix.get(i).get(k).toString().contains("$")){ //IN CASE THERE ARE 2 PATHS (SEPARATED WITH $ CHAR)
                        while (index!=-1){
                            index=previousMatrix.get(i).get(k).toString().indexOf("$");
                            
                            if (previousMatrix.get(i).get(k).substring(0, index).equals("0") || firstMatrix.get(k).get(j).toString().equals("0")){
                                if (temp.get(i).get(j).toString().equals("#")){ //DOESN'T ADD ZERO IF ALREADY ZERO OR HAS A PATH
                                    temp.get(i).get(j).append("0");
                                    temp.get(i).get(j).deleteCharAt(0);
                                }
                            }
                            else{
                                if (previousMatrix.get(i).get(k).substring(0, index).contains(firstMatrix.get(k).get(j).toString())){ //AN EPISTREFI STON IDIO KOMVO
                                    if (temp.get(i).get(j).toString().length()>1){
                                        temp.get(i).get(j).append("0");
                                        temp.get(i).get(j).delete(0, temp.get(i).get(j).toString().length()-2);
                                    }
                                    else{
                                        temp.get(i).get(j).append("0");
                                        temp.get(i).get(j).deleteCharAt(0);
                                    }
                                }
                                else{
                                    if (temp.get(i).get(j).toString().equals("#") || temp.get(i).get(j).toString().equals("0")){
                                        temp.get(i).get(j).append(previousMatrix.get(i).get(k).substring(0, index));
                                        temp.get(i).get(j).append(firstMatrix.get(k).get(j));
                                        temp.get(i).get(j).deleteCharAt(0);
                                    }
                                    else{ //THERE ARE 2 PATHS, SEPARATES THEM WITH $ CHAR
                                        temp.get(i).get(j).append("$");
                                        temp.get(i).get(j).append(previousMatrix.get(i).get(k).substring(0, index));
                                        temp.get(i).get(j).append(firstMatrix.get(k).get(j));
                                    }
                                }
                            }
                            
                            previousMatrix.get(i).get(k).delete(0, index+1);
                            index=previousMatrix.get(i).get(k).toString().indexOf("$"); //returns -1 if not found!
                        }
                        
                        index=0;
                    }
                    
                    if (previousMatrix.get(i).get(k).toString().equals("0") || firstMatrix.get(k).get(j).toString().equals("0")){
                        if (temp.get(i).get(j).toString().equals("#")){ //DOESN'T ADD ZERO IF ALREADY ZERO OR HAS A PATH
                            temp.get(i).get(j).append("0");
                            temp.get(i).get(j).deleteCharAt(0);
                        }
                    }
                    else{
                        if (previousMatrix.get(i).get(k).toString().contains(firstMatrix.get(k).get(j).toString())){ //AN EPISTREFI STON IDIO KOMVO
                            if (temp.get(i).get(j).toString().length()>1){
                                temp.get(i).get(j).append("0");
                                temp.get(i).get(j).delete(0, temp.get(i).get(j).toString().length()-2);
                            }
                            else{
                                temp.get(i).get(j).append("0");
                                temp.get(i).get(j).deleteCharAt(0);
                            }
                        }
                        else{
                            if (temp.get(i).get(j).toString().equals("#") || temp.get(i).get(j).toString().equals("0")){
                                temp.get(i).get(j).append(previousMatrix.get(i).get(k));
                                temp.get(i).get(j).append(firstMatrix.get(k).get(j));
                                temp.get(i).get(j).deleteCharAt(0);
                            }
                            else{
                                //YPARXOYN 2 MONOPATIA????
                                temp.get(i).get(j).append("$");
                                temp.get(i).get(j).append(previousMatrix.get(i).get(k));
                                temp.get(i).get(j).append(firstMatrix.get(k).get(j));
                            }
                        }
                    }
                }
            }
        }
        
        return temp;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File inputFile=null;
        
        if (args.length==0){ //CHECKS IF USER PROVIDED AN ARGUMENT (filename.txt)
            System.err.println("Please provide an argument and run the program again. Exiting..");
            System.exit(-1);
        }
        
        inputFile=new File(args[0]);
        BufferedReader reader=null;
        ArrayList<ArrayList<Integer>> graphMatrix=null;
        boolean matrixIsOk=false;
        int rows=0;
        int totalValues=0;
        
        try{
            reader=new BufferedReader(new FileReader(inputFile));
            graphMatrix=new ArrayList<>();
            String text=null;
            
            
            while ((text=reader.readLine()) != null){ //READING OF THE FILE. LINE BY LINE.
                rows++;
                ArrayList<Integer> temp=new ArrayList<>();
                String[] tokens=text.split(" "); //SPLITS THE LINE THAT READ
                
                for (int i=0;i<tokens.length;i++){
                    try{
                        int nextBit=Integer.parseInt(tokens[i]);
                        if (nextBit!=0 && nextBit!=1){ //IF THE FILE CONTAINS A NUMBER NOT EQUAL TO 0 AND 1
                            System.err.println("There was an error while reading the file (Line:" + rows +"). Please check your file values. Exiting..");
                            System.exit(-1);
                        }
                        else{
                            if (!matrixIsOk){        //
                                if (nextBit==1){     //
                                    matrixIsOk=true; // CHECK IF MATRIX IS FULL OF ZEROS
                                }                    //
                            }                        //
                            temp.add(nextBit);
                            totalValues++;
                        }
                    } catch (NumberFormatException e){ //IF STRING IS NOT INTEGER
                        System.err.println("There was an error while reading the file (Line:" + rows +"). Please check your file values. Exiting..");
                        System.exit(-1);
                    }
                }
                
                graphMatrix.add(temp);
                
            }
            
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (reader!=null){
                    reader.close();
                }
            } catch (IOException e){
                
            }
        }
               
        if (!matrixIsOk){
            System.out.println("Matrix is full of zeros! Please check your file values. Exiting..");
            System.exit(-1);
        }
        else if (totalValues!=rows*rows){
            System.out.println("Matrix is not square! Please check your file values. Exiting..");
            System.exit(-1);
        }
        else{
            //FIND HAMILTONIAN
            ArrayList<Character> charOfRows=null;
            charOfRows=findCharForArray(rows);
            
            ArrayList<ArrayList<StringBuilder>> firstCharMatrix=null;
            firstCharMatrix=createFirstCharMatrix(graphMatrix,rows,charOfRows);
            
            
            ArrayList<ArrayList<StringBuilder>> secondCharMatrix=null;
            secondCharMatrix=createSecondCharMatrix(graphMatrix,rows,charOfRows);
            
            
            if (rows==1){
                //print only first
                System.out.println("M");
                printCharMatrix(firstCharMatrix,rows);
            }
            else if (rows==2){
                //print only first and second
                System.out.println("M");
                printCharMatrix(firstCharMatrix,rows);
                System.out.println("M1");
                printCharMatrix(secondCharMatrix,rows);
            }
            else{
                System.out.println("M");
                printCharMatrix(firstCharMatrix,rows);
                System.out.println("M1");
                printCharMatrix(secondCharMatrix,rows);
                for (int j=2;j<rows;j++){
                    ArrayList<ArrayList<StringBuilder>> charMatrixNew=null;
                    charMatrixNew=multiplyMatrices(secondCharMatrix,firstCharMatrix,rows);
                    System.out.println("M"+j);
                    printCharMatrix(charMatrixNew,rows);
                    
                    secondCharMatrix=(ArrayList<ArrayList<StringBuilder>>) charMatrixNew.clone();
                }
            }
            
            System.out.println("Hamiltonian paths:");
            for (int i=0;i<rows;i++){
                for (int j=0;j<rows;j++){
                    if (!secondCharMatrix.get(i).get(j).toString().equals("0")){
                        System.out.print(secondCharMatrix.get(i).get(j).toString()+", ");
                    }
                }
            }
            System.out.println();
            System.out.println();
            System.out.println("Hamiltonian circles:"); //with the help of charOfRows we know the index for every character (e.g for C it's index 3)
            for (int i=0;i<rows;i++){
                for (int j=0;j<rows;j++){
                    if (!secondCharMatrix.get(i).get(j).toString().equals("0")){
                        int indexOfLastChar=charOfRows.indexOf(secondCharMatrix.get(i).get(j).toString().charAt(secondCharMatrix.get(i).get(j).toString().length()-1));
                        
                        if (firstCharMatrix.get(indexOfLastChar).toString().contains(Character.toString(secondCharMatrix.get(i).get(j).toString().charAt(0)))){
                            System.out.print(secondCharMatrix.get(i).get(j).toString()+", ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }
}
