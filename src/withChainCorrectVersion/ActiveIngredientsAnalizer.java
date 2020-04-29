package withChainCorrectVersion;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class ActiveIngredientsAnalizer extends ChainOfResponsibilityElement{
	
	private static final String NAME_FIELD_TAGNAME = "name";


	public ActiveIngredientsAnalizer(ChainOfResponsibilityElement e) {
		super(e);
	}

	// Parses the contents of a medicine list.
	private StringBuffer readActiveIngredients(JsonReader reader) throws IOException {
		StringBuffer actData = new StringBuffer();
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			actData.append(readActiveIngredientEntry(reader)).append("\n");
			reader.endObject();
		}
		actData.append("\n");
		reader.endArray();
		return actData;
	}

	// Parses the contents of a medicine.
	private String readActiveIngredientEntry(JsonReader reader) throws IOException {
		String actIngName = null;
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(NAME_FIELD_TAGNAME)) {
				actIngName = reader.nextString();
			} else {
				reader.skipValue();
			}
		}

		return actIngName;
	}

	public StringBuffer read(String tagname, JsonReader reader) throws IOException {

		StringBuffer readData = new StringBuffer();
		if (tagname.equals("activeIngredients")) {
			readData.append(readActiveIngredients(reader)).append("\n");

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
