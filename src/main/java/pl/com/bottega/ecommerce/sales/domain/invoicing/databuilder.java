package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;

public class databuilder {

	private Id id;
	private String name;

	public databuilder withId(Id id) {
		this.id = id;
		return this;
	}

	public databuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ClientData build() {
		return new ClientData(id, name);
	}

}