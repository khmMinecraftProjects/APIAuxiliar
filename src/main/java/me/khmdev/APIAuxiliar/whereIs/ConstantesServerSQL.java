package me.khmdev.APIAuxiliar.whereIs;

import me.khmdev.APIBase.Almacenes.ConstantesAlmacen;

public class ConstantesServerSQL {
	public static final String tablaServ = "servidor", ServidorID = "IP",
			ServidorAlias = "alias", UserServidor = "servidor",
			ServerNull = "null", ServerNullName = "Desconectado";

	public static final String[] sql = {
			"CREATE TABLE IF NOT EXISTS `" + tablaServ + "` (" + "" + ServidorID
					+ " varchar(22) NOT NULL," + "" + ServidorAlias
					+ " varchar(30) NOT NULL," + "PRIMARY KEY (" + ServidorID
					+ ")" + ")\n",

			"INSERT INTO `" + tablaServ + "` (`" + ServidorID + "`, `"
					+ ServidorAlias + "`)" + " VALUES (" + "'" + ServerNull
					+ "', '" + ServerNullName + "'" + ")\n",
			"ALTER TABLE `" + ConstantesAlmacen.tabla + "` " + "ADD "
					+ UserServidor + " varchar(22)," + "ADD FOREIGN KEY ( "
					+ UserServidor + " ) REFERENCES " + tablaServ + "( "
					+ ServidorID + " ) ON DELETE CASCADE ON UPDATE CASCADE"
					+ ")\n" };
}
