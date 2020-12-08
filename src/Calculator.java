import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

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
		
		System.out.println("What is the value of the property? Enter as a number only in GBP");
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
				System.out.println("Stamp Duty to pay: £" + calculateStampDuty(propertyValue));
				break;
		}
    }
	
	/**
	 * Calculates the LBTT - Land and Buildings Transaction Tax (Scotland) ~ rates effective 15/07/20 - 31/03/21
	 * https://www.gov.scot/policies/taxes/land-and-buildings-transaction-tax/
	 * @return LBTT Tax due
	 */
	public static BigDecimal calculateLBTT(double propertyValue) {
		TreeMap<Integer, Double> taxBands = new TreeMap<>();
		taxBands.put(750000, 0.12);
		taxBands.put(325000, 0.1);
		taxBands.put(250000, 0.05);
		
		return calculateTax(propertyValue, taxBands);
	}
	
	/**
	 * Calculates the Stamp Duty (England/NI) ~ rates effective 08/07/20 - 31/03/21
	 * https://www.gov.uk/stamp-duty-land-tax/residential-property-rates
	 * @return Stamp Duty to pay
	 */
	public static BigDecimal calculateStampDuty(double propertyValue) {
		TreeMap<Integer, Double> taxBands = new TreeMap<>();
		taxBands.put(1500000, 0.12);
		taxBands.put(925000, 0.1);
		taxBands.put(500000, 0.05);
		
		return calculateTax(propertyValue, taxBands);
	}
	
	/**
	 * Handles calculation of both LBTT/Stamp Duty.
	 * @return Tax to pay
	 */
	public static BigDecimal calculateTax(double propertyValue, TreeMap<Integer, Double> taxBands) {
		BigDecimal tax = new BigDecimal(0.0);
		
		if (propertyValue<=0.0) {
			return tax;
		}	
		
		BigDecimal taxableSum = BigDecimal.valueOf(propertyValue); //Use BigDecimal for currency

		for (int taxBand: taxBands.descendingKeySet()) {
			if (propertyValue >= taxBand) {
				BigDecimal amountTaxable = taxableSum.subtract(BigDecimal.valueOf(taxBand));
				tax = tax.add(amountTaxable.multiply(new BigDecimal(taxBands.get(taxBand)).setScale(2, RoundingMode.HALF_EVEN)));
				taxableSum = taxableSum.subtract(amountTaxable);
			}		
		}
		
		return (tax.setScale(2, RoundingMode.HALF_EVEN));
	}
}
