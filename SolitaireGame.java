import java.util.*;

public class SolitaireGame {

    private Deck deck;
    private List<List<Card>> tableau;   // 7 piles
    private List<List<Card>> foundation; // 4 piles
    private List<Card> stock;
    private List<Card> waste;

    public SolitaireGame() {
        deck = new Deck();
        deck.shuffle();

        tableau = new ArrayList<>();
        foundation = new ArrayList<>();
        stock = new ArrayList<>();
        waste = new ArrayList<>();

        for (int i = 0; i < 7; i++) tableau.add(new ArrayList<>());
        for (int i = 0; i < 4; i++) foundation.add(new ArrayList<>());

        dealTableau();
        fillStock();
    }

    private void dealTableau() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <= i; j++) {
                Card card = deck.draw();
                if (j == i) card.flip(); // last card face up
                tableau.get(i).add(card);
            }
        }
    }

    private void fillStock() {
        while (!deck.isEmpty()) {
            stock.add(deck.draw());
        }
    }

    public void drawFromStock() {
        if (stock.isEmpty()) {
            // recycle waste
            Collections.reverse(waste);
            for (Card c : waste) c.flip();
            stock.addAll(waste);
            waste.clear();
            System.out.println("Recycled waste back into stock.");
            return;
        }

        Card card = stock.remove(stock.size() - 1);
        card.flip();
        waste.add(card);
        System.out.println("Drew: " + card);
    }

    public void printGameState() {
        System.out.println("\n=== TABLEAU ===");
        for (int i = 0; i < 7; i++) {
            System.out.print("Pile " + (i + 1) + ": ");
            for (Card c : tableau.get(i)) System.out.print(c + " ");
            System.out.println();
        }

        System.out.println("\n=== WASTE ===");
        System.out.println(waste.isEmpty() ? "(empty)" : waste.get(waste.size() - 1));

        System.out.println("\n=== STOCK ===");
        System.out.println(stock.size() + " cards remaining");

        System.out.println("\n=== FOUNDATION ===");
        for (int i = 0; i < 4; i++) {
            System.out.print("Foundation " + (i + 1) + ": ");
            System.out.println(foundation.get(i));
        }
    }

    public static void main(String[] args) {
        SolitaireGame game = new SolitaireGame();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            game.printGameState();
            System.out.println("\nCommands: draw, quit");
            System.out.print("> ");

            String cmd = scanner.nextLine().trim().toLowerCase();

            if (cmd.equals("draw")) {
                game.drawFromStock();
            } else if (cmd.equals("quit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }
    }
}
