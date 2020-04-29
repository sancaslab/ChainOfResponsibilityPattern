package withChainCorrectVersion;

import java.io.IOException;

public class GsonDatabaseClient {

	public static void main(String[] args) throws IOException {
		// TOD Auto-generated method stub 

		MedicinesAnalizer medAn = new MedicinesAnalizer(null);
		RescueMedPresAnalizer resMedAn = new RescueMedPresAnalizer(medAn);
		
		PhysiotherapiesAnalizer phyAn = new PhysiotherapiesAnalizer(resMedAn);
		PosologiesAnalizer poAn = new PosologiesAnalizer(phyAn);
		UserManualStepsAnalizer usManAn = new UserManualStepsAnalizer(poAn);
		
		//Inicialmente daban error -> solucionado con la función peek()
		UserManualPhysioStepsAnalizer usManPhyAn = new UserManualPhysioStepsAnalizer(usManAn);
		MedicinePresentationsAnalizer medPredAn = new MedicinePresentationsAnalizer(usManPhyAn);
		
		ActiveIngredientsAnalizer actIngAn = new ActiveIngredientsAnalizer(medPredAn);
		InhalersAnalizer inhAn = new InhalersAnalizer(actIngAn);
		
		
		DatabaseJSonReader dbjr = new DatabaseJSonReader(inhAn);
		System.out.println(dbjr.parse("datos.json"));

	}

}
