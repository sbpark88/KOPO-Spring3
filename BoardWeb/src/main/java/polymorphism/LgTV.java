package polymorphism;

public class LgTV implements TV {
	@Override
	public void powerOn() {
		System.out.println("LG TV---전원 켠다.");		
	}

	@Override
	public void powerOff() {
		System.out.println("LG TV---전원 끈다.");		
	}

	@Override
	public void volumeUp() {
		System.out.println("LG TV---소리 올린다.");
	}

	@Override
	public void volumeDown() {
		System.out.println("LG TV---소리 내린다.");
	}
}
