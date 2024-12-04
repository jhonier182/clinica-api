package com.jhonier.ws.utilidades;

import com.jhonier.ws.Dto.PersonaDto;

import java.util.ArrayList;
import java.util.List;

public class PersonaUtilidades {

    public final static int TIPO_ADMIN=1;
    public final static int TIPO_EMPLEADO=2;
    static int bandera=0;

    public static List<PersonaDto>listarPersonas = new ArrayList<PersonaDto>();

    public static  void iniciarLista(){
        if (bandera==0){
            listarPersonas.add(new PersonaDto("admin","Administrador","NA",0,"NA","admin",1));
            listarPersonas.add(new PersonaDto("111","Cristian David Henao","567567",44,"Ingeniero","111",TIPO_ADMIN));
            listarPersonas.add(new PersonaDto("222","Juan Martin lozano","34534534",21,"Estudiante","222",TIPO_EMPLEADO));
            listarPersonas.add(new PersonaDto("333","Maria Andres Arias","345345",45,"Estudiante","333",TIPO_EMPLEADO));
        }
    }



}
