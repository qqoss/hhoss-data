package com.hhoss.bean;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.hhoss.date.Formatter;
import com.hhoss.jour.Logger;
import com.hhoss.lang.ClassMeta;

public class Bean<V> {
	private static final Logger logger = Logger.get();
	private static final String NA="N/A";
	
	private static final Map<Class<?>, Class<?>> basicType = new IdentityHashMap<>(16);
	//private static final Map<Class<?>, Class<?>> plainType = new IdentityHashMap<>(16);
	static{
		basicType.put(Void.TYPE, Void.class);//=void.class
		basicType.put(Byte.TYPE, Byte.class);//=byte.class
		basicType.put(Long.TYPE, Long.class);//=long.class
		basicType.put(Short.TYPE, Short.class);//=short.class
		basicType.put(Float.TYPE, Float.class);//=float.class
		basicType.put(Double.TYPE, Double.class);//=double.class
		basicType.put(Integer.TYPE, Integer.class);//=int.class
		basicType.put(Boolean.TYPE, Boolean.class);//=boolean.class
		basicType.put(Character.TYPE, Character.class);//=char.class		
		basicType.put(Void.class, Void.class);
		basicType.put(Byte.class, Byte.class);
		basicType.put(Long.class, Long.class);
		basicType.put(Short.class, Short.class);
		basicType.put(Float.class, Float.class);
		basicType.put(Double.class, Double.class);
		basicType.put(Integer.class, Integer.class);
		basicType.put(Boolean.class, Boolean.class);
		basicType.put(Character.class, Character.class);		
		basicType.put(String.class, Character.class);
		
		//plainType.put(Date.class, Character.class);
		//plainType.put(VO.class, Character.class);
	}
	
	public static Class<?> normalize(Class<?> cls){
		return (cls.isPrimitive())?basicType.get(cls):cls;
	}
	
	/**
	 * @param klass
	 * @return true if class is primitive type or primitive wrapped class
	 */
	public static boolean isBasic(Class<?> klass){
		return basicType.get(klass)!=null;
	}
	
	/**
	 * @param klass
	 * @return true if String, Date, Enum, VO or Primitive
	 */
	public static boolean isPlain(Class<?> klass){
		if(klass==null){return false;}
    	return basicType.get(klass)!=null||
    		   //plainType.get(klass)!=null||
      		   Number.class.isAssignableFrom(klass)||
       		   Date.class.isAssignableFrom(klass)||
       		   Item.class.isAssignableFrom(klass)||
		   	   klass.isEnum();
 	}
	
	public static boolean isFrame(Class<?> klass) {
		if(klass==null){return false;}
		return  klass.isArray()||
				Map.class.isAssignableFrom(klass)||
				Collection.class.isAssignableFrom(klass);


	}

