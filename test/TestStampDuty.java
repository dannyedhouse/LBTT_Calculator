
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;


class TestStampDuty {
	
	@Test
	void testPropertyValueZero() {
		BigDecimal expected = new BigDecimal(0);
		BigDecimal result = Calculator.calculateStampDuty(0.0);
		org.junit.Assert.assertEquals(expected, result);
	}
	
	@Test
	void testPropertyValueSmall() {
		BigDecimal expected = new BigDecimal("0.05"); // £0.05
		BigDecimal result = Calculator.calculateStampDuty(500001);
		org.junit.Assert.assertEquals(expected, result);
	}

	@Test
	void testPropertyValueMedium() {
		BigDecimal expected = new BigDecimal("31809.00");
		BigDecimal result = Calculator.calculateStampDuty(1030589.99);
		org.junit.Assert.assertEquals(expected, result);
	}
	
	@Test
	void testPropertyValueHigh() {
		BigDecimal expected = new BigDecimal("2712868.20"); // £2,712,868.20
		BigDecimal result = Calculator.calculateStampDuty(23450985);
		org.junit.Assert.assertEquals(expected, result);
	}
}
