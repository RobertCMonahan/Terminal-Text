import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;

public class TerminalText {

protected static Label lastSaveLabel;
protected static Label linesLabel;
protected static Label fileTypeLabel;
protected static TextBox textBox =  new TextBox("", TextBox.Style.MULTI_LINE);;

private static Screen screen;
private static Terminal terminal;
private static BasicWindow window;

/**
 * The main method handles building the gui and setting up the window.
 * It also adds a number of components that will always be displayed
 * such as the top buttons and the bottom info bar.
 *
 */
public static void main(String[] args) throws IOException {
        // Setup terminal and screen layers
        terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));

        // Create window to hold the panel
        window = new BasicWindow("Terminal Text");
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new BorderLayout());

        // Sets the textbox to be in the center of the screen
        //TextBox textBox = new TextBox("", TextBox.Style.MULTI_LINE);
        textBox.setLayoutData(BorderLayout.Location.CENTER);
        mainPanel.addComponent(textBox);

        // Begin Top Pannel Stuff //
        Panel topPanel = new Panel();
        topPanel.setLayoutData(BorderLayout.Location.TOP);
        topPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        // Create buttons
        Button fileButton = new Button("File", new Runnable(){
                                               @Override
                                               public void run(){
                                                       // Actions go here
                                                       // call the list drop down thing
                                                       ActionListDialogs.fileDialog(gui);
                                               }
                                       });

        Button helpButton = new Button("Help", new Runnable(){
                                               @Override
                                               public void run(){
                                                       ActionListDialogs.helpDialog(gui);
                                               }
                                       });

        // add all components for the top Pannel
        topPanel.addComponent(fileButton);
        topPanel.addComponent(helpButton);

        mainPanel.addComponent(topPanel.withBorder(Borders.singleLine()));
        // End Top Pannel Stuff //

        // Begin Bottom Pannel Stuff //
        Panel bottomPanel = new Panel();
        bottomPanel.setLayoutData(BorderLayout.Location.BOTTOM);
        bottomPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        linesLabel = new Label("");
        lastSaveLabel = new Label("");
        fileTypeLabel = new Label("");
        bottomPanel.addComponent(linesLabel);
        bottomPanel.addComponent(lastSaveLabel);
        bottomPanel.addComponent(fileTypeLabel);

        mainPanel.addComponent(bottomPanel.withBorder(Borders.singleLine("")));
        // End Bottom Pannel Stuff //

        window.setComponent(mainPanel);

        Tabs.createInitalFile();
        
        //start gui
        gui.addWindowAndWait(window);

}

/**
 * This method shuts down laterna and the text editor
 */
protected static void shutdown(){
        window.close();
        /* not sure if these are nesscery but others used
         * this to show down laterna, but they cause an
         * IOException everytime saying I cant call
         * exitPrivateMode() when not in private mode.
         *
         * screen.stopScreen();
         * terminal.exitPrivateMode();
         *
         */
        System.exit(0);
}
}
