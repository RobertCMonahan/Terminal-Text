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
public static void fileDialog(MultiWindowTextGUI gui){
        new ActionListDialogBuilder()
        .setTitle("File")
        .addAction("New File", new Runnable() {
                           @Override
                           public void run() {
                                   Utils.newFile(gui);
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
                                   Utils.askForFilePath(gui);
                                   // this line was causeing a crash not totally sure why yet, I should try this again though because ive changed alot for the askForFilePath
                                   //WriteToFile.overwrite(filepath);

                           }
                   })
        .addAction("Save and Quit", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("Quit", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .build()
        .showDialog(gui);
}



public static void editDialog(MultiWindowTextGUI gui){
        new ActionListDialogBuilder()
        .setTitle("Edit")
        .addAction("Undo", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 1st thing...
                           }
                   })
        .addAction("Redo", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 2nd thing...
                           }
                   })
        .addAction("Cut", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("Copy", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("Paste", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("Select All", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("Preferences", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .build()
        .showDialog(gui);
}



public static void viewDialog(MultiWindowTextGUI gui){
        new ActionListDialogBuilder()
        .setTitle("View")
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 1st thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 2nd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .build()
        .showDialog(gui);
}



public static void findDialog(MultiWindowTextGUI gui){
        new ActionListDialogBuilder()
        .setTitle("Find")
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 1st thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 2nd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .build()
        .showDialog(gui);
}



public static void packagesDialog(MultiWindowTextGUI gui){
        new ActionListDialogBuilder()
        .setTitle("Packages")
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 1st thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 2nd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .build()
        .showDialog(gui);
}



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
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .addAction("?", new Runnable() {
                           @Override
                           public void run() {
                                   // Do 3rd thing...
                           }
                   })
        .build()
        .showDialog(gui);
}

}
