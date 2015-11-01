import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kamal and Kyle Synden on 10/31/15.
 */
public class Squawk {


    public static void main(String[] args)
    {

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        int s = scan.nextInt();
        int t = scan.nextInt();

        //make nodes
        Node[] nodes = new Node[n];

        for(int i = 0; i< n; i++)
        {
            nodes[i] = new Node(m);
        }
        //specify first infected
        nodes[s].setFirstZombie();

        //connect nodes
        for(int i = 0; i<m; i++)
        {
            int n1 = scan.nextInt();
            int n2 = scan.nextInt();

            Node node1 = nodes[n1];
            Node node2 = nodes[n2];
            node1.addConnection(node2);
            node2.addConnection(node1);
        }

        scan.close();

        int time = 1;
        //run and compute
        while(time < t)
        {
            for(Node node: nodes)
            {
                if(node.isInfected())
                    node.ripple();
            }
            for(Node node: nodes)
            {
                node.updateInfected();
            }
            time++;
        }

        BigInteger count = BigInteger.valueOf(0);

        for(Node node: nodes)
        {
            if(node.isInfected())
                count = count.add(node.ripple());
        }

        System.out.println(count);
    }



    public static class Node {
        private ArrayList<Node> connected;
        private BigInteger lastSquawk;
        private BigInteger nextSquawk;
        boolean willInfect;
        boolean infected;

        public Node(int maxConn)
        {
            connected = new ArrayList<>(maxConn);
            lastSquawk = BigInteger.valueOf(0);
            nextSquawk = BigInteger.valueOf(0);
            willInfect = false;
            infected = false;
        }

        public void setFirstZombie()
        {
            infected = true;
            lastSquawk = BigInteger.valueOf(1);
        }

        public BigInteger ripple()
        {
            BigInteger count = BigInteger.valueOf(0);
            for(Node node: connected)
            {
                node.Squawk(lastSquawk);
                count = count.add(lastSquawk);
            }
            infected = false;
            lastSquawk = BigInteger.valueOf(0);
            return count;
        }

        public void Squawk(BigInteger i)
        {
            nextSquawk = nextSquawk.add(i);
            willInfect = true;
        }

        public void updateInfected()
        {
            infected = willInfect;
            willInfect = false;
            lastSquawk = nextSquawk;
            nextSquawk = BigInteger.valueOf(0);
        }

        public boolean isInfected()
        {
            return infected;
        }

        public void addConnection(Node n)
        {
            connected.add(n);
        }
    }
}
