package kdt.dev.ecommerce.item.domain.entity;

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
import kdt.dev.ecommerce.item.domain.dto.ItemDetailDto;
import kdt.dev.ecommerce.item.exception.StockNotEnoughException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "item_detail")
public class ItemDetail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private Item item;

	private String color;

	private String size;

	private String customOption;

	private int changeAmount;

	private int stock;

	public ItemDetail(ItemDetailDto itemDetailDto) {
		this.item = itemDetailDto.item();
		this.color = itemDetailDto.color();
		this.size = itemDetailDto.size();
		this.customOption = itemDetailDto.customOption();
		this.changeAmount = itemDetailDto.changeAmount();
		this.stock = itemDetailDto.stock();
	}

	public void updateStock(int orderQuantity) {
		if (stock < orderQuantity) {
			throw new StockNotEnoughException(
				item.getItemName(),
				color,
				size,
				customOption
			);
		}
		this.stock -= orderQuantity;
	}

	public int getItemPrice() {
		return item.getItemPrice();
	}
}
