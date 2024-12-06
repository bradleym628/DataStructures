package source;

public class RepairTask implements Comparable<RepairTask> {
	// Repair Task with unique taskID number
	private static int taskIdCounter = 1;
	private int taskId;
	private String description;
	private int priority;

	//Constructor, iterates through unique task ids
	public RepairTask(String description, int priority) {
		this.taskId = taskIdCounter++;
		this.description = description;
		setPriority(priority);
	}

	//Getters and setters
	public int getTaskId() {
		return taskId;
	}

	public String getDescription() {
		return description;
	}

	public int getPriority() {
		return priority;
	}

	//Repair tasks description. 
	public void setDescription(String description) {
		this.description = description;
	}

	// sets the priority, input validation, but unnecessary because input comes from GUI and GUI only has 1, 2, and 3 populated in drop-down menu. 
	public void setPriority(int priority) {
		if (priority < 1 || priority > 3) {
			throw new IllegalArgumentException("Priority must be 1 (High), 2 (Medium), or 3 (Low)");
		}
		this.priority = priority;
	}

	// Compares the priority of one task to another
	@Override
	public int compareTo(RepairTask other) {
		return Integer.compare(this.priority, other.priority);
	}

	// To String method for printing to the console. 
	@Override
	public String toString() {
		return "Task ID: " + taskId + ", Description: " + description + ", Priority: " + priority;
	}

}
