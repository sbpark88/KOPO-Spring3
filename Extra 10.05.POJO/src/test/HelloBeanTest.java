package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import kopo.di.xml.Hello;
import kopo.di.xml.Printer;

public class HelloBeanTest {
	public static void main(String[] args) {
		// 1. IoC 컨테이너 생성
		ApplicationContext context = new GenericXmlApplicationContext("config/beans.xml");
		
		// 2. 위 1번에서 만든 context 변수(config/beans.xml를 가짐)에서 "hello"라는 id를 가져온다(getBean메소드).
		// 그리고 그 가져온 값을 Hello 자료형의 hello라는 변수에 담는다.
		Hello hello = (Hello)context.getBean("hello");
		// 즉 아래 값이 담겨있다.
		// <bean id="hello" class="kopo.di.xml.Hello">
		// 	  <property name="name" value="kopo!!!" />
		// 	  <property name="printer" ref="stringPrinter" />
		// </bean>
		// 이걸 해석하면 id는 헬로(getBean으로 가져올 때 사용), 그리고 클래스는 kopo.di.xml 패키지 안에 Hello라는 자바 클래스.
		// property는 이 Hello 자바클래스에 생성자처럼 작동(정확히는 setter를 이용해 값을 대입),
		// name에 값으로 문자열(value) "kopo!!!"를 넣고, printer는 참조(bean id) "WhoAreYou"를 넣는데,
		// 이 "WhoAreYou"의 bean id는 아래 또 다른 bean에 의해 kopo.di.xml.SringPrinter로 정의되어있다.
		
		// hello의 sayHello 메소드를 실행한다. 그러면 Hello + name(<- 여기에 kopo!!! 가 대입되어있다.)를 return하고 그것을 출력한다.
		System.out.println(hello.sayHello());
		
		// 얘는 hello의 print() 메소드를 실행하므로 this.printer(sayHello())를 실행한다.
		// 즉, printer는 bean id="WhoAreYou"에 의해 kopo.di.xml.StringPrinter를 참조하므로 StringPrinter call + message (<- sayHello())를 출력
		hello.print();
		
		System.out.println();
		
		// Printer 자료형의 printer 변수에 context에서 "conslePrinter"를 id로 갖는 bean을 찾아 넣는다. (3가지 방법으로 구현. 그에 따른 print메소드 실행은 아래 2가지로 나뉨.)
		Printer printer = context.getBean("consolePrinter", Printer.class);	// 좌변이 Printer라서 우변의 ConsolePrinter를 Printer.class라는 자료형으로 받겠다고 미리 이야기.(제네릭?)
//		Printer printer = (Printer) context.getBean("consolePrinter");	// 좌변이 Printer라서 우변의 ConsolePrinter를 Printer로 형변환.
//		Object printer = context.getBean("consolePrinter");	// 우변이 ConsolePrinter라서 뭐든 받을 수 있는 최상위 객체 부모인 Object 객체 자체로 받음.
		
		
		// printer 변수(Printer 자료형으로 형변환 된 ConsolePrinter 객체)의 print(String message) 메소드를 실행.
		// ConsolePrinter call + message (<- "GOOD~~~~~") 출력.
		printer.print("GOOD~~~~~");
		
		// 위에서 좌변에서 우변의 ConsolePrinter 객체를 Object로 받아서, printer라는 Object 변수를 Printer로 형변환한다. 그 다음 Printer 자료형의 printer 메소드 실행 가능.
//		((Printer) printer).print("GOOD~~~~~");
		
		// 즉, Hello hello = (Hello)context.getBean("hello"); 이것과 같은 얘기다.
		Hello hello2 = context.getBean("hello", Hello.class);
		
		System.out.println();
		
		// hello와 hello2는 불러온 방식만 다르지 같은 값을 가진 2개의 인스턴스인 것처럼 보인다. 데이터는 같고 메모리 주소는 다른...		
		// 하지만 둘의 해시코드를 확인해보면 동일한 값 하나를 둘이 동시에 참조한다는 것을 알 수 있다.
		System.out.printf("\n hello.hashCode()  : %s \n hello2.hashCode() : %s", hello.hashCode(), hello2.hashCode());
		System.out.printf("\n hello == hello2, they are singleton. Is it true? >> %s!! \n", hello.hashCode() == hello2.hashCode());
	
	}
}
