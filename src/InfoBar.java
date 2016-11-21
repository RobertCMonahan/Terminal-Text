import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class InfoBar {


/**
 * This Method updates the infobar at the bottom of the
 * editor. This includes the line count, last saved time
 * stamp and the filetype.
 *
 * @param Path filePath is the path of the file you would
 * like to use to update the values, this should always be
 * the same file this is currently open in the editor.
 */
protected static void updateAllInfo(Path filePath){
        updateFileType(filePath);
        updateLineCount();
        updateSavedTimeStamp(filePath);
}

/**
 * This method counts the lines in the editor and updates
 * TerminalText.linesLabel with the current count
 */
private static void updateLineCount() {
        String lineCount = String.valueOf( TerminalText.textBox.getLineCount());
        TerminalText.linesLabel.setText("Lines: " + lineCount + "  || ");
}

/**
 * This method gets the current time HH:mm and updates
 * TerminalText.lastSave with the current time
 *
 * @param Path filePath is the path of the file you would
 * like to use to update the last saved time stamp, this
 * should always be the same file this is currently open
 * in the editor.
 */
private static void updateSavedTimeStamp(Path filePath) {
        // get the current hour and minute
        String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        // get the filename from the currentOpenFilePath
        String filename = filePath.getFileName().toString();

        // if the files is a temp untitled file remove the string of numbers and just call it untitled
        Pattern p = Pattern.compile("\\Auntitled\\d{1,}.*");
        Matcher m = p.matcher(filename);
        boolean matchFound = m.matches();
        if (matchFound == true) {
                TerminalText.lastSaveLabel.setText( "untitled || ");
        } else {
                // Concatinate everything
                TerminalText.lastSaveLabel.setText( filename +" last saved at "+ timeStamp +" || ");
        }
}

/*
 * identifyFileTypeUsingFilesProbeContentType was written in the "Inspired
 * by Actual Events" blog by Dustin Marx, on Wednesday, February 18, 2015.
 * Under the title "Determining File Types in Java," and is licensed under
 * a Creative Commons Attribution 4.0 International License.
 *
 * This code and blog post can found in it's entirety, at the URL below.
 * https://marxsoftware.blogspot.com/2015/02/determining-file-types-in-java.html
 */

/**
 * Identify file type of file with provided path and name
 * using JDK 7's Files.probeContentType(Path).
 *
 * @param fileName Name of file whose type is desired.
 * @return String representing identified type of file with provided name.
 */
private static String identifyFileTypeUsingFilesProbeContentType(String fileName){

        String fileType = "Undetermined";
        final File file = new File(fileName);
        try
        {
                fileType = Files.probeContentType(file.toPath());
        }
        catch (IOException ioException)
        {
                System.out.println(
                        "ERROR: Unable to determine file type for " + fileName
                        + " due to exception " + ioException);
        }

        return fileType;
}

/**
 * Identify file type of file with provided path and name
 * and display the file type in the bottomPanel
 *
 * @param fileName Name of file whose type is desired.
 */
//@SuppressWarnings("unchecked")
private static void updateFileType(Path filepath){

        String fileName = filepath.toString();
        String fileTypeStr = identifyFileTypeUsingFilesProbeContentType(fileName);

        HashMap<String, String> hmap = new HashMap<String, String>();
        // Put elements to the map
        hmap.put("text/plain", "Plain Text");
        hmap.put("text/markdown", "Github Markdown");
        hmap.put("text/x-java", "Java");
        hmap.put("text/x-python", "Python");
        hmap.put("text/html", "HTML");
        hmap.put("text/xml", "XML");
        hmap.put("application/pdf", "PDF");
        hmap.put("application/x-java-archive", "Java Archive");
        hmap.put("application/x-shellscript", "Shell Script");
        hmap.put("application/javascript", "Javascript");

        // convert to more readable format
        String fileTypeReformated = hmap.get(fileTypeStr);

        if (fileTypeReformated == null) {
                // if the file type isn't found in the HashMap use the non formated file type
                TerminalText.fileTypeLabel.setText( fileTypeStr +"  || ");
        } else {
                TerminalText.fileTypeLabel.setText( fileTypeReformated +"  || ");
        }


}
}
