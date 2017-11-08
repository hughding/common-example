package pers.hugh.common.util;

import java.io.*;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/9</pre>
 */
public class CloneUtil {

    public static Object cloneObj(Serializable obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        ByteArrayInputStream bais = null;
        try {
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(obj);

            bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bais);
            return in.readObject();
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException ex) {
                // ignore
            }
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException ex) {
                // ignore
            }
        }
    }
}
