package group10.client.controller;

/**
 * Interface for controllers that have form submissions.
 * Controllers that have Form View must implement this interface.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public interface FormView {

    /**
     * Sets the error message field of the form to given message
     *
     * @param msg Message to be set
     */
    void setErrorMessage(String msg);

    /**
     * Sets the success message field of the form to given message
     *
     * @param msg Message to be set
     */
    void setSuccessMessage(String msg);

    /**
     * Clears the error message field by setting it to empty string.
     */
    void clearErrorMessage();

    /**
     * Validates the form
     *
     * @return True if form is valid and ready to submit. False otherwise.
     */
    boolean validateForm();

}
