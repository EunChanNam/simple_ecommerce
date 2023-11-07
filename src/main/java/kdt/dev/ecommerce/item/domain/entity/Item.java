package kdt.dev.ecommerce.item.domain.entity;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String itemName;

	private int itemPrice;
}
