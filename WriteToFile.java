import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
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
                Utils.updateSavedTimeStamp();
                // update the lines Label
                Utils.updateLineCount();

        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
}
}
