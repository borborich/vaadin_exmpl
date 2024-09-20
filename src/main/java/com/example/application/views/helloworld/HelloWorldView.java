package com.example.application.views.helloworld;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.page.PendingJavaScriptResult;
import com.example.application.CssUtil;

@PageTitle("Hello World")
@Route(value = "", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;
    private Button getLocation;
    private Button setCustomStyle;
    private TextField colorPicker;

    public HelloWorldView() {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        getLocation = new Button("Get Location");
        getLocation.addClickListener(e -> {
            Page page = getUI().get().getPage();
            PendingJavaScriptResult result = page.executeJs(
                    "if (navigator.geolocation) { " +
                            "  navigator.geolocation.getCurrentPosition(function(position) { " +
                            "    return position.coords.latitude + ',' + position.coords.longitude; " +
                            "  }); " +
                            "} else { " +
                            "  return 'Geolocation is not supported by this browser.'; " +
                            "}"
            );
            result.then(String.class, coords -> {
                Notification.show("Your location: " + coords);
            });
        });

        // place for set color
        colorPicker = new TextField("Bg Color (e.g., #ff0000 or 'lightblue')");
        colorPicker.setPlaceholder("Enter color");

        setCustomStyle = new Button("Set Custom Style");
        setCustomStyle.addClickListener(e -> {
            String selectedColor = colorPicker.getValue();
            if (selectedColor != null && !selectedColor.isEmpty()) {
                String customCss = "body { background-color: " + selectedColor + "; }";
                CssUtil.setCustomCss(customCss);
                Notification.show("Custom style set!");

                // Перезагрузка страницы после применения CSS
                getUI().get().getPage().reload();
            } else {
                Notification.show("Please enter a valid color.");
            }
        });

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello, getLocation, colorPicker, setCustomStyle);

        add(name, sayHello, getLocation, colorPicker, setCustomStyle);
    }
}
