package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ItemBuilder {
	private ProductData productData = new productDataBuilder().build();
	private int quantity = 1;
	private Money totalCost = new Money(0);

	public ItemBuilder withProductData(ProductData data) {
		this.productData = data;
		return this;
	}

	public ItemBuilder withQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public ItemBuilder witTotalCost(double cost) {
		this.totalCost = new Money(cost);
		return this;
	}

	public RequestItem build() {
		return new RequestItem(productData, quantity, totalCost);
	}
}