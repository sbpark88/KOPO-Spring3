package kopo.di.xml;

public class StringPrinter implements Printer {

	@Override
	public void print(String message) {
		System.out.printf("StringPrinter call %s \n", message);		
	}
	
}
