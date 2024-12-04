package com.jhonier.ws.Service;

import com.jhonier.ws.Dto.PersonaDto;
import com.jhonier.ws.dao.PersonaDao;
import com.jhonier.ws.utilidades.PersonaUtilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonaService {

      private  final PersonaDao personaDao;

      @Autowired
     public PersonaService(PersonaDao personaDao) {
          this.personaDao = personaDao;
      }


      public PersonaDto obtenerPersonaPorDocumento(String documento) {
         return personaDao.ConsultarPersonaIndividual(documento);
      }


    public PersonaDto consultarPersonaIdProfesion(String documento,String idProfesion){
          for (PersonaDto p: PersonaUtilidades.listarPersonas){
              if (p.getDocumento().equals(documento) && p.getProfesion().equals(idProfesion)){
                  PersonaDto personaVo = new PersonaDto(p.getDocumento(),p.getNombre(),p.getTelefono()
                  ,p.getEdad(),p.getProfesion(),p.getPassword(),p.getTipo());
                  return personaVo;
              }
          }return null;
    }

    public List<PersonaDto>obtenerListaPersonas(){
        return personaDao.obtenerListaPersonas();
    }



    public PersonaDto registrarPersona(PersonaDto persona){
          if (persona == null|| persona.getDocumento() == null || persona.getDocumento().isEmpty()){
              return null;
          }
          return personaDao.registrarPersona(persona);
    }


    public PersonaDto consultarLogin(PersonaDto persona){
          if (persona == null|| persona.getDocumento() == null || persona.getDocumento().isEmpty()){
              return null;
          }
          return  personaDao.consultarLogin(persona.getDocumento(),persona.getPassword());
    }


     public  PersonaDto actualizarPersona(PersonaDto persona){
          if (persona == null || persona.getDocumento() == null || persona.getDocumento().isEmpty() ){
              return null;
          }
          PersonaDto personaExistente = personaDao.ConsultarPersonaIndividual(persona.getDocumento());
          if (personaExistente == null){
              return persona;
          }
          return personaDao.actualizarPersona(persona);
     }

     public boolean eliminarPersona(String documento){
          PersonaDto  personaExistente = personaDao.ConsultarPersonaIndividual(documento);
          if (personaExistente == null){
              return false;
          }
          return personaDao.eliminarPersona(personaExistente);
     }

}
