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
        if (input.equals("Close")) {
                return "closed";
        }
        //check path for validity
        File f = new File(input);
        if (f.exists()) {
                // Show dialog Warning of overwrite, this occurs when the file inputed alredy exists
                MessageDialogButton buttonResponse = new MessageDialogBuilder()
                                                     .setTitle("Warning")
                                                     .setText("Would you like to overwrite " + input)
                                                     .addButton(MessageDialogButton.No)
                                                     .addButton(MessageDialogButton.Yes)
                                                     .build()
                                                     .showDialog(gui);

                String warningInput = buttonResponse.toString();

                if (warningInput.equals("Yes")) {
                        System.out.println("Yes");
                        // user chose to overwrite. use f path to overwrite
                        // overwrite(f.getPath());
                        currentOpenFileString = f.getPath();
                        currentOpenFilePath = f.toPath();
                        return "overwrite";

                } else {
                        // user chose NOT to overwrite. start over
                        System.out.println("No");
                        askForFilePath(gui);
                }


        } else {
                // if no file exists attempt to create the file
                try {
                        f.createNewFile();
                        currentOpenFileString = f.getPath();
                        currentOpenFilePath = f.toPath();
                        loadFileIntoEditor(gui);
                        return "new";
                } catch (IOException ioe) {
                        //catch exception when path is invalid
                        // if Retry reopen the askForFilePath
                        // else close the dialog
                        MessageDialogButton buttonResponse = new MessageDialogBuilder()
                                                             .setTitle("Error")
                                                             .setText(input + " is not a valid path or filename")
                                                             .addButton(MessageDialogButton.Retry)
                                                             .addButton(MessageDialogButton.Close)
                                                             .build()
                                                             .showDialog(gui);

                        String invalidPathErrorInput = buttonResponse.toString();

                        if (invalidPathErrorInput.equals("Retry")) {
                                System.out.println("Retry");
                                askForFilePath(gui);
                        } else {
                                System.out.println("Close");
                                return "closed";
                                // exit dialog
                        }
                }
        }

        return "closed";
}

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

        loadFileIntoEditor(gui);

}

public static void newFile(MultiWindowTextGUI gui){
        // called from the newFile in ActionListDialogs
        String response = askForFilePath(gui);

        if (response.equals("overwrite")) {
                // load in empty String
                TerminalText.textBox.setText(new String(""));
                // update the lines Label
                updateLineCount();
                updateSavedTimeStamp();

        } else if (response.equals("new")) {

        } else {

        }

}



private static void loadFileIntoEditor(MultiWindowTextGUI gui){
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

private static void updateLineCount() {
        String lineCount = String.valueOf( TerminalText.textBox.getLineCount());
        TerminalText.linesLabel.setText("Lines: " + lineCount + " || ");
}

private static void updateSavedTimeStamp() {
        // get the current hour and minute
        String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        // get the filename from the currentOpenFilePath
        String filename = currentOpenFilePath.getFileName().toString();
        // Concatinate everything
        TerminalText.lastSave.setText( filename +" last saved at "+ timeStamp +" || ");
}



}
