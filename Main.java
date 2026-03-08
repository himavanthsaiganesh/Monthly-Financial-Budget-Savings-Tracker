import java.util.*;

class Expense {

    String category;
    double amount;
    String payment;

    Expense(String c,double a,String p){
        category=c;
        amount=a;
        payment=p;
    }
}

class Node {

    Expense data;
    Node next;

    Node(Expense e){
        data=e;
        next=null;
    }
}

class MonthData {

    Node head;
    double income;
    double goal;

    MonthData(){
        head=null;
        income=0;
        goal=0;
    }
}

class BudgetTracker {

    HashMap<String,MonthData> months = new HashMap<>();

    Stack<Expense> history = new Stack<>();
    Queue<Expense> paymentQueue = new LinkedList<>();
    PriorityQueue<Expense> maxExpense =
            new PriorityQueue<>((a,b)->Double.compare(b.amount,a.amount));

    MonthData getMonth(String month){

        months.putIfAbsent(month,new MonthData());
        return months.get(month);
    }

    void setIncome(String month,double income){

        MonthData m = getMonth(month);
        m.income = income;

        System.out.println("Income saved for "+month);
    }

    void setGoal(String month,double goal){

        MonthData m = getMonth(month);
        m.goal = goal;

        System.out.println("Saving goal set for "+month);
    }

    void addExpense(String month,String category,double amount,String payment){

        MonthData m = getMonth(month);

        Expense e = new Expense(category,amount,payment);

        Node newNode = new Node(e);

        if(m.head==null)
            m.head=newNode;
        else{

            Node temp=m.head;

            while(temp.next!=null)
                temp=temp.next;

            temp.next=newNode;
        }

        history.push(e);
        paymentQueue.add(e);
        maxExpense.add(e);

        System.out.println("Expense added successfully");
    }

    void displayExpenses(String month){

        MonthData m = months.get(month);

        if(m==null || m.head==null){

            System.out.println("No expenses in this month");
            return;
        }

        Node temp = m.head;

        System.out.println("\nExpenses for "+month);

        while(temp!=null){

            System.out.println(
                    temp.data.category+
                    " | ₹"+temp.data.amount+
                    " | "+temp.data.payment
            );

            temp=temp.next;
        }
    }

    void monthlySummary(String month){

        MonthData m = months.get(month);

        if(m==null){

            System.out.println("No data found");
            return;
        }

        Node temp=m.head;

        double total=0;

        while(temp!=null){

            total+=temp.data.amount;
            temp=temp.next;
        }

        double savings = m.income-total;

        System.out.println("\n---- "+month+" Summary ----");

        System.out.println("Income : "+m.income);
        System.out.println("Expenses : "+total);
        System.out.println("Savings : "+savings);
        System.out.println("Goal : "+m.goal);
        System.out.println("Goal Remaining : "+(m.goal-savings));
    }

    void searchExpense(String month,String category){

        MonthData m = months.get(month);

        if(m==null){

            System.out.println("No data");
            return;
        }

        Node temp=m.head;

        while(temp!=null){

            if(temp.data.category.equalsIgnoreCase(category)){

                System.out.println(
                        "Found -> "+
                        temp.data.category+
                        " ₹"+temp.data.amount
                );

                return;
            }

            temp=temp.next;
        }

        System.out.println("Expense not found");
    }

    void bubbleSort(String month){

        MonthData m = months.get(month);

        if(m==null || m.head==null) return;

        for(Node i=m.head;i!=null;i=i.next){

            for(Node j=i.next;j!=null;j=j.next){

                if(i.data.amount > j.data.amount){

                    Expense temp=i.data;
                    i.data=j.data;
                    j.data=temp;
                }
            }
        }

        System.out.println("Expenses sorted by amount");
    }

    void showHistory(){

        System.out.println("\nRecent Expenses (Stack)");

        for(Expense e:history){

            System.out.println(
                    e.category+" ₹"+e.amount
            );
        }
    }

    void processPayments(){

        System.out.println("\nProcessing Payments (Queue)");

        while(!paymentQueue.isEmpty()){

            Expense e = paymentQueue.poll();

            System.out.println(
                    "Paid "+e.category+" ₹"+e.amount
            );
        }
    }

    void highestExpense(){

        if(maxExpense.isEmpty()){

            System.out.println("No expenses recorded");
            return;
        }

        Expense e = maxExpense.peek();

        System.out.println(
                "Highest Expense -> "+
                e.category+" ₹"+e.amount
        );
    }
}

public class Main {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        BudgetTracker bt = new BudgetTracker();

        String month;

        int choice;

        do{

            System.out.println("\n===== MONTHLY BUDGET TRACKER =====");

            System.out.println("1 Set Income");
            System.out.println("2 Set Saving Goal");
            System.out.println("3 Add Expense");
            System.out.println("4 View Expenses");
            System.out.println("5 Monthly Summary");
            System.out.println("6 Search Expense");
            System.out.println("7 Sort Expenses");
            System.out.println("8 Recent Expense History");
            System.out.println("9 Process Payments");
            System.out.println("10 Highest Expense");
            System.out.println("11 Exit");

            System.out.print("Enter choice: ");

            choice=sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:

                    System.out.print("Enter Month: ");
                    month=sc.nextLine();

                    System.out.print("Enter Income: ");
                    bt.setIncome(month,sc.nextDouble());

                    break;

                case 2:

                    System.out.print("Enter Month: ");
                    month=sc.nextLine();

                    System.out.print("Enter Goal: ");
                    bt.setGoal(month,sc.nextDouble());

                    break;

                case 3:

                    System.out.print("Enter Month: ");
                    month=sc.nextLine();

                    System.out.print("Category: ");
                    String c=sc.nextLine();

                    System.out.print("Amount: ");
                    double a=sc.nextDouble();

                    sc.nextLine();

                    System.out.print("Payment (UPI/Cash/Card): ");
                    String p=sc.nextLine();

                    bt.addExpense(month,c,a,p);

                    break;

                case 4:

                    System.out.print("Enter Month: ");
                    bt.displayExpenses(sc.nextLine());

                    break;

                case 5:

                    System.out.print("Enter Month: ");
                    bt.monthlySummary(sc.nextLine());

                    break;

                case 6:

                    System.out.print("Enter Month: ");
                    month=sc.nextLine();

                    System.out.print("Category: ");
                    bt.searchExpense(month,sc.nextLine());

                    break;

                case 7:

                    System.out.print("Enter Month: ");
                    bt.bubbleSort(sc.nextLine());

                    break;

                case 8:

                    bt.showHistory();
                    break;

                case 9:

                    bt.processPayments();
                    break;

                case 10:

                    bt.highestExpense();
                    break;

            }

        }while(choice!=11);

    }
}