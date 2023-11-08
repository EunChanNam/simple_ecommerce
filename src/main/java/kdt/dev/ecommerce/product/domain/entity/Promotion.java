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
@Table(name = "promotion")
public class Promotion extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String promotionName;

	private double discountAmount;

	public Promotion(
		String promotionName,
		double discountAmount
	) {
		this.promotionName = promotionName;
		this.discountAmount = discountAmount;
	}

	public int discount(int price) {
		return (int)(price * ((100 - discountAmount) / 100));
	}
}
