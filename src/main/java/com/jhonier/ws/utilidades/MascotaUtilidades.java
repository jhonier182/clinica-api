package com.jhonier.ws.utilidades;

import com.jhonier.ws.Dto.MascotaDto;

import java.util.ArrayList;
import java.util.List;

public class MascotaUtilidades {

    public static List<MascotaDto> listarMascotas = new ArrayList<>();

    public static void iniciarLista() {
        if (listarMascotas.isEmpty()) {
            listarMascotas.add(new MascotaDto(1, "Fido", "Labrador", 3, 111));
            listarMascotas.add(new MascotaDto(2, "Luna", "Bulldog", 2, 222));
            listarMascotas.add(new MascotaDto(3, "Max", "Pastor Alemán", 4, 111));
        }
    }
}
