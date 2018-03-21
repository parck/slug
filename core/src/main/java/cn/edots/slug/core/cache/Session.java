package cn.edots.slug.core.cache;

import android.support.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Parck.
 * @date 2017/9/30.
 * @desc
 */
public class Session {
    protected static AppCachePool<String, Object> session = AppCachePool.getInstance().newTAG(Session.class.getSimpleName());
    protected static final StringBuilder path = new StringBuilder("/persistence/");
    protected static final String suffix = ".ser";

    public static Object getAttribute(String key) {
        if (session.get(key) == null) session.put(key, read(key));
        return session.get(key);
    }

    public static void setAttribute(String key, Object value) {
        session.put(key, value);
    }

    public static void setAttribute(Object value) {
        setAttribute(value.getClass().getSimpleName(), value);
    }

    public static void write(String key, Serializable o) {
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;
        try {
            fileOut = new FileOutputStream(getPath(key));
            out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            setAttribute(key, o);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                assert fileOut != null;
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void write(Serializable o) {
        write(o.getClass().getSimpleName(), o);
    }

    public static
    @Nullable
    Serializable read(String key) {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        Serializable o;
        try {
            fileIn = new FileInputStream(getPath(key));
            in = new ObjectInputStream(fileIn);
            o = (Serializable) in.readObject();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fileIn != null) {
                    fileIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

    private static String getPath(String key) {
        return path.append(key).append(suffix).toString();
    }

    public static void remove(String key) {
        session.remove(key);
    }

    public static void remove(Object o) {
        session.remove(o);
    }

    public static void clear() {
        session.clear();
    }
}
