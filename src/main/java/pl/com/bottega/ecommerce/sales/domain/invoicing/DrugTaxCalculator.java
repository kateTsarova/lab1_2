package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.math.BigDecimal;

public class DrugTaxCalculator implements TaxCalculator {
    private RequestItem item;
    private Money net;

    public DrugTaxCalculator(RequestItem item, Money net) {
        this.item = item;
        this.net = net;
    }

    @Override
    public InvoiceLine calculate() {
        BigDecimal ratio =  BigDecimal.valueOf(0.05);
        String desc = "5% (D)";

        Money taxValue = net.multiplyBy(ratio);
        Tax tax = new Tax(taxValue, desc);

        return new InvoiceLine(item.getProductData(), item.getQuantity(), net, tax);
    }
}