package junitIntroduction;

public class TemperatureConverter {
	
	public static double celsisusToFahrenheit(double celsius) {
		
		double fahrenheit = (9.0/5.0)*celsius + 32;
		return fahrenheit;
	}
	
	public static double fahrenheitToCelsius(double fahrenheit){
		
		double celsius = (5.0/9.0)*(fahrenheit - 32);
		return celsius;
		
	}

}
