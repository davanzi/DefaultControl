package com.controldefault.util;

public class InfoUtil {

	public static void resultInfo (int response, String origin, String operation){
		switch (response) {
		case 1:
			System.out.println("INFO CONTROL: GOOD: InfoUtil: "+ origin +"Tipo de opera��o: "+ operation +": Resultado = OK");
			break;
		case 0:
			System.out.println("INFO CONTROL: GOOD: InfoUtil: "+ origin +"Tipo de opera��o: "+ operation +": Resultado = FALHOU");
			break;
		case -1:
			System.out.println("INFO CONTROL: GOOD: InfoUtil: "+ origin +"Tipo de opera��o: "+ operation +": Resultado = INCOMPLETA");
			break;
		default:
			break;
		}
	}
}
