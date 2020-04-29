package withChainCorrectVersion;

public class Pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MedicinesAnalizer medAn = new MedicinesAnalizer(null);
		System.out.println(medAn.getClass().getTypeName());
		System.out.println(medAn.getClass().getTypeName().equals("withChainCorrectVersion.MedicinesAnalizer"));
		

	}

}
