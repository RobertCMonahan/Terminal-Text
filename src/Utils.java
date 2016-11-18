import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Utils {

protected static String currentOpenFileString;
protected static Path currentOpenFilePath;


/**
 * This method is used to overwrite a file using the text
 * in the textBox.
 *
 * This method also updates the line count & last saved timestamp.
 *
 * @param filePath This is the filepath to be overwriten
 */
protected static void overwrite(MultiWindowTextGUI gui, String filePath, boolean useTextBox) {
        File file = new File(filePath);
        String source;
        FileWriter fw;
        if (useTextBox == true) {
                source = TerminalText.textBox.getText();
        } else {
                source = "";
        }

        try{
                fw = new FileWriter(file, false);         // load file into the FileWriter and set to overwrite (true for the 2nd arg set the FileWriter to append to the document)
                fw.write(source);
                fw.close();

                //update InfoBar
                InfoBar.updateAllInfo(currentOpenFilePath);
                Warning.savedDialog(gui, currentOpenFilePath);

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
protected static void openFile(MultiWindowTextGUI gui){
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

                loadFileIntoEditor(currentOpenFilePath);
        } else {
                // when cancel is selected do nothing
                // laterna automaticlly closes the dialog
        }
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
 * @param operation is used to determine if the user wants to create
 * a new file or save existing work as a certin file name. It changes a
 * few minor but important steps.
 */
protected static void newSaveAs(MultiWindowTextGUI gui, String operation){
        // called from the newFile in ActionListDialogs
        File file = askForFilePath(gui);
        if (file != null) {
                // test if file exists
                if (file.exists() ) {
                        // if file exists popup the overwriteWarning
                        boolean overwrite = Warning.overwriteWarning(gui, file);
                        if (overwrite == true) {
                                // user wants to overwrite file
                                currentOpenFileString = file.getPath();
                                currentOpenFilePath = file.toPath();
                                if (operation.equals("new")) {
                                        // empty file
                                        overwrite(gui, currentOpenFileString, false);
                                        loadFileIntoEditor(currentOpenFilePath);
                                } else {
                                        // saveas
                                        overwrite(gui, currentOpenFileString, true);
                                        Warning.savedDialog(gui, currentOpenFilePath);
                                }

                        } else {
                                // user dosent want to overwrite
                                // start over
                                newSaveAs(gui, operation);
                        }
                } else {
                        // file dosent exist
                        try {
                                file.createNewFile();
                                currentOpenFileString = file.getPath();
                                currentOpenFilePath = file.toPath();
                                if (operation.equals("new")) {
                                        // load a blank file into the editor
                                        loadFileIntoEditor(currentOpenFilePath);
                                } else {
                                        // if saveas
                                        // overwrite using the text in the editor
                                        overwrite(gui, currentOpenFileString, true);
                                        Warning.savedDialog(gui, currentOpenFilePath);
                                }
                        } catch (IOException ioe) {
                                System.out.println("IOException");
                                //catch exception when path is invalid
                                // call invalidPath Dialogs
                                boolean retry = Warning.invalidPathError(gui, file);
                                if (retry == true) {
                                        newSaveAs(gui, operation);
                                }
                        }
                }
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
 * @param Path filePath is the filepath of the file you would like
 * load into the editor
 */
protected static void loadFileIntoEditor(Path filePath){
        // read the file into the textBox
        // This method is only approprate for smallish Files
        try {
                TerminalText.textBox.setText( new String(Files.readAllBytes(filePath)) );
        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
        // update InfoBar
        InfoBar.updateAllInfo(filePath);
}

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

}
