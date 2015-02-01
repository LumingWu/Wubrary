package Binary;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Little loader and saver.
 *
 * @author Luming
 */
public class BoardBinaryManager2D {

    public ArrayList<ArrayList<Integer>> readFile(String path) {
        File file = new File(path);
        try {
            if (file != null) {
                byte[] bytes = new byte[Long.valueOf(file.length()).intValue()];
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);

                bis.read(bytes);
                bis.close();

                DataInputStream dis = new DataInputStream(bais);

                int x = dis.readInt();
                int y = dis.readInt();
                ArrayList columns = new ArrayList(x);
                for (int i = 0; i < x; i++) {
                    ArrayList rows = new ArrayList(y);
                    for (int j = 0; j < y; j++) {
                        rows.add(dis.readInt());
                    }
                    columns.add(rows);
                }
                return columns;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveFile(ArrayList<ArrayList<Integer>> data, String path) {
        File file = new File(path);
        try {
            if (file != null) {
                FileOutputStream fos;
                fos = new FileOutputStream(path);
                DataOutputStream dos = new DataOutputStream(fos);
                
                int x = data.size();
                int y = data.get(0).size();
                
                dos.write(x);
                dos.write(y);
                
                for(int i = 0; i < x; i++){
                    for(int j = 0; j < y; j++){
                        dos.write(data.get(x).get(y));
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BoardBinaryManager2D.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BoardBinaryManager2D.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
