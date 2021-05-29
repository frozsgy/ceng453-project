package group10.client.controller;

import org.springframework.stereotype.Controller;

@Controller
public class ForgotController implements FormView{
    @Override
    public void setErrorMessage(String msg) {

    }

    @Override
    public void clearErrorMessage() {

    }

    @Override
    public boolean validateForm() {
        return false;
    }
}
