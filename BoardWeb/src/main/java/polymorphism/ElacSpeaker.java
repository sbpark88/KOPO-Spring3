package polymorphism;

public class ElacSpeaker implements Speaker {
	public ElacSpeaker() {
		System.out.println("===> ElacSpeaker 객체 생성.");
	}
	
	@Override
	public void volumeUp() {
		System.out.println("Elac Speaker---소리 올린다.");
	}
	
	@Override
	public void volumeDown() {
		System.out.println("Elac Speaker---소리 내린다.");
	}
}
