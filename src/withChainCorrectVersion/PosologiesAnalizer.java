package withChainCorrectVersion;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class PosologiesAnalizer extends ChainOfResponsibilityElement{
	

	public PosologiesAnalizer(ChainOfResponsibilityElement e) {
		super(e);

	}

	private String readPosologyEntry(JsonReader reader) throws IOException {
		// reader.require(XmlPullParser.START_TAG, ns, SINGLE_ELEMENT_TAGNAME);
		String poDesc = null;

		while (reader.hasNext()) {
			String NextName = reader.nextName();
			if (NextName.equals("description")) {
				poDesc = reader.nextString();
			} else {
				reader.skipValue();
			}
		}

		return poDesc;
	}

	
	private StringBuffer readPosologies(JsonReader reader) throws IOException {
		StringBuffer phyData = new StringBuffer();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			phyData.append(readPosologyEntry(reader)).append("\n");
			reader.endObject();
		}
		phyData.append("\n");
		reader.endArray();

		return phyData;
	}

	public StringBuffer read(String tagname, JsonReader reader) throws IOException {

		StringBuffer readData = new StringBuffer();

		if (tagname.equals("posologies")) {
			readData.append(readPosologies(reader)).append("\n");
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

