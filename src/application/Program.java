package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		Locale.setDefault(Locale.US);

		System.out.print("Entre o caminho do arquivo: ");
		String filePath = sc.next();

		List<Sale> salesList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line = br.readLine();

			while (line != null) {
				String[] fields = line.split(",");
				salesList.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			Set<String> sellers = new HashSet<>(
					salesList.stream().map(p -> p.getSeller()).collect(Collectors.toList()));

			System.out.println();

			System.out.println("Total de vendas por vendedor:");

			for (String seller : sellers) {

				Double totalPerSeller = salesList.stream().filter(p -> p.getSeller().equals(seller))
						.map(p -> p.getTotal()).reduce(0.0, (x, y) -> x + y);

				System.out.println(seller + " - " + "R$ " + String.format("%.2f", totalPerSeller));
			}

		} catch (IOException e) {
			System.out.println("Error: " + filePath + " (O sistema não pode encontrar o arquivo especificado)");
		}

		sc.close();

	}
}