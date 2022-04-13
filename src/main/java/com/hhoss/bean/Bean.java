package com.hhoss.bean;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
import com.hhoss.lang.Beans;
import com.hhoss.lang.Classes;

public class Bean<V> {
	private static final Logger logger = Logger.get();
	private static final String NA="N/A";
	private static List<Class<?>> extTypes = Arrays.asList(new Class<?>[]{Item.class});
	
	
	/**
	 * @param klass
	 * @return true if String, Date, Enum, VO or Primitive
	 */
	private static boolean support(Class<?> klass){
		if(klass==null){return false;}
    	return Classes.isPlain(klass)||Classes.isGroup(klass)||Item.class.isAssignableFrom(klass);
 	}
	

	
    /**
     * @param obj object to serialization string
     * @param deep recurse loop deep
     * @return
     */
    public static final String str(Object obj, int deep){
		//return Beans.string(this);
    	if(Classes.isDefault(obj)){
    		return null;//Not output;
    	}
    	Class<?> cls = obj.getClass();
    	if(deep<0){
    		return NA;
    	//	return cls.getSimpleName()+"@0";//System.identityHashCode(obj);
    	}else if(Classes.isBasic(cls)){
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
             for(Field field : Classes.getFields(cls,extTypes)) {
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
    	if(!support(cls)) {return NA;}
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
    	if(!Classes.isBasic(Classes.getGenericType(map.getClass(), 0))){return NA;}
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
		for (String name : Beans.getFieldNames(target.getClass(),extTypes)){
			Object val=null;
			try {
				Field ofld = clazz.getField(name);
				if( ofld!=null ){
					ofld.setAccessible(true);
					val=ofld.get(source);
					if(val!=null){
						Beans.setFieldValue(target, name, val);
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
		for (String name : Beans.getFieldNames(target.getClass(),extTypes)){
			Object val=null;
			try {
				if( (val=map.get(name))!=null || (val=map.get(name.toUpperCase()))!=null ){
					Beans.setFieldValue(target, name, val);
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
					Object fld = Beans.getFieldValue(target,child.getLocalName());
					if(fld instanceof Item){// ignore if fld is null!
						((Item<?>)fld).load(child);
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
    public static void let(Object target,String field, Object value){
		for (String name : Beans.getFieldNames(target.getClass(),extTypes)){
			try {
				if( name.equalsIgnoreCase(field)){
					Beans.setFieldValue(target, name, value);
				}
			} catch (Exception e) {
				logger.warn("set field[{}] value[{}] error:{}-{}", name,value,e.getClass().getSimpleName(),e.getMessage());
			}
		}
	}
	
}
