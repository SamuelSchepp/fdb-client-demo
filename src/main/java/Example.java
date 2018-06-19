import com.apple.foundationdb.Database;
import com.apple.foundationdb.FDB;
import com.apple.foundationdb.tuple.Tuple;

public class Example {
    public static void main(String[] args) {
        FDB fdb = FDB.selectAPIVersion(510);

        try(Database db = fdb.open()) {

            db.run(transaction -> {
                transaction.set("my_key".getBytes(), "Hello, World!".getBytes());
                return null;
            });

            String value = db.run(transaction -> {
                byte[] result = transaction.get("my_key".getBytes()).join();
                return new String(result);
            });

            System.out.println(value);
        }
    }
}