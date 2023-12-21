package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile("addtoend.in");
        StdOut.setFile("addtoend.out");

        // Use this file to test addProduct]
        Warehouse w = new Warehouse();
        int num = StdIn.readInt();
        for (int i = 0; i < num; i++) {
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();

            w.addProduct(id, name, stock, day, demand);
        }
        StdOut.println(w);
    }
    // javac -d bin src/warehouse/*.java
    // java -cp bin warehouse.AddProduct addproduct.in addproduct.out
}
