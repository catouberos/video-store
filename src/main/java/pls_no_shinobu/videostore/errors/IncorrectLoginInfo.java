/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.errors;

public class IncorrectLoginInfo extends Exception {
    public IncorrectLoginInfo() {
        super();
    }

    public IncorrectLoginInfo(String message) {
        super(message);
    }
}
