package daw2a.gestion_alimentos_api_rest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Existencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idAlimento;
    private Long idUbicacion;
    private int cantidad;
    private LocalDate fechaEntrada;
}
