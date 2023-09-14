package com.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

class DivisionTest {

	@Test
	void test() {
		Division d = new Division();
		assertEquals(5, d.division(10,2));
	}

}
