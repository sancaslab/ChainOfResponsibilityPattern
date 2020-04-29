package withChainCorrectVersion;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class ChainOfResponsibilityElement {

	protected ChainOfResponsibilityElement sig;
	
	public ChainOfResponsibilityElement(ChainOfResponsibilityElement e) {
		sig = e;
		
	}
	
	public StringBuffer read(String tagname, JsonReader reader) throws IOException{
		return sig.read(tagname, reader);
		
	}
}
