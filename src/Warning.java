import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import java.io.File;
import java.nio.file.Path;


class Warning {
/**
 * This method opens a error dialog stating the users file path was invalid
 *
 * @param gui A MultiWindowTextGUI and is used to build the dialog
 * @param f Is a filepath that is used in the text so the user can
 * see what their input was.
 * @return boolean If the user selects yes (wants to overwrite)
 * true is returned. And false if no is selected.
 */
protected static boolean overwriteWarning(MultiWindowTextGUI gui, File f){
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
 * This method opens when a user selects quit from the file menu
 *
 * @param gui A MultiWindowTextGUI and is used to build the dialog
 *
 * @return boolean If the user selects yes (wants to quit without saving)
 * true is returned. And false if no is selected.
 */
protected static boolean quitWarning(MultiWindowTextGUI gui){
        // create MessageDialogButton what ask the user if they really want to quit
        MessageDialogButton buttonResponse = new MessageDialogBuilder()
                                             .setTitle("")
                                             .setText("Are you sure you would like to Quit?")
                                             .addButton(MessageDialogButton.No)
                                             .addButton(MessageDialogButton.Yes)
                                             .build()
                                             .showDialog(gui);

        String warningInput = buttonResponse.toString();

        if (warningInput.equals("Yes")) {
                // user wants to quit
                return true;
        }
        // user dosent want to quit
        return false;
}

/**
 * This method opens a popup dialog telling the user that a file has been saved
 *
 * @param gui A MultiWindowTextGUI and is used to build the dialog
 *
 * @param filePath Path is the Path of the file that has just been saved.
 */
protected static void savedDialog(MultiWindowTextGUI gui, Path filePath){

        String fileName = filePath.getFileName().toString();

        MessageDialogButton buttonResponse = new MessageDialogBuilder()
                                             .setTitle("")
                                             .setText(fileName +" saved")
                                             .addButton(MessageDialogButton.OK)
                                             .build()
                                             .showDialog(gui);

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
protected static boolean invalidPathError(MultiWindowTextGUI gui, File f){
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



}
