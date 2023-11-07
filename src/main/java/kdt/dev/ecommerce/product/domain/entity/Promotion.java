package kdt.dev.ecommerce.product.domain.entity;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kdt.dev.ecommerce.global.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "product")
public class Promotion extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String promotionName;

	private int discountAmount;

	public Promotion(
		String promotionName,
		int discountAmount
	) {
		this.promotionName = promotionName;
		this.discountAmount = discountAmount;
	}
}
