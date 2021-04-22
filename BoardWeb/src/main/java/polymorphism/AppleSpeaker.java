package polymorphism;

public class AppleSpeaker implements Speaker {
	public AppleSpeaker() {
		System.out.println("===> Apple Speaker 객체 생성.");
	}
	
	@Override
	public void volumeUp() {
		System.out.println("Apple Speaker---소리 올린다.");		
	}

	@Override
	public void volumeDown() {
		System.out.println("Apple Speaker---소리 내린다.");
	}
}
