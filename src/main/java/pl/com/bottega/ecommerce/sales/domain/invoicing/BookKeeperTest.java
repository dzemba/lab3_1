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
	
			Id id = new Id("1");
	 		Money money = new Money(1);
	 		InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
	 		bookKeeper = new BookKeeper(mockInvoiceFactory);
	 		ClientData clientData = new databuilder().build();
	 		when(mockInvoiceFactory.create(clientData)).thenReturn(
	 				new Invoice(id, clientData));
	 		InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
	 		TaxPolicy taxPolicy = mock(TaxPolicy.class);
	 		when(taxPolicy.calculateTax(ProductType.FOOD, money)).thenReturn(
	 				new Tax(money, "opis"));
	 		ProductData productData = new productDataBuilder().withPrice(1).withProductType(ProductType.FOOD).build();
	
			RequestItem requestItem = new ItemBuilder().withProductData(productData).witTotalCost(1).build();
	 		invoiceRequest.add(requestItem);
	 
	 		// when
	 		Invoice invoiceResult = bookKeeper.issuance(invoiceRequest, taxPolicy);
	 		int result = invoiceResult.getItems().size();
	 
	 		// then
	 		assertThat(result, is(1));
	 		
		}
	
		@Test
			public void requestInvoiceWitTwoPosition_callCalculateTaxTwice() {
		
			Id id = new Id("1");
			Money moneyy = new Money(1);
			
			ProductType productTypeEveryItem = ProductType.FOOD;
			InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
			bookKeeper = new BookKeeper(mockInvoiceFactory);
			ClientData clientData = new ClientData(id, "Test");
			
			
			ProductData productData = new productDataBuilder().withPrice(1).withProductType(ProductType.FOOD).build();
			
			RequestItem requestItem = new ItemBuilder().withProductData(productData).witTotalCost(1).build();
			
			InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
			
						
			
			
			when( mockInvoiceFactory.create( (ClientData) Mockito.any() ) ).thenReturn( new Invoice( Id.generate() , clientData ) );
					TaxPolicy taxPolicy = mock( TaxPolicy.class );
					when( taxPolicy.calculateTax( (ProductType)Mockito.any(), (Money)Mockito.any() ) ).thenReturn( new Tax( new Money( 0 ) , "" ) ); 
			 
			
			

				invoiceRequest.add(requestItem);
				invoiceRequest.add(requestItem);
		
		
				bookKeeper.issuance(invoiceRequest, taxPolicy);
		
	
				Mockito.verify(taxPolicy, Mockito.times(2)).calculateTax(
						productTypeEveryItem, moneyy);
			}
		
		@Test
			public void testWithNoPosition_shouldReturnNoPosition() {
		
			Id id = new Id("1");
			Money money = new Money(1);
			InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
			bookKeeper = new BookKeeper(mockInvoiceFactory);
			ClientData clientData = new databuilder().build();
			when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(id, clientData));
			
			InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
			TaxPolicy taxPolicy = mock(TaxPolicy.class);
						
			ProductData productData = new productDataBuilder().withPrice(1).withProductType(ProductType.FOOD).build();
			
		

				assertThat(bookKeeper.issuance(invoiceRequest, taxPolicy).getItems().size(), is(0));
			}
		
		
		@Test
		public void requestInvoiceWitZeroPosition_noCalculateTaxTwice() {
	
		Id id = new Id("1");
		Money moneyy = new Money(1);
		
		ProductType productTypeEveryItem = ProductType.FOOD;
		InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
		bookKeeper = new BookKeeper(mockInvoiceFactory);
		ClientData clientData = new databuilder().build();
		when(mockInvoiceFactory.create(clientData)).thenReturn(new Invoice(id, clientData));
		
		ProductData productData = new ProductData(id, moneyy,"book", productTypeEveryItem, new Date());
		
		RequestItem requestItem = new RequestItem(productData, 4,moneyy);
		
		InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
		TaxPolicy taxPolicy = mock(TaxPolicy.class);
					
		
		
		when(taxPolicy.calculateTax(ProductType.FOOD, moneyy)).thenReturn(new Tax(moneyy, "spis"));
	
	
			bookKeeper.issuance(invoiceRequest, taxPolicy);
	

			Mockito.verify(taxPolicy, Mockito.times(0)).calculateTax(
					productTypeEveryItem, moneyy);
		}
		
}
