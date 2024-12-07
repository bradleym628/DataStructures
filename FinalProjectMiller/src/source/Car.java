package source;
import java.util.LinkedList;


// A Car class that represents an object 'Car' to be used 
public class Car {
	//Car starts with an ID number starting with 1, so that each new car object has a unique ID number
	private static int idCounter = 1;
	private int carId;
	//Owner name to additionally identify a Car.
	private String ownerName;
	//Linked list object that holds a list of repair tasks for each car. 
	private LinkedList<RepairTask> repairTasks;

	//Car Constructor, iterates through unique id numbers
	public Car(String ownerName) {
		this.carId = idCounter++;
		this.ownerName = ownerName;
		this.repairTasks = new LinkedList<>();
	}

	//getters and setter
	public int getCarId() {
		return carId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public LinkedList<RepairTask> getRepairTasks() {
		return repairTasks;
	}

	public void addRepairTask(RepairTask task) {
		repairTasks.add(task);
	}

	public boolean removeRepairTask(RepairTask task) {
		return repairTasks.remove(task);
	}

	// To String method for printing to console. 
	@Override
	public String toString() {
		return "Car ID: " + carId + ", Owner: " + ownerName + ", Tasks: " + repairTasks.size();
	}
}