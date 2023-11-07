package kdt.dev.ecommerce.order.domain.entity;

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
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private Product product;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_detail_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private ItemDetail itemDetail;

	private int quantity;

	private int price;

	private Order(
		User user,
		Product product,
		ItemDetail itemDetail,
		int quantity
	) {
		updateItemStock(itemDetail, quantity);
		this.user = user;
		this.product = product;
		this.itemDetail = itemDetail;
		this.quantity = quantity;
		this.price = calculatePrice(product, itemDetail, quantity);
	}

	private void updateItemStock(ItemDetail itemDetail, int quantity) {
		itemDetail.updateStock(quantity);
	}

	private int calculatePrice(
		Product product,
		ItemDetail itemDetail,
		int quantity
	) {
		int totalPrice = itemDetail.getItemPrice() * quantity;
		int discountedPrice = product.getDiscountedPrice(totalPrice);
		int additionalPrice = itemDetail.getChangeAmount() * quantity;
		return discountedPrice + additionalPrice;
	}

	//==Factory method==//
	public static Order of(
		User user,
		Product product,
		ItemDetail itemDetail,
		int quantity
	) {
		return new Order(
			user,
			product,
			itemDetail,
			quantity
		);
	}
}
