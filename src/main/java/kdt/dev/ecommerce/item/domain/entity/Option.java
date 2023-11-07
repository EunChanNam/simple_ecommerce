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
import kdt.dev.ecommerce.item.domain.dto.OptionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "option")
public class Option extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private Item item;

	private String color;

	private String size;

	private String customOption;

	private String changeAmount;

	private int stock;

	public Option(OptionDto optionDto) {
		this.item = optionDto.item();
		this.color = optionDto.color();
		this.size = optionDto.size();
		this.customOption = optionDto.customOption();
		this.changeAmount = optionDto.changeAmount();
		this.stock = optionDto.stock();
	}
}
