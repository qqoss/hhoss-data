package com.hhoss.data;

import java.util.Date;

import com.hhoss.bean.Slot;
import com.hhoss.util.HMap;

public class SlotTest {

	public static void main(String[] args) {
		test1();
		test2();
	}
	public static void test1() {
		Slot slot = new Slot(){Slot child=this;};
		slot.load(new HMap("SID", 423948239482L, "updatime","code","codeFIeld", new Date(),"TIMS", 78798L));
		slot.load(new HMap("ds11", "ds11string", "ds21", 3.5d,"DS31", new Long(2545),"DS41",254));
		Long d = slot.getTims();
		System.out.println(slot);
	}

	public static void test2() {
		Slot slot = new Slot();
		slot.load(new HMap("SID", 423948239482L, "updatime","code","codeFIeld", new Date(),"TIMS", 1231654165L));
		slot.load(new HMap("ds11", "ds11string", "ds21", 3.5d,"DS31", new Long(2545),"DS41",254));
		Long d = slot.getTims();
		System.out.println(slot.xml());
	}

}
