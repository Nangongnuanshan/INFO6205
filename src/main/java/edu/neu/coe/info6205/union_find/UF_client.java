package edu.neu.coe.info6205.union_find;
import java.util.Random;

public class UF_client {
    public static int count(int n) {
        UF_HWQUPC uf = new UF_HWQUPC(n);
        int connections = 0;
        Random random = new Random();

        while (uf.components() > 1) {
            int p = random.nextInt(n);
            int q = random.nextInt(n);

            if (!uf.isConnected(p, q)) {
                uf.union(p, q);
            }

            connections++;
        }

        return connections;
    }

    public static void main(String[] args) {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 128;
        int num = 30;
        for(int i = 0; i < 10; i++){
            int sum = 0;
            for(int j = 0; j < num; j++){
                int connections = count(n);
                sum += connections;
            }
            sum = sum / num;
            System.out.println("For " + n + " sites, " + sum + " connections were made to connect all sites.");
            n = n * 2;
        }

    }
}
