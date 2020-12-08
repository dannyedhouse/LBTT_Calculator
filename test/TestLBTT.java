
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;


class TestLBTT {
	
	@Test
	void testPropertyValueZero() {
		BigDecimal expected = new BigDecimal(0);
		BigDecimal result = Calculator.calculateLBTT(0);
		org.junit.Assert.assertEquals(expected, result);
	}
	
	@Test
	void testPropertyValueSmall() {
		BigDecimal expected = new BigDecimal("0.05"); // £0.05
		BigDecimal result = Calculator.calculateLBTT(250001);
		org.junit.Assert.assertEquals(expected, result);
	}

	@Test
	void testPropertyValueMedium() {
		BigDecimal expected = new BigDecimal("36250.00");
		BigDecimal result = Calculator.calculateLBTT(650000);
		org.junit.Assert.assertEquals(expected, result);
	}
	
	@Test
	void testPropertyValueHigh() {
		BigDecimal expected = new BigDecimal("1223562.08"); // £1,223,562.08
		BigDecimal result = Calculator.calculateLBTT(10560934);
		org.junit.Assert.assertEquals(expected, result);
	}
}
