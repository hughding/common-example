package pers.hugh.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/9</pre>
 */
public class CloneUtil {

    private static final Logger logger = LoggerFactory.getLogger(CloneUtil.class);

    public static Object cloneObj(Serializable obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        ByteArrayInputStream bais = null;
        try {
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(obj);

            bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bais);
            return in.readObject();
        } catch (Exception e) {
            logger.error("ObjectInputStream readObject error", e);
            return null;
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                // ignore
                logger.error("ByteArrayInputStream close error", e);
            }
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                // ignore
                logger.error("ByteArrayOutputStream close error", e);
            }
        }
    }
}
