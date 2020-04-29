package withChainCorrectVersion;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class PhysiotherapiesAnalizer extends ChainOfResponsibilityElement {

	public PhysiotherapiesAnalizer(ChainOfResponsibilityElement e) {
		super(e);

	}

	private String readPhysiotherapyEntry(JsonReader reader) throws IOException {
		// reader.require(XmlPullParser.START_TAG, ns, SINGLE_ELEMENT_TAGNAME);
		String phyName = null;
		String phyImage = null;

		while (reader.hasNext()) {
			String NextName = reader.nextName();
			if (NextName.equals("name")) {
				phyName = reader.nextString();
			} else if (NextName.equals("image")) {
				phyImage = reader.nextString();
			} else {
				reader.skipValue();
			}
		}

		return phyName + ": " + phyImage;
	}

	
	private StringBuffer readPhysiotherapies(JsonReader reader) throws IOException {
		StringBuffer phyData = new StringBuffer();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			phyData.append(readPhysiotherapyEntry(reader)).append("\n");
			reader.endObject();
		}
		phyData.append("\n");
		reader.endArray();

		return phyData;
	}

	public StringBuffer read(String tagname, JsonReader reader) throws IOException {

		StringBuffer readData = new StringBuffer();

		if (tagname.equals("physiotherapies")) {
			readData.append(readPhysiotherapies(reader)).append("\n");
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
