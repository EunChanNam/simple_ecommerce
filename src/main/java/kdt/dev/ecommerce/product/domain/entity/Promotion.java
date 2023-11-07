package kdt.dev.ecommerce.product.domain.entity;

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
@Table(name = "product")
public class Promotion {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String promotionName;

	private int discountAmount;
}
