package com.hhoss.data;

import com.hhoss.jour.Logger;
import com.hhoss.mold.Item;

public class ItemTest {

	public static void main(String[] args) {
		test1();
	}
	public static void test1() {
		Item data = new Item();
		data.addFlag(184);
		data.log();
		data.addFlag(28);
		data.log();
		data.addMark("0S");
		data.log();
		data.addMark("5r");
		data.log();
		data.addMark("qT");
		data.log();
		data.addMark("xZ68");
		data.log();
		data.setFlag(25,true);
		data.log();
		Logger.get().info("{}",data.hasFlag(185));
		data.setFlag(0,false);
		data.log();
		Logger.get().info("{}",data.hasFlag(185));
		Logger.get().info("{}",data.hasFlag(184));
		data.addFlag(3);
		data.log();
		data.ridFlag(3);
		data.log();
		data.ridFlag(-1);
		data.log();
		data.addMark("0");
		data.log();

	}


}
