package kdt.dev.ecommerce.product.domain.entity;

import static jakarta.persistence.ConstraintMode.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kdt.dev.ecommerce.global.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "product")
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String productName;

	private String brand;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "promotion_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private Promotion promotion;

	public Product(
		String productName,
		String brand,
		Promotion promotion
	) {
		this.productName = productName;
		this.brand = brand;
		this.promotion = promotion;
	}

	public int getDiscountedPrice(int price) {
		return promotion.discount(price);
	}
}
