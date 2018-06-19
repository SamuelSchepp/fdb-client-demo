import com.apple.foundationdb.Database;
import com.apple.foundationdb.FDB;
import com.apple.foundationdb.tuple.Tuple;

public class Example {
    public static void main(String[] args) {
        FDB fdb = FDB.selectAPIVersion(510);

        try(Database db = fdb.open()) {

            db.run(transaction -> {
                transaction.set(Tuple.from("my_key").pack(), Tuple.from("Hello, World!").pack());
                return null;
            });

            String value = db.run(transaction -> {
                byte[] result = transaction.get(Tuple.from("my_key").pack()).join();
                return Tuple.fromBytes(result).getString(0);
            });

            System.out.println(value);
        }
    }
}