	public static boolean isDefault(Object obj){
		if(obj==null){ return true; }		
		Class<?> cls = obj.getClass();
		if(cls==Boolean.class){
			return Boolean.FALSE.equals(obj);
		}if(cls==Character.class){
			return Character.valueOf((char)0).equals(obj);
		}else if(Number.class.isAssignableFrom(cls)){
			return ((Number)obj).doubleValue()==0d;
		}else if(CharSequence.class.isAssignableFrom(cls)){
			return ((CharSequence)obj).length()==0;
		}else if(cls.isArray()) {
			 return ((Object[])obj).length==0;
		}else if(Map.class.isAssignableFrom(cls)) {
			 return ((Map<?,?>)obj).size()==0;
		}else if(Collection.class.isAssignableFrom(cls)) {
			 return ((Collection<?>)obj).isEmpty();
		}
		return false;
	}


	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object obj, String fld) throws Exception{
		Field field = getField((obj instanceof Class)?(Class<?>)obj:obj.getClass(),fld);
		field.setAccessible(true);
		return (T)field.get(obj);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T setFieldValue(Object obj, String fld, Object val) throws Exception{
		if( obj==null||fld==null ){
			throw new IllegalArgumentException("object or filed name should not be null!");
		}
		Class<?> clazz = (obj instanceof Class)?(Class<?>)obj:obj.getClass();
		Field field = getField(clazz,fld);
		if( field==null ){
			throw new IllegalArgumentException("can't find filed["+fld+"] in class["+val.getClass()+"].");
		}
		if(val==null || normalize(field.getType()).isInstance(val) ){		
			field.setAccessible(true);
			Object old=field.get(obj);
			field.set(obj, val);
			return (T)old;
		}else{
			throw new IllegalArgumentException("typeof field["+field.getType().getSimpleName()+"]["+field.getType().getSimpleName()+"] is not compatible with typeof value["+val.getClass().getSimpleName()+"].");
		}
	}	
	
	public static Field getField(Class<?> klass, String name){
		Class<?> cls = klass;
		while(cls!=null){
			for(Field fld:cls.getDeclaredFields()){
				if(fld.getName().equalsIgnoreCase(name)){
					return fld;
				}
			}
			cls = cls.getSuperclass(); //得到父类,然后赋给自己
		}
		return null;		
	}
	
	public static List<Field> getFields(){
		return getFields(ClassMeta.caller(1));
	}

	/**
	 * @param klass
	 * @param anno
	 * @return basic fields with special annotation
	 */
	public static List<Field> getFields(Class<?> klass, Class<Annotation> anno){
		List<Field> flds = new ArrayList<>();
		for (Field fld : getFields(klass)){
			if(anno!=null && fld.isAnnotationPresent(anno) && !fld.getName().startsWith("this$")){//this$0 内部类引用的主类
				flds.add(fld);
			}
		}
		return flds;		
	}	
	
	/**
	 * @param klass
	 * @return basic fields ,include static fields
	 */
	public static List<Field> getFields(Class<?> klass){
		Class<?> cls = klass;
		List<Field> fields = new ArrayList<>();
		while(cls!=null){//cls.getSimpleName().equals("Object")||cls.getSimpleName().equals("Object")
			for(Field fld:cls.getDeclaredFields()){
				if( ( isPlain(fld.getType()) || isFrame(fld.getType()) ) && 
					(!Modifier.isStatic(fld.getModifiers())) && (!fld.getName().startsWith("this$")) ){
					fields.add(fld);
				}
			}
			cls = cls.getSuperclass(); //loop parent class
		}
		return fields;		
	}
	
    /**
     * @param cls Class.
     * @return field names for the class with cached.
     */
	public static String[] getFieldNames(Class<?> cls) {
        String key = cls.getName() + System.identityHashCode(cls.getClassLoader());
        String[] names = classFieldNames.get(key);
        if( names == null ) {
        	HashSet<String> set = new HashSet<String>();
            for (Field f : getFields(cls)) set.add(f.getName());
            classFieldNames.put(key, names=set.toArray(new String[]{}));
        }        
        return names;
    }
    private static final Map<String,String[]> classFieldNames = new HashMap<>();	
	public static String[] getFieldNames() {
		return getFieldNames(ClassMeta.caller(1));
	}

	
    /**
     * @param obj object to serialization string
     * @param deep recurse loop deep
     * @return
     */
    public static final String str(Object obj, int deep){
		//return Beans.string(this);
    	if(isDefault(obj)){
    		return null;//Not output;
    	}
    	Class<?> cls = obj.getClass();
    	if(deep<0){
    		return NA;
    	//	return cls.getSimpleName()+"@0";//System.identityHashCode(obj);
    	}else if(isBasic(cls)){
    		return obj.toString();
    	}else if(cls.isArray()) {
    		return str((Object[])obj,deep);
    	}else if(cls.isEnum()){
    		return ((Enum<?>)obj).name();
    	}else if(obj instanceof Date){
    		return Formatter.format((Date)obj,"'\"'yyyyMMddHHmmss.sss'\"'");
    	}else if(obj instanceof Map) {
    		return str((Map<?,?>)obj,deep);
    	}else if(obj instanceof Collection) {
    		return str((Collection<?>)obj,deep);
    	}else {
    		
    	}
    	
    	StringBuilder buf = new StringBuilder();
        buf.append("{@").append(cls.getSimpleName()).append(':').append(deep);
        try {
             for(Field field : getFields(cls)) {
	            field.setAccessible(true);		            
            	Object v = str(field.get(obj),deep-1);
            	if(v==null||v==NA){continue;}
                buf.append(',').append(field.getName()); 
                buf.append(':').append(v); 
            }
        } catch (Exception e) {
            buf.append(',').append(e.getClass().getSimpleName());
            buf.append(':').append(e.getMessage());
        }
        buf.append("}");
        return buf.toString();
	}
    

    /**
     * @param obj object to serialization string
     * @param deep recurse loop deep
     * @return String representation of an array.
     */
    private static String str(Object[] arr, int deep) {
    	Class<?> cls = arr.getClass().getComponentType();
    	if(!isPlain(cls)) {return NA;}
        StringBuilder b = new StringBuilder("[");
        for (int i = 0; i<arr.length-1; i++) {
        	if(i>0){b.append(',');}
            b.append(str(arr[i],deep));
        }
        return b.append(']').toString();
    }

    /**
     * @param map object to serialization string
     * @param deep recurse loop deep
     * @return String representation of an Map.
     * only handle baisc class as key and plain class as value;
     */
    private static String str(Map<?,?> map, int deep) {
    	if(!isBasic(ClassMeta.getGenericType(map.getClass(), 0))){return NA;}
    	//if(!isPlain(ClassMeta.getGenericType(map.getClass(), 1))){return NA;}//often Object.class 
        StringBuilder sb = new StringBuilder("{");
       	map.forEach((key,val)->{sb.append(key).append(':').append(str(val,deep-1)).append(',');});
        sb.setCharAt(sb.length()-1, ']');
        return 	sb.toString();
    }

    /**
     * @param map object to serialization string
     * @param deep recurse loop deep
     * @return String representation of an Map.
     * only handle baisc class as key and plain class as value;
     */
    private static String str(Iterable<?> iter, int deep) {
    	//if(!isPlain(ClassMeta.getGenericType(iter.getClass(), 0))){return NA;}//often Object.class 
        StringBuilder sb = new StringBuilder("[");
        iter.forEach(el->{sb.append(str(el,deep)).append(',');});
        sb.setCharAt(sb.length()-1, ']');
        return 	sb.toString();
    }

	
	public static String xml(Object obj){
		ByteArrayOutputStream bos  = new ByteArrayOutputStream();         
		try(XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(bos))){			
	        //encoder.setOwner(obj);
	        /*
	        encoder.setExceptionListener(new ExceptionListener() {
	            public @Override void exceptionThrown(Exception e) {
	                System.out.println("got exception. e=" + e.getMessage());
	                e.printStackTrace();
	            }});
	        */
	        encoder.writeObject(obj);  
		}
 		return bos.toString();
    }


	
	   
    @SuppressWarnings("unchecked")
	public static <T> T copy(Object source, T target){
       	if(source instanceof Map) {
    		return load((Map<String, Serializable>)source,target);
    	}
       	if(source instanceof Node) {
    		return load((Node)source,target);
    	}
    	//todo array?
    	
		Class<?> clazz = source.getClass();
		for (String name : Bean.getFieldNames(target.getClass())){
			Object val=null;
			try {
				Field ofld = clazz.getField(name);
				if( ofld!=null ){
					ofld.setAccessible(true);
					val=ofld.get(source);
					if(val!=null){
						Bean.setFieldValue(target, name, val);
					}
				}
			} catch (Exception e) {
				logger.warn("set field[{}] value[{}] error:{}-{}", name,val,e.getClass().getSimpleName(),e.getMessage());
			}
		}
		return target;
    }
    
	/**
	 * please care the field names should match the class fields
	 * @param map
	 * @return
	 */
    public static <T> T load(Map<String, Serializable> map, T target){
		for (String name : Bean.getFieldNames(target.getClass())){
			Object val=null;
			try {
				if( (val=map.get(name))!=null || (val=map.get(name.toUpperCase()))!=null ){
					Bean.setFieldValue(target, name, val);
				}
			} catch (Exception e) {
				logger.warn("set field[{}] value[{}] error:{}-{}", name,val,e.getClass().getSimpleName(),e.getMessage());
			}
		}
		return target;
	}
    
	/**
	 * @param node w3c abstract node, eg:Element,Document Attr, Entity, CDATA...
	 * @return operated object
	 */
   public static <T> T load(Node node, T target){
		if(node instanceof Attr){
			Attr xml = (Attr)node;
			let(target,xml.getLocalName(),xml.getNodeValue());
		}else if(node instanceof Document){
			load(((Document)node).getDocumentElement(),target);
		}else if(node instanceof Element){
			NamedNodeMap nnm = ((Element)node).getAttributes();
			for(int i=0;i<nnm.getLength();i++){
				load(nnm.item(i),target);
			}
			NodeList nl = ((Element)node).getChildNodes();
			for(int j=0;j<nl.getLength();j++){//child Elements 
				org.w3c.dom.Node child = nl.item(j);
				if(child instanceof Element)try{
					Object fld = Bean.getFieldValue(target,child.getLocalName());
					if(fld instanceof com.hhoss.bean.Item){// ignore if fld is null!
						((com.hhoss.bean.Item<?>)fld).load(child);
					}
				}catch(Exception e){
					logger.warn("copy Element to object error:{}-{}",e.getClass().getSimpleName(),e.getMessage());
				}
			}			
		}else if(node instanceof Text){
			
		}
		return target;		
	}
    
	/**
	 * set the object field value
	 * @param object to set value for 
	 * @param field name case-insensitive
	 * @param value may null
	 */
    public static void let(Object object,String field, Object value){
		for (String name : Bean.getFieldNames(object.getClass())){
			try {
				if( name.equalsIgnoreCase(field)){
					Bean.setFieldValue(object, name, value);
				}
			} catch (Exception e) {
				logger.warn("set field[{}] value[{}] error:{}-{}", name,value,e.getClass().getSimpleName(),e.getMessage());
			}
		}
	}
    
    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
            return false;
        }
        return obj.getClass().isArray();
    }
	
}
