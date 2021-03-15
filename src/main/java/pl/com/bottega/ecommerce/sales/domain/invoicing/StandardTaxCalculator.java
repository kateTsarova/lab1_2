package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.math.BigDecimal;

public class StandardTaxCalculator implements TaxCalculator {
    private RequestItem item;
    private Money net;

    public StandardTaxCalculator(RequestItem item, Money net) {
        this.item = item;
        this.net = net;
    }

    @Override
    public InvoiceLine calculate() {
        BigDecimal ratio =  BigDecimal.valueOf(0.23);
        String desc = "23%";

        Money taxValue = net.multiplyBy(ratio);
        Tax tax = new Tax(taxValue, desc);

        return new InvoiceLine(item.getProductData(), item.getQuantity(), net, tax);
    }
}