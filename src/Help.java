import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


class Help {
/**
 * Displays the License for Terminal Text as a message dialog
 *
 * @param gui A MultiWindowTextGUI and is used to build various gui elements
 */
protected static void displayLicense(MultiWindowTextGUI gui){
        try {
                MessageDialogButton buttonResponse = new MessageDialogBuilder()
                                                     .setTitle("Terminal Text License")
                                                     .setText(new String(Files.readAllBytes(Paths.get("LICENSE"))))
                                                     .addButton(MessageDialogButton.Close)
                                                     .build()
                                                     .showDialog(gui);
        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
}

/**
 * Displays the FAQ for Terminal Text as a message dialog
 *
 * @param gui A MultiWindowTextGUI and is used to build various gui elements
 */
protected static void displayFAQ(MultiWindowTextGUI gui){
        try {
                MessageDialogButton buttonResponse = new MessageDialogBuilder()
                                                     .setTitle("Frequently Asked Questions")
                                                     .setText(new String(Files.readAllBytes(Paths.get("FAQ"))))
                                                     .addButton(MessageDialogButton.Close)
                                                     .build()
                                                     .showDialog(gui);
        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
}

/**
 * Displays the About info for Terminal Text as a message dialog
 *
 * @param gui A MultiWindowTextGUI and is used to build various gui elements
 */
protected static void displayAbout(MultiWindowTextGUI gui){
        try {
                MessageDialogButton buttonResponse = new MessageDialogBuilder()
                                                     .setTitle("About Terminal Text")
                                                     .setText(new String(Files.readAllBytes(Paths.get("ABOUT"))))
                                                     .addButton(MessageDialogButton.Close)
                                                     .build()
                                                     .showDialog(gui);
        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
}

}
