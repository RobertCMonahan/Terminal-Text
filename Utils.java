import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.TerminalSize;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;


public class Utils {
public static String currentOpenFileString;
public static Path currentOpenFilePath;

public static void openFile(MultiWindowTextGUI gui){
        File choosenFile = new FileDialogBuilder()
                           .setTitle("Open File")
                           .setDescription("Choose a file")
                           .setActionLabel("Open")
                           // user  getTerminalSize() and base the openfile size off the current terminal size.
                           .setSuggestedSize(new TerminalSize(100,20))
                           .build()
                           .showDialog(gui);
// get path as a String
        currentOpenFileString = choosenFile.getPath();
// gets path as a Path
        currentOpenFilePath = choosenFile.toPath();
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

public static void updateLineCount() {
        String lineCount = String.valueOf( TerminalText.textBox.getLineCount());
        TerminalText.linesLabel.setText("Lines: " + lineCount + " || ");
}

public static void updateSavedTimeStamp() {
        // get the current hour and minute
        String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        // get the filename from the currentOpenFilePath
        String filename = currentOpenFilePath.getFileName().toString();
        // Concatinate everything
        TerminalText.lastSave.setText( filename +" last saved at "+ timeStamp +" || ");
}

public static String askForFilePath(MultiWindowTextGUI gui){

        // get the present working directory
        String pwd = Paths.get(".").toAbsolutePath().normalize().toString();
        // open dialog asking for filePath'
        String input = new TextInputDialogBuilder()
                       .setTextBoxSize(new TerminalSize(70,1))
                       .setTitle("Enter the Filename or Filepath you would like to save as")
                       .setDescription("the present working directory is\n" + pwd)
                       .build()
                       .showDialog(gui);
        //check path for validity
        File f = new File(input);
        if (f.exists()) {
                // figure out how to get the input out of the message dialogs
                // its enum data but im not sure how to get it from the dialog
                // if test is selected then save over the file
                // else reopen the askForFilePath
                MessageDialogButton warningInput = new MessageDialogBuilder()
                                                   .setTitle("Warning")
                                                   .setText("Would you like to overwrite" + input)
                                                   .addButton(MessageDialogButton.Yes)
                                                   .addButton(MessageDialogButton.No)
                                                   .build()
                                                   .showDialog(gui);

        } else {
                try {
                        f.createNewFile();
                } catch (IOException ioe) {
                        //catch exception when path is invalid
                        // if Retry reopen the askForFilePath
                        // else close the dialog
                        new MessageDialogBuilder()
                        .setTitle("Error")
                        .setText(input + " is not a valid path or filename")
                        .addButton(MessageDialogButton.Retry)
                        .addButton(MessageDialogButton.Close)
                        .build()
                        .showDialog(gui);
                }
        }

        return input;
}


}
