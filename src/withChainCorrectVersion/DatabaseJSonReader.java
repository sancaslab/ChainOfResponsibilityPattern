package withChainCorrectVersion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;

public class DatabaseJSonReader {

	private ChainOfResponsibilityElement chainElement;
	
	public DatabaseJSonReader(ChainOfResponsibilityElement c) {
		chainElement = c;
		
	}

	public String parse(String jsonFileName) throws IOException {

		InputStream usersIS = new FileInputStream(new File(jsonFileName));
		JsonReader reader = new JsonReader(new InputStreamReader(usersIS, "UTF-8"));

		reader.beginObject();
		StringBuffer readData = new StringBuffer();
		
		while (reader.hasNext()) {
			String name = reader.nextName();
			readData.append(chainElement.read(name, reader));

		}

		return new String(readData);
	}

}
