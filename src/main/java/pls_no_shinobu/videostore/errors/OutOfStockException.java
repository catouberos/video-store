/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.errors;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
