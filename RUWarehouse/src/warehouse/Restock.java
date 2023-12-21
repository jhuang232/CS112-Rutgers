package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        // Uset his file to test restock
        Warehouse w = new Warehouse();
        int num = StdIn.readInt();
        for (int i = 0; i < num; i++) {
            String command = StdIn.readString();
            if (command.equals("restock")) {
                int stockId = StdIn.readInt();
                int restock = StdIn.readInt();
                // System.out.println("here" + stockId + restock);
                w.restockProduct(stockId, restock);
            } else {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();

                w.addProduct(id, name, stock, day, demand);
            }
        }
        StdOut.println(w);
    }
}
