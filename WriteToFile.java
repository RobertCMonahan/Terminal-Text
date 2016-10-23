import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.nio.file.Path;

public class WriteToFile {
public static void overwrite(String filePath) {
// use -> getText()
        File file = new File(filePath);
        String source =  TerminalText.textBox.getText();
        FileWriter fw;

        try{
                fw = new FileWriter(file, false); // load file into the FileWriter and set to overwrite (true for the 2nd arg set the FileWriter to append to the document)
                fw.write(source);
                fw.close();

                //update the lastsaved timeStamp
                String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                String filename = ActionListDialogs.currentOpenFilePath.getFileName().toString();
                TerminalText.lastSave.setText( filename +" last saved at "+ timeStamp +" || ");
                // update the lines Label
                TerminalText.linesLabel.setText("Lines: " + String.valueOf( TerminalText.textBox.getLineCount()) + " || ");

        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
}
}
