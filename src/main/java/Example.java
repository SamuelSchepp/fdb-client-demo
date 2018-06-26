import com.apple.foundationdb.Database;
import com.apple.foundationdb.FDB;
import java.math.BigInteger;

public class Example {
    public static void main(String[] args) {
        FDB fdb = FDB.selectAPIVersion(510);

        try(Database db = fdb.open()) {
            db.run(transaction -> {
                transaction.set("konto_A".getBytes(), BigInteger.valueOf(150).toByteArray());
                transaction.set("konto_B".getBytes(), BigInteger.valueOf(100).toByteArray());
                return null;
            });

            db.run(transaction -> {
                BigInteger kontoA = new BigInteger(transaction.get("konto_A".getBytes()).join());
                BigInteger kontoB = new BigInteger(transaction.get("konto_B".getBytes()).join());
                kontoA = kontoA.subtract(BigInteger.valueOf(90));
                kontoB = kontoB.add(BigInteger.valueOf(90));
                transaction.set("konto_A".getBytes(), kontoA.toByteArray());
                transaction.set("konto_B".getBytes(), kontoB.toByteArray());
                return null;
            });

            db.run(transaction -> {
                BigInteger kontoA = new BigInteger(transaction.get("konto_A".getBytes()).join());
                BigInteger kontoB = new BigInteger(transaction.get("konto_B".getBytes()).join());
                System.out.println("kontoA: " + kontoA.toString());
                System.out.println("kontoB: " + kontoB.toString());
                return null;
            });
        }
    }
}

