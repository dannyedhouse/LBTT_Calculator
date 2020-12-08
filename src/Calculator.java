import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Calculator {

	public static void main(String[] args) {
		List<String> validInput = Arrays.asList("E", "S");
		Scanner in = new Scanner(System.in);
		
		System.out.println("LBTT Calculator\nWhere is the property located?\nEnter 'E' for England\nEnter 'S' for Scotland");
		String input = in.nextLine().toUpperCase();	
		while(!validInput.contains(input)) {
			System.out.println("Invalid input\nEnter 'E' for England\nEnter 'S' for Scotland");
			input = in.nextLine().toUpperCase();
		}
		
		System.out.println("What is the value of the property? Enter as a number in GDP");
		double propertyValue = 0.0;
		try {
			propertyValue = in.nextDouble();
		} catch (InputMismatchException e) {
			System.out.println("Please enter a whole number");
		}
		in.close();
		
		switch (input) {
			case "S":
				System.out.println("LBTT to pay: £" + calculateLBTT(propertyValue));
				break;
			case "E":
				break;
		}
    }
	
	/**
	 * Calculates the LBTT - Land and Buildings Transaction Tax (Scotland) ~ rates effective 15/07/20 - 31/03/21
	 * https://www.gov.scot/policies/taxes/land-and-buildings-transaction-tax/
	 * @return LBTT Tax due
	 */
	public static BigDecimal calculateLBTT(double propertyValue) {
		final double taxBands[] = {750000,325000,250000};
		final double taxRates[] = {0.12, 0.1, 0.05};
		
		return calculateTax(propertyValue, taxBands, taxRates);
	}
	
	/**
	 * Handles calculation of both LBTT/Stamp Duty.
	 * @return Tax to pay
	 */
	public static BigDecimal calculateTax(double propertyValue, double[] taxBands, double[] taxRates) {
		BigDecimal tax = new BigDecimal(0.0);
		
		if (propertyValue<=0.0) {
			return tax;
		}	
		
		BigDecimal taxableSum = BigDecimal.valueOf(propertyValue); //Use BigDecimal for currency

		for (int i = 0; i<taxBands.length; i++) {
			if (propertyValue >= taxBands[i]) {
				BigDecimal amountTaxable = taxableSum.subtract(BigDecimal.valueOf(taxBands[i]));
				tax = tax.add(amountTaxable.multiply(new BigDecimal(taxRates[i]).setScale(2, RoundingMode.HALF_EVEN)));
				taxableSum = taxableSum.subtract(amountTaxable);
			}		
		}
		
		return (tax.setScale(2, RoundingMode.HALF_EVEN));
	}
}
