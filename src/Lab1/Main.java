package Lab1;

public class Main {
    public static void main(String[] args){
        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        n = n * 3;
        n = n + 0b10101;
        n = n + 0xFF;
        n = n * 6;
        int result = n;
        while(result > 9){
            int sum = 0;
            while(result > 0){
                sum = sum + result % 10;
                result = result / 10;
            }
            result = sum;
        }
        System.out.println("Willy-nilly, this semester I will learn " + languages[result]);
    }
}
