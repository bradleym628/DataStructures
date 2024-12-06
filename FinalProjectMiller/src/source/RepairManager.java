package source;
import java.util.*;

// Class Repair Manager implements a queue for the cars, and a priority queue for the repair tasks for each car
public class RepairManager {
	private Queue<Car> carQueue;
	private PriorityQueue<RepairTask> urgentTasks;

	// Create a linked list queue, and a priority queue for tasks
	public RepairManager() {
		this.carQueue = new LinkedList<>();
		this.urgentTasks = new PriorityQueue<>();
	}

	// method adds a Car object to the queue of cars
	public void addCar(Car car) {
		carQueue.add(car);
	}

	// method gets the next car in the queue and removes it
	public Car getNextCar() {
		return carQueue.poll();
	}

	// Method adds a task to a specified car object
	public void addTaskToCar(Car car, RepairTask task) {
		car.addRepairTask(task);
	}

	// Method adds an urgent task to the priority queue
	public void addUrgentTask(RepairTask task) {
		urgentTasks.add(task);
	}

	// Method takes the next urgent task and removes it from the queue
	public RepairTask processUrgentTask() {
		return urgentTasks.poll();
	}

	// Sorts a list of repair tasks in ascending order of priority using insertion sort. 
	public static void insertionSort(LinkedList<RepairTask> tasks) {
		for (int i = 1; i < tasks.size(); i++ ) {
			RepairTask current = tasks.get(i);
			int j = i - 1;
			while (j >= 0 && tasks.get(j).compareTo(current) > 0) {
				tasks.set(j + 1, tasks.get(j));
				j--;
			}
			tasks.set(j + 1, current);
		}
	}
	
	// Returns a list of all the cars currently in the queue. 
	public List<Car> getCarsInQueue() {
		return new ArrayList<>(carQueue);
	}

	// Returns the list of tasks associated with a specific car.
	public List<RepairTask> getTasksForCar(Car car) {
		return car.getRepairTasks();
	}

	// Displays the list of cars currently in the queue.
	public void displayCarsInQueue() {
		for (Car car : carQueue) {
			System.out.println(car);
		}
	}

	// Displays all the tasks in the urgent tasks queue. 
	public void displayUrgentTasks() {
		PriorityQueue<RepairTask> tempQueue = new PriorityQueue<>(urgentTasks);
		while (!tempQueue.isEmpty()) {
			System.out.println(tempQueue.poll());
		}
	}

	// Replaces the current car queue with a sorted list of cars. 
	public void replaceCarsInQueue(LinkedList<Car> sortedCars) {
		// Clears the current queue. 
		carQueue.clear();
		// Adds the sorted list back into the queue
		carQueue.addAll(sortedCars); 
	}

	// Retrieves a car by its unique ID
	public Car getCarById(int carId) {
		for (Car car : carQueue) {
			if (car.getCarId() == carId) {
				return car;
			}
		}
		// Returns null if no car matches the ID. 
		return null;
	}

}
