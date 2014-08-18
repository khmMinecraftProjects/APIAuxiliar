package me.khmdev.APIAuxiliar.whereIs;

import me.khmdev.APIBase.Almacenes.ConstantesAlmacen;

public class WIConstantes {
	public static final String tabla="servidor",
			ServidorID="IP",
			ServidorAlias="alias",
			UserServidor="servidor",
			ServerNull="null",
			ServerNullName="Desconectado";
			
	public static final String[] sql={
		"CREATE TABLE IF NOT EXISTS `"+tabla+"` (" +
		""+ServidorID+" varchar(22) NOT NULL,"+
		""+ServidorAlias+" varchar(30) NOT NULL," +
		"PRIMARY KEY ("+ServidorID+")" +
		")\n" ,
		"INSERT INTO `"+tabla+"` (`"+ServidorID+"`, `"+ServidorAlias+"`)" +
		" VALUES (" +
		"'"+ServerNull+"', '"+ServerNullName+"'" +
		")\n" ,
		"ALTER TABLE `"+ConstantesAlmacen.tabla+"` " +
		"ADD "+UserServidor+" varchar(22)," +
		"ADD FOREIGN KEY ( "+UserServidor+" ) REFERENCES "+tabla
		+"( "+ServidorID+" ) ON DELETE CASCADE ON UPDATE CASCADE" +
		"\n"};
}
