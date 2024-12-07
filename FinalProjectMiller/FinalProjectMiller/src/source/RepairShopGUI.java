package source;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

// Class implements the GUI for managing car repairs. 
public class RepairShopGUI {
	private JFrame frame;
	private JTextField ownerNameField;
	private JComboBox<String> vehicleDropdown;
	private JComboBox<String> taskDropdown;
	private JComboBox<Integer> priorityDropdown;
	private DefaultTableModel tableModel;
	private JTable table;
	private RepairManager repairManager;

	public RepairShopGUI() {
	    repairManager = new RepairManager();

	    // Set up the main application frame.
	    frame = new JFrame("Mechanic's Shop Repair System");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(800, 600);

	    // Create input panel for user inputs.
	    JPanel inputPanel = new JPanel();
	    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Set BoxLayout for vertical alignment

	    // Add input panel for user names.
	    inputPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a gap of 10 pixels
	    inputPanel.add(new JLabel("Owner Name:"));
	    ownerNameField = new JTextField();
	    inputPanel.add(ownerNameField);

	    // Dropdown so a user may select a previously entered vehicle.
	    inputPanel.add(new JLabel("Select Vehicle:"));
	    vehicleDropdown = new JComboBox<>();
	    vehicleDropdown.addItem("Add New Vehicle");
	    inputPanel.add(vehicleDropdown);

	    // Dropdown to select from a pre-prepared list of basic tasks, so user input does not need to be validated.
	    inputPanel.add(new JLabel("Repair Task:"));
	    taskDropdown = new JComboBox<>(new String[] { "Oil/oil filter changed", "Wiper blades replacement",
	            "Replace air filter", "Scheduled maintenance", "New tires", "Battery replacement", "Brake work",
	            "Antifreeze added", "Engine tune-up", "Wheels aligned/balanced" });
	    inputPanel.add(taskDropdown);

	    // Dropdown for selecting task priority.
	    inputPanel.add(new JLabel("Priority:"));
	    priorityDropdown = new JComboBox<>(new Integer[] { 1, 2, 3 });
	    priorityDropdown.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
	                boolean cellHasFocus) {
	            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            if (value instanceof Integer) {
	                int priority = (Integer) value;
	                String label = switch (priority) {
	                case 1 -> "1 - High";
	                case 2 -> "2 - Medium";
	                case 3 -> "3 - Low";
	                default -> "Unknown";
	                };
	                setText(label);
	            }
	            return c;
	        }
	    });
	    inputPanel.add(priorityDropdown);

	    // Buttons for adding and deleting tasks.
	    inputPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a gap of 10 pixels
	    JButton addButton = new JButton("Add Task");
	    inputPanel.add(addButton);

	    JButton deleteButton = new JButton("Delete Task");
	    inputPanel.add(deleteButton);

	    JButton prioritizeButton = new JButton("Display High Priority Tasks");
	    inputPanel.add(prioritizeButton);

	    JButton resetButton = new JButton("Reset Task Order");
	    inputPanel.add(resetButton);

	    // Create a table for displaying repair tasks.
	    String[] columnNames = { "Vehicle ID", "Owner Name", "Task", "Priority" };
	    tableModel = new DefaultTableModel(columnNames, 0);
	    table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);

	    // Set up the frame layout and add components.
	    frame.setLayout(new BorderLayout());
	    frame.add(inputPanel, BorderLayout.NORTH);
	    frame.add(scrollPane, BorderLayout.CENTER);

	    // Add action listeners for the buttons.
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            addTask();
	        }
	    });

	    deleteButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            deleteTask();
	        }
	    });

	    vehicleDropdown.addActionListener(e -> {
	        if ("Add New Vehicle".equals(vehicleDropdown.getSelectedItem())) {
	            ownerNameField.setEnabled(true);
	        } else {
	            ownerNameField.setEnabled(false);
	        }
	    });

	    prioritizeButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            prioritizeHighTasks();
	        }
	    });

	    resetButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            resetTaskOrder();
	        }
	    });

	    frame.setVisible(true);
	}


	// Adds a repair task to a vehicle.
	private void addTask() {
		String selectedVehicle = (String) vehicleDropdown.getSelectedItem();
		Car car;

		if ("Add New Vehicle".equals(selectedVehicle)) {
			String ownerName = ownerNameField.getText().trim();
			if (ownerName.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Owner name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			car = new Car(ownerName);
			repairManager.addCar(car);
			vehicleDropdown.addItem(car.getOwnerName() + " (ID: " + car.getCarId() + ")");
			vehicleDropdown.setSelectedItem(car.getOwnerName() + " (ID: " + car.getCarId() + ")");
		} else {
			int carId = extractCarId(selectedVehicle);
			car = repairManager.getCarById(carId);
		}

		String taskDescription = (String) taskDropdown.getSelectedItem();
		int priority = (Integer) priorityDropdown.getSelectedItem();
		RepairTask task = new RepairTask(taskDescription, priority);

		repairManager.addTaskToCar(car, task);
		sortTasksForCar(car);
		refreshTable();
		ownerNameField.setText("");

		// Reset the vehicle dropdown to the default option
		vehicleDropdown.setSelectedIndex(0);
	}

	// Deletes a selected repair task. 
	private void deleteTask() {
		int selectedRow = table.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(frame, "Please select a task to delete.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Get carId and task description from the selected row
		int carId = (int) tableModel.getValueAt(selectedRow, 0);
		String taskDescription = (String) tableModel.getValueAt(selectedRow, 2);

		// Iterate over the cars in the queue to find the matching car
		LinkedList<Car> updatedCars = new LinkedList<>();
		for (Car car : repairManager.getCarsInQueue()) {
			if (car.getCarId() == carId) {
				// Remove the task from the car's task list
				car.getRepairTasks().removeIf(task -> task.getDescription().equals(taskDescription));

				// If no tasks remain, exclude the car from the updated queue
				if (!car.getRepairTasks().isEmpty()) {
					updatedCars.add(car);
				}
			} else {
				updatedCars.add(car);
			}
		}

		// Update the queue in the RepairManager
		repairManager.replaceCarsInQueue(updatedCars);

		// Refresh the table
		refreshTable();
	}

	// Refreshes the task list table to keep data current
	private void refreshTable() {
		tableModel.setRowCount(0);

		for (Car car : repairManager.getCarsInQueue()) {
			for (RepairTask task : car.getRepairTasks()) {
				tableModel.addRow(new Object[] { car.getCarId(), car.getOwnerName(), task.getDescription(),
						switch (task.getPriority()) {
						case 1 -> "High";
						case 2 -> "Medium";
						case 3 -> "Low";
						default -> "Unknown";
						} });
			}
		}
	}
	
	private void prioritizeHighTasks() {
	    tableModel.setRowCount(0); // Clear the table

	    // Temporary list for priority 1 tasks
	    List<Object[]> priorityOneTasks = new ArrayList<>();
	    List<Object[]> otherTasks = new ArrayList<>();

	    for (Car car : repairManager.getCarsInQueue()) {
	        for (RepairTask task : car.getRepairTasks()) {
	            Object[] row = new Object[] {
	                car.getCarId(), car.getOwnerName(), task.getDescription(),
	                switch (task.getPriority()) {
	                    case 1 -> "High";
	                    case 2 -> "Medium";
	                    case 3 -> "Low";
	                    default -> "Unknown";
	                }
	            };

	            if (task.getPriority() == 1) {
	                priorityOneTasks.add(row);
	            } else {
	                otherTasks.add(row);
	            }
	        }
	    }

	    // Add priority 1 tasks first, then other tasks
	    for (Object[] row : priorityOneTasks) {
	        tableModel.addRow(row);
	    }
	    for (Object[] row : otherTasks) {
	        tableModel.addRow(row);
	    }
	}
	
	private void resetTaskOrder() {
	    refreshTable();
	}

	// Sorts the tasks for a specific car by priority. 
	private void sortTasksForCar(Car car) {
		LinkedList<RepairTask> tasks = car.getRepairTasks();
		RepairManager.insertionSort(tasks);
	}

	// Extracts the car ID from the dropdown. 
	private int extractCarId(String selectedVehicle) {
		return Integer
				.parseInt(selectedVehicle.substring(selectedVehicle.indexOf("ID: ") + 4, selectedVehicle.indexOf(")")));
	}

	// Main method to start the application
	public static void main(String[] args) {
		new RepairShopGUI();
	}
}