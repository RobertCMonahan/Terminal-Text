import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.TerminalSize;
import java.io.File;
import java.nio.file.Path;


class ActionListDialogs {
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
                                   Utils.newSaveAs(gui, "save");
                                   //Utils.overwrite(gui, Utils.currentOpenFileString, true);
                           }
                   })
        .addAction("Save As...", new Runnable() {
                           @Override
                           public void run() {
                                   Utils.newSaveAs(gui, "saveas");
                           }
                   })
        .addAction("Save and Quit", new Runnable() {
                           @Override
                           public void run() {
                                   // needs to check in its a new file or not so it can ask for a file path to save to
                                   Utils.overwrite(gui, Utils.currentOpenFileString, true);

                                   // asks the user if they really do want to exit
                                   boolean userExit = Warning.quitWarning(gui);
                                   if (userExit == true) {
                                           TerminalText.shutdown();
                                   }
                           }
                   })
        .addAction("Quit", new Runnable() {
                           @Override
                           public void run() {
                                   // asks the user if they really do want to exit
                                   boolean userExit = Warning.quitWarning(gui);
                                   if (userExit == true) {
                                           TerminalText.shutdown();
                                   }


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
        .addAction("View Licence", new Runnable() {
                           @Override
                           public void run() {
                                   Help.displayLicense(gui);
                           }
                   })
        .addAction("FAQ", new Runnable() {
                           @Override
                           public void run() {
                                   Help.displayFAQ(gui);
                           }
                   })
        .addAction("About Terminal Text", new Runnable() {
                           @Override
                           public void run() {
                                   Help.displayAbout(gui);
                           }
                   })
        .build()
        .showDialog(gui);
}

}
