package warehouse;

/*
 * Use this class to put it all together.
 */
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        // Use this file to test all methods
        Warehouse w = new Warehouse();
        int num = StdIn.readInt();
        for (int i = 0; i < num; i++) {
            String command = StdIn.readString();
            if (command.equals("delete")) {
                int deleteId = StdIn.readInt();
                w.deleteProduct(deleteId);
                System.out.println("delete " + w.getSectors()[2]);
            } else if (command.equals("purchase")) {
                int purchaseDay = StdIn.readInt();
                int purchaseID = StdIn.readInt();
                int purchaseQuant = StdIn.readInt();
                w.purchaseProduct(purchaseID, purchaseDay, purchaseQuant);
                System.out.println("purchase " + w.getSectors()[2]);

            } else if (command.equals("restock")) {
                int stockId = StdIn.readInt();
                int restock = StdIn.readInt();
                // System.out.println("here" + stockId + restock);
                w.restockProduct(stockId, restock);
                System.out.println("restock " + w.getSectors()[2]);

            } else {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();

                w.addProduct(id, name, stock, day, demand);
                System.out.println("add " + w.getSectors()[2]);

            }
        }
        StdOut.println(w); // sect 5 and 8 aren't correct
    }
}
