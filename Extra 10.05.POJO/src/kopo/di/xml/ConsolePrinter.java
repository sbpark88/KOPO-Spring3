package kopo.di.xml;

public class ConsolePrinter implements Printer {
	
	@Override
	public void print(String message) {
		System.out.printf("ConsolePrinter call %s \n", message);
	}

}
