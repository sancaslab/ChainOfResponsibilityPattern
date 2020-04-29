package withChainCorrectVersion;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class MedicinesAnalizer extends ChainOfResponsibilityElement {

	
	private static final String NAME_FIELD_TAGNAME = "name";

	public MedicinesAnalizer(ChainOfResponsibilityElement e) {
		super(e);
	}

	// Parses the contents of a medicine list.
	private StringBuffer readMedicines(JsonReader reader) throws IOException {
		StringBuffer medicineData = new StringBuffer();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			medicineData.append(readMedicineEntry(reader)).append("\n");
			reader.endObject();
		}
		medicineData.append("\n");
		reader.endArray();
		return medicineData;
	}

	// Parses the contents of a medicine.
	private String readMedicineEntry(JsonReader reader) throws IOException {
		// reader.require(XmlPullParser.START_TAG, ns, SINGLE_ELEMENT_TAGNAME);
		String medName = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(NAME_FIELD_TAGNAME)) {
				medName = reader.nextString();
			} else {
				reader.skipValue();
			}
		}

		return medName;
	}

	public StringBuffer read(String tagname, JsonReader reader) throws IOException {
		StringBuffer readData = new StringBuffer();
		if (tagname.equals("medicines")) {
			readData.append(readMedicines(reader)).append("\n");

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
