package com.cmdi.runi.clinetTest;

import java.util.BitSet;

public class BitTest {

	/**
	 * @date：2016-4-26
	 * @author：hukai
	 * @param @param args todo：TODO
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BitSet bitset = new BitSet();
		bitset.set(129, true);
		bitset.set(123, true);
		bitset.set(100, true);
		bitset.set(75, true);
		System.out.println(bitset.size());
		System.out.println(bitset.length());
		System.out.println(bitset.cardinality());
	}
}
