package com.frame.z.accumulate.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Map;

/**
 * sharedpreferences工具类
 * Created by Administrator on 2017/9/22.
 */

public class SpUtil {

    private SharedPreferences sp = null;

    public SpUtil(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SpUtil(Context context, String name) {
        this(context, name, Context.MODE_PRIVATE);
    }

    public SpUtil(Context context, String name, int mode) {
        sp = context.getSharedPreferences(name, mode);
    }


    public boolean put(String key, Object value) {
        boolean r = false;
        if (value instanceof Boolean)
            r = putBoolean(key, (Boolean) value);
        else if (value instanceof Float)
            r = putFloat(key, (Float) value);
        else if (value instanceof Integer)
            r = putInt(key, (Integer) value);
        else if (value instanceof Long)
            r = putLong(key, (Long) value);
        else if (value instanceof String)
            r = putString(key, (String) value);
        return r;
    }

    public Object get(String key, Object defValue) {
        Object obj = null;
        if (defValue instanceof Boolean)
            obj = getBoolean(key, (Boolean) defValue);
        else if (defValue instanceof Float)
            obj = getFloat(key, (Float) defValue);
        else if (defValue instanceof Integer)
            obj = getInt(key, (Integer) defValue);
        else if (defValue instanceof Long)
            obj = getLong(key, (Long) defValue);
        else if (defValue instanceof String)
            obj = getString(key, (String) defValue);
        return obj;
    }

    public boolean contains(String key) {
        return sp.contains(key);
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public boolean clear() {
        return sp.edit().clear().commit();
    }

    public boolean remove(String key) {
        return sp.edit().remove(key).commit();
    }

    public boolean remove(String... key) {
        SharedPreferences.Editor editor = sp.edit();
        for (String k : key) {
            editor.remove(k);
        }
        return editor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public boolean putBoolean(String key, boolean value) {
        return sp.edit().putBoolean(key, value).commit();
    }

    public boolean putFloat(String key, float value) {
        return sp.edit().putFloat(key, value).commit();
    }

    public boolean putInt(String key, int value) {
        return sp.edit().putInt(key, value).commit();
    }

    public boolean putLong(String key, long value) {
        return sp.edit().putLong(key, value).commit();
    }

    public boolean putString(String key, String value) {
        return sp.edit().putString(key, value).commit();
    }


    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */
    public boolean setObj(String key, Object object) {

        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入key值为key的sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            return editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public <T> T getObj(String key, Class<T> clazz) {

        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
