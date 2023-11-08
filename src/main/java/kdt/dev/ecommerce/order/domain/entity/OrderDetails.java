package kdt.dev.ecommerce.order.domain.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class OrderDetails {

	@OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private final List<OrderDetail> orderDetails = new ArrayList<>();

	public OrderDetails (
		Order order,
		List<ItemDetail> itemDetails,
		int quantity
	) {
		itemDetails.forEach(itemDetail -> {
			itemDetail.updateStock(quantity);
			orderDetails.add(new OrderDetail(order, itemDetail));
		});
	}

	public int getAdditionalPrice() {
		return orderDetails.stream()
			.mapToInt(OrderDetail::getChangeAmount)
			.reduce(Integer::sum)
			.orElse(0);
	}
}
