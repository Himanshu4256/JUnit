package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculaterTest {

	@Test
	void test() {
		Calculater cal = new Calculater();
		assertEquals(20, cal.calculator(2, 10));
	}
	
	@Test
	void testString() {
		Calculater str = new Calculater();
		assertEquals("HimanshuSharma", str.check("Sharma"));
		
	}

}
