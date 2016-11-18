import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;


class Tabs {
protected static void createInitalFile(){
        try {
                Path tempDir = Files.createTempDirectory("temp");
                Path untitled = Files.createTempFile(tempDir, "untitled", ".txt");
                Utils.loadFileIntoEditor(untitled);

        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
}
}
