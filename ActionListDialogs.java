import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.TerminalSize;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class ActionListDialogs {
//Create Action List Dialogs

/**
 * This method builds the ActionListDialogBuilder, or the dropdown menu for
 * for the "File" button and includes the following buttons/actions
 * New File
 * Open File
 * Save
 * Save As...
 * Save and Quit
 * Quit
 *
 * @param gui A MultiWindowTextGUI and is used for various GUI elements
 * including but not limited to the dropdown menu itself
 */
public static void fileDialog(MultiWindowTextGUI gui){
        new ActionListDialogBuilder()
        .setTitle("File")
        .addAction("New File", new Runnable() {
                           @Override
                           public void run() {
                                   Utils.newSaveAs(gui, "new");
                           }
                   })
        .addAction("Open File", new Runnable() {
                           @Override
                           public void run() {
                                   Utils.openFile(gui);
                           }
                   })
        .addAction("Save", new Runnable() {
                           @Override
                           public void run() {
                                   Utils.overwrite(Utils.currentOpenFileString);
                           }
                   })
        .addAction("Save As...", new Runnable() {
                           @Override
                           public void run() {
                                   Utils.newSaveAs(gui, "saveas");
                                   // this line was causeing a crash not totally sure why yet, I should try this again though because ive changed alot for the askForFilePath
                                   //WriteToFile.overwrite(filepath);

                           }
                   })
        .addAction("Save and Quit", new Runnable() {
                           @Override
                           public void run() {

                                   // needs to check in its a new file or not so it can ask for a file path to save to
                                   Utils.overwrite(Utils.currentOpenFileString);
                                   TerminalText.shutdown();
                           }
                   })
        .addAction("Quit", new Runnable() {
                           @Override
                           public void run() {
                                   TerminalText.shutdown();
                           }
                   })
        .build()
        .showDialog(gui);
}


/**
 * This method builds the ActionListDialogBuilder, or the dropdown menu for
 * for the "Help" button and includes the following buttons/actions
 * View Terms Of Use
 * View Licence
 * FAQ
 * About Terminal Text
 *
 * @param gui A MultiWindowTextGUI and is used for various GUI elements
 * including but not limited to the dropdown menu itself
 */
public static void helpDialog(MultiWindowTextGUI gui){
        new ActionListDialogBuilder()
        .setTitle("Help")
        .addAction("View Terms Of Use", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 1st thing...
                           }
                   })
        .addAction("View Licence", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 2nd thing...
                           }
                   })
        .addAction("FAQ", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("About Terminal Text", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .build()
        .showDialog(gui);
}

}
