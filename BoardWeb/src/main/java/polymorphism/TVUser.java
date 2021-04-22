package polymorphism;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TVUser {
	public static void main(String[] args) {
//		LgTV tv = new LgTV();
//		SamsungTV tv = new SamsungTV();
		
		// Design pattern : Factory pattern
//		BeanFactory factory = new BeanFactory();
//		TV tv = (TV)factory.getBean(args[0]);
//				
//		tv.powerOn();
//		tv.volumeUp();
//		tv.volumeDown();
//		tv.powerOff();
		
		// Spring
		// 1. Spring container를 구동한다. (등록된 bean class 객체를 생성하는 pre-loading 실행.)
		AbstractApplicationContext factory = new GenericXmlApplicationContext("applicationContext.xml");	// Constructor(생성자), init-method(객체 초기화) 실행.
		System.out.println("");
		
		// 2. Spring container로부터 필요한 객체를 요청(Lookup)한다.
		TV tv = (TV)factory.getBean("tv");	// bean의 id를 이용해 호출한다.
		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();
		System.out.println("");
		
		// 5. Design pattern : Singleton pattern as one of the GoF patterns.
		TV tv2 = (TV)factory.getBean("tv");
		TV tv3 = (TV)factory.getBean("tv");
		
		// 4. lazy-init 객체 생성 테스트.
		TV secondTv = (TV)factory.getBean("secondTv");	// lazy-init으로 인해 bean이 사용되는 시점에 객체가 생성되어 Constructor, init-method를 실행.
		secondTv.powerOn();
		secondTv.volumeUp();
		secondTv.volumeDown();
		secondTv.powerOff();
		System.out.println("");
		
		// 3. Spring container가 객체를 소멸한다.
		factory.close();	// destroy-method(Destructor, 소멸자) 실행.

	}
}
