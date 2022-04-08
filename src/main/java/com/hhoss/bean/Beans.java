package com.hhoss.bean;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.xml.sax.InputSource;

import com.hhoss.jour.Logger;
import com.hhoss.util.HMap;
/**
 * Provides auto-generation framework for {@code toString()} output.
 * <p>
 * Default exclusion policy (can be overridden with {@link GridToStringInclude}
 * annotation):
 * <ul>
 * <li>fields with {@link GridToStringExclude} annotations
 * <li>classes that have {@link GridToStringExclude} annotation (current list):
 *      <ul>
 *      <li>GridManager
 *      <li>GridManagerRegistry
 *      <li>GridProcessor
 *      <li>GridProcessorRegistry
 *      <li>IgniteLogger
 *      <li>GridDiscoveryMetricsProvider
 *      <li>GridByteArrayList
 *      </ul>
 * <li>static fields
 * <li>non-private fields
 * <li>arrays
 * <li>fields of type {@link Object}
 * <li>fields of type {@link Thread}
 * <li>fields of type {@link Runnable}
 * <li>fields of type {@link Serializable}
 * <li>fields of type {@link Externalizable}
 * <li>{@link InputStream} implementations
 * <li>{@link OutputStream} implementations
 * <li>{@link EventListener} implementations
 * <li>{@link Lock} implementations
 * <li>{@link ReadWriteLock} implementations
 * <li>{@link Condition} implementations
 * <li>{@link Map} implementations
 * <li>{@link Collection} implementations
 * </ul>
 */
public class Beans {
	public static final Logger logger=Logger.get();
    private static final HMap<ArrayList<String>> classCache = new HMap<>();

    /** */
    private static final ReadWriteLock rwLock = new ReentrantReadWriteLock();


    /**
     * Creates an uniformed string presentation for the given object.
     *@see org.apache.ignite.internal.util.tostring.GridToStringBuilder
     */
    protected static String str(Object obj) {
    	Class<?> cls = obj.getClass();
    	StringBuilder buf = new StringBuilder(cls.getSimpleName());
        try {
            buf.append("{ ");
            for(String name : getFieldNames(cls)) {
                Field field = cls.getDeclaredField(name);
                field.setAccessible(true);
                buf.append(name).append(':'); 
                buf.append(field.get(obj)).append(',');
                
            }
            buf.setCharAt(buf.length()-1,' ');
            buf.append('}');
            return buf.toString();
        } catch (Exception e) {
            rwLock.writeLock().lock();
            try {
                classCache.remove(cls.getName() + System.identityHashCode(cls.getClassLoader()));
            } finally {
                rwLock.writeLock().unlock();
            }
            throw new RuntimeException(e);
        }
    }

   
    

    /**
     * @param cls Class.
     * @param <T> Type of the object.
     * @return Descriptor for the class.
     */
    private static <T> ArrayList<String> getFieldNames(Class<T> cls) {
        assert cls != null;
        String key = cls.getName() + System.identityHashCode(cls.getClassLoader());
        ArrayList<String> cd;
        rwLock.readLock().lock();
        try {
            cd = classCache.get(key);
        }
        finally {
            rwLock.readLock().unlock();
        }

        if (cd == null) {
            cd = new ArrayList<String>();
            List<Field> flds = Bean.getFields(cls, null);
            //final GridToStringInclude incFld = f.getAnnotation(GridToStringInclude.class);
            //final GridToStringInclude incType = type.getAnnotation(GridToStringInclude.class);

            for (Field f : flds) {
            	Class<?> c = f.getType();
            	if((c.isPrimitive()||c.isEnum()||String.class.isAssignableFrom(c))&&!c.isArray()){
            		cd.add(f.getName());
            	}
            }
            /*
             * Allow multiple puts for the same class - they will simply override.
             */
            rwLock.writeLock().lock();
            try {
                classCache.put(key, cd);
            }
            finally {
                rwLock.writeLock().unlock();
            }
        }

        return cd;
    }

    
	@SuppressWarnings({ "resource", "unchecked" })
	public static <T> T from(String xml){
		InputSource is = new InputSource(new StringReader(xml));
	    return (T) new XMLDecoder(is).readObject();  
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T from(URI uri) throws IOException{
		FileInputStream fis = new FileInputStream(new File(uri));
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(fis)); 
		T o = (T) decoder.readObject();
        decoder.close();
		return o;
	}
    
    public static void main(String[] args) {
    	System.out.print( Number.class.isAssignableFrom(Integer.class));
	}
  
	    
}
