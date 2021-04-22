package polymorphism;

public class SamsungTV implements TV {
	private Speaker speaker;
	private int price;
	
	public SamsungTV() {
		System.out.println("===> Samsung TV 객체 생성 (using default constructor).");
	}
	
	public void initMethod() {
		System.out.println("init-method 호출 : Samsung TV 객체 초기화 작업 처리..");
	}
	
	public void destroyMethod() {
		System.out.println("destroy-method 호출 : Samsung TV 객체 삭제 직전 작업 처리..");
	}
	
	// Setter Injection
	public void setSpeaker(Speaker newSpeaker) {
		System.out.println("===> Samsung TV setSpeaker() 호출");
		this.speaker = newSpeaker;
	}
	
	public void setPrice(int price) {
		System.out.println("===> Samsung TV setPrice() 호출");
		this.price = price;
	}	
	
	@Override
	public void powerOn() {
		System.out.printf("Samsung TV---전원 켠다. (가격 : %d )\n", this.price);		
	}
	@Override
	public void powerOff() {
		System.out.println("Samsung TV---전원 끈다.");
	}
	@Override
	public void volumeUp() {
//		System.out.println("Samsung TV---소리 올린다.");
		speaker.volumeUp();
	}
	@Override
	public void volumeDown() {
//		System.out.println("Samsung TV---소리 내린다.");
		speaker.volumeDown();
	}
}
