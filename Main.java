import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

class Expense {
    int id;
    double amount;
    String category;

    Expense(int id, double amount, String category){
        this.id= id;
        this.amount= amount;
        this.category= category;
    }
}
public class Main{
        static void saveExpenses(ArrayList<Expense> expenses) {
            try {
                FileWriter writer = new FileWriter("expenses.txt");

                for (Expense expense : expenses) {
                    writer.write(
                        expense.id + "," +
                        expense.amount + "," +
                        expense.category + "\n"
                    );
                }

                writer.close();
                System.out.println("Expenses saved to file.");

            } catch (IOException e) {
                System.out.println("Error saving expenses.");
            }
        }
        
        static void loadExpenses(ArrayList<Expense> expenses) {
            try {
                BufferedReader reader =
                    new BufferedReader(new FileReader("expenses.txt"));

                String line;

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");

                    int id = Integer.parseInt(data[0]);
                    double amount = Double.parseDouble(data[1]);
                    String category = data[2];

                    Expense expense = new Expense(id, amount, category);
                    expenses.add(expense);
                }

                reader.close();

            } catch (IOException e) {
                System.out.println("No previous expense file found.");
            }
        }


     public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ArrayList<Expense> expenses = new ArrayList<>();
        loadExpenses(expenses);
        int nextId = 1;

        for (Expense expense : expenses) {
            if (expense.id >= nextId) {
                nextId = expense.id + 1;
            }
        }
        System.out.println("Expenses loaded: " + expenses.size());
        int choice;

        do {
            System.out.println("\n=== Personal Expense Manager ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Spending");
            System.out.println("4. Search by Category");
            System.out.println("5. Delete Expense");
            System.out.println("6. Exit");
            System.out.println("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter Amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine(); //consumes the leftover newline

                    System.out.println("Enter Category: ");
                    String category = sc.nextLine();

                    int id = nextId;
                    nextId++;

                    Expense e1 = new Expense(id, amount, category);
                    expenses.add(e1);
                    saveExpenses(expenses);

                    System.out.println("Expenses Added Successfully!");
                    break;

                case 2:
                    if(expenses.isEmpty()){
                        System.out.println("No Expenses Is Found..");
                    }
                    else{
                        for(Expense e : expenses){
                            System.out.println("ID: " + e.id);
                            System.out.println("AMOUNT: Rs" + e.amount);
                            System.out.println("CATEGORY: " + e.category);
                        }
                    }
                    break;

                case 3:
                    double total = 0;

                    for(Expense expense : expenses){
                        total = total + expense.amount;
                    }
                    System.out.println("Total Spending: Rs." + total);
                    break;

                case 4:
                    sc.nextLine(); // Consume leftover newline

                    System.out.print("Enter category to search: ");
                    String searchCategory = sc.nextLine();

                    boolean found = false;
                    double categoryTotal = 0;

                    for (Expense expense : expenses) {
                        if (expense.category.equalsIgnoreCase(searchCategory)) {
                            System.out.println("ID: " + expense.id);
                            System.out.println("Amount: Rs. " + expense.amount);
                            System.out.println("Category: " + expense.category);
                            categoryTotal += expense.amount;
                            found = true;
                        }
                    }

                    if (found) {
                        System.out.println("Total spending on " + searchCategory + ": Rs. " + categoryTotal);
                    } else {
                        System.out.println("No expenses found for this category.");
                    }
                    break;
                
                case 5:
                    System.out.print("Enter expense ID to delete: ");
                    int deleteId = sc.nextInt();

                    Iterator<Expense> iterator = expenses.iterator();
                    boolean deleted = false;

                    while (iterator.hasNext()) {
                        Expense expense = iterator.next();

                        if (expense.id == deleteId) {
                            iterator.remove();
                            deleted = true;
                            System.out.println("Expense deleted successfully!");
                            break;
                        }
                    }

                    if (!deleted) {
                        System.out.println("Expense ID not found.");
                    }
                    if (deleted) {
                        saveExpenses(expenses);
                    }
                    break;
                
                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        System.out.println("Goodbye!");
        sc.close();
    }
}