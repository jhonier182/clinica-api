package com.jhonier.ws.dao;

import com.jhonier.ws.utilidades.MascotaUtilidades;
import com.jhonier.ws.utilidades.PersonaUtilidades;
import com.jhonier.ws.Dto.MascotaDto;
import com.jhonier.ws.Dto.PersonaDto;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class MascotaDao {

    public MascotaDao() {
        MascotaUtilidades.iniciarLista();
    }


    public MascotaDto registrarMascota(MascotaDto mascotaVo) {
        PersonaDto persona = null;
        for (PersonaDto p : PersonaUtilidades.listarPersonas) {
            if (p.getDocumento().equals(String.valueOf(mascotaVo.getPersonaId()))) {
                persona = p;
                break;
            }
        }
        if (persona == null) {
            return null;
        }
        for (MascotaDto m : MascotaUtilidades.listarMascotas) {
            if (m.getId() == mascotaVo.getId()) {
                return null;
            }
        }
        MascotaUtilidades.listarMascotas.add(mascotaVo);
        return mascotaVo;
    }


    public MascotaDto consultarMascotaPorId(int id) {
        for (MascotaDto mascota : MascotaUtilidades.listarMascotas) {
            if (mascota.getId() == id) {
                return mascota;
            }
        }
        return null;
    }

    public List<MascotaDto> obtenerTodasMascotas() {
        return MascotaUtilidades.listarMascotas;
    }


    public MascotaDto actualizarMascota(MascotaDto mascotaVo) {
        for (MascotaDto m : MascotaUtilidades.listarMascotas) {
            if (m.getId() == mascotaVo.getId()) {
                m.setNombre(mascotaVo.getNombre());
                m.setRaza(mascotaVo.getRaza());
                m.setEdad(mascotaVo.getEdad());
                m.setPersonaId(mascotaVo.getPersonaId());
                return m;
            }
        }
        return null;
    }

    public boolean eliminarMascota(int id) {
        MascotaDto mascota = consultarMascotaPorId(id);
        if (mascota != null) {
            MascotaUtilidades.listarMascotas.remove(mascota);
        }
        return false;
    }


    public List<MascotaDto> obtenerMascotasPorPersonaId(int personaId) {
        List<MascotaDto> mascotasPersona = new ArrayList<>();
        for (MascotaDto mascota : MascotaUtilidades.listarMascotas) {
            if (mascota.getPersonaId() == personaId) {
                mascotasPersona.add(mascota);
            }
        }
        return mascotasPersona;
    }
}
