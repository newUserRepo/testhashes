package com.example.demo.layout.dbJdbc;

import com.example.demo.MyUI;
import com.example.demo.util.ShowData;
import com.example.demo.util.TypesFields;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = TypesFields.CUSTOMER)
public class CustomerUI extends VerticalLayout implements View {

    private MyUI ui;

    private Binder<Customer> binder = new Binder<>(Customer.class);
    private Customer customer;
    private Grid<Customer> grid = new Grid<>();
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private Button save = new Button("Save", e -> saveCustomer());
    private Button showData = new Button("Show data ", e -> initData());
    private VerticalLayout v = new VerticalLayout();

    @Autowired
    private CustomerService service;

    @PostConstruct
    public void init() {
        grid.addColumn(Customer::getFirstName).setCaption("First Name");
        grid.addColumn(Customer::getLastName).setCaption("Last Name");
        grid.addSelectionListener(e -> updateForm());

        binder.bindInstanceFields(this);
        v.addComponents(firstName,lastName,save);
        setFormVisible(false);

        final HorizontalLayout row  = new HorizontalLayout(grid, new VerticalLayout(showData,v));
        addComponents( row );
    }

    public void setParams(final MyUI ui) { this.ui = ui;}

    private void initData() {
        updateGrid();
    }

    private void updateForm() {
        if (grid.asSingleSelect().isEmpty()) {
            v.setVisible(false);
        } else {
            //customer = grid.asSingleSelect().getValue();
            long id = 0;
            customer = new Customer(++id, firstName.getValue(),lastName.getValue());
            binder.setBean(customer);
            v.setVisible(true);
        }
    }

    private void updateGrid() {
        List<Customer> customers = service.findAll();
        grid.setItems(customers);
        v.setVisible(false);
    }

    private void setFormVisible(boolean visible) {
        v.setVisible(visible);
    }

    private void saveCustomer() {
        service.update(customer);
        updateGrid();
    }
}
