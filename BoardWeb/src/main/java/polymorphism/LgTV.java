package polymorphism;

public class LgTV implements TV {
	private Speaker speaker;
	private int price;
	
	// Default Constructor
	public LgTV() {
		System.out.println("===> LG TV 객체 생성 (using default constructor).");
	}
	
	// Constructor Injection
	public LgTV(Speaker speaker) {
		System.out.println("===> LG TV 객체 생성 (using 1 parameter(speaker) constructor).");
		this.speaker = speaker;
	}
	
	public LgTV(Speaker speaker, int price) {
		System.out.println("===> LG TV 객체 생성 (using 2 parameters(speaker, price) constructor).");
		this.speaker = speaker;
		this.price = price;
	}
	
	// Setter Injection
	public void setSpeaker(Speaker newSpeaker) {
		System.out.println("===> LG TV setSpeaker() 호출");
		this.speaker = newSpeaker;
	}
	
	public void setPrice(int price) {
		System.out.println("===> LG TV setPrice() 호출");
		this.price = price;
	}	
	
	public void initMethod() {
		System.out.println("init-method 호출 : LG TV 객체 초기화 작업 처리..");
	}
	
	public void destroyMethod() {
		System.out.println("destroy-method 호출 : LG TV 객체 삭제 직전 작업 처리..");
	}
	
	@Override
	public void powerOn() {
		System.out.printf("LG TV---전원 켠다. (가격 : %d )\n", this.price);		
	}

	@Override
	public void powerOff() {
		System.out.println("LG TV---전원 끈다.");		
	}

	@Override
	public void volumeUp() {
//		System.out.println("LG TV---소리 올린다.");
		speaker.volumeUp();
	}

	@Override
	public void volumeDown() {
//		System.out.println("LG TV---소리 내린다.");
		speaker.volumeDown();
	}
	
	
}
