package Dictionary;

import java.io.*;
import java.util.*;
import javafx.application.Platform;

/**
 * A Thread that contains the application we are going to animate
 *
 */
public class MisspellActionThread implements Runnable {

    DictionaryController controller;
    private final String textFileName;
    private final String dictionaryFileName;

    private LinesToDisplay myLines;
    private DictionaryInterface<String, String> myDictionary;
    private boolean dictionaryLoaded;

    /**
     * Constructor for objects of class MisspellActionThread
     *
     * @param controller
     */
    public MisspellActionThread(DictionaryController controller) {
        super();

        this.controller = controller;
        textFileName = "check.txt";
        dictionaryFileName = "sampleDictionary.txt";

        myDictionary = new HashedMapAdaptor<String, String>();
        myLines = new LinesToDisplay();
        dictionaryLoaded = false;

    }

    @Override
    public void run() {

        // ADD CODE HERE TO LOAD DICTIONARY

        loadDictionary(dictionaryFileName,myDictionary);
        Platform.runLater(() -> {
            if (dictionaryLoaded) {
               controller.SetMsg("The Dictionary has been loaded"); 
            } else {
               controller.SetMsg("No Dictionary is loaded"); 
            }
        });
        
        // ADD CODE HERE TO CALL checkWords


        checkWords(textFileName,myDictionary);
    }

    /**
     * Load the words into the dictionary.
     *
     * @param theFileName The name of the file holding the words to put in the
     * dictionary.
     * @param theDictionary The dictionary to load.
     */
    public void loadDictionary(String theFileName, DictionaryInterface<String, String> theDictionary) {
        Scanner input;
        try {
            String inString;
            String correctWord;

            input = new Scanner(new File(theFileName));
            
            // ADD CODE HERE TO READ WORDS INTO THE DICTIONARY     
            while(input.hasNext()){
                inString = input.next();
                correctWord = inString;
                theDictionary.add (inString,correctWord);
            }
            input.close();
            dictionaryLoaded = true;
            
            
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }

    }

    /**
     * Get the words to check, check them, then put Wordlets into myLines. When
     * a single line has been read do an animation step to wait for the user.
     *
     */
    public void checkWords(String theFileName, DictionaryInterface<String, String> theDictionary) {
        Scanner input;
        try {
            String inString;
            String aWord;

            input = new Scanner(new File(theFileName));
            // ADD CODE HERE    
            String OPERATORS = "\":?;!)([, .]\"";
            
            while (input.hasNextLine()) {
                inString = input.nextLine();
                StringTokenizer st = new StringTokenizer (inString, OPERATORS ,true);
                while (st.hasMoreTokens()){
                     
                    aWord = st.nextToken();
                    myLines.addWordlet(new Wordlet(aWord, checkWord(aWord,theDictionary)));
                }
                
                myLines.nextLine();
                showLines(myLines);
            }
            input.close();
            
            
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }

    }

    /**
     * Check the spelling of a single word.
     *
     */
    public boolean checkWord(String word, DictionaryInterface<String, String> theDictionary) {
        boolean result;
        String w = word.trim();
        // ADD CODE HERE    
        if(word.equals(",")||word.equals(".")||word.equals(":")||word.equals(";")||word.equals("?")||word.equals("!")||word.equals("(")||word.equals(")")||word.equals("\"")){
            result = true;
        }
        else if (theDictionary.contains(w)){
            
            result = true;
        }
        else{
            result = false;
        }
        

        return result;

    }

    private void showLines(LinesToDisplay lines) {
        try {
            Thread.sleep(500);
            Platform.runLater(() -> {
                if (myLines != null) {
                    controller.UpdateView(lines);
                }
            });
        } catch (InterruptedException ex) {
        }
    }

} // end class MisspellActionThread

