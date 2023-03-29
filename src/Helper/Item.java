package Helper;

public class Item {

	private int id;
	private String valueString;
	
	public Item(int id, String valueString) {
		super();
		this.id = id;
		this.valueString = valueString;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getValueString() {
		return valueString;
	}
	
	public void setValueString(String valueString) {
		this.valueString = valueString;
	}
	
	@Override
	public String toString() {
		return this.valueString;
	}
	
}
