package com.hhoss.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.hhoss.util.HMap;

public class BeansTest extends Item<BeansTest> {
	private static final long serialVersionUID = 7262469863427614677L;
	int gg=8;
	Object[] ints=new Integer[] {34,567,89};
	ArrayList<Number> al = new ArrayList<>(Arrays.asList(358,256,85.6d));	
	HMap<Number> map=new HMap<>("m1",3.4f,"m2",543.54f);
	
	Item<?> child0; 
	Item child1=new Item(); 
	Item<?> child2=new Item(){boolean f21=true; boolean f22=false;} ;
	Item<?> child3=new Item(){Boolean f31=true; Boolean f32=false; Boolean f33; Integer f34=0;Integer f35=1; } ;
	BeansTest child4;


	public static void main(String[] args) {
		BeansTest t = new BeansTest();
		t.child4=t;
		t.child1.load(new com.hhoss.util.HMap("SID", 423948239482L, "updatime", new Date(),"TIMS", 45646513213454L));
		t.child1.load(new HMap("ds11", "ds11string", "ds21", 3.5d,"DS31", new Long(2545),"DS41",254));
		t.al.add(5345.d);
		Date d = t.child1.getUpdateTime();
		System.out.println(t);
	}

}
