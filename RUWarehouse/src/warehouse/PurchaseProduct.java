package warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        // Use this file to test purchaseProduct
        Warehouse w = new Warehouse();
        int num = StdIn.readInt();
        for (int i = 0; i < num; i++) {
            String command = StdIn.readString();
            if (command.equals("purchase")) {
                int purchaseDay = StdIn.readInt();
                int purchaseID = StdIn.readInt();
                int purchaseQuant = StdIn.readInt();
                w.purchaseProduct(purchaseID, purchaseDay, purchaseQuant);
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
