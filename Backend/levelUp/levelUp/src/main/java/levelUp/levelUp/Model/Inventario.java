package levelUp.levelUp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Clase que representa a la tabla inventario en la bd
@Entity
@Table(name= "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemTitle;

    @Column(nullable = false)
    private String itemImageLink;

    @Column(nullable = false)
    private String itemCategory;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String itemDescription;

    @Column(nullable = false)
    private Double itemPrice;

    @Column(nullable = false)
    private int itemQuantity;
}
