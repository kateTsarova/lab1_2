/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.invoicing;

import java.math.BigDecimal;
import java.util.List;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeper {

    public Invoice issuance(InvoiceRequest invoiceRequest) {
        Invoice invoice = Invoice.create(invoiceRequest.getClient());

        for (RequestItem item : invoiceRequest.getItems()) {
            Money net = item.getTotalCost();
            TaxCalculator taxCalculator;

            switch (item.getProductData().getType()) {
                case DRUG:
                    taxCalculator = new DrugTaxCalculator(item, net);
                    break;
                case FOOD:
                    taxCalculator = new FoodTaxCalculator(item, net);
                    break;
                case STANDARD:
                    taxCalculator = new StandardTaxCalculator(item, net);
                    break;

                default:
                    throw new IllegalArgumentException(item.getProductData().getType() + " not handled");
            }

            invoice.addItem(taxCalculator.calculate());
        }

        return invoice;
    }

}
