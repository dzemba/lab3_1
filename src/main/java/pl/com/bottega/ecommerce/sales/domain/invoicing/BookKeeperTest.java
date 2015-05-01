package pl.com.bottega.ecommerce.sales.domain.invoicing;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;


import org.mockito.Mockito;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;


public class BookKeeperTest {

	private BookKeeper bookKeeper;
	
		@Test
		public void requestInvoiceWithOnePosition_shouldReturnInvoiceWithOnePosition() {
	
			
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
	
		@Test
			public void requestInvoiceWitTwoPosition_callCalculateTaxTwice() {
		
			Id id = new Id("999");
			Money moneyy = new Money(1);
			
			ProductType productTypeEveryItem = ProductType.FOOD;
			InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
			bookKeeper = new BookKeeper(mockInvoiceFactory);
			ClientData clientData = new ClientData(id, "Test");
			when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(id, clientData));
			
			ProductData productData = new ProductData(id, moneyy,"book", productTypeEveryItem, new Date());
			
			RequestItem requestItem = new RequestItem(productData, 4,moneyy);
			
			InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
			TaxPolicy taxPolicy = mock(TaxPolicy.class);
						
			
			
			when(taxPolicy.calculateTax(ProductType.FOOD, moneyy)).thenReturn(new Tax(moneyy, "spis"));
			
			

				invoiceRequest.add(requestItem);
				invoiceRequest.add(requestItem);
		
		
				bookKeeper.issuance(invoiceRequest, taxPolicy);
		
	
				Mockito.verify(taxPolicy, Mockito.times(2)).calculateTax(
						productTypeEveryItem, moneyy);
			}
		
		@Test
			public void testWithNoPosition_shouldReturnNoPosition() {
		
			Id id = new Id("999");
			Money money = new Money(1);
			InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
			bookKeeper = new BookKeeper(mockInvoiceFactory);
			ClientData clientData = new ClientData(id, "Test");
			when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(id, clientData));
			
			InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
			TaxPolicy taxPolicy = mock(TaxPolicy.class);
						
			ProductData productData = new ProductData(id, money, "ksiazka",ProductType.FOOD, new Date());
			
		

				assertThat(bookKeeper.issuance(invoiceRequest, taxPolicy).getItems().size(), is(0));
			}
		
}
