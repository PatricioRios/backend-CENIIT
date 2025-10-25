package ar.edu.ceniit.demo.reservas.infraestructure.input.mapper;

import ar.edu.ceniit.demo.reservas.domain.Reserva;
import ar.edu.ceniit.demo.reservas.infraestructure.input.dto.ReservaResponseDTO;

public interface ReservaDTOMapper {
    ReservaResponseDTO toDTO(Reserva reserva);
    Reserva toDomain(ReservaResponseDTO dto);
}
