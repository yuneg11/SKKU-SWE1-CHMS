import java.util.ArrayList;

import JSON.JSONObject;
import JSON.JSONArray;
import JSON.parser.JSONParser;

class GraphicCard extends Graphic {
	// Variable
    private String slot;
    private ArrayList<StringLongPair> ports;
    
 // Constructor
    public GraphicCard() {
		this.quantity = 1L;
		ports = new ArrayList<StringLongPair>();
    }
    public GraphicCard(String attributes) {
		this.quantity = 1L;
		ports = new ArrayList<StringLongPair>();
    	setAttribute(attributes);
    }
    
    // Setter & Getter
    public String getProductType() {
    	return "GraphicCard";
    }

    public void setAttribute(String attributes) {
    	try {
    		JSONObject obj = (JSONObject)(new JSONParser().parse(attributes));
    		if(obj.containsKey("Name")) 		this.name 			= (String)obj.get("Name");
    		if(obj.containsKey("Price")) 		this.price 			= (Long)obj.get("Price");
			if(obj.containsKey("Manufacturer")) this.manufacturer 	= (String)obj.get("Manufacturer");
			if(obj.containsKey(Str.quantity))	this.quantity 		= (Long)obj.get(Str.quantity);
    		if(obj.containsKey("CoreNumber"))	this.coreNumber 	= (Long)obj.get("CoreNumber");
    		if(obj.containsKey("ClockRate"))	this.clockRate 		= (Double)obj.get("ClockRate");
    		if(obj.containsKey("Fabrication"))	this.fabrication 	= (Long)obj.get("Fabrication");
    		if(obj.containsKey("TDP"))			this.tdp 			= (Long)obj.get("TDP");
    		if(obj.containsKey("Chipset"))		this.chipset 		= (String)obj.get("Chipset");
    		if(obj.containsKey("Memory"))		this.memory 		= new Memory(((JSONObject)obj.get("Memory")).toJSONString());
			if(obj.containsKey("Slot"))			this.slot 			= (String)obj.get("Slot");
			if(obj.containsKey(Str.port)) {
				JSONArray values = (JSONArray)obj.get(Str.port);
				for(Object value : values) {
					ports.add(new StringLongPair((String)((JSONObject)value).get("Name"), (Long)((JSONObject)value).get("Number")));
				}
			}
    	} catch(Exception exc) {
    		System.out.println("Unexpected error occurred");
    	}
    }
    public String getAttribute(String keys) {
    	try {
			JSONObject required = (JSONObject)(new JSONParser().parse(keys));
			JSONArray keyArray = (JSONArray)required.get("Keys");
    		JSONObject obj = new JSONObject();
    		for(Object key : keyArray) {
				switch((String)key) {
					case "ProductType":		obj.put(Str.productType, "GraphicCard");
					case "Name":			if(this.name != null) obj.put("Name", this.name);
					case "Price": 			if(this.price != null) obj.put("Price", this.price);
					case "Manufacturer": 	if(this.manufacturer != null) obj.put("Manufacturer", this.manufacturer);
					case "Quantity": 		obj.put("Quantity", this.quantity);
					case "CoreNumber": 		if(this.coreNumber != null) obj.put("CoreNumber", this.coreNumber);
					case "ClockRate": 		if(this.clockRate != null) obj.put("ClockRate", this.clockRate);
					case "Fabrication": 	if(this.fabrication != null) obj.put("Fabrication", this.fabrication);
					case "TDP": 			if(this.tdp != null) obj.put("TDP", this.tdp);
					case "Chipset": 		if(this.chipset != null) obj.put("Chipset", this.chipset);
					case "Memory": 			if(this.memory != null) obj.put("Memory", this.memory.toJSONObject()); //!!! CONVERT TO JSON NEEDED
					case "Slot": 			if(this.slot != null) obj.put("Slot", this.slot);
					case "Port":			if(ports.size() != 0) {
												JSONArray values = new JSONArray();
												for(StringLongPair value : ports) {
													JSONObject slp = new JSONObject();
													slp.put("Name", value.name);
													slp.put("Number", value.num);
													values.add(slp);
												}
												obj.put("Port", values);
											}
				}
			}
    		return obj.toJSONString();
    	} catch(Exception exc) {
    		System.out.println("Unexpected error occurred");
    		return null;
    	}
	}
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		JSONArray keyArray = new JSONArray();
		keyArray.add(Str.productType);
		keyArray.add(Str.name);
		keyArray.add(Str.price);
		keyArray.add(Str.manufacturer);
		keyArray.add(Str.quantity);
		keyArray.add(Str.coreNumber);
		keyArray.add(Str.clockRate);
		keyArray.add(Str.fabrication);
		keyArray.add(Str.tdp);
		keyArray.add(Str.chipset);
		keyArray.add(Str.memory);
		keyArray.add(Str.slot);
		keyArray.add(Str.port);
		obj.put("Keys", keyArray);
    	try {
			return (JSONObject)(new JSONParser().parse(getAttribute(obj.toJSONString())));
		} catch(Exception ex) {
			System.out.println("Unexpected error occurred");
			return null;
		}
	}
}