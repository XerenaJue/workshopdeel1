package opdracht2;

public class FillDB {

	public static void main(String[] args) {
		ConnectionFactory.useMySQL();
		FillBatchDatabase.clearDatabase();
		FillBatchDatabase.fillBatchDatabase();

	}

}
