package Libraries;

import java.io.File;

//FileFilter class for the JFileChooser (mp3 files)

public class FileFilter extends javax.swing.filechooser.FileFilter {
    
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".mp3");
    }
    
    @Override
    public String getDescription() {
        return "MP3 Files";
    }
}