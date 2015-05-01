package pl.com.bottega.ecommerce.sales.domain.invoicing;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;


import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;


public class BookKeeperTest {

	private BookKeeper bookKeeper;
	
		@Test
		public void requestInvoiceWithOnePosition_shouldReturnInvoiceWithOnePosition() {
	
			// given
			Id id = new Id("999");
			Money money = new Money(1);
			InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
			bookKeeper = new BookKeeper(mockInvoiceFactory);
			ClientData clientData = new ClientData(id, "Test");
			when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(id, clientData));
			
			InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
			TaxPolicy taxPolicy = mock(TaxPolicy.class);
						
			ProductData productData = new ProductData(id, money, "ksiazka",ProductType.FOOD, new Date());
			
			when(taxPolicy.calculateTax(ProductType.FOOD, money)).thenReturn(new Tax(money, "spis"));
			
			RequestItem requestItem = new RequestItem(productData, 4, money);invoiceRequest.add(requestItem);

			assertThat(bookKeeper.issuance(invoiceRequest, taxPolicy).getItems().size(), is(1));
		}

}
