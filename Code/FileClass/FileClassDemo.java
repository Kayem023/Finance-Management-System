package FileClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileClassDemo {

    String filename;

    public FileClassDemo(String filename) {
        this.filename = filename;
    }

    public void open() throws IOException {
        if (filename.startsWith("AllFile")) {
            try {
                File file = new File(filename);
                file.createNewFile();
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        } else {
            System.out.println(filename);
            final File parentFile = new File("AllFile");
            parentFile.mkdir();
            final String st = filename.replace("*", "_");
            final File file = new File(parentFile, st);
            file.createNewFile();
        }
    }

    public void appendwrite(String data) {
        try {

            FileWriter fw = new FileWriter(filename, true);
            fw.write(data);
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public String read() {
        FileReader fr = null;
        char data[] = null;
        try {
            File f = new File(filename);
            fr = new FileReader(f);
            data = new char[(int) f.length()];
            fr.read(data);
            System.out.println(new String(data));
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileClassDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileClassDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(FileClassDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new String(data);
    }

}
