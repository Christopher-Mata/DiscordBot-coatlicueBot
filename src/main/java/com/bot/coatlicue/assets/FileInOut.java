package com.bot.coatlicue.assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileInOut {

    /**
     * Default file names.
     */
    private final String DEFAULTINFILENAME = "default_in.txt";
    private final String DEFAULTOUTFILENAME = "default_out.txt";

    /**
     * name of input file
     */
    private String inFilename;

    /**
     * name of output file
     */
    private String outFilename;

    /**
     * Name of the Scanner used for the input
     */
    private Scanner inputFile;

    /**
     * name of the PrintWriter that does the output
     */
    private PrintWriter outputFile;

    /**
     * Default Construction
     * <p>
     * This constructor makes a FileInOut Object using the constants information
     * and opens the files.
     * NOTE: I suppressed this warning because it wanted me to delete the method, but the default constructor is good
     * to have to test things out.
     */
    public FileInOut(){

        //Sets a default value to the class variables
        inFilename = DEFAULTINFILENAME;
        outFilename = DEFAULTOUTFILENAME;

        // This opens the default files when the default constructor is used
        openFiles();
    }

    /**
     * two argument constructor to instantiate a FileInOut object.
     * <p>
     * This constructor uses the provided input file names to set
     * the objects internal input. The files can also
     * be opened by passing TRUE as the openFile parameter.
     *
     * @param inFilename variable that contains the name of the file the user wants to read from
     * @param openFile variable that decides whether to open the files or not.
     */
    public FileInOut(String inFilename, boolean openFile)
    {
        //Sets the Class variables with Local Variables
        this.inFilename = inFilename;

        //Checks to see if the user wants the files open
        if(openFile){
            openFileIn();
        }
    }

    /**
     *
     * Three argument constructor to instantiate a FileInOut object.
     * <p>
     * This constructor uses the provided input and output file names to set
     * the objects internal input and output file names. The files can also
     * be opened by passing TRUE as the pOpenFlag parameter.
     * NOTE: I suppressed this warning because it wanted me to delete the method, but this method is crucial if
     * I ever want to come back to the game and make it output to a txt document instead of the console.
     *
     * @param inFilename variable that contains the name of the file the user wants to read from
     * @param outFileName variable that contains the name of the file the user want to write from
     * @param openFile variable that decides whether to open the files or not.
     */
    public FileInOut (String inFilename, String outFileName, boolean openFile)
    {
        //Sets the Class variables with Local Variables
        this.inFilename = inFilename;
        this.outFilename = outFileName;

        //Checks to see if the user wants the files open
        if(openFile){
            openFiles();
        }
    }

    /**
     * Meta-method that opens both the input file and the output file.
     */
    public void openFiles(){

        // This calls the other methods to open the files
        this.openInFiles();
        this.openOutFiles();
    }

    public void openFileIn(){

        // This calls the other methods to open the files
        this.openInFiles();
    }


    /**
     * Opens the input file for input using a Scanner.
     * <p>
     * This method opens the file whose name is specified in the class level
     * variable inFilename.  The length is checked to ensure the variable has
     * content.  The file is opened via the Java Scanner class.
     */
    public void openInFiles(){

        //Checks to see if inFilename has a file name in it
        if(inFilename.length() >= 1) {

            // Creates the file instance of the input File
            File file = new java.io.File(inFilename);

            // Checks to see if the file exist and if it does it implement a scanner.
            // If it does not then a message prints to the console
            if (file.exists()) {
                try {
                    this.inputFile = new Scanner(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("File does not exist!!!");
            }
        }
    }

    /**
     * Opens the output file for output using a PrintWriter.
     * <p>
     * This method opens the file whose name is specified in the class level
     * variable outFilename.  The length is checked to ensure the variable has
     * content.  The file is opened via the Java PrintWriter class.
     */
    public void openOutFiles(){

        // This checks if the output has a filename
        if(outFilename.length() > 1) {
            // This creates the new file instance for the output file
            File file = new java.io.File(outFilename);

            // This checks too see if the file exists and if it does it implement a new PrintWriter instance.
            // If it does not exist it prints a message into the console
            if (file.exists()) {
                try {
                    this.outputFile = new PrintWriter(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Output File does not exist!!!");
            }
        }
    }

    /**
     * Meta-method to close all the open files.
     * NOTE: I suppressed this warning because it wanted me to delete the method, but this method is crucial if
     * I ever want to come back to the game and make it output to a txt document instead of the console.
     */
    public void closeFiles()
    {

        // This calls other methods to close the files to reduce an information leak
        this.closeInFile();
        this.closeOutFile();
    }

    /**
     * This closes the input file
     */
    public void closeInFile(){
        inputFile.close();
    }

    /**
     * This closes the output file
     */
    public void closeOutFile(){
        outputFile.close();
    }

    /**
     * Returns the input file name
     * NOTE: I suppressed this warning because it wanted me to delete the method, but it is generally good to have
     * getters and setters for all the class variables(or so I was taught)
     * @return infileName
     */
    public String getInFilename(){
        return inFilename;
    }

    /**
     * Returns the output file name
     * NOTE: I suppressed this warning because it wanted me to delete the method, but it is generally good to have
     * getters and setters for all the class variables(or so I was taught)
     * @return outFileName
     */
    public String getOutFilename(){
        return outFilename;
    }

    /**
     * sets the output file name
     * NOTE: I suppressed this warning because it wanted me to delete the method, but it is generally good to have
     * getters and setters for all the class variables(or so I was taught)
     * @param outFilename variable that holds the name for the new output file that the user want to be written.
     */
    public void setOutFilename(String outFilename){
        this.outFilename = outFilename;
    }

    /**
     * sets the input file name
     * NOTE: I suppressed this warning because it wanted me to delete the method, but it is generally good to have
     * getters and setters for all the class variables(or so I was taught)
     * @param inFilename variable that holds the new file the user wants to read from.
     */
    public void setInFilename(String inFilename){
        this.inFilename =  inFilename;
    }

    /**
     * Returns the Scanner for the input file.
     * NOTE: I suppressed this warning because it wanted me to delete the method, but it is generally good to have
     * getters and setters for all the class variables(or so I was taught)
     * @return inputFile
     */
    public Scanner getInFile(){
        return inputFile;
    }

    /**
     * Returns the output PrintWrite
     * NOTE: I suppressed this warning because it wanted me to delete the method, but it is generally good to have
     * getters and setters for all the class variables(or so I was taught)
     * @return outputFile
     */
    public PrintWriter getOutFile(){
        return outputFile;
    }
}
