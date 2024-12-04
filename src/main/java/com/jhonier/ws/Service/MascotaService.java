package com.jhonier.ws.Service;

import com.jhonier.ws.Dto.MascotaDto;
import com.jhonier.ws.dao.MascotaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    @Autowired
    private MascotaDao mascotaDao;


    public MascotaDto registrarMascota(MascotaDto mascotaDto) {
        if (mascotaDto == null || mascotaDto.getNombre() == null || mascotaDto.getNombre().isEmpty()) {
            return null;
        }
        return mascotaDao.registrarMascota(mascotaDto);
    }


    public MascotaDto consultarMascotaPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return mascotaDao.consultarMascotaPorId(id);
    }


    public List<MascotaDto> obtenerTodasLasMascotas() {
        return mascotaDao.obtenerTodasMascotas();
    }

    public MascotaDto actualizarMascota(int id, MascotaDto mascotaDto) {
        if (mascotaDto == null || mascotaDto.getNombre() == null || mascotaDto.getNombre().isEmpty()) {
            return null;
        }
        if (id <= 0) {
            return null;
        }
        mascotaDto.setId(id);
        return mascotaDao.actualizarMascota(mascotaDto);
    }

    public boolean eliminarMascota(int id) {
        if (id <= 0) {
            return false;
        }
        MascotaDto mascotaExistente = mascotaDao.consultarMascotaPorId(id);
        if (mascotaExistente == null) {
            return false;
        }
        return mascotaDao.eliminarMascota(id);
    }


    public List<MascotaDto> obtenerMascotasPorPersonaId(int personaId) {
        if (personaId <= 0) {
            return null;
        }
        return mascotaDao.obtenerMascotasPorPersonaId(personaId);
    }
}
