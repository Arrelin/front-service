package com.arrelin.front.views.shop;

import com.arrelin.front.entity.Product;
import com.arrelin.front.service.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route("shop")
public class ShopView extends VerticalLayout {

    private final ProductService productService;
    private VerticalLayout shopList = new VerticalLayout();
    private TextField nameField = new TextField("Name");
    private TextField descriptionField = new TextField("Description");
    private TextField priceField = new TextField("Price");
    private List<Checkbox> checkboxes = new ArrayList<>();

    @Autowired
    public ShopView(ProductService productService) {
        this.productService = productService;
        try {
            add("Products");
            add(shopList);
            showAllProducts();
            add("Create product");
            addCreateProductForm();
            addDeleteButton();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
        }
    }

    private void showAllProducts() {
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            shopList.add(createProductLayout(product));
        }
    }

    private HorizontalLayout createProductLayout(Product product) {
        HorizontalLayout productLayout = new HorizontalLayout();
        Checkbox checkbox = new Checkbox();
        checkboxes.add(checkbox);
        TextField nameField = new TextField("Name", product.getName());
        TextField descriptionField = new TextField("Description", product.getDescription());
        TextField priceField = new TextField("Price", String.valueOf(product.getPrice()));

        productLayout.add(checkbox, nameField, descriptionField, priceField);
        return productLayout;
    }

    private void addCreateProductForm() {
        FormLayout formLayout = new FormLayout();
        Button createButton = new Button("Create Product", event -> createProduct());

        formLayout.add(nameField, descriptionField, priceField, createButton);
        add(formLayout);
    }

    private void createProduct() {
        String name = nameField.getValue();
        String description = descriptionField.getValue();
        double price = Double.parseDouble(priceField.getValue());

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setPrice(price);

        productService.saveProduct(newProduct);
        shopList.add(createProductLayout(newProduct));
    }

    private void addDeleteButton() {
        Button deleteButton = new Button("Delete Selected Products", event -> deleteSelectedProducts());
        add(deleteButton);
    }

    private void deleteSelectedProducts() {
        List<Product> products = productService.getAllProducts();
        for (int i = 0; i < checkboxes.size(); i++) {
            if (checkboxes.get(i).getValue()) {
                productService.deleteProduct(products.get(i).getId());
                shopList.remove(shopList.getComponentAt(i));
            }
        }
        checkboxes.removeIf(Checkbox::getValue);
    }
}