package withChainCorrectVersion;

import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class MedicinePresentationsAnalizer extends ChainOfResponsibilityElement {

	private static final String MEDREF_FIELD_TAGNAME = "medicineRef";
	private static final String ACTINGREF_FIELD_TAGNAME = "activeIngRef";
	private static final String INHREF_FIELD_TAGNAME = "inhalerRef";
	private static final String DOSE_FIELD_TAGNAME = "dose";
	private static final String POSOLOGY_FIELD_TAGNAME = "posologyRef";
	private static final String FIELD_SEPARATOR = "; ";

	public MedicinePresentationsAnalizer(ChainOfResponsibilityElement e) {
		super(e);

	}

	// Parses the contents of a medicine list.
	private StringBuffer readMedicinePresentations(JsonReader reader) throws IOException {
		StringBuffer rescueMedicinePresentationData = new StringBuffer();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			rescueMedicinePresentationData.append(readMedicinePresentationEntry(reader)).append("\n");
			reader.endObject();
		}
		rescueMedicinePresentationData.append("\n");
		reader.endArray();
		return rescueMedicinePresentationData;
	}

	// Parses the contents of a rescue medicine presentation entry
	private String readMedicinePresentationEntry(JsonReader reader) throws IOException {
		String medRef = null;
		String aiRef = null;
		String inhRef = null;
		String dose = null;
		String pos = null;

		while (reader.hasNext()) {

			String name = reader.nextName();

			if (name.equals(MEDREF_FIELD_TAGNAME)) {
				medRef = reader.nextString();
			} else if (name.equals(ACTINGREF_FIELD_TAGNAME)) {
				aiRef = reader.nextString();

			} else if (name.equals(INHREF_FIELD_TAGNAME)) {
				StringBuffer res = new StringBuffer();
				reader.beginArray();
				while (reader.hasNext()) {
					res.append(reader.nextString()).append(",");
				}
				reader.endArray();
				res.deleteCharAt(res.length() - 1);
				inhRef = new String(res);

			} else if (name.equals(DOSE_FIELD_TAGNAME)) {
				StringBuffer res = new StringBuffer();
				reader.beginArray();
				while (reader.hasNext()) {
					res.append(reader.nextString()).append(",");
				}
				reader.endArray();
				res.deleteCharAt(res.length() - 1);
				dose = new String(res);

			} else if (name.equals(POSOLOGY_FIELD_TAGNAME)) {

				JsonToken js = JsonToken.STRING;
				if (reader.peek().equals(js)) {
					pos = reader.nextString();
				} else {

					StringBuffer res = new StringBuffer();
					reader.beginArray();
					while (reader.hasNext()) {
						res.append(reader.nextString()).append(",");
					}
					reader.endArray();
					res.deleteCharAt(res.length() - 1);
					pos = new String(res);

				}

			} else {
				reader.skipValue();
			}

		}

		return medRef + FIELD_SEPARATOR + aiRef + FIELD_SEPARATOR + inhRef + FIELD_SEPARATOR + dose + FIELD_SEPARATOR
				+ pos;
	}

	
	public StringBuffer read(String tagname, JsonReader reader) throws IOException {

		StringBuffer readData = new StringBuffer();

		if (tagname.equals("medicinePresentations")) {
			readData.append(readMedicinePresentations(reader)).append("\n");
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
