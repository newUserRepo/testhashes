package com.example.demo.validate;

import com.example.demo.MyUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class Validacion {


    public static String validateRegExp(final TextField txtValidar, final Button btn, final MyUI ui) {
        final StringBuilder sb = new StringBuilder();
        final String validar = txtValidar.getValue().trim();
        try {
            final ValidateRegExp result = ValidateRegExp.getRegExpValidate(validar);
            switch (result) {
                case REG_EXP_1:
                    Notification.show("Correcto");
                    sb.append(validar);
                    btn.setEnabled(true);
                    return sb.toString().replace("_","").replace(".","");
                case REG_EXP_2:
                    ui.access(() ->  {
                        //Notification.show("Correcto");
                        sb.append(validar);
                        btn.setEnabled(true);
                    });
                    return sb.toString();
            }
        }catch (Exception ex) {
            ui.access(() -> {
                txtValidar.focus();
                Notification.show("Error" + ex.getMessage() ,Notification.Type.ERROR_MESSAGE);
            });

        }
        return "";
    }
}
