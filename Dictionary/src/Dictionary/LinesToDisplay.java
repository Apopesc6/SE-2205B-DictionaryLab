package Dictionary;

import java.util.Iterator;


/**
 * A class that will be used to display the lines of text that are corrected.
 *
 */
public class LinesToDisplay {

    public static final int LINES = 10;     // Display 10 lines
    private AList<Wordlet>[] lines;
    private int currentLine;

    /**
     * Constructor for objects of class LinesToDisplay
     */
    public LinesToDisplay() {
        //ADD CODE FOR THE CONSTRUCTOR
        lines = (AList<Wordlet>[]) new AList[LINES + 1];
        for(int i = 0; i <LINES+1; i++){
            lines[i] = new AList<>();
        }
        currentLine = 1;
    }

    public void addWordlet(Wordlet w) {
        //ADD CODE HERE TO ADD A WORDLET TO THE CURRENT LINE
        lines[currentLine].add(w);
        
    }
    public void nextLine() {
        //ADD CODE TO HANDLE THE NEXT LINE
        if (currentLine == LINES) {
            
            for (int i = 0; i < LINES; i++) {
                lines[i] = lines[i + 1];
            }
            lines[10] = new AList<>();
        }
        else{
            currentLine++;
        }
        
    }

      
    public int getCurrentLine(){
        return currentLine;
    }
    
    public AList<Wordlet>[] getLines(){
        return lines;
    }
}
