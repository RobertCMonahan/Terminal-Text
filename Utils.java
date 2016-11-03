import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Utils {

public static String currentOpenFileString;
public static Path currentOpenFilePath;

/**
 * This method is used to prompt the user for a Filepath using
 * a TextInputDialog. If only a file name is entered it uses
 * the present working directory to complete the Filepath.
 * the returned File is not checked for validity
 *
 * @param gui A MultiWindowTextGUI and is used to build the TextInputDialog
 * @return File This returns whatever the user inputed as a File
 */
private static File askForFilePath(MultiWindowTextGUI gui){
        // get the present working directory
        String pwd = Paths.get(".").toAbsolutePath().normalize().toString();
        // open dialog asking for filePath'
        String input = new TextInputDialogBuilder()
                       .setTextBoxSize(new TerminalSize(70,1))
                       .setTitle("Enter the Filename or Filepath you would like to save as")
                       .setDescription("the present working directory is\n" + pwd)
                       .build()
                       .showDialog(gui);

        // when cancel is not selected (cancel == null)
        if (input != null) {
                return new File(input);
        }
        return null;

}

/**
 * This method is used to overwrite a file using the text
 * in the textBox.
 *
 * This method also updates the line count & last saved timestamp.
 *
 * @param filePath This is the filepath to be overwriten
 */
public static void overwrite(String filePath) {
        File file = new File(filePath);
        String source =  TerminalText.textBox.getText();
        FileWriter fw;

        try{
                fw = new FileWriter(file, false); // load file into the FileWriter and set to overwrite (true for the 2nd arg set the FileWriter to append to the document)
                fw.write(source);
                fw.close();

                //update the lastsaved timeStamp
                updateSavedTimeStamp();
                // update the lines Label
                updateLineCount();

        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
}


/**
 * This method is used to open a file and loads it into the textBox
 * using the loadFileIntoEditor method. openFile builds a FileDialog
 * the prompts the user to choose a file to open. The Choosen file is
 * used to alter the currentOpenFile (path & string).
 *
 * @param gui A MultiWindowTextGUI and is used to build the FileDialogBuilder
 */
public static void openFile(MultiWindowTextGUI gui){
        File choosenFile = new FileDialogBuilder()
                           .setTitle("Open File")
                           .setDescription("Choose a file")
                           .setActionLabel("Open")
                           // user  getTerminalSize() and base the openfile size off the current terminal size.
                           .setSuggestedSize(new TerminalSize(100,20))
                           .build()
                           .showDialog(gui);


        // when cancel is not selected (cancel == null)
        if (choosenFile != null) {
                // get path as a String
                currentOpenFileString = choosenFile.getPath();
                // gets path as a Path
                currentOpenFilePath = choosenFile.toPath();

                loadFileIntoEditor();
        } else {
                // when cancel is selected do nothing
                // laterna automaticlly closes the dialog
        }
}

/**
 * This method is used to load a file into the textBox by
 * reading the file into a string and then setting the
 * textBox as the string.
 *
 * The major flaw is that then entire file is stored in memory
 * before it's loaded into the textBox so this will not work
 * with files to large to store in memory.
 *
 * This method also updates the line count & last saved timestamp.
 */
private static void loadFileIntoEditor(){
        // read the file into the textBox
        // This method is only approprate for smallish Files
        try {
                TerminalText.textBox.setText(new
                                             String(Files.readAllBytes(Paths.get(currentOpenFileString))) );
        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
        // update the lines Label
        updateLineCount();
        updateSavedTimeStamp();

}

/**
 * This method counts the lines in the editor and updates
 * TerminalText.linesLabel with the current count
 */
private static void updateLineCount() {
        String lineCount = String.valueOf( TerminalText.textBox.getLineCount());
        TerminalText.linesLabel.setText("Lines: " + lineCount + " || ");
}

/**
 * This method gets the current time HH:mm and updates
 * TerminalText.lastSave with the current time
 */
private static void updateSavedTimeStamp() {
        // get the current hour and minute
        String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        // get the filename from the currentOpenFilePath
        String filename = currentOpenFilePath.getFileName().toString();
        // Concatinate everything
        TerminalText.lastSave.setText( filename +" last saved at "+ timeStamp +" || ");
}

/**
 * This method opens a error dialog stating the users file path was invalid
 *
 * @param gui A MultiWindowTextGUI and is used to build the dialog
 * @param f Is a filepath that is used in the text so the user can
 * see what their input was.
 * @return boolean If the user selects yes (wants to overwrite)
 * true is returned. And false if no is selected.
 */
private static boolean overwriteWarning(MultiWindowTextGUI gui, File f){
        MessageDialogButton buttonResponse = new MessageDialogBuilder()
                                             .setTitle("Warning")
                                             .setText("Would you like to overwrite " + f )
                                             .addButton(MessageDialogButton.No)
                                             .addButton(MessageDialogButton.Yes)
                                             .build()
                                             .showDialog(gui);

        String warningInput = buttonResponse.toString();

        if (warningInput.equals("Yes")) {
                // user wants to overwrite file
                return true;
        }
        // user dosent want to overwrite
        return false;
}

/**
 * This method opens a error dialog stating the users file path was invalid
 *
 * @param gui A MultiWindowTextGUI and is used to build the dialog
 * @param f Is a filepath that is used in the text so the user can
 * see what their input was.
 * @return boolean If the user selects retry (entering a filepath)
 * true is returned. And false if close is selected.
 */
private static boolean invalidPathError(MultiWindowTextGUI gui, File f){
        MessageDialogButton buttonResponse = new MessageDialogBuilder()
                                             .setTitle("Error")
                                             .setText(f + " is not a valid path or filename")
                                             .addButton(MessageDialogButton.Retry)
                                             .addButton(MessageDialogButton.Close)
                                             .build()
                                             .showDialog(gui);

        String invalidPathErrorInput = buttonResponse.toString();

        if (invalidPathErrorInput.equals("Retry")) {
                // user chose to retry
                return true;
        }
        // user chose to close dialog
        return false;
}

/**
 * This method is used to create a new file or save as a file
 *
 * The user is asked for a file path for the file they want to
 * create/save as. Then the path is checked to see if it already
 * exists, if it does then a overwrite Warning is called, and the
 * user can decide if they want to overwrite the existing file.
 * Then the path is checked to ensure it is a valid path, if the
 * path is valid the file is created and saved to. If it is not
 * valid the path the user is prompted for a new path.
 *
 * @param gui A MultiWindowTextGUI and is used to build various gui elements
 * @param newOrSaveAs is used to determine if the user wants to create
 * a new file or save existing work as a certin file name. It changes a
 * few minor but important steps.
 */
public static void newSaveAs(MultiWindowTextGUI gui, String newOrSaveAs){
        // called from the newFile in ActionListDialogs
        File file = askForFilePath(gui);
        if (file != null) {
                // test if file exists
                if (file.exists()) {
                        // if file exists popup the overwriteWarning
                        boolean overwrite = overwriteWarning(gui, file);
                        if (overwrite == true) { // user wants to overwrite file
                                currentOpenFileString = file.getPath();
                                currentOpenFilePath = file.toPath();
                                if (newOrSaveAs.equals("new")) {
                                } else { // user dosent want to overwrite
                                         // start over
                                        newSaveAs(gui, newOrSaveAs);
                                }
                        } else {
                                try {
                                        file.createNewFile();
                                        currentOpenFileString = file.getPath();
                                        currentOpenFilePath = file.toPath();
                                        if (newOrSaveAs.equals("new")) {
                                                loadFileIntoEditor();
                                        } else {
                                                overwrite(currentOpenFileString);
                                        }
                                } catch (IOException ioe) {
                                        //catch exception when path is invalid
                                        // call invalidPath Dialogs
                                        boolean retry = invalidPathError(gui, file);
                                        if (retry == true) {
                                                newSaveAs(gui, newOrSaveAs);
                                        }
                                }
                        }
                }
        }
}

/*
 * identifyFileTypeUsingFilesProbeContentType was written in the "Inspired
 * by Actual Events" blog by Dustin Marx, on Wednesday, February 18, 2015.
 * Under the title "Determining File Types in Java," and is licensed under
 * a Creative Commons Attribution 4.0 International License.
 *
 * This code and blog post can found in it's entirety, at URL below.
 * https://marxsoftware.blogspot.com/2015/02/determining-file-types-in-java.html
 *
 */

/**
 * Identify file type of file with provided path and name
 * using JDK 7's Files.probeContentType(Path).
 *
 * @param fileName Name of file whose type is desired.
 * @return String representing identified type of file with provided name.
 */
public String identifyFileTypeUsingFilesProbeContentType(final String fileName)

{
        String fileType = "Undetermined";
        final File file = new File(fileName);
        try
        {
                fileType = Files.probeContentType(file.toPath());
        }
        catch (IOException ioException)
        {
                System.out.println(
                        "ERROR: Unable to determine file type for " + fileName
                        + " due to exception " + ioException);
        }
        return fileType;
}

public static void setFileTypeIntoInfoBar(){





}


}
