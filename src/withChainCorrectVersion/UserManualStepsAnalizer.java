package withChainCorrectVersion;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class UserManualStepsAnalizer extends ChainOfResponsibilityElement{

	public UserManualStepsAnalizer(ChainOfResponsibilityElement e) {
		super(e);

	}

	private String readUserManualStepsEntry(JsonReader reader) throws IOException {
		// reader.require(XmlPullParser.START_TAG, ns, SINGLE_ELEMENT_TAGNAME);
		String stepTit = null;
		String stepTex = null;
		String stepImag = null;
		String inhalerR = null;

		while (reader.hasNext()) {
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

			else if (NextName.equals("inhalerRef")) {
				inhalerR = reader.nextString();
			}

			else {
				reader.skipValue();
			}
		}

		return stepTit + "; " + stepTex + "; " + stepImag + "; " + inhalerR;
	}

	
	private StringBuffer readUserManualSteps(JsonReader reader) throws IOException {
		StringBuffer userData = new StringBuffer();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			userData.append(readUserManualStepsEntry(reader)).append("\n");
			reader.endObject();
		}
		userData.append("\n");
		reader.endArray();

		return userData;
	}

	public StringBuffer read(String tagname, JsonReader reader) throws IOException {

		StringBuffer readData = new StringBuffer();

		if (tagname.equals("userManualSteps")) {
			readData.append(readUserManualSteps(reader)).append("\n");
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
