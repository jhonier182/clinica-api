package com.jhonier.ws.dao;

import com.jhonier.ws.utilidades.PersonaUtilidades;
import com.jhonier.ws.Dto.PersonaDto;
import org.springframework.stereotype.Repository;


import java.util.List;

import static com.jhonier.ws.utilidades.PersonaUtilidades.listarPersonas;

@Repository
public class PersonaDao {

    public PersonaDao() {
        PersonaUtilidades.iniciarLista();
    }


    public PersonaDto ConsultarPersonaIndividual(String documento) {
        PersonaDto personaVo = null;
        for (PersonaDto p : listarPersonas) {
            if (p.getDocumento().equals(documento)) {
                personaVo = new PersonaDto();
                personaVo.setDocumento(p.getDocumento());
                personaVo.setNombre(p.getNombre());
                personaVo.setTelefono(p.getTelefono());
                personaVo.setEdad(p.getEdad());
                personaVo.setProfesion(p.getProfesion());
                personaVo.setPassword(p.getPassword());
                personaVo.setTipo(p.getTipo());
            }
        }
        return personaVo;
    }


    public List<PersonaDto>obtenerListaPersonas(){
        return PersonaUtilidades.listarPersonas;
    }



    public PersonaDto registrarPersona(PersonaDto persona) {
      for (PersonaDto obj :PersonaUtilidades.listarPersonas) {
          if (obj.getDocumento().equals(persona.getDocumento())) {
              return null;
          }
      }
       persona.setPassword(persona.getDocumento());
       PersonaUtilidades.listarPersonas.add(persona);
       return persona;
    }




    public PersonaDto consultarLogin(String documento, String pass) {

        for (PersonaDto p : PersonaUtilidades.listarPersonas) {
            if (p.getDocumento().equals(documento) && p.getPassword().equals(pass)) {
                return p;
            }
        }
        return null;
    }


    public PersonaDto actualizarPersona(PersonaDto persona){
        PersonaDto personavo=null;
        for(PersonaDto obj: PersonaUtilidades.listarPersonas){
            if (obj.getDocumento().equals(persona.getDocumento())){
                obj.setDocumento(persona.getDocumento());
                obj.setNombre(persona.getNombre());
                obj.setTelefono(persona.getTelefono());
                obj.setEdad(persona.getEdad());
                obj.setProfesion(persona.getProfesion());
                obj.setPassword(persona.getPassword());
                obj.setTipo(persona.getTipo());
                personavo = obj;
                break;
            }
        }
        return personavo;
    }

    public boolean eliminarPersona(PersonaDto persona){
        for (int i= 0;i<PersonaUtilidades.listarPersonas.size();i++){
            if (PersonaUtilidades.listarPersonas.get(i).getDocumento().equals(persona.getDocumento())) {
                PersonaUtilidades.listarPersonas.remove(i);
                return true;
            }
        }
        return false;
    }





}


