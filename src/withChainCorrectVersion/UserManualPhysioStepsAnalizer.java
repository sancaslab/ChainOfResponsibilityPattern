package withChainCorrectVersion;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class UserManualPhysioStepsAnalizer extends ChainOfResponsibilityElement {

	public UserManualPhysioStepsAnalizer(ChainOfResponsibilityElement e) {
		super(e);

	}

	private String readUserManualPhyStepsEntry(JsonReader reader) throws IOException {
		// reader.require(XmlPullParser.START_TAG, ns, SINGLE_ELEMENT_TAGNAME);

		String stepTit = null;
		String stepTex = null;
		String stepImag = null;
		String physioR = null;
		//String NextName = null;
		
		while (reader.hasNext()) {

			/*
			JsonToken js = JsonToken.STRING;
			if (reader.peek().equals(js)) {
				NextName = reader.nextString();
			} else {
				NextName = reader.nextName();
			}
			*/
			
			String NextName = reader.nextName();
			if (NextName.equals("stepTitle")) {
				stepTit = reader.nextString();
			}

			else if (NextName.equals("stepImage")) {
				stepImag = reader.nextString();
			}

			else if (NextName.equals("stepText")) {
				stepTex = reader.nextString();
			}

			else if (NextName.equals("physioRef")) {
				physioR = reader.nextString();
			}

			else {
				reader.skipValue();
			}
		}

		return stepTit + "; " + stepTex + "; " + stepImag + "; " + physioR;
	}

	
	private StringBuffer readUserManualPhySteps(JsonReader reader) throws IOException {
		StringBuffer phyData = new StringBuffer();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			phyData.append(readUserManualPhyStepsEntry(reader)).append("\n");
			reader.endObject();
		}
		phyData.append("\n");
		reader.endArray();

		return phyData;
	}

	public StringBuffer read(String tagname, JsonReader reader) throws IOException {

		StringBuffer readData = new StringBuffer();
		if (tagname.equals("userManualPhysioSteps")) {
			readData.append(readUserManualPhySteps(reader)).append("\n");
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
