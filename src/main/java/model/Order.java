package model;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * class performs execute orders functionality 
 */
public interface Order {

/**
 * implement the execution of particular order
 * @return true if execution is successful, false if it fails
 */
public boolean execute();

}
