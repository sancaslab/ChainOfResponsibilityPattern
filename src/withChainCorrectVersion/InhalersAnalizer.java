package withChainCorrectVersion;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class InhalersAnalizer extends ChainOfResponsibilityElement {
	
	public InhalersAnalizer(ChainOfResponsibilityElement e) {
		super(e);

	}

	private String readInhalerEntry(JsonReader reader) throws IOException {
		// reader.require(XmlPullParser.START_TAG, ns, SINGLE_ELEMENT_TAGNAME);
		String inhName = null;
		String inhImage = null;

		while (reader.hasNext()) {
			String NextName = reader.nextName();
			if (NextName.equals("name")) {
				inhName = reader.nextString();
			} else if (NextName.equals("image")) {
				inhImage = reader.nextString();
			} else {
				reader.skipValue();
			}
		}

		return inhName + ": " + inhImage;
	}

	private StringBuffer readInhalers(JsonReader reader) throws IOException {
		StringBuffer Data = new StringBuffer();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			Data.append(readInhalerEntry(reader)).append("\n");
			reader.endObject();
		}
		Data.append("\n");
		reader.endArray();

		return Data;
	}

	public StringBuffer read(String tagname, JsonReader reader) throws IOException {

		StringBuffer readData = new StringBuffer();

		if (tagname.equals("inhalers")) {
			readData.append(readInhalers(reader)).append("\n");
		} else {
			
			if (sig != null) {
				readData.append(super.read(tagname, reader));
			} else {
				reader.skipValue();
				System.err.println("Category " + tagname + " not processed.");
			}
		}

		return readData;

	}

}
