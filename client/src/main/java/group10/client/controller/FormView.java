package group10.client.controller;


public interface FormView {

    void setErrorMessage(String msg);

    void clearErrorMessage();

    boolean validateForm();

}